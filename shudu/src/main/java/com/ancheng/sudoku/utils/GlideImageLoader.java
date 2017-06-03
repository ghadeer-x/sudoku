package com.ancheng.sudoku.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.ancheng.sudoku.R;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * author: ancheng
 * date: 2017/6/1
 * email: lzjtugjc@163.com
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//        Picasso.with(activity)//
//                .load(Uri.fromFile(new File(path)))//
//                .placeholder(R.mipmap.default_image)//
//                .error(R.mipmap.default_image)//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
