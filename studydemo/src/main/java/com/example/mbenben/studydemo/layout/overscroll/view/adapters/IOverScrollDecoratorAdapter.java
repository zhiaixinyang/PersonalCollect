package com.example.mbenben.studydemo.layout.overscroll.view.adapters;

import android.view.View;

/**
 * @author amitd
 *
 */
public interface IOverScrollDecoratorAdapter {

    View getView();

    /**
     * Is view in it's absolute start position - such that a negative over-scroll can potentially
     * be initiated. For example, in list-views, this is synonymous with the first item_guide_view being
     * fully visible.
     *
     * @return Whether in absolute start position.
     */
    boolean isInAbsoluteStart();

    /**
     * Is view in it's absolute end position - such that an over-scroll can potentially
     * be initiated. For example, in list-views, this is synonymous with the last item_guide_view being
     * fully visible.
     *
     * @return Whether in absolute end position.
     */
    boolean isInAbsoluteEnd();
}
