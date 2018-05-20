package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;

import java.util.List;

public class DownNetBookAdapter extends BaseAdapter {
    private static final String TAG = DownNetBookAdapter.class.getSimpleName();
    private static final String DOWNLOADED = "已下载";
    private static final String DOWNLOADING = "正在下载";
    private static final String PAUSEED = "暂停下载";
    private static final String WAITED = "等待下载";

    private static Context appContext;
    private final DownVideoDb mDownVideo;
    private Context mContext;
    private ListView lv_download;
    private List<DownVideoVo> lists;
    private LayoutInflater mInflater;
    private final DbHelperDownAssist mDao;

    public DownNetBookAdapter(Context context,DownVideoDb lists,  ListView lv_download) {
        this.lists = lists.getDownlist();
        this.mDownVideo=lists;
        this.mContext = context;
        appContext = context.getApplicationContext();
        this.mInflater = LayoutInflater.from(this.mContext);
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
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.item_net_down_infom,null);

        }
        return null;
    }
}
