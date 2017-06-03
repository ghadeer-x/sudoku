package com.ancheng.sudoku.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancheng.sudoku.R;
import com.ancheng.sudoku.model.UserManager;
import com.ancheng.sudoku.model.bean.User;
import com.ancheng.sudoku.utils.IntentTools;
import com.ancheng.sudoku.utils.ToastUtils;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.lzy.imagepicker.ImagePicker.TAG;

/**
 * author: ancheng
 * date: 2017/5/26
 * email: lzjtugjc@163.com
 */

public class MineFragment extends Fragment {

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    Unbinder unbinder;
    private static final int QUESTCODE_MINE_INFO = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDate();
    }

    /**
     * 更新数据
     */
    private void updateDate() {
        String objectId = UserManager.getInstance().getObjectId();
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    //更新失败
                    LogUtils.tag(TAG).d("更新失败");
                } else {
                    User user = list.get(0);
                    UserManager.getInstance().setUser(user);
                }
                initView();
            }
        });


    }

    private void initView() {
        UserManager userManager = UserManager.getInstance();
        //设置头像
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_mine, R.id.rl_score, R.id.rl_settting, R.id.rl_help, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine:
                //个人信息设置
                startActivityForResult(IntentTools.getMineInfoActivityIntent(getContext()), QUESTCODE_MINE_INFO);
                break;
            case R.id.rl_score:
                //历史成绩排行
                startActivity(IntentTools.getMineScoreActivityIntent(getContext()));
                break;
            case R.id.rl_settting:
                //个人设置
                startActivity(IntentTools.getSettingActivityIntent(getContext()));
                break;
            case R.id.rl_help:
                //帮助
                startActivity(IntentTools.getHelpActivityIntent(getContext()));
                break;
            case R.id.tv_exit:
                //退出账户
                startActivity(IntentTools.getLoginActivityIntent(getContext()));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTCODE_MINE_INFO) {
                Intent intent = data;
                ToastUtils.showShort("更新数据");
            }
        }
    }
}
