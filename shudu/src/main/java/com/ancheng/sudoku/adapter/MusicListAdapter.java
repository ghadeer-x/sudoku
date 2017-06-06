package com.ancheng.sudoku.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.bean.MusicInfo;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * author: ancheng
 * date: 2017/6/4
 * email: lzjtugjc@163.com
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private List<MusicInfo> mMusicInfos;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private static final String TAG = "MusicListAdapter";
    private String seletMusicUrl;

    public MusicListAdapter(List<MusicInfo> musicInfos, Context context,String musicPath) {
        mMusicInfos = musicInfos;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        seletMusicUrl = musicPath;
    }

    @Override
    public MusicListAdapter.MusicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MusicListViewHolder(mLayoutInflater.inflate(R.layout.item_music_list,parent,false));
    }

    @Override
    public void onBindViewHolder(MusicListAdapter.MusicListViewHolder holder, int position) {
        holder.bindData(mMusicInfos.get(position));
    }

    @Override
    public int getItemCount() {
        if (mMusicInfos != null && mMusicInfos.size() != 0) {
            return mMusicInfos.size();
        } else {
            return 0;
        }
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder {

        public ImageView albumImage;
        public TextView music_title;
        public TextView music_artist;
        public TextView music_duration;
        public ImageView ivSelectMusic;
        public LinearLayout linearLayout;

        public MusicListViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            albumImage = (ImageView) itemView.findViewById(R.id.album_image);
            music_title = (TextView)itemView.findViewById(R.id.music_title);
            music_artist = (TextView)itemView.findViewById(R.id.music_Artist);
            music_duration = (TextView)itemView.findViewById(R.id.music_duration);
            ivSelectMusic = (ImageView)itemView.findViewById(R.id.list_down_button);
        }

        public void bindData(final MusicInfo musicInfo) {
            final String url = musicInfo.getUrl();
            final boolean isSelect = TextUtils.equals(url,seletMusicUrl);

            String albumPath = musicInfo.getAlbum();
            Glide.with(mContext)
                    .load(albumPath)
                    .placeholder(R.drawable.icon_music)
                    .error(R.drawable.icon_music)
                    .into(albumImage);
            music_title.setText(musicInfo.getTitle());
            music_artist.setText(musicInfo.getArtist());
            music_duration.setText(String.valueOf(formatTime(musicInfo.getDuration()))); //显示长度
            if(isSelect){
                ivSelectMusic.setImageResource(R.drawable.icon_select);
            }else {
                ivSelectMusic.setImageResource(R.drawable.icon_nomal);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断当前是否被选中
                    if(isSelect){
                        seletMusicUrl = "";
                    }else{
                        seletMusicUrl = url;
                    }

                    if(mIMusicSelect != null){
                        mIMusicSelect.onMusicSelectLinstener(seletMusicUrl,musicInfo.getTitle());
                    }
                }
            });
        }

        public  String formatTime(Long time){                     //将歌曲的时间转换为分秒的制度
            String min = time / (1000 * 60) + "";
            String sec = time % (1000 * 60) + "";

            if(min.length() < 2)
                min = "0" + min;
            switch (sec.length()){
                case 4:
                    sec = "0" + sec;
                    break;
                case 3:
                    sec = "00" + sec;
                    break;
                case 2:
                    sec = "000" + sec;
                    break;
                case 1:
                    sec = "0000" + sec;
                    break;
            }
            return min + ":" + sec.trim().substring(0,2);
        }
    }
    private IMusicSelect mIMusicSelect;

    public void setOnMusicSelectLinstener(IMusicSelect iMusicSelect){
        mIMusicSelect = iMusicSelect;
    }
    public interface IMusicSelect{
        void onMusicSelectLinstener(String seletMusicUrl, String musicName);
    }
}
