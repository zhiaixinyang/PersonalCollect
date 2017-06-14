package com.example.mbenben.studydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.OnItemClickListener;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.db.SearchDBManager;
import com.example.mbenben.studydemo.model.HashSetSearchBean;
import com.example.mbenben.studydemo.net.httpurl.HttpUrlAcitvity;
import com.example.mbenben.studydemo.view.behavior.BehaviorActivity;
import com.example.mbenben.studydemo.view.bezier.ClearBezierActivity;
import com.example.mbenben.studydemo.view.bezier.GiftBezierActivity;
import com.example.mbenben.studydemo.view.bigimage.GlideLoaderActivity;
import com.example.mbenben.studydemo.view.canvasapi.CanvasApiActivity;
import com.example.mbenben.studydemo.view.chart.ChartActivity;
import com.example.mbenben.studydemo.view.circle.CircleActivity;
import com.example.mbenben.studydemo.view.couponview.CouponViewActivity;
import com.example.mbenben.studydemo.view.credit.CreditActivity;
import com.example.mbenben.studydemo.view.enviews.ENViewsActivity;
import com.example.mbenben.studydemo.view.expandabletextview.ExpandableActivity;
import com.example.mbenben.studydemo.layout.gradationtitle.QQTitleActivity;
import com.example.mbenben.studydemo.view.floatingview.FloatViewActivity;
import com.example.mbenben.studydemo.view.healthytables.HealthyTablesActivity;
import com.example.mbenben.studydemo.view.htmltext.HtmlTextActivity;
import com.example.mbenben.studydemo.view.justify.JustifyTextActivity;
import com.example.mbenben.studydemo.view.loading.LoadingTextViewActivity;
import com.example.mbenben.studydemo.view.lol.LoLActivity;
import com.example.mbenben.studydemo.view.passwordedittext.PassWordActivity;
import com.example.mbenben.studydemo.view.progressbar.ProgressBarActivity;
import com.example.mbenben.studydemo.view.qqhealth.QQHealthActivity;
import com.example.mbenben.studydemo.view.radar.RadarViewActivity;
import com.example.mbenben.studydemo.view.shader.ShaderActivity;
import com.example.mbenben.studydemo.view.switchbutton.SwitchButtonActivity;
import com.example.mbenben.studydemo.view.timelytext.TimelyTextActivity;
import com.example.mbenben.studydemo.view.toast.NewToastActivity;
import com.example.mbenben.studydemo.view.viscosity.ViscosityActivity;
import com.example.mbenben.studydemo.view.wave.WaveActivity;
import com.example.mbenben.studydemo.view.weibosport.WeiBoSportActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/17.
 */

public class ViewsFragment extends Fragment{
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    @BindView(R.id.tv_desc) TextView tvDesc;

    private SearchDBManager manager;

    private static final String KEY="views";
    private List<String> datas;
    private Map<String,String> mapTitle;

    private CommonAdapter<String> adapter;
    public static ViewsFragment newInstance(String desc) {

        Bundle args = new Bundle();
        args.putString(KEY,desc);
        ViewsFragment fragment = new ViewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_main_layout,container,false);
        manager=new SearchDBManager();
        initView(view);
        initRlv();
        addDB();
        return view;
    }

    private void initRlv() {
        adapter=new CommonAdapter<String>(App.getInstance().getContext(),R.layout.item_info,datas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info, mapTitle.get(s));
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                switch (o.toString()){
                    case "ClearBezierActivity":
                        Intent intentNav=new Intent(App.getInstance().getContext(), ClearBezierActivity.class);
                        startActivity(intentNav);
                        break;
                    case "GiftBezierActivity":
                        Intent intentView=new Intent(App.getInstance().getContext(), GiftBezierActivity.class);
                        startActivity(intentView);
                        break;
                    case "HealthyTablesActivity":
                        Intent intentNestedScroll=new Intent(App.getInstance().getContext(), HealthyTablesActivity.class);
                        startActivity(intentNestedScroll);
                        break;
                    case "CreditActivity":
                        Intent intentPuzzle=new Intent(App.getInstance().getContext(), CreditActivity.class);
                        startActivity(intentPuzzle);
                        break;
                    case "WeiBoSportActivity":
                        Intent intentIndicator=new Intent(App.getInstance().getContext(), WeiBoSportActivity.class);
                        startActivity(intentIndicator);
                        break;
                    case "QQHealthActivity":
                        Intent intentDrawerLayout=new Intent(App.getInstance().getContext(), QQHealthActivity.class);
                        startActivity(intentDrawerLayout);
                        break;
                    case "ChartActivity":
                        Intent intentRecycler=new Intent(App.getInstance().getContext(), ChartActivity.class);
                        startActivity(intentRecycler);
                        break;
                    case "LoLActivity":
                        Intent intentViewPager=new Intent(App.getInstance().getContext(), LoLActivity.class);
                        startActivity(intentViewPager);
                        break;
                    case "WaveActivity":
                        Intent intentSwipeCards=new Intent(App.getInstance().getContext(), WaveActivity.class);
                        startActivity(intentSwipeCards);
                        break;
                    case "ViscosityActivity":
                        Intent intentEle=new Intent(App.getInstance().getContext(), ViscosityActivity.class);
                        startActivity(intentEle);
                        break;
                    case "ExpandableActivity":
                        Intent intentSelect=new Intent(App.getInstance().getContext(), ExpandableActivity.class);
                        startActivity(intentSelect);
                        break;
                    case "ENViewsActivity":
                        Intent intentMyButterKnife=new Intent(App.getInstance().getContext(), ENViewsActivity.class);
                        startActivity(intentMyButterKnife);
                        break;
                    case "QQTitleActivity":
                        Intent intentQQTitle=new Intent(App.getInstance().getContext(), QQTitleActivity.class);
                        startActivity(intentQQTitle);
                        break;
                    case "ProgressBarActivity":
                        Intent intentArcLayout=new Intent(App.getInstance().getContext(), ProgressBarActivity.class);
                        startActivity(intentArcLayout);
                        break;
                    case "HtmlTextActivity":
                        Intent intentMultiLineChoose=new Intent(App.getInstance().getContext(), HtmlTextActivity.class);
                        startActivity(intentMultiLineChoose);
                        break;
                    case "PassWordActivity":
                        Intent intentRickText=new Intent(App.getInstance().getContext(), PassWordActivity.class);
                        startActivity(intentRickText);
                        break;
                    case "BehaviorActivity":
                        Intent intentBehavior=new Intent(App.getInstance().getContext(), BehaviorActivity.class);
                        startActivity(intentBehavior);
                        break;
                    case "GlideLoaderActivity":
                        Intent intentGlideLoader = new Intent(App.getInstance().getContext(), GlideLoaderActivity.class);
                        startActivity(intentGlideLoader);
                        break;
                    case "CircleActivity":
                        Intent intentCircle = new Intent(App.getInstance().getContext(), CircleActivity.class);
                        startActivity(intentCircle);
                        break;
                    case "CanvasApiActivity":
                        Intent intentCanvasApi = new Intent(App.getInstance().getContext(), CanvasApiActivity.class);
                        startActivity(intentCanvasApi);
                        break;
                    case "LoadingTextViewActivity":
                        Intent intentLoadingTextView = new Intent(App.getInstance().getContext(), LoadingTextViewActivity.class);
                        startActivity(intentLoadingTextView);
                        break;
                    case "JustifyTextActivity":
                        Intent intentJustifyText = new Intent(App.getInstance().getContext(), JustifyTextActivity.class);
                        startActivity(intentJustifyText);
                        break;
                    case "NewToastActivity":
                        Intent intentNewToast = new Intent(App.getInstance().getContext(), NewToastActivity.class);
                        startActivity(intentNewToast);
                        break;
                    case "TimelyTextActivity":
                        Intent intentTimelyText= new Intent(App.getInstance().getContext(), TimelyTextActivity.class);
                        startActivity(intentTimelyText);
                        break;
                    case "CouponViewActivity":
                        Intent intentCouponView= new Intent(App.getInstance().getContext(), CouponViewActivity.class);
                        startActivity(intentCouponView);
                        break;
                    case "FloatViewActivity":
                        Intent intentFloatView= new Intent(App.getInstance().getContext(), FloatViewActivity.class);
                        startActivity(intentFloatView);
                        break;
                    case "RadarViewActivity":
                        Intent intentRadarView= new Intent(App.getInstance().getContext(), RadarViewActivity.class);
                        startActivity(intentRadarView);
                        break;
                    case "ShaderActivity":
                        Intent intentShader= new Intent(App.getInstance().getContext(), ShaderActivity.class);
                        startActivity(intentShader);
                        break;
                    case "SwitchButtonActivity":
                        Intent intentSwitchButton= new Intent(App.getInstance().getContext(), SwitchButtonActivity.class);
                        startActivity(intentSwitchButton);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        rlvMain.setAdapter(adapter);
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);

        rlvMain.setLayoutManager(new GridLayoutManager(App.getInstance().getContext(),2));

        Bundle bundle = getArguments();
        String string = bundle.getString(KEY);
        tvDesc.setText(string);

        datas=new ArrayList<>();
        mapTitle =new HashMap<>();

        datas.add("CanvasApiActivity");
        mapTitle.put("CanvasApiActivity","Canvas基本API展示");
        App.addData(new HashSetSearchBean("CanvasApiActivity",CanvasApiActivity.class));

        datas.add("ClearBezierActivity");
        mapTitle.put("ClearBezierActivity","贝塞尔曲线实现发生火箭的效果");
        App.addData(new HashSetSearchBean("ClearBezierActivity",ClearBezierActivity.class));

        datas.add("GiftBezierActivity");
        mapTitle.put("GiftBezierActivity","贝塞尔曲线实现发生直播平台送礼物的效果");
        App.addData(new HashSetSearchBean("GiftBezierActivity",GiftBezierActivity.class));

        datas.add("HealthyTablesActivity");
        mapTitle.put("HealthyTablesActivity","折线图效果");
        App.addData(new HashSetSearchBean("HealthyTablesActivity",HealthyTablesActivity.class));

        datas.add("CreditActivity");
        mapTitle.put("CreditActivity","支付宝信用效果");
        App.addData(new HashSetSearchBean("CreditActivity",CreditActivity.class));

        datas.add("WeiBoSportActivity");
        mapTitle.put("WeiBoSportActivity","圆形评分进度效果");
        App.addData(new HashSetSearchBean("WeiBoSportActivity",WeiBoSportActivity.class));

        datas.add("QQHealthActivity");
        mapTitle.put("QQHealthActivity","QQ步数效果");
        App.addData(new HashSetSearchBean("QQHealthActivity",QQHealthActivity.class));

        datas.add("ChartActivity");
        mapTitle.put("ChartActivity","柱状图效果");
        App.addData(new HashSetSearchBean("ChartActivity",ChartActivity.class));

        datas.add("LoLActivity");
        mapTitle.put("LoLActivity","LOL能力数值分析效果");
        App.addData(new HashSetSearchBean("LoLActivity",LoLActivity.class));

        datas.add("WaveActivity");
        mapTitle.put("WaveActivity","波浪动画实例");
        App.addData(new HashSetSearchBean("WaveActivity",WaveActivity.class));

        datas.add("ViscosityActivity");
        mapTitle.put("ViscosityActivity","仿QQ粘性消息拖拽效果");
        App.addData(new HashSetSearchBean("ViscosityActivity",ViscosityActivity.class));

        datas.add("ExpandableActivity");
        mapTitle.put("ExpandableActivity","可折叠式TextView");
        App.addData(new HashSetSearchBean("ExpandableActivity",ExpandableActivity.class));

        datas.add("BehaviorActivity");
        mapTitle.put("BehaviorActivity","Behavior实践：缩放式搜索框");
        App.addData(new HashSetSearchBean("BehaviorActivity",BehaviorActivity.class));

        datas.add("ENViewsActivity");
        mapTitle.put("ENViewsActivity","酷炫的小动画，如：加载..");
        App.addData(new HashSetSearchBean("ENViewsActivity",ENViewsActivity.class));

        datas.add("ProgressBarActivity");
        mapTitle.put("ProgressBarActivity","自定义ProgressBar（下载进度条）");
        App.addData(new HashSetSearchBean("ProgressBarActivity",ProgressBarActivity.class));

        datas.add("HtmlTextActivity");
        mapTitle.put("HtmlTextActivity","可以解析Html的TextView");
        App.addData(new HashSetSearchBean("HtmlTextActivity",HtmlTextActivity.class));

        datas.add("PassWordActivity");
        mapTitle.put("PassWordActivity","仿安全登录EditText");
        App.addData(new HashSetSearchBean("PassWordActivity",PassWordActivity.class));

        datas.add("GlideLoaderActivity");
        mapTitle.put("GlideLoaderActivity","操作图片（双击扩大，移动查看等）");
        App.addData(new HashSetSearchBean("GlideLoaderActivity",GlideLoaderActivity.class));

        datas.add("CircleActivity");
        mapTitle.put("CircleActivity","圆角图片");
        App.addData(new HashSetSearchBean("CircleActivity",CircleActivity.class));

        datas.add("LoadingTextViewActivity");
        mapTitle.put("LoadingTextViewActivity","自定义Loading");
        App.addData(new HashSetSearchBean("LoadingTextViewActivity",LoadingTextViewActivity.class));

        datas.add("JustifyTextActivity");
        mapTitle.put("JustifyTextActivity","文字对齐");
        App.addData(new HashSetSearchBean("JustifyTextActivity",JustifyTextActivity.class));

        datas.add("NewToastActivity");
        mapTitle.put("NewToastActivity","自定义Toast");
        App.addData(new HashSetSearchBean("NewToastActivity",NewToastActivity.class));

        datas.add("TimelyTextActivity");
        mapTitle.put("TimelyTextActivity","数字Path变化");
        App.addData(new HashSetSearchBean("TimelyTextActivity",TimelyTextActivity.class));

        datas.add("CouponViewActivity");
        mapTitle.put("CouponViewActivity","卡券View");
        App.addData(new HashSetSearchBean("CouponViewActivity",CouponViewActivity.class));

        datas.add("FloatViewActivity");
        mapTitle.put("FloatViewActivity","浮动的View + 点击特效");
        App.addData(new HashSetSearchBean("FloatViewActivity",FloatViewActivity.class));

        datas.add("RadarViewActivity");
        mapTitle.put("RadarViewActivity","雷达效果");
        App.addData(new HashSetSearchBean("RadarViewActivity",RadarViewActivity.class));

        datas.add("ShaderActivity");
        mapTitle.put("ShaderActivity","Shader效果使用");
        App.addData(new HashSetSearchBean("ShaderActivity",ShaderActivity.class));

        datas.add("SwitchButtonActivity");
        mapTitle.put("SwitchButtonActivity","SwitchButton效果");
        App.addData(new HashSetSearchBean("ShaderActivity",SwitchButtonActivity.class));

    }

    private void addDB() {
        for (String name:datas){
            SearchBean searchBean=new SearchBean();
            searchBean.setActivity(name);
            searchBean.setTitle(mapTitle.get(name));
            manager.insert(searchBean);
        }
    }
}
