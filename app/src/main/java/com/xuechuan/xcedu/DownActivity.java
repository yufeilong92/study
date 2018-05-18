package com.xuechuan.xcedu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderErrorReason;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderProgressListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderSpeedListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderStartListener;
import com.xuechuan.xcedu.utils.L;

public class DownActivity extends AppCompatActivity implements View.OnClickListener {

    private String vid = "d740a563579313bc28f551eca7443902_d";
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        initView();

    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:

                break;
        }
    }
}
