package com.example.mbenben.studydemo.view.pathview.svgpath;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.view.pathview.svgpath.res.StoreHousePath;
import com.example.mbenben.studydemo.view.pathview.svgpath.utils.PathAnimView;
import com.example.mbenben.studydemo.view.pathview.svgpath.utils.PathParserUtils;
import com.example.mbenben.studydemo.view.pathview.svgpath.utils.SvgPathParser;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/13.
 */
/**
 * 介绍：一个路径动画的View
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * <p>
 * 通过mPathAnimHelper 对SourcePath做动画：
 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 *
 * 原作者项目GitHub：https://github.com/mcxtzhang/PathAnimView
 */
public class SVGPathActivity extends AppCompatActivity{
    @BindView(R.id.storeView3) PathAnimView storeView3;
    @BindView(R.id.namePath) PathAnimView namePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svgpath);
        ButterKnife.bind(this);
        namePath.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("ZhaoJing", 1.1f, 16)));
        namePath.getPathAnimHelper().setAnimTime(5000);
        namePath.startAnim();

        SvgPathParser svgPathParser = new SvgPathParser();
        String svgPath = getString(R.string.svg_path);
        try {
            Path path = svgPathParser.parsePath(svgPath);
            storeView3.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        storeView3.getPathAnimHelper().setAnimTime(5000);
        storeView3.startAnim();
    }
}
