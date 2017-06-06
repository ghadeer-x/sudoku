package com.ancheng.sudoku.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.MusicListAdapter;
import com.ancheng.sudoku.constant.GameSetting;
import com.ancheng.sudoku.model.bean.MusicInfo;
import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicListActivity extends AppCompatActivity {

    @BindView(R.id.civ_music_list)
    RecyclerView civMusicList;
    private List<MusicInfo> mMusicInfos;
    private MusicListAdapter mMusicListAdapter;
    private String mSelectMusicPath;
    private static final String TAG = "MusicListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData() {
        mMusicInfos.clear();
        mMusicInfos.addAll(getMusicInfos(getContentResolver()));
        mMusicListAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mSelectMusicPath = GameSetting.getMusicPath();
        mMusicInfos = new ArrayList<>();
        civMusicList.setLayoutManager(new LinearLayoutManager(this));
        mMusicListAdapter = new MusicListAdapter(mMusicInfos, this,mSelectMusicPath);
        mMusicListAdapter.setOnMusicSelectLinstener(new MusicListAdapter.IMusicSelect() {
            @Override
            public void onMusicSelectLinstener(String seletMusicUrl, String musicName) {
                mMusicListAdapter.notifyDataSetChanged();
                //保存url和歌曲名字
                LogUtils.tag(TAG).d(seletMusicUrl);
                GameSetting.putMusicPath(seletMusicUrl);
                GameSetting.putMusicName(musicName);
            }
        });
        civMusicList.setAdapter(mMusicListAdapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    public List<MusicInfo> getMusicInfos(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<MusicInfo> MusicInfos = new ArrayList<MusicInfo>();
        for (int i = 0; i < cursor.getCount(); i++) {
            MusicInfo MusicInfo = new MusicInfo();                               //新建一个歌曲对象,将从cursor里读出的信息存放进去,直到取完cursor里面的内容为止.
            cursor.moveToNext();


            long id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id

            String title = cursor.getString((cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

            String album = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片

            long album_id = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID

            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

            if (isMusic != 0 && duration / (1000 * 60) >= 1) {     //只把1分钟以上的音乐添加到集合当中
                MusicInfo.setId(id);
                MusicInfo.setTitle(title);
                MusicInfo.setArtist(artist);
                MusicInfo.setDuration(duration);
                MusicInfo.setSize(size);
                MusicInfo.setUrl(url);
                MusicInfo.setAlbum(album);
                MusicInfo.setAlbum_id(album_id);
                MusicInfos.add(MusicInfo);
            }
        }
        return MusicInfos;
    }
}
