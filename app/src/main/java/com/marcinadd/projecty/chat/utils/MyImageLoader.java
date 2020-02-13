package com.marcinadd.projecty.chat.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.marcinadd.projecty.helper.ServerHelper;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;

import okhttp3.OkHttpClient;

public class MyImageLoader implements ImageLoader {
    private Context context;
    private OkHttpClient client;

    public MyImageLoader(Context context, OkHttpClient client) {
        this.context = context;
        this.client = client;
    }

    @Override
    public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
        Picasso.Builder builder = new Picasso.Builder(context);
        Picasso picasso = builder.downloader(new OkHttp3Downloader(client)).build();

        if (url != null && !url.isEmpty()) {
//            variable url is username in this case
            picasso.load(ServerHelper.getServerURL(context) + "/user/" + url + "/avatar").into(imageView);
        }
    }
}
