package com.marcinadd.projecty.message.helper;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.widget.TextView;

public class AvatarHelper {
    public static void setAvatar(TextView textView, String username) {
        // TODO Set avatar from file
        // Generate avatar
        String letter = username.substring(0, 1).toUpperCase();
        textView.setText(letter);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(Color.GRAY);
        textView.setBackground(drawable);
    }
}
