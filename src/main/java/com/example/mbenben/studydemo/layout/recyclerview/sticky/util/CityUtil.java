package com.example.mbenben.studydemo.layout.recyclerview.sticky.util;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavin
 * Created date 17/5/31
 * Created log
 */

public class CityUtil {
    /**
     * 获取城市名
     *
     * @return
     */
    public static List<City> getCityList() {
        List<City> dataList = new ArrayList<>();
        final String SHAN_DONG = "山东省";
        final int SHAN_DONG_ICON = R.drawable.city1;
        dataList.add(new City("济南", SHAN_DONG, SHAN_DONG_ICON));
        dataList.add(new City("枣庄", SHAN_DONG, SHAN_DONG_ICON));
        dataList.add(new City("青岛", SHAN_DONG, SHAN_DONG_ICON));
        dataList.add(new City("济宁", SHAN_DONG, SHAN_DONG_ICON));
        dataList.add(new City("淄博", SHAN_DONG, SHAN_DONG_ICON));
        dataList.add(new City("德州", SHAN_DONG, SHAN_DONG_ICON));
        final String FU_JIAN = "福建省";
        final int FU_JIAN_ICON = R.drawable.city1;
        dataList.add(new City("福州", FU_JIAN, FU_JIAN_ICON));
        dataList.add(new City("厦门", FU_JIAN, FU_JIAN_ICON));
        dataList.add(new City("泉州", FU_JIAN, FU_JIAN_ICON));
        dataList.add(new City("宁德", FU_JIAN, FU_JIAN_ICON));
        dataList.add(new City("漳州", FU_JIAN, FU_JIAN_ICON));
        final String AN_HUI = "安徽省";
        final int AN_HUI_ICON = R.drawable.city2;
        dataList.add(new City("合肥", AN_HUI, AN_HUI_ICON));
        dataList.add(new City("芜湖", AN_HUI, AN_HUI_ICON));
        dataList.add(new City("蚌埠", AN_HUI, AN_HUI_ICON));
        final String ZHE_JIANG = "浙江省";
        final int ZHE_JIANG_ICON = R.drawable.city3;
        dataList.add(new City("杭州", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("宁波", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("温州", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("嘉兴", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("绍兴", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("金华", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("湖州", ZHE_JIANG, ZHE_JIANG_ICON));
        dataList.add(new City("舟山", ZHE_JIANG, ZHE_JIANG_ICON));
        final String JIANG_SU = "江苏省";
        final int JIANG_SU_ICOM = R.drawable.city4;
        dataList.add(new City("南京", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("苏州", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("徐州", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("南通", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("无锡", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("盐城", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("淮安", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("泰州", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("常州", JIANG_SU, JIANG_SU_ICOM));
        dataList.add(new City("连云港", JIANG_SU, JIANG_SU_ICOM));
        return dataList;
    }
}
