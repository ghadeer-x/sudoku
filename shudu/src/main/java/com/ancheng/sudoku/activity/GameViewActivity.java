package com.ancheng.sudoku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.constant.GameConstant;
import com.ancheng.sudoku.constant.GameSetting;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.Game_Score;
import com.ancheng.sudoku.service.MusicService;
import com.ancheng.sudoku.utils.IntentTools;
import com.ancheng.sudoku.utils.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cz.romario.opensudoku.db.SudokuDatabase;
import cz.romario.opensudoku.game.SudokuGame;
import cz.romario.opensudoku.gui.GameTimeFormat;
import cz.romario.opensudoku.gui.HintsQueue;
import cz.romario.opensudoku.gui.OnPuzzleSolvedListener;
import cz.romario.opensudoku.gui.SudokuBoardView;
import cz.romario.opensudoku.gui.Timer;
import cz.romario.opensudoku.gui.inputmethod.IMControlPanel;
import cz.romario.opensudoku.gui.inputmethod.IMControlPanelStatePersister;
import cz.romario.opensudoku.gui.inputmethod.IMNumpad;
import cz.romario.opensudoku.gui.inputmethod.IMPopup;
import cz.romario.opensudoku.gui.inputmethod.IMSingleNumber;

public class GameViewActivity extends AppCompatActivity {

    public static final String EXTRA_SUDOKU_ID = "sudoku_id";

    public static final int MENU_ITEM_RESTART = Menu.FIRST;
    public static final int MENU_ITEM_CLEAR_ALL_NOTES = Menu.FIRST + 1;
    public static final int MENU_ITEM_FILL_IN_NOTES = Menu.FIRST + 2;
    public static final int MENU_ITEM_UNDO = Menu.FIRST + 3;
    public static final int MENU_ITEM_HELP = Menu.FIRST + 4;
    public static final int MENU_ITEM_SETTINGS = Menu.FIRST + 5;

    public static final int MENU_ITEM_SET_CHECKPOINT = Menu.FIRST + 6;

    public static final int MENU_ITEM_UNDO_TO_CHECKPOINT = Menu.FIRST + 7;

    private static final int DIALOG_RESTART = 1;
    private static final int DIALOG_WELL_DONE = 2;
    private static final int DIALOG_CLEAR_NOTES = 3;
    private static final int DIALOG_UNDO_TO_CHECKPOINT = 4;

    private static final int REQUEST_SETTINGS = 1;


    private long mSudokuGameID;
    private SudokuGame mSudokuGame;


    private SudokuDatabase mDatabase;

    private Handler mGuiHandler;

    private IMControlPanel mIMControlPanel;
    private IMControlPanelStatePersister mIMControlPanelStatePersister;
    private IMPopup mIMPopup;
    private IMSingleNumber mIMSingleNumber;
    private IMNumpad mIMNumpad;

    private boolean mShowTime = true;
    private GameTimer mGameTimer;
    private GameTimeFormat mGameTimeFormatter = new GameTimeFormat();
    private boolean mFullScreen;
    private boolean mFillInNotesEnabled = false;
    private static final String TAG = "GameViewActivity";
    private HintsQueue mHintsQueue;

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.sbv_game_view)
    SudokuBoardView sbvGameView;
    @BindView(R.id.input_methods)
    IMControlPanel inputMethods;
    @BindView(R.id.ll_root_view)
    LinearLayout mRootLayout;

    private boolean isPlayMusic;
    private long mGameLevel;
    private int mGameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);

        parseIntent();
        initVariable(savedInstanceState);
    }

    /**
     * 获取游戏的ID
     */
    private void parseIntent() {
        mGameLevel = getIntent().getLongExtra(GameConstant.GAME_LEVEL, 1);
        mSudokuGameID = getIntent().getLongExtra(GameViewActivity.EXTRA_SUDOKU_ID, 0);
        mGameId = getIntent().getIntExtra(GameConstant.GAME_ID, 0);
    }

    private void initVariable(Bundle savedInstanceState) {
        mDatabase = new SudokuDatabase(getApplicationContext());
        mHintsQueue = new HintsQueue(this);
        mGameTimer = new GameTimer();
        mGuiHandler = new Handler();

        if (savedInstanceState == null) {
            mSudokuGame = mDatabase.getSudoku(mSudokuGameID);
//            LogUtils.tag(TAG).d("这里执行了");
        } else {
            //恢复之前状态
            mSudokuGame = new SudokuGame();
            mSudokuGame.restoreState(savedInstanceState);
            mGameTimer.restoreState(savedInstanceState);
//            LogUtils.tag(TAG).d("state : %d", mSudokuGame.getState());
//            LogUtils.tag(TAG).d("time : %d", mSudokuGame.getTime());
//            LogUtils.tag(TAG).d("lastPalyed : %d", mSudokuGame.getLastPlayed());
        }

        if (mSudokuGame.getState() == SudokuGame.GAME_STATE_NOT_STARTED) {
            mSudokuGame.start();
        } else if (mSudokuGame.getState() == SudokuGame.GAME_STATE_PLAYING) {
            mSudokuGame.resume();
        }

        if (mSudokuGame.getState() == SudokuGame.GAME_STATE_COMPLETED) {
            sbvGameView.setReadOnly(true);
        }
        sbvGameView.setGame(mSudokuGame);

        mSudokuGame.setOnPuzzleSolvedListener(onSolvedListener);

        mHintsQueue.showOneTimeHint("welcome", R.string.welcome, R.string.first_run_hint);

        mIMControlPanel = (IMControlPanel) findViewById(R.id.input_methods);
        mIMControlPanel.initialize(sbvGameView, mSudokuGame, mHintsQueue);
        mIMControlPanelStatePersister = new IMControlPanelStatePersister(this);
        mIMPopup = mIMControlPanel.getInputMethod(IMControlPanel.INPUT_METHOD_POPUP);
        mIMSingleNumber = mIMControlPanel.getInputMethod(IMControlPanel.INPUT_METHOD_SINGLE_NUMBER);
        mIMNumpad = mIMControlPanel.getInputMethod(IMControlPanel.INPUT_METHOD_NUMPAD);

    }


    @Override
    protected void onResume() {
        super.onResume();

        isPlayMusic = GameSetting.isOpenMusicSwitch();
        if (isPlayMusic) {
            Intent intent = new Intent(this, MusicService.class);
            startService(intent);
        }
        // read game settings
        SharedPreferences gameSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int screenPadding = gameSettings.getInt("screen_border_size", 0);
        mRootLayout.setPadding(screenPadding, screenPadding, screenPadding, screenPadding);

        mFillInNotesEnabled = gameSettings.getBoolean("fill_in_notes_enabled", false);

        sbvGameView.setHighlightWrongVals(gameSettings.getBoolean("highlight_wrong_values", true));
        sbvGameView.setHighlightTouchedCell(gameSettings.getBoolean("highlight_touched_cell", true));

        mShowTime = gameSettings.getBoolean("show_time", true);
        if (mSudokuGame.getState() == SudokuGame.GAME_STATE_PLAYING) {
            mSudokuGame.resume();

            if (mShowTime) {
                mGameTimer.start();
            }
        }
        // mTimeLabel.setVisibility(mFullScreen && mShowTime ? View.VISIBLE : View.GONE);

        mIMPopup.setEnabled(gameSettings.getBoolean("im_popup", true));
        mIMSingleNumber.setEnabled(gameSettings.getBoolean("im_single_number", true));
        mIMNumpad.setEnabled(gameSettings.getBoolean("im_numpad", true));
        mIMNumpad.setMoveCellSelectionOnPress(gameSettings.getBoolean("im_numpad_move_right", false));
        mIMPopup.setHighlightCompletedValues(gameSettings.getBoolean("highlight_completed_values", true));
        mIMPopup.setShowNumberTotals(gameSettings.getBoolean("show_number_totals", false));
        mIMSingleNumber.setHighlightCompletedValues(gameSettings.getBoolean("highlight_completed_values", true));
        mIMSingleNumber.setShowNumberTotals(gameSettings.getBoolean("show_number_totals", false));
        mIMNumpad.setHighlightCompletedValues(gameSettings.getBoolean("highlight_completed_values", true));
        mIMNumpad.setShowNumberTotals(gameSettings.getBoolean("show_number_totals", false));

        mIMControlPanel.activateFirstInputMethod(); // make sure that some input method is activated
        mIMControlPanelStatePersister.restoreState(mIMControlPanel);
        updateTime();
    }


    @Override
    protected void onPause() {
        super.onPause();
//        LogUtils.tag(TAG).d("time  :  " + mSudokuGame.getTime());
//        LogUtils.tag(TAG).d("lastPlayed  :  " + mSudokuGame.getLastPlayed());
//        LogUtils.tag(TAG).d("state  :  " + mSudokuGame.getState());
//        // we will save game to the database as we might not be able to get back

        if (isPlayMusic) {
            Intent intent = new Intent(this, MusicService.class);
            stopService(intent);
        }

        mSudokuGame.setLastPlayed(System.currentTimeMillis());
        mDatabase.updateSudoku(mSudokuGame);
        mGameTimer.stop();
        mIMControlPanel.pause();
        mIMControlPanelStatePersister.saveState(mIMControlPanel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDatabase.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mGameTimer.stop();
        if (mSudokuGame.getState() == SudokuGame.GAME_STATE_PLAYING) {
            mSudokuGame.pause();
        }
        mSudokuGame.saveState(outState);
        mGameTimer.saveState(outState);
    }

    @OnClick({R.id.iv_back, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_setting:
                startActivityForResult(IntentTools.getSettingActivityIntent(this), REQUEST_SETTINGS);
                break;
        }
    }

    // This class implements the game clock.  All it does is update the
    // status each tick.
    private class GameTimer extends Timer {

        GameTimer() {
            super(1000);
        }

        @Override
        protected boolean step(int count, long time) {
            updateTime();
            // Run until explicitly stopped.
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SETTINGS) {
                restartActivity();
            }
        }
    }

    /**
     * 重启Activity
     */
    private void restartActivity() {
        startActivity(getIntent());
        finish();
    }

    /**
     * 更新时间
     */
    private void updateTime() {
        tvTime.setText(mGameTimeFormatter.format(mSudokuGame.getTime()));
    }

    /**
     * 数独被解之后，设置数独为只读，并且弹出对话框
     */
    private OnPuzzleSolvedListener onSolvedListener = new OnPuzzleSolvedListener() {
        @Override
        public void onPuzzleSolved() {
            sbvGameView.setReadOnly(true);
            //showDialog(DIALOG_WELL_DONE);
            ToastUtils.showLong("该数独被解");
            //上传游戏成绩
            //添加数据到数据库中

            Game_Score gameScore = new Game_Score();
            gameScore.setUserId(UserManager.getInstance().getUser_id());
            long gameTime = mSudokuGame.getTime();
            gameScore.setTime(gameTime);
            gameScore.setLevel(mGameLevel + "-" + (mGameId + 1));
            int score = 0;
            if (mGameLevel == 1) {
                if (gameTime <= 10 * 60 * 1000) {
                    //小于十分钟
                    score = 30;
                } else if (gameTime > 10 * 60 * 1000 && gameTime <= 20 * 60 * 1000) {
                    //大于10分钟小于20分钟
                    score = 20;
                } else if (gameTime > 20 * 60 * 1000) {
                    score = 10;
                } else {
                    score = 5;
                }
            } else if (mGameLevel == 2) {
                if (gameTime <= 10 * 60 * 1000) {
                    //小于十分钟
                    score = 60;
                } else if (gameTime > 10 * 60 * 1000 && gameTime <= 20 * 60 * 1000) {
                    //大于10分钟小于20分钟
                    score = 50;
                } else if (gameTime > 20 * 60 * 1000) {
                    score = 35;
                } else {
                    score = 10;
                }
            } else if (mGameLevel == 3) {
                if (gameTime <= 10 * 60 * 1000) {
                    //小于十分钟
                    score = 100;
                } else if (gameTime > 10 * 60 * 1000 && gameTime <= 20 * 60 * 1000) {
                    //大于10分钟小于20分钟
                    score = 80;
                } else if (gameTime > 20 * 60 * 1000) {
                    score = 60;
                } else {
                    score = 40;
                }
            }
            gameScore.setScore(score);
            gameScore.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    //保存成功
                }
            });
        }
    };

}
