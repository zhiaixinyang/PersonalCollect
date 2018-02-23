package com.example.mbenben.studydemo.utils;

/**
 * @author MDove on 18/2/15
 */
public abstract class Singleton<T> {
    private volatile T mInstance;

    protected abstract T create();

    public final T get() {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create();
                }

            }
        }
        return mInstance;
    }

    public final void release() {
        mInstance = null;
    }
}
