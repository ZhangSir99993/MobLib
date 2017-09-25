package com.mobsdk.app.mobsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import util.ThirdPartyAppUtil;

public class MainActivity extends AppCompatActivity implements PlatformActionListener {
    @BindView(R.id.dianwo)
    Button dianwo;
    private Platform wechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    private void weChatLogin() {
        if (!ThirdPartyAppUtil.isWeChatAvilible(this)) {
            Toast.makeText(this, "未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        if (wechat == null) {
            wechat = ShareSDK.getPlatform(Wechat.NAME);
            wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
            wechat.setPlatformActionListener(this); // 设置分享事件回调
        }
        wechat.authorize();//单独授权
//        wechat.showUser(null);//授权并获取用户信息
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "授权出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.dianwo)
    public void onViewClicked() {
        Toast.makeText(this, "OnClick", Toast.LENGTH_SHORT).show();
//        weChatLogin();
        showShare();
    }
}
