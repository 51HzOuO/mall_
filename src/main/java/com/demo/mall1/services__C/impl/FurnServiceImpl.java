package com.demo.mall1.services__C.impl;

import com.demo.mall1.beans.Furn;
import com.demo.mall1.services__C.FurnService;
import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

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
//            GetQueryRunner.getQueryRunner().execute("insert into furniture (img_path, name, merchant, price, sales, total) VALUES (?,?,?,?,?,?)", furn.getPath(), furn.getName(), furn.getMerchant(), furn.getPrice(), furn.getSales(), furn.getTotal());
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
            return GetQueryRunner.getQueryRunner().query("select * from furniture where id = ?", new BeanHandler<>(Furn.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
