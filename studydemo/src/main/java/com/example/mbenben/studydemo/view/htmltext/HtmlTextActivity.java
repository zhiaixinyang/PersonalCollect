package com.example.mbenben.studydemo.view.htmltext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.example.mbenben.studydemo.MainActivity;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.htmltext.views.ClickableTableSpan;
import com.example.mbenben.studydemo.view.htmltext.views.DrawTableLinkSpan;
import com.example.mbenben.studydemo.view.htmltext.views.HtmlResImageGetter;
import com.example.mbenben.studydemo.view.htmltext.views.HtmlTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MBENBEN on 2017/1/15.
 */

public class HtmlTextActivity extends AppCompatActivity{
    @BindView(R.id.html_text) HtmlTextView htmlTextView;
    @BindView(R.id.webView) WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmltext);
        ButterKnife.bind(this);
        InputStream is = getResources().openRawResource(R.raw.example);
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is,"utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        String str = "";

        try {
            while((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String html = sb.toString();
        webView.loadDataWithBaseURL(null,html, "text/html",  "utf-8", null);
        //htmlTextView.setHtml(R.raw.example, new HtmlResImageGetter(htmlTextView));
    }

}
