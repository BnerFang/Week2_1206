package com.umeng.soexample.util;

import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 联网的工具类
 */

public class HttpUtil {

    private final int HTTP_SUCCESS=1000;//成功

    private final int HTTP_FAIL=1001;//失败

    public HttpUtil(){}

    //get请求
    public HttpUtil get(String url){
        doHttp(url,"GET",null);
        return this;
    }

    //post
    public HttpUtil post(String url, String string){
        doHttp(url,"POST",string);
        return this;
    }

    //网络请求
    private void doHttp(final String url,final String method,final String string){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL u=new URL(url);
                    HttpURLConnection connection= (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(5000);
                    if("POST".equals(method)){
                        //post请求添加参数
                        PrintWriter writer=new PrintWriter(connection.getOutputStream());
                        writer.write(string);
                        writer.flush();
                        writer.close();
                    }
                    connection.connect();
                    int code=connection.getResponseCode();

                    Message message=Message.obtain();
                    if(code==HttpURLConnection.HTTP_OK){
                        message.what=HTTP_SUCCESS;
                        InputStream is = connection.getInputStream();
                        String data=convertStream2String(is);
                        message.obj=data;
                    }else{
                        message.what=HTTP_FAIL;
                    }

                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HTTP_SUCCESS://成功
                    String data= (String) msg.obj;
                    listener.success(data);
                    break;
                case HTTP_FAIL://失败
                    listener.fail();
                    break;
            }
        }
    };


    private HttpListener listener;
    public void result(HttpListener listener){
        this.listener=listener;
    }


    public interface HttpListener{
        void success(String data);
        void fail();
    }

    public  String convertStream2String(InputStream input){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// 自带缓存的输出流
        int len=-1;
        byte [] buffer = new byte[512];
        try {
            while((len = input.read(buffer))!=-1){
                baos.write(buffer, 0, len); // 将读到的字节，写入baos
            }
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


