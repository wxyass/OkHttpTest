package com.okhttp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 同步请求
        // getDatasync();
        // 异步请求
        asyRequest();
    }

    private void asyRequest() {
        // 1 创建OkHttpClient客户端类
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        // 2 创建请求报文信息Request对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();

        // 3 将Request封装成Call对象,Call对象可理解为连接Request与Response的桥梁
        Call call = client.newCall(request);

        // 4 调用call.enqueue()发送异步请求,在Callback中获取响应报文信息Response对象
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Fail");
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                // 5 打印response.body().string()
                System.out.println("asy==" + response.body().string());
            }
        });
    }


    // 同步请求必须放在子线程中,防止阻塞线程
    public void getDatasync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncRequest();
            }
        }).start();
    }

    // 发起同步请求
    public void syncRequest() {
        // 1 创建OkHttpClient客户端类
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        // 2 创建请求报文信息Request对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .get()
                .build();

        // 3 将Request封装成Call对象,Call对象可理解为连接Request与Response的桥梁
        Call call = client.newCall(request);

        try {
            // 4 调用call.execute()发送同步请求,获取响应报文信息Response对象
            Response response = call.execute();

            if (response.isSuccessful()) {
                // 5 打印response.body().string()
                System.out.println("sync==" + response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
