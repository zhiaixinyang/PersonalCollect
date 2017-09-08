package com.example.mbenben.studydemo.layout.overscroll.activity.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.overscroll.activity.control.DemoContentHelper;
import com.example.mbenben.studydemo.layout.overscroll.activity.control.DemoItem;
import com.example.mbenben.studydemo.layout.overscroll.view.OverScrollDecoratorHelper;

import java.util.List;


/**
 * @author amitd
 */
public class ListViewDemoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.listview_overscroll_demo, null, false);
        initVerticalListView(DemoContentHelper.getSpectrumItems(getResources()), (ListView) fragmentView.findViewById(R.id.list_view));
        return fragmentView;
    }

    private void initVerticalListView(List<DemoItem> content, ListView listView) {
        LayoutInflater appInflater = LayoutInflater.from(getActivity().getApplicationContext());
        ListAdapter adapter = new DemoListAdapter(appInflater, content);
        listView.setAdapter(adapter);

        OverScrollDecoratorHelper.setUpOverScroll(listView);
    }
}
