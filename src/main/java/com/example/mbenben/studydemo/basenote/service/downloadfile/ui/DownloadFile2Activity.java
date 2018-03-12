package com.example.mbenben.studydemo.basenote.service.downloadfile.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.service.downloadfile.adapter.FileListAdapter;
import com.example.mbenben.studydemo.basenote.service.downloadfile.bean.FileInfo;
import com.example.mbenben.studydemo.basenote.service.downloadfile.service.DownloadService2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadFile2Activity extends BaseActivity {

    private static final String TAG = "DownloadFile2Activity";

    private static final String EXTRA_ACTION = "extra_action";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DownloadFile2Activity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }

    @BindView(R.id.list)
    ListView list;

    private List<FileInfo> fileList;
    private FileListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_downloadfile2);
        ButterKnife.bind(this);

        initData();
        initSetup();
        initRegister();
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    private void initRegister() {
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService2.ACTION_UPDATE);
        filter.addAction(DownloadService2.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 添加数据
     */
    private void initData() {
        fileList = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo(0, "http://dldir1.qq.com/weixin/android/weixin6316android780.apk",
                "weixin.apk", 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, "http://111.202.99.12/sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
                "qq.apk", 0, 0);
        FileInfo fileInfo3 = new FileInfo(2, "http://www.imooc.com/mobile/imooc.apk",
                "imooc.apk", 0, 0);
        FileInfo fileInfo4 = new FileInfo(3, "http://www.imooc.com/download/Activator.exe",
                "Activator.exe", 0, 0);
        fileList.add(fileInfo1);
        fileList.add(fileInfo2);
        fileList.add(fileInfo3);
        fileList.add(fileInfo4);
    }


    private void initSetup() {
        //创建适配器
        listAdapter = new FileListAdapter(this, fileList);
        //给listview设置适配器
        list.setAdapter(listAdapter);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService2.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                Log.e(TAG, "finished==" + finished);
                Log.e(TAG, "id==" + id);
                listAdapter.updateProgress(id, finished);
                //progressBar.setProgress(finished);
            } else if (DownloadService2.ACTION_FINISHED.equals(intent.getAction())) {
                FileInfo fileinfo = (FileInfo) intent.getSerializableExtra("fileinfo");
                //更新进度为100
                listAdapter.updateProgress(fileinfo.getId(), 100);
                Toast.makeText(
                        DownloadFile2Activity.this,
                        fileinfo.getFileName() + "下载完成",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


}
