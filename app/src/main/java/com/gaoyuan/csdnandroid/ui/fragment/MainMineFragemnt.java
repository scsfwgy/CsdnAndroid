package com.gaoyuan.csdnandroid.ui.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.lib.utils.HandlerUtils;
import com.common.lib.utils.SnackbarUtils;
import com.common.lib.utils.TimeUtils;
import com.gaoyuan.csdnandroid.R;
import com.gaoyuan.csdnandroid.base.App;
import com.gaoyuan.csdnandroid.base.BaseFragment;
import com.gaoyuan.csdnandroid.base.Constants;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.event.NullPUser;
import com.gaoyuan.csdnandroid.event.RefreshHome;
import com.gaoyuan.csdnandroid.myview.MyWxProfilePopupWindow;
import com.gaoyuan.csdnandroid.ui.activity.MainActivity;
import com.gaoyuan.csdnandroid.ui.activity.me.CollectionActivity;
import com.gaoyuan.csdnandroid.ui.activity.me.FavoriteModulesActivity;
import com.gaoyuan.csdnandroid.ui.activity.me.LoginActivity;
import com.gaoyuan.csdnandroid.ui.activity.me.SettingActivity;
import com.gaoyuan.csdnandroid.utils.GlideImageUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 作者：wgyscsf on 2016/12/24 16:47
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class MainMineFragemnt extends BaseFragment {

    @Bind(R.id.fmm_head_img)
    ImageView mFmmHeadImg;
    @Bind(R.id.fmm_username_txt)
    TextView mFmmUsernameTxt;
    @Bind(R.id.fmm_rl_login)
    RelativeLayout mFmmRlLogin;
    @Bind(R.id.fmm_rl_coll)
    RelativeLayout mFmmRlColl;
    @Bind(R.id.fmm_rl_three)
    RelativeLayout mFmmRlThree;
    @Bind(R.id.fmm_rl_four)
    RelativeLayout mFmmRlFour;
    @Bind(R.id.fmm_rl_five)
    RelativeLayout mFmmRlFive;
    @Bind(R.id.fmm_rl_six)
    RelativeLayout mFmmRlSix;
    @Bind(R.id.fmm_rl_even)
    RelativeLayout mFmmRlEven;
    @Bind(R.id.fmm_rl_engit)
    RelativeLayout mFmmRlEngit;
    @Bind(R.id.fmm_rl_setting)
    RelativeLayout mFmmRlSetting;

    PUser mPUser;
    //pop相关
    MyWxProfilePopupWindow mMyWxProfilePopupWindow;
    public final int SENDEMAIL = 0x01;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main_mine;

    }

    @Override
    protected void initViewsAndEvents() {
        initData();
    }

    private void initData() {
        mPUser = App.getPUser();
        processUser();
    }

    @OnClick({R.id.fmm_rl_login, R.id.fmm_rl_coll, R.id.fmm_rl_three, R.id.fmm_rl_four, R.id.fmm_rl_five, R.id.fmm_rl_six, R.id.fmm_rl_even, R.id.fmm_rl_engit, R.id.fmm_rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmm_rl_login:
                if (mPUser == null)
                    go(LoginActivity.class);
                else
                    showProfile();
                break;
            case R.id.fmm_rl_coll:
                if (mPUser == null)
                    go(LoginActivity.class);
                else
                    go(CollectionActivity.class);
                break;
            case R.id.fmm_rl_three:
                if (mPUser == null)
                    go(LoginActivity.class);
                else
                    go(FavoriteModulesActivity.class);
                break;
            case R.id.fmm_rl_four:
                if (mPUser == null)
                    go(LoginActivity.class);
                break;
            case R.id.fmm_rl_five:
                if (mPUser == null)
                    go(LoginActivity.class);
                break;
            case R.id.fmm_rl_six:
                if (mPUser == null)
                    go(LoginActivity.class);
                break;
            case R.id.fmm_rl_even:
                if (mPUser == null)
                    go(LoginActivity.class);
                break;
            case R.id.fmm_rl_engit:
                if (mPUser == null)
                    go(LoginActivity.class);
                else
                    goEmail();
                break;
            case R.id.fmm_rl_setting:
                go(SettingActivity.class);
                break;
        }
    }

    private void goEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("意见反馈")
                .setIcon(R.drawable.me_me_toushu)
                .setCancelable(false);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View inflate = inflater.inflate(R.layout.dialog_email, null);
        final EditText editText = (EditText) inflate.findViewById(R.id.de_et_input);
        builder.setView(inflate)
                // Add action buttons
                .setPositiveButton("反馈", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String info = editText.getText().toString().trim();
                        if (!info.equals("")) {
                            email(info);
                        } else {
                            Log.e(TAG, "onClick: null");
                            sendEmailFail();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog dialog = builder.create();
        //show
        dialog.show();
    }


    private void showProfile() {
        if (mMyWxProfilePopupWindow == null) {
            initPop();
        }
        mMyWxProfilePopupWindow.showPopupWindow();
    }

    private void email(final String str) {
        // 第三步：发送消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.host", Constants.MyEmails.MEAIL_HOST);
                props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
                //props.put("mail.smtp.port", "465"); //google使用465或587端口
                props.put("mail.smtp.auth", "true"); // 使用验证
                //props.setProperty("mail.transport.protocol", "smtp");
                // props.put("mail.debug", "true");
                final Session mailSession = Session.getInstance(props, new MyAuthenticator(
                        Constants.MyEmails.FROM, Constants.MyEmails.FROMPSW));
                try {
                    Log.e(TAG, "run: 进来了。。。");
                    InternetAddress fromAddress = new InternetAddress(Constants.MyEmails.FROM);
                    InternetAddress toAddress = new InternetAddress(Constants.MyEmails.TO);

                    MimeMessage message = new MimeMessage(mailSession);
                    message.setFrom(fromAddress);
                    message.addRecipient(Message.RecipientType.TO, toAddress);

                    message.setSentDate(Calendar.getInstance().getTime());
                    message.setSubject("CSDN+意见反馈：来自" + App.getPUser().getId_csdn());
                    message.setContent(str, "text/html;charset=utf-8");


                    Transport transport = mailSession.getTransport("smtp");
                    transport.connect(Constants.MyEmails.MEAIL_HOST, Constants.MyEmails.FROM, Constants.MyEmails.FROMPSW);
                    transport.send(message, message.getRecipients(Message.RecipientType.TO));
                    android.os.Message message2 = new android.os.Message();
                    message2.what = SENDEMAIL;
                    message2.obj = 2;
                    sHandler.sendMessage(message2);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    android.os.Message message = new android.os.Message();
                    message.what = SENDEMAIL;
                    message.obj = 1;
                    sHandler.sendMessage(message);
                }
            }
        }).start();

    }

    private void initPop() {
        mMyWxProfilePopupWindow = new MyWxProfilePopupWindow((Activity) getContext());
        GlideImageUtils.setConrnerImage(getContext(), mMyWxProfilePopupWindow.getPwpwIvHeadImg(), mPUser.getAvatarAddr());
        mMyWxProfilePopupWindow.getPwpwTvNickName().setText("昵称：" + mPUser.getNickName());
        mMyWxProfilePopupWindow.getPwpwTvId().setText("id：" + mPUser.getId_csdn());
        mMyWxProfilePopupWindow.getPwpwTvPhone().setText("电话：" + mPUser.getPhone());
        mMyWxProfilePopupWindow.getPwpwTvEmail().setText("电邮：" + mPUser.getEmail());
        mMyWxProfilePopupWindow.getPwpwTvBolg().setText("博客地址：" + "http://blog.csdn.net/" + mPUser.getId_csdn());
        mMyWxProfilePopupWindow.getPwpwTvAdd().setText("加入csdn+时间：" + TimeUtils.getFriendlyTimeSpanByNow(mPUser.getCreateDate()));

    }

    private void sendEmailFail() {
        SnackbarUtils.showLongSnackbar(mFmmRlSetting, "意见反馈失败", getResources().getColor(R.color.white), getResources().getColor(R.color.colorYellow));
    }

    private void sendEmailOk() {
        SnackbarUtils.showLongSnackbar(mFmmRlSetting, "意见反馈成功", getResources().getColor(R.color.white), getResources().getColor(R.color.colorBlue));
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    public void onEvent(PUser pUser) {
        SnackbarUtils.showLongSnackbar(mFmmRlSetting, "身份验证成功", getResources().getColor(R.color.white), getResources().getColor(R.color.colorBlue));
        mPUser = pUser;
        processUser();
    }

    @Subscribe
    public void onEvent(RefreshHome pUser) {
        SnackbarUtils.showLongSnackbar(mFmmRlSetting, "编辑喜欢的模块成功", getResources().getColor(R.color.white), getResources().getColor(R.color.colorBlue));
    }

    @Subscribe
    public void onEventNullPUser(NullPUser nullPUser) {
        mPUser = null;
        processUser();
    }

    private void processUser() {
        if (mPUser == null) {
            mFmmHeadImg.setImageDrawable(getResources().getDrawable(R.drawable.def_avatar));
            mFmmUsernameTxt.setText("请登录");
            return;
        }
        GlideImageUtils.setImage(getContext(), mFmmHeadImg, mPUser.getAvatarAddr());
        if (mPUser.getNickName() == null) {
            mFmmUsernameTxt.setText(mPUser.getName());
        } else {
            mFmmUsernameTxt.setText(mPUser.getNickName() + "(id:" + mPUser.getName() + ")");
        }
    }


    static class MyAuthenticator extends Authenticator {
        String userName = "";
        String password = "";

        public MyAuthenticator() {

        }

        public MyAuthenticator(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }

    Handler sHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENDEMAIL:
                    if ((int) msg.obj == 2) {
                        sendEmailOk();
                    } else if ((int) msg.obj == 1) {
                        sendEmailFail();
                    }
                    break;
            }
        }
    };
}
