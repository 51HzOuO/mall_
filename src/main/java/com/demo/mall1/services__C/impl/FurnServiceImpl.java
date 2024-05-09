package com.demo.mall1.services__C.impl;

import com.demo.mall1.beans.Furn;
import com.demo.mall1.beans.Page;
import com.demo.mall1.services__C.FurnService;
import com.demo.mall1.utils.GetQueryRunner;
import com.demo.mall1.web__V.listener.ServletInitListener;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class FurnServiceImpl implements FurnService {
    @Override
    public List<Furn> queryAllFurn() {
        try {
            return GetQueryRunner.getQueryRunner().query("select id , img_path path , name , merchant , price , sales , total from furniture", new BeanListHandler<>(Furn.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFurn(Furn furn) {
        try {
            GetQueryRunner.getQueryRunner().execute("insert into furniture values(null,?,?,?,?,?,?)", furn.getPath(), furn.getName(), furn.getMerchant(), furn.getPrice(), furn.getSales(), furn.getTotal());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean updateFurn(Furn furn) {
        try {
            return GetQueryRunner.getQueryRunner().update("update furniture set img_path = ?, name = ?, merchant = ?, price = ?, sales = ?, total = ? where id = ?", furn.getPath(), furn.getName(), furn.getMerchant(), furn.getPrice(), furn.getSales(), furn.getTotal(), furn.getId()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Furn queryFurnById(int id) {
        try {
            return GetQueryRunner.getQueryRunner().query("select id , img_path path , name , merchant , price , sales , total from furniture where id = ?", new BeanHandler<>(Furn.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteFurn(String id) {
        try {
            Furn furn = queryFurnById(Integer.parseInt(id));
            String path = furn.getPath();
            new File(ServletInitListener.contextPath + path).delete();
            return GetQueryRunner.getQueryRunner().update("delete from furniture where id = ?", id) == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Furn> queryFurnByPage(int pageNo, int pageSize) {
        Page<Furn> furnPage = new Page<>();
        furnPage.setPageNo(pageNo);
        furnPage.setPageSize(pageSize);
        try {
            furnPage.setTotalRow((int) (long) GetQueryRunner.getQueryRunner().query("select count(*) from furniture", new ScalarHandler<>()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        furnPage.setMaxPage((int) Math.ceil((double) furnPage.getTotalRow() / pageSize));
        try {
            furnPage.setItems(GetQueryRunner.getQueryRunner().query("select id , img_path path , name , merchant , price , sales , total from furniture limit ?,?", new BeanListHandler<>(Furn.class), (pageNo - 1) * pageSize, pageSize));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return furnPage;
    }
}
