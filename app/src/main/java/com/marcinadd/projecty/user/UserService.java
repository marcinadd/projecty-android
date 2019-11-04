package com.marcinadd.projecty.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserService {
    public static void setSidebarData(View headerView, Context context) {
        TextView title = headerView.findViewById(R.id.nav_header_title);
        TextView subTitle = headerView.findViewById(R.id.nav_header_subtitle);
        ImageView imageView = headerView.findViewById(R.id.nav_header_avatar);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        title.setText(sharedPreferences.getString("username", ""));
        Bitmap avatar = Bitmap.createScaledBitmap(getAvatarFromCache(context), 200, 200, true);
        if (avatar != null) {
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), avatar);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
    }


    public static ResponseBody getAvatar(String username, Context mContext) throws IOException {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(mContext);
        ApiUser apiUser = retrofit.create(ApiUser.class);
        Response<ResponseBody> response = apiUser.getUserAvatar(username).execute();
        if (response.isSuccessful())
            return response.body();
        return null;
    }

    public static boolean saveAvatarInCache(ResponseBody body, Context mContext) {
        try {
            File file = new File(mContext.getCacheDir(), "avatar.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) break;
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static Bitmap getAvatarFromCache(Context mContext) {
        File file = new File(mContext.getCacheDir(), "avatar.png");
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }
}
