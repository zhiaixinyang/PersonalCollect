package com.example.mbenben.studydemo.layout.horizontalscrollview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.horizontalscrollview.adapter.HorizontalScrollViewAdapter;
import com.example.mbenben.studydemo.layout.horizontalscrollview.view.MyHorizontalScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalScrollActivity extends AppCompatActivity {

	private HorizontalScrollViewAdapter mAdapter;
	private List<DataBean> datas = new ArrayList<>();
	@BindView(R.id.id_content) ImageView imageView;
	@BindView(R.id.id_horizontalScrollView)
	MyHorizontalScrollView myHorizontalScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_horizontal_scroll_view);

		ButterKnife.bind(this);
		init();

		mAdapter = new HorizontalScrollViewAdapter(this, datas);
		//添加滚动回调
		myHorizontalScrollView.setCurrentImageChangeListener(
				new MyHorizontalScrollView.CurrentImageChangeListener() {
					@Override
					public void onCurrentImgChanged(int position,
							View viewIndicator) {
						imageView.setImageResource(datas.get(position).getImageId());
						viewIndicator.setBackgroundColor(Color
								.parseColor("#AA024DA4"));
					}
				});
		//添加点击回调
		myHorizontalScrollView.setOnItemClickListener(
				new MyHorizontalScrollView.OnItemClickListener() {
			@Override
			public void onClick(View view, int position) {
				imageView.setImageResource(datas.get(position).getImageId());
				view.setBackgroundColor(Color.parseColor("#AA024DA4"));
			}
		});
		//设置适配器
		myHorizontalScrollView.setAdapter(mAdapter);
	}

	private void init() {
		datas.add(new DataBean(R.drawable.pintu,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pic2,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pic3,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pizza,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pintu,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pic2,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pic3,"我是一个图片"));
		datas.add(new DataBean(R.drawable.pizza,"我是一个图片"));
	}

}
