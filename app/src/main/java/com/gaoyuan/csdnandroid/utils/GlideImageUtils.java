package com.gaoyuan.csdnandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gaoyuan.csdnandroid.R;


public class GlideImageUtils {

    public static void setImage(Context context, ImageView imageView, String path) {
        Glide.with(context).load(path).placeholder(R.drawable.def_loading_img).error(R.drawable.def_err_img).centerCrop().into(imageView);
    }

    public static void setImage(Context context, ImageView imageView, int path) {
        Glide.with(context).load(path).placeholder(R.drawable.def_loading_img).error(R.drawable.def_err_img).centerCrop().into(imageView);
    }

    public static void setCircleImage(final Context context, final ImageView imageView, String path) {
        Glide.with(context).load(path).asBitmap().placeholder(R.drawable.def_loading_img).error(R.drawable.def_err_img).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void setConrnerImage(final Context context, final ImageView imageView, String path) {
        setConrnerImage(context, imageView, path, 4);
    }

    public static void setConrnerImage(final Context context, final ImageView imageView, String path, final int conrnerSize) {
        Glide.with(context).load(path).asBitmap().centerCrop().placeholder(R.drawable.def_loading_img).error(R.drawable.def_err_img).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCornerRadius(conrnerSize == 0 ? 4 : conrnerSize);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
