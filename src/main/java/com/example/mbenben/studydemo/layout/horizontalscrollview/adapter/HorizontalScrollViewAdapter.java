package com.example.mbenben.studydemo.layout.horizontalscrollview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.horizontalscrollview.DataBean;

public class HorizontalScrollViewAdapter extends BaseHorizontalScrollViewAdapter{

	private LayoutInflater inflater;
	private List<DataBean> datas;

	public HorizontalScrollViewAdapter(Context context, List<DataBean> datas) {
		inflater = LayoutInflater.from(context);
		this.datas = datas;
	}

	@Override
	public int getCount()
	{
		return datas.size();
	}

	@Override
	public Object getItem(int position)
	{
		return datas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.item_horizontal_scroll_view, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imageView.setImageResource(datas.get(position).getImageId());
		viewHolder.textView.setText(datas.get(position).getText());

		return convertView;
	}

	private class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
}
