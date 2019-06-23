package nju.androidchat.client.hw1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ItemImageReceive extends LinearLayout{
    @StyleableRes
    int index0 = 0;

    private ImageView imageView;
    private TextView textView;
    private Context context;
    private UUID messageId;
    @Setter
    private OnRecallMessageRequested onRecallMessageRequested;

    public ItemImageReceive(Context context, String text, UUID messageId) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item_image_receive, this);
        this.imageView = findViewById(R.id.chat_item_content_image);
        this.textView = findViewById(R.id.chat_item_content_warning);
        this.messageId = messageId;
        setImage(text);
    }

    public void init(Context context) {

    }

    public void setImage(String url) {
        new Thread(() -> {
            // TODO Auto-generated method stub
            final Bitmap bitmap = getBitMap(url);
            if (bitmap == null) {
                imageView.post(() -> {
                    imageView.setVisibility(View.GONE);
                });
                Looper.prepare();
                Toast.makeText(context, "收到无效图片信息", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                imageView.post(() -> {
                    // TODO Auto-generated method stub
                    imageView.setImageBitmap(bitmap);
                    textView.setVisibility(View.GONE);
                });

            }
        }).start();
    }
}
