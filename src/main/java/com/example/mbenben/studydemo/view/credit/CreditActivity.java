package com.example.mbenben.studydemo.view.credit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bezier.GiftBezierActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MDove on 2017/1/3.
 * <p>
 * 原作者项目博客：http://blog.csdn.net/as7210636/article/details/52634769
 */

public class CreditActivity extends BaseActivity {
    private SesameCreditPanel creditView;
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd");

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, CreditActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_credit);
        creditView = (SesameCreditPanel) findViewById(R.id.creditView);
        creditView.setDataModel(getData());
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    private SesameModel getData() {
        SesameModel model = new SesameModel();
        model.setUserTotal(637);
        model.setAssess("信用良好");
        model.setTotalMin(350);
        model.setTotalMax(950);
        model.setFirstText("BETA");
        model.setTopText("因为信用 所以简单");
        model.setFourText("评估时间:" + formater.format(new Date()));
        ArrayList<SesameItemModel> sesameItemModels = new ArrayList<SesameItemModel>();

        SesameItemModel ItemModel350 = new SesameItemModel();
        ItemModel350.setArea("较差");
        ItemModel350.setMin(350);
        ItemModel350.setMax(550);
        sesameItemModels.add(ItemModel350);

        SesameItemModel ItemModel550 = new SesameItemModel();
        ItemModel550.setArea("中等");
        ItemModel550.setMin(550);
        ItemModel550.setMax(600);
        sesameItemModels.add(ItemModel550);

        SesameItemModel ItemModel600 = new SesameItemModel();
        ItemModel600.setArea("良好");
        ItemModel600.setMin(600);
        ItemModel600.setMax(650);
        sesameItemModels.add(ItemModel600);

        SesameItemModel ItemModel650 = new SesameItemModel();
        ItemModel650.setArea("优秀");
        ItemModel650.setMin(650);
        ItemModel650.setMax(700);
        sesameItemModels.add(ItemModel650);

        SesameItemModel ItemModel700 = new SesameItemModel();
        ItemModel700.setArea("较好");
        ItemModel700.setMin(700);
        ItemModel700.setMax(950);
        sesameItemModels.add(ItemModel700);

        model.setSesameItemModels(sesameItemModels);
        return model;
    }
}
