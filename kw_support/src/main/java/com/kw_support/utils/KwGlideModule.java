package com.kw_support.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.kw_support.constants.LibConfig;

import java.io.File;
import java.io.InputStream;

/**
 * Created by g_chen on 15/8/25.
 */
public class KwGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new DiskCache.Factory() {

            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(new File(FileUtil.getAppCachePath()), LibConfig
                        .DISK_CACHE_CAPACITY);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
