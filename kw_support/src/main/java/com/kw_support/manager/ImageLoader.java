package com.kw_support.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.kw_support.R;

import java.io.File;

/**
 * Created by g_chen on 15/8/25.
 */
public class ImageLoader {

    private static ImageLoader INSTANCE = null;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoader();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 加载图片 同时缓存全尺寸与改变的尺寸两种图片
     *
     * @param act     glide 支持传入Context Fragment Activity 并且根据后两种的生命周期进行管理，建议传入后两种
     * @param load
     * @param tarView 要显示图片的ImageView
     */
    public void display(Activity act, String load, ImageView tarView) {
        Glide.with(act).load(load).diskCacheStrategy(DiskCacheStrategy.ALL).into(tarView);
    }

    public void display(Activity act, File file, ImageView tarView) {
        Glide.with(act).load(file).diskCacheStrategy(DiskCacheStrategy.ALL).into(tarView);
    }


    /**
     * 加载图片
     * <p/>
     * 自定义图片加载后的大小，默认缓存全部尺寸图片与改变尺寸两种图片
     */
    public void display(Activity act, String load, int resizeWidth, int resizeHeight, ImageView
            tarView) {
        Glide.with(act).load(load).override(resizeWidth, resizeHeight).diskCacheStrategy
                (DiskCacheStrategy.ALL).into(tarView);
    }

    /**
     * 加载图片
     *
     * @param diskCacheStrategy 自选硬盘缓存策略，NONE 不缓存
     */
    public void display(Activity act, String load, DiskCacheStrategy
            diskCacheStrategy, ImageView
                                tarView) {
        Glide.with(act).load(load).diskCacheStrategy(diskCacheStrategy).into(tarView);
    }

    /**
     * 加载图片
     *
     * @param act
     * @param errorRes 加载出错后显示的图片
     */
    public void display(Context act, String load, int errorRes, ImageView tarView) {
        Glide.with(act).load(load).error(errorRes).diskCacheStrategy(DiskCacheStrategy.ALL).into
                (tarView);
    }

    public void display(Activity act, String load, int errorRes, ImageView tarView) {
        Glide.with(act).load(load).error(errorRes).into(tarView);
    }

    /**
     * 加载图片
     *
     * @param listener 图片加载的回调
     */
    public void display(Activity act, String load, ImageView tarView, final OnLoadImageListener
            listener) {
        if (listener == null) {
            return;
        }

        Glide.with(act).load(load).into(new ViewTarget<ImageView, GlideDrawable>(tarView) {

            @Override
            public void onStart() {
                super.onStart();
                listener.onStart();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                listener.onLoadCompleted(resource, glideAnimation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onLoadFailed(e, errorDrawable);
            }
        });
    }

    public void display(Activity act, File file, ImageView tarView, final OnLoadImageListener
            listener) {
        if (listener == null) {
            return;
        }

        Glide.with(act).load(file).into(new ViewTarget<ImageView, GlideDrawable>(tarView) {

            @Override
            public void onStart() {
                super.onStart();
                listener.onStart();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                listener.onLoadCompleted(resource, glideAnimation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onLoadFailed(e, errorDrawable);
            }
        });
    }

    public void display(Activity act, File file, int errorImg, ImageView tarView, final OnLoadImageListener listener) {
        if (listener == null) {
            return;
        }

        Glide.with(act).load(file).error(errorImg).into(new ViewTarget<ImageView, GlideDrawable>
                (tarView) {

            @Override
            public void onStart() {
                super.onStart();
                listener.onStart();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                listener.onLoadCompleted(resource, glideAnimation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onLoadFailed(e, errorDrawable);
            }
        });
    }


    public void display(Fragment fragment, String load, ImageView tarView) {
        Glide.with(fragment).load(load).into(tarView);
    }

    public void display(Fragment fragment, String load, int errorRes, ImageView tarView) {
        Glide.with(fragment).load(load).error(errorRes).into(tarView);
    }

    public void display(Fragment fragment, String load, ImageView tarView, final OnLoadImageListener
            listerner) {
        if (listerner == null) {
            return;
        }

        Glide.with(fragment).load(load).into(new ViewTarget<ImageView, GlideDrawable>(tarView) {

            @Override
            public void onStart() {
                super.onStart();
                listerner.onStart();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                listerner.onLoadCompleted(resource, glideAnimation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listerner.onLoadFailed(e, errorDrawable);
            }
        });
    }

    public void display(Context context, String loadPath, int newWidth, int newHeight, ImageView tarView) {
        Glide.with(context).load(new File(loadPath)).override(newWidth, newHeight).placeholder(R.drawable.default_error).error(R.drawable.default_error).centerCrop().into(tarView);
    }

    public void display(Context context, File loadFile, int newWidth, int newHeight, ImageView tarView) {
        Glide.with(context).load(loadFile).override(newWidth, newHeight).placeholder(R.drawable.default_error).error(R.drawable.default_error).centerCrop().into(tarView);
    }

    public void display(Context context, String load, ImageView tarView, final OnLoadImageListener listener) {
        Glide.with(context).load(load).into(new ViewTarget<ImageView, GlideDrawable>(tarView) {
            @Override
            public void onStart() {
                super.onStart();
                listener.onStart();
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                listener.onLoadCompleted(resource, glideAnimation);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                listener.onLoadFailed(e, errorDrawable);
            }
        });
    }

    public interface OnLoadImageListener {

        void onStart();

        void onLoadCompleted(GlideDrawable resource, GlideAnimation<? super GlideDrawable>
                glideAnimation);

        void onLoadFailed(Exception e, Drawable errorDrawable);
    }
}
