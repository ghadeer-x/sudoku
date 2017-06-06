package com.ancheng.sudoku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.adapter.GameMenuListAdapter;
import com.apkfuns.logutils.LogUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.romario.opensudoku.db.SudokuColumns;
import cz.romario.opensudoku.db.SudokuDatabase;
import cz.romario.opensudoku.game.CellCollection;
import cz.romario.opensudoku.game.SudokuGame;
import cz.romario.opensudoku.gui.SudokuListFilter;

/**
 * author: ancheng
 * date: 2017/6/3
 * email: lzjtugjc@163.com
 */

public class GameListActivity extends AppCompatActivity {
    private static final String FILTER_STATE_NOT_STARTED = "filter" + SudokuGame.GAME_STATE_NOT_STARTED;
    private static final String FILTER_STATE_PLAYING = "filter" + SudokuGame.GAME_STATE_PLAYING;
    private static final String FILTER_STATE_SOLVED = "filter" + SudokuGame.GAME_STATE_COMPLETED;
    public static final String EXTRA_FOLDER_ID = "folder_id";

    private Cursor mCursor;
    private long mFolderID;
    private SudokuListFilter mListFilter;
    private SudokuDatabase mDatabase;
    private static final String TAG = "GameListActivity";
    private List<SudokuGame> mSudokuGames;


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rv_game_list)
    RecyclerView rvGameList;
    private GameMenuListAdapter mMenuListAdapter;
    private int mGameCount;
    private long gameLevel;
    private int mCompleteCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
        mSudokuGames = new ArrayList<>();
        parseIntent();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.tag(TAG).d("onStart jjjjjjjjjjjjjjjjj");
        initData();
    }

    private void initView() {
        //设置标题
        String titleText = "";
        gameLevel = mFolderID;
        // LogUtils.tag(TAG).d("mFoloderID : " + mFolderID);
        if (mFolderID == 1) {
            titleText = "初级";
        } else if (mFolderID == 2) {
            titleText = "中级";
        } else if (mFolderID == 3) {
            titleText = "高级";
        }
        tvTitle.setText(titleText);
        //设置状态


        rvGameList.setLayoutManager(new LinearLayoutManager(this));
        mMenuListAdapter = new GameMenuListAdapter(mSudokuGames, this, getSupportFragmentManager(), mDatabase,gameLevel);
        mMenuListAdapter.setOnResetGameListener(new GameMenuListAdapter.IResetGame() {
            @Override
            public void onResetGameListener(long gameID) {
                SudokuGame game = mDatabase.getSudoku(gameID);
                if (game != null) {
                    game.reset();
                    mDatabase.updateSudoku(game);
                }
                initData();
            }
        });
        rvGameList.setAdapter(mMenuListAdapter);
    }

    private void initData() {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mListFilter = new SudokuListFilter(getApplicationContext());
        mListFilter.showStateNotStarted = settings.getBoolean(FILTER_STATE_NOT_STARTED, true);
        mListFilter.showStatePlaying = settings.getBoolean(FILTER_STATE_PLAYING, true);
        mListFilter.showStateCompleted = settings.getBoolean(FILTER_STATE_SOLVED, true);
        mSudokuGames.clear();
        mDatabase = new SudokuDatabase(getApplicationContext());
        Cursor sudokuList = mDatabase.getSudokuList(mFolderID, mListFilter);
        mCompleteCount = 0;
        mGameCount = sudokuList.getCount();
        if (mGameCount > 0) {
            while (sudokuList.moveToNext()) {
                SudokuGame sudokuGame = new SudokuGame();
                sudokuGame.setId(sudokuList.getLong(sudokuList.getColumnIndex(SudokuColumns._ID)));
                sudokuGame.setCreated(sudokuList.getLong(sudokuList.getColumnIndex(SudokuColumns.CREATED)));
                sudokuGame.setLastPlayed(sudokuList.getLong(sudokuList.getColumnIndex(SudokuColumns.LAST_PLAYED)));
                sudokuGame.setState(sudokuList.getInt(sudokuList.getColumnIndex(SudokuColumns.STATE)));
                sudokuGame.setTime(sudokuList.getLong(sudokuList.getColumnIndex(SudokuColumns.TIME)));

                //设置数据
                if (sudokuGame.getState() == SudokuGame.GAME_STATE_COMPLETED) {
                    mCompleteCount++;
                }

                String data = sudokuList.getString(sudokuList.getColumnIndex(SudokuColumns.DATA));
                CellCollection cells = null;
                try {
                    cells = CellCollection.deserialize(data);
                } catch (Exception e) {
                    LogUtils.tag(TAG).d(e);
                }
                sudokuGame.setCells(cells);
                sudokuGame.setNote(sudokuList.getString(sudokuList.getColumnIndex(SudokuColumns.PUZZLE_NOTE)));
                mSudokuGames.add(sudokuGame);
            }
        }
        mMenuListAdapter.notifyDataSetChanged();
        tvStatus.setText(mCompleteCount + "/" + mGameCount);
    }

    /**
     * 获取当前选中的难易程度ID
     */
    private void parseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_FOLDER_ID)) {
            mFolderID = intent.getLongExtra(EXTRA_FOLDER_ID, 0);
            LogUtils.tag(TAG).d("mFloderID = " + mFolderID);
        } else {
            LogUtils.tag(TAG).d("文件夹不存在");
            finish();
            return;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
