package com.example.mbenben.studydemo.utils;

/**
 * @author MDove on 2018/4/12.
 */
public abstract class SingletonExt<R, W> extends Singleton<R> {

    private W extra;

    @Override
    protected final R create() {
        return create(extra);
    }

    protected abstract R create(W w);

    @Override
    public final R get() {
        return get(extra);
    }

    /**
     * 获取对象
     *
     * @param w 创建对象需要的额外数据
     * @return
     */
    public R get(W w) {
        extra = w;
        return super.get();
    }
}
