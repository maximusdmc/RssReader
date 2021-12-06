package com.example.rssreader;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EspacioVertical extends RecyclerView.ItemDecoration {
    int Space;
    public EspacioVertical(int Space){
        this.Space=Space;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left=Space;
        outRect.bottom=Space;
        outRect.right=Space;
        if (parent.getChildLayoutPosition(view)==0){
            outRect.top=Space;
        }
    }
}
