package com.xuechuan.xcedu.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderErrorReason;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderProgressListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderSpeedListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderStartListener;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.player.util.PolyvErrorMessageUtils;
import com.xuechuan.xcedu.ui.net.NetBookMyInfomActivity;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DownNetBookAdapter extends BaseAdapter {
    private static final String TAG = DownNetBookAdapter.class.getSimpleName();
    private static final String DOWNLOADED = "已下载";
    private static final String DOWNLOADING = "正在下载";
    private static final String PAUSEED = "暂停下载";
    private static final String WAITED = "等待下载";

    private static Context appContext;
    private final DownVideoDb mDownVideo;
    private Context context;
    private ListView lv_download;
    private List<DownVideoVo> lists;
    private LayoutInflater inflater;
    private final DbHelperDownAssist mDao;

    public DownNetBookAdapter(DownVideoDb lists, Context context, ListView lv_download) {
        this.lists = lists.getDownlist();
        this.mDownVideo=lists;
        this.context = context;
        appContext = context.getApplicationContext();
        this.inflater = LayoutInflater.from(this.context);
        this.lv_download = lv_download;
        mDao = DbHelperDownAssist.getInstance();
        init();
    }

    private void init() {
        for (int i = 0; i < lists.size(); i++) {
            DownVideoVo downloadInfo = lists.get(i);
            String vid = downloadInfo.getVid();
            int bitrate = Integer.parseInt(downloadInfo.getBitRate());
            PolyvDownloaderManager.getPolyvDownloader(vid, bitrate);
        }
    }

    @Override
    public int getCount() {
        return lists==null?0:lists.size();
    }

    @Override
    public Object getItem(int position) {
        return  lists==null?0:lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lists==null?0:position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
