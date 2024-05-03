package com.demo.mall1.web__V.template;

import com.demo.mall1.beans.Furn;
import com.demo.mall1.services__C.FurnService;
import com.demo.mall1.services__C.impl.FurnServiceImpl;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@MultipartConfig
@WebServlet(urlPatterns = "/manage")
public class ManageServlet extends BasicServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final FurnService furnService = new FurnServiceImpl();

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
                path = "assets/images/product-image/" + part.getSubmittedFileName();
                part.write(req.getServletContext().getRealPath("/assets/images/product-image/" + part.getSubmittedFileName()));

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

    public void updateFurn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String maker = req.getParameter("maker");
        String sales = req.getParameter("sales");
        String stock = req.getParameter("stock");

        Collection<Part> parts = null;
        try {
            parts = req.getParts();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        if (furnService.queryFurnById(Integer.parseInt(id)) == null) {
            resp.getWriter().write("商品不存在");
            return;
        }


        String path = null;
        for (Part part : parts) {
            if (part.getSubmittedFileName() == null) {
                continue;
            }
            if (part.getSubmittedFileName().startsWith(name)) {
                path = "assets/images/product-image/" + part.getSubmittedFileName();
                try {
                    part.write(req.getServletContext().getRealPath("/assets/images/product-image/" + part.getSubmittedFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        Furn furn = new Furn(Integer.parseInt(id), path, name, maker, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock));
        resp.getWriter().write(furnService.updateFurn(furn) ? "修改成功" : "修改失败");
    }
}
