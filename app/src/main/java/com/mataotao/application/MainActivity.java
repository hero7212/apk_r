package com.mataotao.application;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fanneng.android.web.SuperWebX5;
import com.fanneng.android.web.client.ChromeClientCallbackManager;
import com.fanneng.android.web.client.DefaultWebClient;
import com.fanneng.android.web.client.WebDefaultSettingsManager;
import com.fanneng.android.web.client.WebSettings;
import com.fanneng.android.web.file.DownLoadResultListener;
import com.fanneng.android.web.progress.BaseIndicatorView;
import com.fanneng.android.web.utils.PermissionInterceptor;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String originalUrl = "https://mataotao.njshengxie.com/";
    private SuperWebX5 mSuperWebX5;
    private LinearLayout webContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webContainer = findViewById(R.id.web_container);
        initWebView();
    }

    private void initWebView() {
        mSuperWebX5 = SuperWebX5.with(this)
                .setSuperWebParent(webContainer, new LinearLayout.LayoutParams(-1, -1))
                .customProgress(getIndicatorView())
                .setWebSettings(getSettings())
                .setWebViewClient(webViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setReceivedTitleCallback(mCallback)
                .setPermissionInterceptor(mPermissionInterceptor)
                .setNotifyIcon(R.mipmap.download)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownScheme()
                .openParallelDownload()
                .setSecutityType(SuperWebX5.SecurityType.strict)
                .addDownLoadResultListener(mDownLoadResultListener)
                .createSuperWeb()
                .ready()
                .go(originalUrl);


    }


    protected BaseIndicatorView getIndicatorView() {
        return null;
    }

    /**
     * 文件下载结果监听
     */
    protected DownLoadResultListener mDownLoadResultListener = new DownLoadResultListener() {
        @Override
        public void success(String path) {
            Toast.makeText(MainActivity.this, "下载成功,存储地址:" + path, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void error(String path, String resUrl, String cause, Throwable e) {
            Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 权限拦截器
     * 在触发某些敏感的 Action 时候会回调该方法， 比如定位触发 。
     * 例如 https//:www.baidu.com 该 Url 需要定位权限， 返回false ，如果版本大于等于23 ， 会动态申请权限 ，true 该Url对应页面请求定位失败。
     * 该方法是每次都会优先触发的 ， 开发者可以做一些敏感权限拦截
     */
    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            return false;
        }
    };

    /**
     * 获取WebView设置
     */
    public WebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
            return super.shouldOverrideUrlLoading(webView, s);
        }
    };

    /**
     * 获取进度条
     */
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

    };

    /**
     * 获取网页title
     */
    protected ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {

        }
    };

    @Override
    public void onBackPressed() {
        if (mSuperWebX5.back()) {
            return;
        }
        super.onBackPressed();
    }
}
