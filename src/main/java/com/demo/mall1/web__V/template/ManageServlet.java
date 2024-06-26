package com.demo.mall1.web__V.template;

import com.demo.mall1.beans.Furn;
import com.demo.mall1.beans.Page;
import com.demo.mall1.services__C.FurnService;
import com.demo.mall1.services__C.impl.FurnServiceImpl;
import com.demo.mall1.utils.GetQueryRunner;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@MultipartConfig
@WebServlet(urlPatterns = "/manage")
public class ManageServlet extends BasicServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final FurnService furnService = new FurnServiceImpl();

    public void listFurn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int pageNo = Integer.parseInt(req.getParameter("page"));
        int pageSize = 5;
        req.getSession().setAttribute("page", furnService.queryFurnByPage(pageNo, pageSize));
        resp.sendRedirect("views/manage/furn_manage.jsp");
    }

    public void addFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String maker = req.getParameter("maker");
        String sales = req.getParameter("sales");
        String stock = req.getParameter("stock");

        Collection<Part> parts = req.getParts();
        String path = null;
        for (Part part : parts) {
            if (part.getSubmittedFileName() == null) {
                continue;
            }
            if (part.getSubmittedFileName().startsWith(name)) {
                String temp = "assets/images/product-image/";
                String prePath = req.getServletContext().getRealPath("/assets/images/product-image/");
                while (new File(prePath + part.getSubmittedFileName()).exists()) {
                    prePath = prePath + "1";
                    temp = temp + "1";
                }
                path = temp + part.getSubmittedFileName();
                part.write(prePath + part.getSubmittedFileName());
                break;
            }
        }
        //移除sales 与 stock的小数
        sales = sales.split("\\.")[0];
        stock = stock.split("\\.")[0];
        Furn furn = new Furn(path, name, maker, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock));
        furnService.addFurn(furn);
        resp.getWriter().write("添加成功");
    }

    //跳转到update 页面并 根据id 填充数据
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.getSession().removeAttribute("furn");
            resp.sendRedirect("views/manage/furn_update.jsp");
            return;
        }
        Furn furn = furnService.queryFurnById(id);
        req.getSession().setAttribute("furn", furn);
    }

    public void updateFurn(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String maker = req.getParameter("maker");
        String sales = req.getParameter("sales");
        String stock = req.getParameter("stock");

        // Parse the sales and stock to remove decimals
        sales = sales.split("\\.")[0];
        stock = stock.split("\\.")[0];

        Collection<Part> parts = req.getParts();

        Furn existingFurn = furnService.queryFurnById(id);
        if (existingFurn == null) {
            resp.getWriter().write("商品不存在");
            return;
        }

        String existingPath = existingFurn.getPath(); // Get existing file path
        File fileToDelete = new File(req.getServletContext().getRealPath(existingPath));

        String newPath = null;
        Part part = req.getPart("imagefile");

        if (part != null) {

            String temp = "assets/images/product-image/";
            String prePath = req.getServletContext().getRealPath("/assets/images/product-image/");
            while (new File(prePath + part.getSubmittedFileName()).exists()) {
                prePath += "1"; // Add "1" to the path
                temp += "1";    // and temp to make a new unique path
            }
            newPath = temp + part.getSubmittedFileName();
            part.write(prePath + part.getSubmittedFileName());
        }

        // Delete the old file if a new file has been uploaded
        if (newPath != null && fileToDelete.exists()) {
            fileToDelete.delete();
        }

        // If no new image is uploaded, retain the old image
        if (newPath == null) {
            newPath = existingPath;
        }

        req.getSession().removeAttribute("furn");

        Furn updatedFurn = new Furn(Integer.parseInt(id), newPath, name, maker, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock));
        furnService.updateFurn(updatedFurn);
        GetQueryRunner.closeConnection();

        req.getSession().setAttribute("page", furnService.queryFurnByPage(((Page<Furn>) req.getSession().getAttribute("page")).getPageNo(), 5));
        resp.getWriter().write("修改成功");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (furnService.deleteFurn(req.getParameter("id"))) {
            req.getSession().setAttribute("page", furnService.queryFurnByPage(((Page<Furn>) req.getSession().getAttribute("page")).getPageNo(), 5));
            resp.getWriter().write("删除成功");
        } else {
            resp.getWriter().write("删除失败");
        }
    }
}
