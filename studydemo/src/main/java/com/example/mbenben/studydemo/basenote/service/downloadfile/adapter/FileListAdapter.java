package com.example.mbenben.studydemo.basenote.service.downloadfile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.basenote.service.downloadfile.bean.FileInfo;
import com.example.mbenben.studydemo.basenote.service.downloadfile.service.DownloadService2;

import java.util.List;

/**
 * create by luoxiaoke on 2016/4/30 17:02.
 * use for
 */
public class FileListAdapter extends BaseAdapter {
    private Context context;
    private List<FileInfo> fileList;
    private LayoutInflater inflater;

    public FileListAdapter(Context context, List<FileInfo> fileList) {
        this.context = context;
        this.fileList = fileList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileInfo info = fileList.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_downloadfile, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.proText = (TextView) convertView.findViewById(R.id.pro_text);
            holder.start = (Button) convertView.findViewById(R.id.start);
            holder.pasue = (Button) convertView.findViewById(R.id.pause);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

            holder.name.setText(info.getFileName());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int pro = (int) info.getFinish();
        holder.progressBar.setProgress(pro);
        holder.proText.setText(new StringBuffer().append(pro).append("%"));

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.proText.setVisibility(View.VISIBLE);
                Intent intent = new Intent(context, DownloadService2.class);
                intent.setAction(DownloadService2.ACTION_START);
                intent.putExtra("fileinfo", info);
                context.startService(intent);

            }
        });
        holder.pasue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DownloadService2.class);
                intent.setAction(DownloadService2.ACTION_PAUSE);
                intent.putExtra("fileinfo", info);
                context.startService(intent);

            }
        });

        return convertView;
    }

    /**
     * 更新列表进度
     *
     * @param id       id
     * @param progress 进度
     */
    public void updateProgress(int id, long progress) {
        FileInfo fileInfo = fileList.get(id);
        fileInfo.setFinish(progress);
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView name;
        TextView proText;
        Button start;
        Button pasue;
        ProgressBar progressBar;
    }
}
