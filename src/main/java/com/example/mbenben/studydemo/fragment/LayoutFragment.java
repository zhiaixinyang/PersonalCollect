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
import com.example.mbenben.studydemo.MainActivity;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.OnItemClickListener;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.db.SearchBean;
import com.example.mbenben.studydemo.db.SearchDBManager;
import com.example.mbenben.studydemo.layout.arclayout.ArcLayoutActivity;
import com.example.mbenben.studydemo.layout.bottomnavigationview.BottomNavigationViewActivity;
import com.example.mbenben.studydemo.layout.chanelview.ChanelActivity;
import com.example.mbenben.studydemo.layout.doublepull.DoublePullActivity;
import com.example.mbenben.studydemo.layout.drawerlayout.my.SlidingActivity;
import com.example.mbenben.studydemo.layout.esaysidebar.EasySideBarBuilder;
import com.example.mbenben.studydemo.layout.esaysidebar.SortCityActivity;
import com.example.mbenben.studydemo.layout.expandableviewpager.ExpandableViewPagerActivity;
import com.example.mbenben.studydemo.layout.fragmenttabhost.TabActivity;
import com.example.mbenben.studydemo.layout.graffiti.DrawingBoardActvity;
import com.example.mbenben.studydemo.layout.guideview.activity.HomeActivity;
import com.example.mbenben.studydemo.layout.horizontalscrollview.HorizontalScrollActivity;
import com.example.mbenben.studydemo.layout.intercepttouchevent.InterceptActivity;
import com.example.mbenben.studydemo.layout.materialdesign.coordinatorlayouttest.UCActivity;
import com.example.mbenben.studydemo.layout.newbottomnav.BottomNavMainActivity;
import com.example.mbenben.studydemo.layout.materialdesign.MaterialDesignActivity;
import com.example.mbenben.studydemo.layout.overscroll.activity.OverScrollDemoActivity;
import com.example.mbenben.studydemo.layout.photowall.PhotoWallActivity;
import com.example.mbenben.studydemo.layout.pickerview.PickerActivity;
import com.example.mbenben.studydemo.layout.swipmenu.SwipMenuActivity;
import com.example.mbenben.studydemo.layout.titlelayout.ui.activity.ShoppingGoodsActivity;
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
import com.example.mbenben.studydemo.layout.viewpager.ali.activity.AliUPVDemoActivity;
import com.example.mbenben.studydemo.model.HashSetSearchBean;
import com.example.mbenben.studydemo.view.pintu.PinTuActivity;
import com.example.mbenben.studydemo.view.select.SelectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mdove on 2017/1/17.
 */

public class LayoutFragment extends Fragment {
    @BindView(R.id.rlv_main)
    RecyclerView rlvMain;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    private static final String KEY = "layout";
    private List<String> datas;
    private Map<String, String> mapTitle;

    private SearchDBManager manager;

    private CommonAdapter<String> adapter;

    public static LayoutFragment newInstance(String desc) {

        Bundle args = new Bundle();
        args.putString(KEY, desc);
        LayoutFragment fragment = new LayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_layout, container, false);
        manager = new SearchDBManager();
        initView(view);
        initRlv();
//        addDB();
        return view;
    }

    private void initRlv() {
        adapter = new CommonAdapter<String>(App.getInstance().getContext(), R.layout.item_info, datas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.id_info, mapTitle.get(s));
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                switch (o.toString()) {
                    case "NavActivity":
                        Intent intentNav = new Intent(App.getInstance().getContext(), NavActivity.class);
                        startActivity(intentNav);
                        break;
                    case "NestedScrollActivity":
                        Intent intentNestedScroll = new Intent(App.getInstance().getContext(), NestedScrollActivity.class);
                        startActivity(intentNestedScroll);
                        break;
                    case "Puzzle":
                        Intent intentPuzzle = new Intent(App.getInstance().getContext(), PinTuActivity.class);
                        startActivity(intentPuzzle);
                        break;
                    case "IndicatorActivity":
                        Intent intentIndicator = new Intent(App.getInstance().getContext(), IndicatorActivity.class);
                        startActivity(intentIndicator);
                        break;
                    case "DrawerLayoutActivity":
                        Intent intentDrawerLayout = new Intent(App.getInstance().getContext(), DrawerLayoutActivity.class);
                        startActivity(intentDrawerLayout);
                        break;
                    case "RecyclerActivity":
                        Intent intentRecycler = new Intent(App.getInstance().getContext(), RecyclerActivity.class);
                        startActivity(intentRecycler);
                        break;
                    case "ViewPagerActivity":
                        Intent intentViewPager = new Intent(App.getInstance().getContext(), ViewPagerActivity.class);
                        startActivity(intentViewPager);
                        break;
                    case "SwipeCardsActivity":
                        Intent intentSwipeCards = new Intent(App.getInstance().getContext(), SwipeCardsActivity.class);
                        startActivity(intentSwipeCards);
                        break;
                    case "EleViewPagerActivity":
                        EleMainActivity.start(App.getInstance().getContext(), "饿了么选单交互效果");
                        break;
                    case "SelectActivity":
                        Intent intentSelect = new Intent(App.getInstance().getContext(), SelectActivity.class);
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
                        Intent intentRickText = new Intent(App.getInstance().getContext(), RickTextActivity.class);
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
                        Intent intentBottomNav = new Intent(App.getInstance().getContext(), BottomNavMainActivity.class);
                        startActivity(intentBottomNav);
                        break;
                    case "MaterialDesignActivity":
                        Intent intentMaterialDesign = new Intent(App.getInstance().getContext(), MaterialDesignActivity.class);
                        startActivity(intentMaterialDesign);
                        break;
                    case "InterceptActivity":
                        Intent intentIntercept = new Intent(App.getInstance().getContext(), InterceptActivity.class);
                        startActivity(intentIntercept);
                        break;
                    case "ExpandableViewPagerActivity":
                        Intent intentExpandableViewPager = new Intent(App.getInstance().getContext(), ExpandableViewPagerActivity.class);
                        startActivity(intentExpandableViewPager);
                        break;
                    case "DrawingBoardActvity":
                        Intent intentDrawingBoard = new Intent(App.getInstance().getContext(), DrawingBoardActvity.class);
                        startActivity(intentDrawingBoard);
                        break;
                    case "HorizontalScrollActivity":
                        Intent intentHorizontalScroll = new Intent(App.getInstance().getContext(), HorizontalScrollActivity.class);
                        startActivity(intentHorizontalScroll);
                        break;
                    case "SortCityActivity":
                        ArrayList<String> hotCityList = new ArrayList<>();
                        hotCityList.add("北京");
                        hotCityList.add("上海");
                        hotCityList.add("广州");
                        hotCityList.add("深圳");
                        hotCityList.add("杭州");
                        hotCityList.add("成都");
                        hotCityList.add("厦门");
                        hotCityList.add("天津");
                        hotCityList.add("武汉");
                        hotCityList.add("长沙");
                        String[] mIndexItems = {"定位", "热门"};//头部额外的索引

                        new EasySideBarBuilder(getActivity())
                                .setTitle("城市选择")
                                .setIndexColor(0xFF0095EE)
                                .setHotCityList(hotCityList)//热门城市列表
                                .setIndexItems(mIndexItems)//索引字母
                                .setLocationCity("广州")//定位城市
                                .setMaxOffset(60)//索引的最大偏移量
                                .start();
                        break;
                    case "PickerActivity":
                        Intent intentPicker = new Intent(App.getInstance().getContext(), PickerActivity.class);
                        startActivity(intentPicker);
                        break;
                    case "SlidingActivity":
                        Intent intentSlidingActivity = new Intent(App.getInstance().getContext(), SlidingActivity.class);
                        startActivity(intentSlidingActivity);
                        break;
                    case "SwipMenuActivity":
                        Intent intentSwipMenu = new Intent(App.getInstance().getContext(), SwipMenuActivity.class);
                        startActivity(intentSwipMenu);
                        break;
                    case "UCActivity":
                        Intent intentScrolling = new Intent(App.getInstance().getContext(), UCActivity.class);
                        startActivity(intentScrolling);
                        break;
                    case "OverScrollDemoActivity":
                        Intent intentOverScroll = new Intent(App.getInstance().getContext(), OverScrollDemoActivity.class);
                        startActivity(intentOverScroll);
                        break;
                    case "AliUPVDemoActivity":
                        Intent toAliViewPager = new Intent(App.getInstance().getContext(), AliUPVDemoActivity.class);
                        startActivity(toAliViewPager);
                        break;
                    case "HomeActivity":
                        HomeActivity.start(getContext(), "高亮引导页");
                        break;
                    case "ShoppingGoodsActivity":
                        ShoppingGoodsActivity.start(getContext(), "仿饿了么购买页");
                        break;
                    case "PhotoWallActivity":
                        Intent toPhoto = new Intent(App.getInstance().getContext(), PhotoWallActivity.class);
                        startActivity(toPhoto);
                        break;
                    case "ChanelActivity":
                        Intent toChanel = new Intent(App.getInstance().getContext(), ChanelActivity.class);
                        startActivity(toChanel);
                        break;
                    case "DoublePullActivity":
                        Intent toDoublePull = new Intent(App.getInstance().getContext(), DoublePullActivity.class);
                        startActivity(toDoublePull);
                        break;
                    default:
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
        ButterKnife.bind(this, view);

        rlvMain.setLayoutManager(new GridLayoutManager(App.getInstance().getContext(), 2));

        Bundle bundle = getArguments();
        String string = bundle.getString(KEY);
        tvDesc.setText(string);


        if (datas == null) {
            datas = new ArrayList<>();
            mapTitle = new HashMap<>();

            datas.add("NavActivity");
            mapTitle.put("NavActivity", "可收缩头View（嵌套机制实现）");
            App.addData(new HashSetSearchBean("NavActivity", NavActivity.class));

            datas.add("NestedScrollActivity");
            mapTitle.put("NestedScrollActivity", "嵌套机制的布局实现");
            App.addData(new HashSetSearchBean("NestedScrollActivity", NestedScrollActivity.class));

            datas.add("Puzzle");
            mapTitle.put("Puzzle", "简单的拼图效果");
            App.addData(new HashSetSearchBean("Puzzle", PinTuActivity.class));

            datas.add("IndicatorActivity");
            mapTitle.put("IndicatorActivity", "ViewPager指示器");
            App.addData(new HashSetSearchBean("IndicatorActivity", IndicatorActivity.class));

            datas.add("ViewPagerActivity");
            mapTitle.put("ViewPagerActivity", "ViewPager不同的转换效果");
            App.addData(new HashSetSearchBean("ViewPagerActivity", ViewPagerActivity.class));

            datas.add("AliUPVDemoActivity");
            mapTitle.put("AliUPVDemoActivity", "阿里开源ViewPager");
            App.addData(new HashSetSearchBean("AliUPVDemoActivity", AliUPVDemoActivity.class));

            datas.add("DrawerLayoutActivity");
            mapTitle.put("DrawerLayoutActivity", "DrawerLayout抽屉菜单");
            App.addData(new HashSetSearchBean("DrawerLayoutActivity", DrawerLayoutActivity.class));

            datas.add("SlidingActivity");
            mapTitle.put("SlidingActivity", "一个不实际的侧滑的效果");
            App.addData(new HashSetSearchBean("SlidingActivity", SlidingActivity.class));

            datas.add("RecyclerActivity");
            mapTitle.put("RecyclerActivity", "RecyclerView各种使用");
            App.addData(new HashSetSearchBean("RecyclerActivity", RecyclerActivity.class));

            datas.add("SwipeCardsActivity");
            mapTitle.put("SwipeCardsActivity", "仿天天美剧拖动卡片的效果");
            App.addData(new HashSetSearchBean("SwipeCardsActivity", SwipeCardsActivity.class));

            datas.add("EleViewPagerActivity");
            mapTitle.put("EleViewPagerActivity", "饿了么选单交互效果");
            App.addData(new HashSetSearchBean("EleViewPagerActivity", EleMainActivity.class));

            datas.add("SelectActivity");
            mapTitle.put("SelectActivity", "滑动刻度尺效果");
            App.addData(new HashSetSearchBean("SelectActivity", SelectActivity.class));

            datas.add("ArcLayoutActivity");
            mapTitle.put("ArcLayoutActivity", "头部图片移动+弧形Layout");
            App.addData(new HashSetSearchBean("ArcLayoutActivity", ArcLayoutActivity.class));

            datas.add("MultiLineChooseActivity");
            mapTitle.put("MultiLineChooseActivity", "可点击选择Layout（流式）");
            App.addData(new HashSetSearchBean("MultiLineChooseActivity", MultiLineChooseActivity.class));

            datas.add("RickTextActivity");
            mapTitle.put("RickTextActivity", "类似微博的emoji表情与@某人的EdiText");
            App.addData(new HashSetSearchBean("RickTextActivity", RickTextActivity.class));

            datas.add("RadarScanActivity");
            mapTitle.put("RadarScanActivity", "雷达式附近的人");
            App.addData(new HashSetSearchBean("RadarScanActivity", RadarScanActivity.class));

            datas.add("QQTitleActivity");
            mapTitle.put("QQTitleActivity", "随页面滑动渐变的Title");
            App.addData(new HashSetSearchBean("QQTitleActivity", QQTitleActivity.class));

            datas.add("VideoPlayerActivity");
            mapTitle.put("VideoPlayerActivity", "原始视频播放器VideoPlayer");
            App.addData(new HashSetSearchBean("VideoPlayerActivity", VideoPlayerActivity.class));

            datas.add("TabActivity");
            mapTitle.put("TabActivity", "基本的TabHost的使用");
            App.addData(new HashSetSearchBean("TabActivity", TabActivity.class));

            datas.add("BottomNavigationViewActivity");
            mapTitle.put("BottomNavigationViewActivity", "官方design:25.0.0新推出的BottomNavigationView");
            App.addData(new HashSetSearchBean("BottomNavigationViewActivity", BottomNavigationViewActivity.class));

            datas.add("BottomNavMainActivity");
            mapTitle.put("BottomNavMainActivity", "GitHub上开源底部导航栏");
            App.addData(new HashSetSearchBean("BottomNavMainActivity", BottomNavMainActivity.class));

            datas.add("MaterialDesignActivity");
            mapTitle.put("MaterialDesignActivity", "MaterialDesign");
            App.addData(new HashSetSearchBean("MaterialDesignActivity", MaterialDesignActivity.class));

            datas.add("InterceptActivity");
            mapTitle.put("InterceptActivity", "滑动冲突解析");
            App.addData(new HashSetSearchBean("InterceptActivity", InterceptActivity.class));

            datas.add("ExpandableViewPagerActivity");
            mapTitle.put("ExpandableViewPagerActivity", "可以缩放的ViewPager");
            App.addData(new HashSetSearchBean("ExpandableViewPagerActivity", ExpandableViewPagerActivity.class));

            datas.add("DrawingBoardActvity");
            mapTitle.put("DrawingBoardActvity", "涂鸦效果+刮刮卡");
            App.addData(new HashSetSearchBean("DrawingBoardActvity", DrawingBoardActvity.class));

            datas.add("HorizontalScrollActivity");
            mapTitle.put("HorizontalScrollActivity", "画廊效果");
            App.addData(new HashSetSearchBean("HorizontalScrollActivity", HorizontalScrollActivity.class));

            datas.add("SortCityActivity");
            mapTitle.put("SortCityActivity", "3D式索引");
            App.addData(new HashSetSearchBean("SortCityActivity", SortCityActivity.class));

            datas.add("PickerActivity");
            mapTitle.put("PickerActivity", "三级联动");
            App.addData(new HashSetSearchBean("PickerActivity", PickerActivity.class));

            datas.add("SwipMenuActivity");
            mapTitle.put("SwipMenuActivity", "Item侧滑效果");
            App.addData(new HashSetSearchBean("SwipMenuActivity", SwipMenuActivity.class));

            datas.add("UCActivity");
            mapTitle.put("UCActivity", "仿支付宝部缩放效果");
            App.addData(new HashSetSearchBean("UCActivity", UCActivity.class));

            datas.add("OverScrollDemoActivity");
            mapTitle.put("OverScrollDemoActivity", "粘性布局");
            App.addData(new HashSetSearchBean("OverScrollDemoActivity", OverScrollDemoActivity.class));

            datas.add("HomeActivity");
            mapTitle.put("HomeActivity", "高亮引导页");
            App.addData(new HashSetSearchBean("HomeActivity", HomeActivity.class));

            datas.add("PhotoWallActivity");
            mapTitle.put("PhotoWallActivity", "照片墙滑动加载");
            App.addData(new HashSetSearchBean("PhotoWallActivity", PhotoWallActivity.class));

            datas.add("ShoppingGoodsActivity");
            mapTitle.put("ShoppingGoodsActivity", "仿饿了么购买页");
            App.addData(new HashSetSearchBean("ShoppingGoodsActivity", ShoppingGoodsActivity.class));

            datas.add("ChanelActivity");
            mapTitle.put("ChanelActivity", "堆叠式ViewPager（不是ViewPager的实现）");
            App.addData(new HashSetSearchBean("ChanelActivity", ChanelActivity.class));

            datas.add("DoublePullActivity");
            mapTitle.put("DoublePullActivity", "上滑式拉动布局");
            App.addData(new HashSetSearchBean("DoublePullActivity", DoublePullActivity.class));
        }
    }

    private void addDB() {
        for (String name : datas) {
            SearchBean searchBean = new SearchBean();
            searchBean.setActivity(name);
            searchBean.setTitle(mapTitle.get(name));
            manager.insert(searchBean);
        }
    }
}
