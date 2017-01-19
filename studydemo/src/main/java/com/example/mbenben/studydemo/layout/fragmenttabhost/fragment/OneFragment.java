package com.example.mbenben.studydemo.layout.fragmenttabhost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.SDCardUtils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by MBENBEN on 2016/9/26.
 */
public class OneFragment extends Fragment{
    private Button btn_rxvolley;
    private TextView tv_content;
    private String path="http://120.27.4.196:8080/test/servlet/ShowServlet";
    private String pathSend="http://120.27.4.196:8080/test/servlet/SendServlet?username=";
    private String postSend="http://120.27.4.196:8080/test/servlet/SendServlet";
    private EditText et_username,et_password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fth_fragment_one,container,false);
        btn_rxvolley= (Button) view.findViewById(R.id.btn_rxvolley);
        tv_content= (TextView) view.findViewById(R.id.tv_content);
        et_username= (EditText) view.findViewById(R.id.et_username);
        et_password= (EditText) view.findViewById(R.id.et_password);

        return view;
    }

}
