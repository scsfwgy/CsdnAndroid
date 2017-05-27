package com.common.lib.widget.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.common.lib.R;

/**
 * Created by tiangongyipin on 16/2/22.
 */
public class LoadingPopup extends PopupWindow {

    private Context mContext;

    public LoadingPopup(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View pickPhotoView = LayoutInflater.from(mContext).inflate(R.layout.popup_loading, null);

//        pickPhotoView.getBackground().setAlpha(150);
        setContentView(pickPhotoView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);

    }

}
