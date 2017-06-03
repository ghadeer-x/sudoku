package com.ancheng.sudoku.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ancheng.sudoku.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.wb_help)
    WebView wbHelp;
    public static final String webUrl = "http://baike.baidu.com/item/%E6%95%B0%E7%8B%AC%E6%8A%80%E5%B7%A7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        initWebView();
    }

    private void initWebView() {
        wbHelp.loadUrl(webUrl);
        wbHelp.setWebViewClient(new WebViewClient());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
