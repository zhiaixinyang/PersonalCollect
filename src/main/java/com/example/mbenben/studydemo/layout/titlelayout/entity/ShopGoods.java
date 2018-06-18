package com.example.mbenben.studydemo.layout.titlelayout.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GongWenBo on 2018/5/26.
 *
 * 原项目GItHub:https://github.com/GongWnbo/SuperRecycleView
 */

public class ShopGoods implements Serializable {

    private ArrayList<ShopGoodsBean> mList = new ArrayList<>();

    public ShopGoods(ArrayList<ShopGoodsBean> list) {
        mList = list;
    }

    public ArrayList<ShopGoodsBean> getList() {
        return mList;
    }

    public void setList(ArrayList<ShopGoodsBean> list) {
        mList = list;
    }
}
