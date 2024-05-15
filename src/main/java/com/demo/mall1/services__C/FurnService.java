package com.demo.mall1.services__C;

import com.demo.mall1.beans.Furn;
import com.demo.mall1.beans.Page;

import java.util.List;
import java.util.Map;

public interface FurnService {
    List<Furn> queryAllFurn();

    void addFurn(Furn furn);

    boolean updateFurn(Furn furn);

    Furn queryFurnById(String id);

    boolean deleteFurn(String id);

    Page<Furn> queryFurnByPage(int pageNo, int pageSize);

    Page<Furn> queryFurnByPage(int pageNo, int pageSize, String key);

    boolean addFurnToCart(String id, Map<Furn, Integer> map);
}
