package com.demo.mall1.web__V.template;

import com.demo.mall1.services__C.FurnService;
import com.demo.mall1.services__C.impl.FurnServiceImpl;
import com.demo.mall1.web__V.BasicServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends BasicServlet {
    public static final ThreadLocal<Object> returnInfo = new ThreadLocal<>();
    private final FurnService furnService = new FurnServiceImpl();

    public void listFurn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int pageNo = Integer.parseInt(req.getParameter("page"));
        int pageSize = 4;
        String searchKey = (String) req.getSession().getAttribute("searchKey");
        // 首次访问时 searchKey 为 null
        if (searchKey == null) {
            searchKey = "";
        }
        req.getSession().setAttribute("page", furnService.queryFurnByPage(pageNo, pageSize, searchKey));
        resp.sendRedirect(req.getContextPath());
    }

    public void searchFurn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = req.getParameter("key");
        req.getSession().setAttribute("searchKey", key);
    }
}
