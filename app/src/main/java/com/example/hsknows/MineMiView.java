package com.example.hsknows;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class MineMiView extends ViewGroup {
    public MineMiView(Context context){
        super(context, null, 0);
    }
    public MineMiView(Context context, AttributeSet attrs){
        super(context, attrs, 0);
    }
    public MineMiView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        Context mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
