package cz.romario.opensudoku.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: ancheng
 * date: 2017/6/2
 * email: lzjtugjc@163.com
 */

public class GameMenuListAdapter extends RecyclerView.Adapter<GameMenuListAdapter.GameMenuListViewHolder> {

    @Override
    public GameMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(GameMenuListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GameMenuListViewHolder extends RecyclerView.ViewHolder {
        public GameMenuListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
