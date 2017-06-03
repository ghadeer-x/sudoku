package com.ancheng.sudoku.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.IntentTools;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineInfoActivity extends AppCompatActivity {

    private static final int IMAGE_PICKER = 100;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.ll_signature)
    RelativeLayout llSignature;
    private static final String TAG = "MineInfoActivity";
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_rank)
    TextView tvRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, Color.BLACK);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateData();

    }

    @OnClick({R.id.iv_back, R.id.rl_avatar, R.id.ll_nick_name, R.id.ll_email, R.id.ll_number, R.id.ll_score, R.id.ll_rank_list, R.id.ll_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_avatar:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.ll_nick_name:
                //更改昵称
                startActivity(IntentTools.getNickNameSetActivityIntent(this));
                break;
            case R.id.ll_email:
                startActivity(IntentTools.getEmailSetActivityIntent(this));
                break;
            case R.id.ll_number:
                break;
            case R.id.ll_score:
                break;
            case R.id.ll_rank_list:
                break;
            case R.id.ll_signature:
                startActivity(IntentTools.getSignatureSetActivityIntent(this));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> image = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                LogUtils.d(image);
                final File file = new File(image.get(0).path);

                final BmobFile bmobFile = new BmobFile(file);
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //上传成功
                            User user = new User();
                            user.setAvatar(bmobFile);
                            String objectId = UserManager.getInstance().getObjectId();
                            if (TextUtils.isEmpty(objectId)) {
                                return;
                            }
                            user.update(objectId, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        UserManager.getInstance().setAvatar(bmobFile);
                                        Glide.with(MineInfoActivity.this)
                                                .load(bmobFile.getUrl())
                                                .error(R.mipmap.default_image)
                                                .into(civAvatar);
                                    } else {
                                        LogUtils.tag(TAG).d(e);
                                    }
                                }
                            });
                        } else {
                            //上传失败
                            LogUtils.tag(TAG).d(e);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateData() {
        String objectId = UserManager.getInstance().getObjectId();
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    //更新成功
                    User user = list.get(0);
                    UserManager.getInstance().setUser(user);
                } else {
                    LogUtils.tag(TAG).d(e);
                }
                initView();
            }
        });
    }

    private void initView() {
        UserManager userManager = UserManager.getInstance();
        //设置头像
        BmobFile avatar = userManager.getAvatar();
        LogUtils.tag(TAG).d(avatar);
        if (avatar != null) {
            Glide.with(this)
                    .load(avatar.getUrl())
                    .error(R.mipmap.default_image)
                    .into(civAvatar);
        }
        //设置昵称
        String nickName = userManager.getNickName();
        if (TextUtils.isEmpty(nickName)) {
            tvNickName.setText("数独大师");
        } else {
            tvNickName.setText(nickName);
        }
        //设置签名
        String signature = userManager.getSignature();
        tvSignature.setText(TextUtils.isEmpty(signature) ? "还没有签名，赶紧设置一个吧！" : signature);
        //设置邮箱
        String email = userManager.getEmail();
        tvEmail.setText(TextUtils.isEmpty(email) ? "绑定邮箱" : email);

        //设置手机号
        String number = userManager.getPhone_number();
        tvNumber.setText(number);

        //设置总得分
        //TODO

        //设置排名
        //TODO
    }
}
