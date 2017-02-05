package com.example.arpitdec5.popularmovies.utils;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arpitdec5 on 16-04-2016.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    public ItemOffsetDecoration(int itemOffset){

        mSpace = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = mSpace;
    }
}
