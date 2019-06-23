package nju.androidchat.client.hw1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//该类负责根据text获取图片链接 以及 获得图片bitmap两个职责
public class ImageUtil {

    //根据图片URL获取图片
    public static Bitmap getBitMap(String url){
        System.out.println(url);
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //根据text判断是否为图片URL并转化为图片
    public static String isImage(String text) {
        System.out.println(text);
        String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        String pattern = "!\\[]\\{" + REGEX_URL + "\\}";
        boolean flag = Pattern.matches(pattern, text);
        if (flag) {
            Pattern url = Pattern.compile(REGEX_URL);
            Matcher matcher = url.matcher(text);
            if (matcher.find()) {
                String s = matcher.group();
                System.out.println(s);
                return s;
            }
            return "";
        } else {
            return "";
        }
    }
}
