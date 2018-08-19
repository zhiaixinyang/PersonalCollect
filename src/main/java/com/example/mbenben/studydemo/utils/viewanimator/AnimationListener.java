package com.example.mbenben.studydemo.utils.viewanimator;

import android.view.View;

/**
 * Created by florentchampigny on 22/12/2015.
 */
public class AnimationListener {

    private AnimationListener(){}

    public interface Start{
        void onStart();
    }

    public interface Stop{
        void onStop();
    }

    public interface Update<V extends View>{
        void update(V view, float value);
    }
}
