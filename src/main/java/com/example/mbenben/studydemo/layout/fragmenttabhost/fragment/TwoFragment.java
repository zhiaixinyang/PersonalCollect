package com.example.mbenben.studydemo.layout.fragmenttabhost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.fragmenttabhost.bean.WebConfig;
import com.example.mbenben.studydemo.layout.fragmenttabhost.server.TestHttpServer;
import com.example.mbenben.studydemo.utils.NetUtil;

/**
 * Created by MBENBEN on 2016/9/26.
 */
public class TwoFragment extends Fragment{
    private TestHttpServer ths;
    private TextView tv_webserver;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fth_fragment_two,container,false);
        tv_webserver= (TextView) view.findViewById(R.id.tv_webserver);
        if (NetUtil.isNetworkAvailable(App.getInstance().getContext())){
            tv_webserver.setText("本服务器的IP:"+ NetUtil.getWifiLocalIP());
        }
        WebConfig wcf=new WebConfig();
        wcf.setMax(50);
        wcf.setPort(8088);
         ths=new TestHttpServer(wcf);
        ths.startAsync();
        return view;
    }

    @Override
    public void onDestroy() {
        ths.stopAsync();
        super.onDestroy();
    }
}
