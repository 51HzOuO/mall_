package com.demo.mall1.services__C;

import com.demo.mall1.beans.Furn;

import java.util.List;

public interface FurnService {
    List<Furn> queryAllFurn();

    void addFurn(Furn furn);

    boolean updateFurn(Furn furn);

    Furn queryFurnById(int id);
}
