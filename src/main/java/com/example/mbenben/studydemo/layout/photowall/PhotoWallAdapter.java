package com.example.mbenben.studydemo.layout.photowall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * Created by MDove on 2017/9/13.
 */

public class PhotoWallAdapter extends RecyclerView.Adapter<PhotoWallAdapter.ViewHolder> {

    private LruCache<String, Bitmap> lruCache;
    private String[] images;
    private OkHttpClient client;
    private Context context;
    private RecyclerView recyclerView;

    private Set<LoaderAsyncTask> loaders;

    public PhotoWallAdapter(Context context, String[] images, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.images = images;
        client = new OkHttpClient();
        loaders = new HashSet<>();

        int allocationMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache<String, Bitmap>(allocationMaxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (newState == SCROLL_STATE_IDLE) {
                        loadBitmaps(firstItemPosition, lastItemPosition - firstItemPosition);
                    } else {
                        cancelAllTasks();
                    }
                }
            }

        });
    }

    private void cancelAllTasks() {
        if (loaders != null) {
            for (LoaderAsyncTask task : loaders) {
                task.cancel(false);
            }
        }
    }

    private void loadBitmaps(int firstItemPosition, int count) {
        for (int i = firstItemPosition; i < firstItemPosition + count; i++) {
            String url = images[i];
            Bitmap bitmap = lruCache.get(url);
            if (bitmap == null) {
                LoaderAsyncTask task = new LoaderAsyncTask();
                loaders.add(task);
                task.execute(url);
            } else {
                ImageView iv = (ImageView) recyclerView.findViewWithTag(url);
                iv.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_photo_wall, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = images[position];
        Bitmap bitmap = lruCache.get(url);
        holder.imageView.setTag(url);
        if (bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.pictures_no);
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    Bitmap bitmap = null;

    public class LoaderAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                bitmap = BitmapFactory.decodeStream(client.newCall(request).execute().body().byteStream());
                if (bitmap != null) {
                    lruCache.put(url, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                ImageView iv = (ImageView) recyclerView.findViewWithTag(url);
                iv.setImageBitmap(bitmap);
            }
        }
    }
}
