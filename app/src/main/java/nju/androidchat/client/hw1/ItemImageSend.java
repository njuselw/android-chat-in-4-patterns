package nju.androidchat.client.hw1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.StyleableRes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import lombok.Setter;
import nju.androidchat.client.R;
import nju.androidchat.client.component.OnRecallMessageRequested;

import static nju.androidchat.client.hw1.ImageUtil.getBitMap;

//图片发送类，类似于ItemTextSend，处理图片显示
public class ItemImageSend extends LinearLayout implements View.OnLongClickListener {
    @StyleableRes
    int index0 = 0;

    private ImageView imageView;
    private Context context;
    private UUID messageId;
    @Setter
    private OnRecallMessageRequested onRecallMessageRequested;

    public ItemImageSend(Context context, String text, UUID messageId, OnRecallMessageRequested onRecallMessageRequested) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item_image_send, this);
        this.imageView = findViewById(R.id.chat_item_content_image);
        this.messageId = messageId;
        this.onRecallMessageRequested = onRecallMessageRequested;

        this.setOnLongClickListener(this);
        setImage(text);
    }

    public void setImage(String url) {
        new Thread(() -> {
            // TODO Auto-generated method stub
            final Bitmap bitmap = getBitMap(url);
            imageView.post(() -> {
                // TODO Auto-generated method stub
                imageView.setImageBitmap(bitmap);
            });
        }).start();
    }



    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要撤回这条消息吗？")
                .setPositiveButton("是", (dialog, which) -> {
                    if (onRecallMessageRequested != null) {
                        onRecallMessageRequested.onRecallMessageRequested(this.messageId);
                    }
                })
                .setNegativeButton("否", ((dialog, which) -> {
                }))
                .create()
                .show();

        return true;


    }
}
