package cz.romario.opensudoku.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cz.romario.opensudoku.R;

public class GameMenuListActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvStatus;
    RecyclerView rvGameMenuList;
    ImageView ivBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu_list);

        initView();
        initListener();

    }

    private void initListener() {
        ivBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        rvGameMenuList = (RecyclerView) findViewById(R.id.rv_game_menu_list);
        ivBlack = (ImageView) findViewById(R.id.iv_back);


        rvGameMenuList.setLayoutManager(new LinearLayoutManager(this));

//        rvGameMenuList.setAdapter();
    }

}
