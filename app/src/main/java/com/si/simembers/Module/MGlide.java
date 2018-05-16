package com.si.simembers.Module;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.si.simembers.Base.BaseApplication;

/**
 * Created by byun on 2018-03-29.
 */

public class MGlide implements GlideModule {
    private static final String PARAMS = "RS=450&SP=1&FQ=1&QT=90";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        int cacheSize100MegaBytes = 104857600;

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes))
                .setMemoryCache(new LruResourceCache(customMemoryCacheSize))
                .setBitmapPool(new LruBitmapPool(customBitmapPoolSize))
                .setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    public static DrawableTypeRequest<String> load(String url) {
        return load(BaseApplication.getContext(), url);
    }

    public static DrawableTypeRequest<String> load(Context context, String url) {
        Uri uri = TextUtils.isEmpty(url) ? null : Uri.parse(url);

        if (uri != null) {
            if (TextUtils.isEmpty(uri.getQuery())) {
                url += url.endsWith("?") ? PARAMS : "?" + PARAMS;
            } else {
                String rs = uri.getQueryParameter("RS");

                if (TextUtils.isEmpty(rs)) {
                    url += url.endsWith("&") ? PARAMS : "&" + PARAMS;
                } else {
                    int rsValue = Integer.valueOf(rs);
                    if (450 < rsValue) {
                        url = url.replace("RS=" + rs, "RS=450");
                    }
                }
            }
        }

        return Glide.with(context).load(url);
    }
}