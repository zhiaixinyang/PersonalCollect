package com.example.mbenben.studydemo.layout.recyclerview.others.dragItemAnimatorUse;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.CommonRecyclerAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.ViewHolder;
import com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse.basicUse.DividerGridItemDecoration;
import com.example.mbenben.studydemo.layout.recyclerview.others.widget.WrapRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Darren on 2016/12/29.
 * Email: 240336124@qq.com
 * Description:  列表条目拖动排序和删除
 *
 * 原作者项目GitHub:https://github.com/Shenmowen/RecyclerAnalysis
 */

public class DragItemAnimatorActivity extends AppCompatActivity {
    private WrapRecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private List<ItemBean> mItems = new ArrayList<ItemBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_recycler);

        initData();

        mRecyclerView = (WrapRecyclerView) findViewById(R.id.recycler_view);


        mAdapter = new HomeAdapter(this, mItems);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        final DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);

        // 实现左边侧滑删除 和 拖动排序
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
                // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                int swipeFlags = ItemTouchHelper.LEFT;


                // 拖动
                int dragFlags = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    // GridView 样式四个方向都可以
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                            ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
                } else {
                    // ListView 样式不支持左右
                    dragFlags = ItemTouchHelper.UP |
                            ItemTouchHelper.DOWN;
                }

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /**
             * 拖动的时候不断的回调方法
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();
                // 得到目标的位置
                int targetPosition = target.getAdapterPosition();
                if (fromPosition > targetPosition) {
                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(mItems, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(mItems, i, i - 1);// 改变实际的数据集
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, targetPosition);
                return true;
            }

            /**
             * 侧滑删除后会回调的方法
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 获取当前删除的位置
                int position = viewHolder.getAdapterPosition();
                mItems.remove(position);
                // adapter 更新notify当前位置删除
                mAdapter.notifyItemRemoved(position);
            }

            /**
             * 拖动选择状态改变回调
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    // ItemTouchHelper.ACTION_STATE_IDLE 看看源码解释就能理解了
                    // 侧滑或者拖动的时候背景设置为灰色
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }


            /**
             * 回到正常状态的时候回调
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 正常默认状态下背景恢复默认
                viewHolder.itemView.setBackgroundColor(0);
                ViewCompat.setTranslationX(viewHolder.itemView,0);
            }
        });
        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    protected void initData() {
        for (int i = 0; i < 10; i++) {
            mItems.add(new ItemBean(i * 8 + 0, "收款", R.drawable.takeout_ic_category_brand));
            mItems.add(new ItemBean(i * 8 + 1, "转账", R.drawable.takeout_ic_category_flower));
            mItems.add(new ItemBean(i * 8 + 2, "余额宝", R.drawable.takeout_ic_category_fruit));
            mItems.add(new ItemBean(i * 8 + 3, "手机充值", R.drawable.takeout_ic_category_medicine));
            mItems.add(new ItemBean(i * 8 + 4, "医疗", R.drawable.takeout_ic_category_motorcycle));
            mItems.add(new ItemBean(i * 8 + 5, "彩票", R.drawable.takeout_ic_category_public));
            mItems.add(new ItemBean(i * 8 + 6, "电影", R.drawable.takeout_ic_category_store));
            mItems.add(new ItemBean(i * 8 + 7, "游戏", R.drawable.takeout_ic_category_sweet));
        }
        mItems.add(new ItemBean(mItems.size(), "更多", R.drawable.takeout_ic_more));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
        return true;
    }

    class HomeAdapter extends CommonRecyclerAdapter<ItemBean> {


        public HomeAdapter(Context context, List<ItemBean> data) {
            super(context, data, R.layout.item_drag_sort_delete_others_recyclerview);
        }

        @Override
        public void convert(ViewHolder holder, ItemBean item) {
            holder.setText(R.id.item_text, item.text);
            holder.setImageResource(R.id.item_img, item.icon);
        }
    }

    public class ItemBean {
        public int id;
        public String text;
        public int icon;

        public ItemBean(int id, String text, int icon) {
            this.id = id;
            this.text = text;
            this.icon = icon;
        }
    }
}
