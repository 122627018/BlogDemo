package com.wxxiaomi.ming.blogdemo;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 12262 on 2016/5/22.
 */
public class engine {
    public static Observable<String> getBlog(final String url){
       return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                HttpURLConnection conn = null;
                try {
                    URL realUrl = new URL(url);
                    conn = (HttpURLConnection) realUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setUseCaches(false);
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);
                    conn.setInstanceFollowRedirects(false);
//                    conn.setRequestProperty("Accept-Charset", "GBK");
//                    conn.setRequestProperty("contentType", "GBK");
//                    conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//                    conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//                    conn.setRequestProperty("Accept-Encoding","gzip, deflate");
                    conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
                    int code = conn.getResponseCode();

                    Log.i("wang", "code=" + code);
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
//                        java.io.ByteArrayOutputStream baos =
//                                new java.io.ByteArrayOutputStream();

//                        int buffer = 1024;
//                        byte[] b = new byte[buffer];
//                        int n = 0;
//                        while ((n = is.read(b, 0, buffer)) > 0) {
//                            baos.write(b, 0, n);
//                        }
//                        String result = new String(baos.toByteArray(),"utf-8");
                        BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ((line = in.readLine()) != null){
                            buffer.append(line);
                        }
                        String result = buffer.toString();
//                        String result = "";
//                        InputStreamReader isr = new InputStreamReader(is);
//                        BufferedReader bufferReader = new BufferedReader(isr);
//                        String inputLine  = "";
//                        while((inputLine = bufferReader.readLine()) != null){
//                            result += inputLine + "\n";
//                        }
                        Log.i("wang", "result=" + result);
                        subscriber.onNext(result);
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
