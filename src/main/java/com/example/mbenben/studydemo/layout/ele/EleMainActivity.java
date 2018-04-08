package com.example.mbenben.studydemo.layout.ele;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.cuboid.CuboidBtnActivity;

/**
 * Created by MDove on 2017/1/3.
 *
 * 原作者项目GitHub：https://github.com/githubwing/ZoomHeader
 */

public class EleMainActivity extends BaseActivity {
  private static final String ACTION_EXTRA = "action_extra";

  public static void start(Context context, String title) {
    Intent intent = new Intent(context, EleMainActivity.class);
    intent.putExtra(ACTION_EXTRA, title);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getIntent().getStringExtra(ACTION_EXTRA));
    setContentView(R.layout.activity_ele_main);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    recyclerView.setAdapter(new ListAdapter());
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  protected boolean isNeedCustomLayout() {
    return false;
  }

  class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());

      return new ViewHolder(inflater.inflate(R.layout.item_ele_main, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(EleMainActivity.this, EleViewPagerActivity.class);
          startActivity(intent);
        }
      });
    }

    @Override public int getItemCount() {
      return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

      public ViewHolder(View view) {
        super(view);
      }
    }
  }
}
