package com.example.mbenben.studydemo.layout.recyclerview;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mbenben.studydemo.layout.recyclerview.adapter.RecyclerView2Adapter;
import com.example.mbenben.studydemo.layout.recyclerview.model.DataBean;
import com.example.mbenben.studydemo.utils.DpUtils;
import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MBENBEN on 2017/1/1.
 */

public class RecyclerView2Activity extends AppCompatActivity{
    private RecyclerView rlvMain;
    private List<DataBean> datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview2);
        initData();
        rlvMain= (RecyclerView) findViewById(R.id.rlv_main);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type=rlvMain.getAdapter().getItemViewType(position);
                if (type==DataBean.TYPE_THREE){
                    /**
                     * 此处的返回值就是设置自己在一行Item所占的比例。
                     * 例：Manager为5，返回为3。那么所占比例就是3/5；
                     */
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        rlvMain.setLayoutManager(gridLayoutManager);
        rlvMain.setAdapter(new RecyclerView2Adapter(this,datas));
        //设置Item间距
        rlvMain.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                GridLayoutManager.LayoutParams lp= (GridLayoutManager.LayoutParams) view.getLayoutParams();
                //如果此行Item为一列则为1
                int spanSize=lp.getSpanSize();
                //一行里第几列，从0开始算
                int spanIndex=lp.getSpanIndex();
                //设置Item的上边距
                outRect.top= DpUtils.dp2px(12);
                if (spanSize!=gridLayoutManager.getSpanCount()){

                    if (spanIndex==1){
                        outRect.left=DpUtils.dp2px(6);
                        outRect.right=DpUtils.dp2px(12);
                    }else{
                        outRect.left=DpUtils.dp2px(12);
                        outRect.right=DpUtils.dp2px(6);
                    }
                }
            }
        });
    }

    private void initData() {
        datas=new ArrayList<>();
        for (int i=0;i<20;i++){
            DataBean dataBean=new DataBean();
            if (new Random().nextInt(4)+1==1){
                dataBean.setType(1);
                dataBean.setTvOne("类型为1的Item->"+i);
            }else if (new Random().nextInt(4)+1==2){
                dataBean.setType(2);
                dataBean.setIvTwo(R.drawable.ic_launcher);
            }else{
                dataBean.setType(3);
                dataBean.setTvThreeOne("类型为3的Item->"+i);
                dataBean.setTvThreeTwo("类型为3的Item->"+i);
                dataBean.setIvThree(R.drawable.ic_launcher);
            }
            datas.add(dataBean);
        }
    }
}
