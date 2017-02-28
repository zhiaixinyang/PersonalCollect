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
import com.example.mbenben.studydemo.layout.arclayout.ArcLayoutActivity;
import com.example.mbenben.studydemo.layout.bottomnavigationview.BottomNavigationViewActivity;
import com.example.mbenben.studydemo.layout.expandableviewpager.ExpandableViewPagerActivity;
import com.example.mbenben.studydemo.layout.fragmenttabhost.TabActivity;
import com.example.mbenben.studydemo.layout.graffiti.DrawingBoardActvity;
import com.example.mbenben.studydemo.layout.intercepttouchevent.InterceptActivity;
import com.example.mbenben.studydemo.layout.newbottomnav.BottomNavMainActivity;
import com.example.mbenben.studydemo.layout.materialdesign.MaterialDesignActivity;
import com.example.mbenben.studydemo.layout.videoplayer.VideoPlayerActivity;
import com.example.mbenben.studydemo.layout.drawerlayout.DrawerLayoutActivity;
import com.example.mbenben.studydemo.layout.ele.EleMainActivity;
import com.example.mbenben.studydemo.layout.indicator.IndicatorActivity;
import com.example.mbenben.studydemo.layout.flowlayout.MultiLineChooseActivity;
import com.example.mbenben.studydemo.layout.nav.NavActivity;
import com.example.mbenben.studydemo.layout.nestedscroll.NestedScrollActivity;
import com.example.mbenben.studydemo.layout.radarscan.RadarScanActivity;
import com.example.mbenben.studydemo.layout.recyclerview.RecyclerActivity;
import com.example.mbenben.studydemo.layout.ricktext.RickTextActivity;
import com.example.mbenben.studydemo.layout.swipecards.SwipeCardsActivity;
import com.example.mbenben.studydemo.layout.viewpager.ViewPagerActivity;
import com.example.mbenben.studydemo.layout.gradationtitle.QQTitleActivity;
import com.example.mbenben.studydemo.view.pintu.PinTuActivity;
import com.example.mbenben.studydemo.view.select.SelectActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/17.
 */

public class LayoutFragment extends Fragment{
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    @BindView(R.id.tv_desc) TextView tvDesc;

    private static final String KEY="layout";
    private List<String> datas=new ArrayList<>();
    private Map<String,String> map=new HashMap<>();

    private CommonAdapter<String> adapter;
    public static LayoutFragment newInstance(String desc) {
        
        Bundle args = new Bundle();
        args.putString(KEY,desc);
        LayoutFragment fragment = new LayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_main_layout,container,false);
        initView(view);
        initRlv();
        return view;
    }

    private void initRlv() {
        adapter=new CommonAdapter<String>(App.getInstance().getContext(),R.layout.item_info,datas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info,map.get(s));
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                switch (o.toString()){
                    case "NavActivity":
                        Intent intentNav=new Intent(App.getInstance().getContext(), NavActivity.class);
                        startActivity(intentNav);
                        break;
                    case "NestedScrollActivity":
                        Intent intentNestedScroll=new Intent(App.getInstance().getContext(), NestedScrollActivity.class);
                        startActivity(intentNestedScroll);
                        break;
                    case "Puzzle":
                        Intent intentPuzzle=new Intent(App.getInstance().getContext(), PinTuActivity.class);
                        startActivity(intentPuzzle);
                        break;
                    case "IndicatorActivity":
                        Intent intentIndicator=new Intent(App.getInstance().getContext(), IndicatorActivity.class);
                        startActivity(intentIndicator);
                        break;
                    case "DrawerLayoutActivity":
                        Intent intentDrawerLayout=new Intent(App.getInstance().getContext(), DrawerLayoutActivity.class);
                        startActivity(intentDrawerLayout);
                        break;
                    case "RecyclerActivity":
                        Intent intentRecycler=new Intent(App.getInstance().getContext(), RecyclerActivity.class);
                        startActivity(intentRecycler);
                        break;
                    case "ViewPagerActivity":
                        Intent intentViewPager=new Intent(App.getInstance().getContext(), ViewPagerActivity.class);
                        startActivity(intentViewPager);
                        break;
                    case "SwipeCardsActivity":
                        Intent intentSwipeCards=new Intent(App.getInstance().getContext(), SwipeCardsActivity.class);
                        startActivity(intentSwipeCards);
                        break;
                    case "EleViewPagerActivity":
                        Intent intentEle=new Intent(App.getInstance().getContext(), EleMainActivity.class);
                        startActivity(intentEle);
                        break;
                    case "SelectActivity":
                        Intent intentSelect=new Intent(App.getInstance().getContext(), SelectActivity.class);
                        startActivity(intentSelect);
                        break;
                    case "ArcLayoutActivity":
                        Intent intentArcLayout = new Intent(App.getInstance().getContext(), ArcLayoutActivity.class);
                        startActivity(intentArcLayout);
                        break;
                    case "MultiLineChooseActivity":
                        Intent intentMultiLineChoose = new Intent(App.getInstance().getContext(), MultiLineChooseActivity.class);
                        startActivity(intentMultiLineChoose);
                        break;
                    case "RickTextActivity":
                        Intent intentRickText=new Intent(App.getInstance().getContext(), RickTextActivity.class);
                        startActivity(intentRickText);
                        break;
                    case "RadarScanActivity":
                        Intent intentRadarScan = new Intent(App.getInstance().getContext(), RadarScanActivity.class);
                        startActivity(intentRadarScan);
                        break;
                    case "QQTitleActivity":
                        Intent intentQQTitle = new Intent(App.getInstance().getContext(), QQTitleActivity.class);
                        startActivity(intentQQTitle);
                        break;
                    case "VideoPlayerActivity":
                        Intent intentVideoPlayer = new Intent(App.getInstance().getContext(), VideoPlayerActivity.class);
                        startActivity(intentVideoPlayer);
                        break;
                    case "TabActivity":
                        Intent intentVideoTab = new Intent(App.getInstance().getContext(), TabActivity.class);
                        startActivity(intentVideoTab);
                        break;
                    case "BottomNavigationViewActivity":
                        Intent intentBottomNavigationView = new Intent(App.getInstance().getContext(), BottomNavigationViewActivity.class);
                        startActivity(intentBottomNavigationView);
                        break;
                    case "BottomNavMainActivity":
                        Intent intentBottomNav= new Intent(App.getInstance().getContext(), BottomNavMainActivity.class);
                        startActivity(intentBottomNav);
                        break;
                    case "MaterialDesignActivity":
                        Intent intentMaterialDesign= new Intent(App.getInstance().getContext(), MaterialDesignActivity.class);
                        startActivity(intentMaterialDesign);
                        break;
                    case "InterceptActivity":
                        Intent intentIntercept= new Intent(App.getInstance().getContext(), InterceptActivity.class);
                        startActivity(intentIntercept);
                        break;
                    case "ExpandableViewPagerActivity":
                        Intent intentExpandableViewPager= new Intent(App.getInstance().getContext(), ExpandableViewPagerActivity.class);
                        startActivity(intentExpandableViewPager);
                        break;
                    case "DrawingBoardActvity":
                        Intent intentDrawingBoard= new Intent(App.getInstance().getContext(), DrawingBoardActvity.class);
                        startActivity(intentDrawingBoard);
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

        datas.add("NavActivity");
        map.put("NavActivity","可收缩头View（嵌套机制实现）");

        datas.add("NestedScrollActivity");
        map.put("NestedScrollActivity","嵌套机制的布局实现");

        datas.add("Puzzle");
        map.put("Puzzle","简单的拼图效果");

        datas.add("IndicatorActivity");
        map.put("IndicatorActivity","ViewPager指示器");

        datas.add("ViewPagerActivity");
        map.put("ViewPagerActivity","ViewPager不同的转换效果");

        datas.add("DrawerLayoutActivity");
        map.put("DrawerLayoutActivity","DrawerLayout抽屉菜单");

        datas.add("RecyclerActivity");
        map.put("RecyclerActivity","RecyclerView各种使用");

        datas.add("SwipeCardsActivity");
        map.put("SwipeCardsActivity","仿天天美剧拖动卡片的效果");

        datas.add("EleViewPagerActivity");
        map.put("EleViewPagerActivity","饿了么选单交互效果");

        datas.add("SelectActivity");
        map.put("SelectActivity","滑动刻度尺效果");

        datas.add("ArcLayoutActivity");
        map.put("ArcLayoutActivity","头部图片移动+弧形Layout");

        datas.add("MultiLineChooseActivity");
        map.put("MultiLineChooseActivity","可点击选择Layout（流式）");

        datas.add("RickTextActivity");
        map.put("RickTextActivity","类似微博的emoji表情与@某人的EdiText");

        datas.add("RadarScanActivity");
        map.put("RadarScanActivity","雷达式附近的人");

        datas.add("QQTitleActivity");
        map.put("QQTitleActivity","随页面滑动渐变的Title");

        datas.add("VideoPlayerActivity");
        map.put("VideoPlayerActivity","原始视频播放器VideoPlayer");

        datas.add("TabActivity");
        map.put("TabActivity","基本的TabHost的使用");

        datas.add("BottomNavigationViewActivity");
        map.put("BottomNavigationViewActivity","官方design:25.0.0新推出的BottomNavigationView");

        datas.add("BottomNavMainActivity");
        map.put("BottomNavMainActivity","GitHub上底部导航");

        datas.add("MaterialDesignActivity");
        map.put("MaterialDesignActivity","MaterialDesign");

        datas.add("InterceptActivity");
        map.put("InterceptActivity","滑动冲突解析");

        datas.add("ExpandableViewPagerActivity");
        map.put("ExpandableViewPagerActivity","可以缩放的ViewPager");

        datas.add("DrawingBoardActvity");
        map.put("DrawingBoardActvity","涂鸦效果+刮刮卡");
    }
}
