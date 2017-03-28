package com.example.mbenben.studydemo.layout.horizontalscrollview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;

import java.util.List;

public abstract class BaseHorizontalScrollViewAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Integer> datas;


	public abstract int getCount();

	public abstract Object getItem(int position);

	public abstract long getItemId(int position);

	public abstract View getView(int position, View convertView, ViewGroup parent) ;

}
