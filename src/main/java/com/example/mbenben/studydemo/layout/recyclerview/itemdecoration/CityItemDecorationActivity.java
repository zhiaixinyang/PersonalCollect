package com.example.mbenben.studydemo.layout.recyclerview.itemdecoration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.layout.recyclerview.itemdecoration.model.CityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2018/1/1.
 */

public class CityItemDecorationActivity extends AppCompatActivity {
    private RecyclerView rlv;
    private List<CityModel> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_decoration);
        rlv = (RecyclerView) findViewById(R.id.rlv);
        mData = new ArrayList<>();

        CityModel jN = new CityModel();
        jN.cityName = "济南";
        jN.provinceName = "山东省";
        jN.isFirst = true;
        mData.add(jN);

        CityModel zZ = new CityModel();
        zZ.cityName = "枣庄";
        zZ.provinceName = "山东省";
        zZ.isFirst = false;
        mData.add(zZ);

        CityModel qD = new CityModel();
        qD.cityName = "青岛";
        qD.provinceName = "山东省";
        qD.isFirst = false;
        mData.add(qD);

        CityModel zZ2 = new CityModel();
        zZ2.cityName = "枣庄";
        zZ2.provinceName = "山东省";
        zZ2.isFirst = false;
        mData.add(zZ2);

        CityModel qD2 = new CityModel();
        qD2.cityName = "青岛";
        qD2.provinceName = "山东省";
        qD2.isFirst = false;
        mData.add(qD2);

        CityModel jN2 = new CityModel();
        jN2.cityName = "济南";
        jN2.provinceName = "山东省";
        jN2.isFirst = false;
        mData.add(jN2);

        CityModel zZ3 = new CityModel();
        zZ3.cityName = "枣庄";
        zZ3.provinceName = "山东省";
        zZ3.isFirst = false;
        mData.add(zZ3);

        CityModel qD3 = new CityModel();
        qD3.cityName = "青岛";
        qD3.provinceName = "山东省";
        qD3.isFirst = false;
        mData.add(qD3);

        CityModel jN3 = new CityModel();
        jN3.cityName = "济南";
        jN3.provinceName = "山东省";
        jN3.isFirst = false;
        mData.add(jN3);

        CityModel tY = new CityModel();
        tY.cityName = "太原";
        tY.provinceName = "山西省";
        tY.isFirst = true;
        mData.add(tY);

        CityModel dT = new CityModel();
        dT.cityName = "大同";
        dT.provinceName = "山西省";
        dT.isFirst = false;
        mData.add(dT);

        CityModel tY2 = new CityModel();
        tY2.cityName = "太原";
        tY2.provinceName = "山西省";
        tY2.isFirst = false;
        mData.add(tY2);

        CityModel sZ = new CityModel();
        sZ.cityName = "朔州";
        sZ.provinceName = "山西省";
        sZ.isFirst = false;
        mData.add(sZ);

        CityModel dT2 = new CityModel();
        dT2.cityName = "大同";
        dT2.provinceName = "山西省";
        dT2.isFirst = false;
        mData.add(dT2);

        CityModel sZ3 = new CityModel();
        sZ3.cityName = "朔州";
        sZ3.provinceName = "山西省";
        sZ3.isFirst = false;
        mData.add(sZ3);

        CityModel dT3 = new CityModel();
        dT3.cityName = "大同";
        dT3.provinceName = "山西省";
        dT3.isFirst = false;
        mData.add(dT3);

        CityModel sZ4 = new CityModel();
        sZ4.cityName = "朔州";
        sZ4.provinceName = "山西省";
        sZ4.isFirst = false;
        mData.add(sZ4);

        rlv.setLayoutManager(new LinearLayoutManager(this));
        MyCityItemDecoration myCityItemDecoration = new MyCityItemDecoration();
        myCityItemDecoration.setGetDataListener(new MyCityItemDecoration.GetDataListener() {
            @Override
            public boolean getIsFirst(int position) {
                return mData.get(position).isFirst;
            }

            @Override
            public String getProvince(int position) {
                return mData.get(position).provinceName;
            }
        });
        rlv.addItemDecoration(myCityItemDecoration);
        rlv.setAdapter(new CommonAdapter<CityModel>(this, R.layout.item_info, mData) {
            @Override
            public void convert(ViewHolder holder, CityModel o) {
                holder.setText(R.id.id_info, o.cityName);
            }
        });
    }
}
