package com.leyou.game.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyou.game.Constants;
import com.leyou.game.R;
import com.leyou.game.adapter.ViewPagerFragmentAdapter;
import com.leyou.game.base.BaseFragmentActivity;
import com.leyou.game.bean.PushBean;
import com.leyou.game.bean.UpdateAppBean;
import com.leyou.game.bean.UserData;
import com.leyou.game.event.MainTabEvent;
import com.leyou.game.fragment.FightFragment;
import com.leyou.game.fragment.FriendFragment;
import com.leyou.game.fragment.MineFragment;
import com.leyou.game.fragment.TreasureFragment;
import com.leyou.game.fragment.WinAwardFragment;
import com.leyou.game.ipresenter.IMainActivity;
import com.leyou.game.presenter.MainActivityPresenter;
import com.leyou.game.util.DataCleanManager;
import com.leyou.game.util.NotificationsUtil;
import com.leyou.game.util.PayUtil;
import com.leyou.game.util.SPUtil;
import com.leyou.game.widget.DragPointView;
import com.leyou.game.widget.GuideView;
import com.leyou.game.widget.IndexViewPager;
import com.leyou.game.widget.dialog.DiamondConvertFlowDialog;
import com.leyou.game.widget.dialog.NewVersionDialog;
import com.leyou.game.widget.dialog.PrizeDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;

/**
 * Description : 应用首页
 *
 * @author : rocky
 * @Create Time : 2017/3/29 下午4:21
 * @Modified By: rocky
 * @Modified Time : 2017/3/29 下午4:21
 */

public class MainActivity extends BaseFragmentActivity implements IMainActivity, DragPointView.OnDragListener, IUnReadMessageObserver {

    @BindView(R.id.viewPager_container)
    IndexViewPager viewPagerContainer;

    @BindView(R.id.re_bar_treasury)
    RelativeLayout reBarTreasury;
    @BindView(R.id.iv_bar_treasury)
    ImageView ivBarTreasury;
    @BindView(R.id.tv_bar_treasury)
    TextView tvBarTreasury;
    @BindView(R.id.tv_treasure_tips_number)
    DragPointView tvTreasureTipsNumber;
    @BindView(R.id.re_bar_friend)
    RelativeLayout reBarFriend;
    @BindView(R.id.iv_bar_friend)
    ImageView ivBarFriend;
    @BindView(R.id.tv_bar_friend)
    TextView tvBarFriend;
    @BindView(R.id.tv_friend_tips_number)
    DragPointView tvFriendTipsNumber;
    @BindView(R.id.re_bar_win)
    RelativeLayout reBarWin;
    @BindView(R.id.iv_bar_win)
    ImageView ivBarWin;
    @BindView(R.id.tv_bar_win)
    TextView tvBarWin;
    @BindView(R.id.re_bar_fight)
    RelativeLayout reBarFight;
    @BindView(R.id.iv_bar_fight)
    ImageView ivBarFight;
    @BindView(R.id.tv_bar_fight)
    TextView tvBarFight;
    @BindView(R.id.tv_fight_tips_number)
    DragPointView tvFightTipsNumber;
    @BindView(R.id.re_bar_mine)
    RelativeLayout reBarMine;
    @BindView(R.id.iv_bar_mine)
    ImageView ivBarMine;
    @BindView(R.id.tv_bar_mine)
    TextView tvBarMine;
    @BindView(R.id.tv_mine_tips_number)
    DragPointView tvMineTipsNumber;

    private List<Fragment> fragmentList = new ArrayList<>();
    private TreasureFragment treasureFragment;
    private FriendFragment friendFragment;
    private WinAwardFragment winAwardFragment;
    private FightFragment fightFragment;
    private MineFragment mineFragment;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    private MainActivityPresenter presenter;
    private int index = 2;
    private Handler handler = new Handler();
    private int unReadMessageCount;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constants.PRIZE_ACTION:
                    PushBean pushInfo = intent.getParcelableExtra("pushBean");
                    String prizeId = intent.getStringExtra("prizeId");
                    if (null != pushInfo) {
                        showPrizeView(pushInfo, prizeId);
                    }
                    break;
                case Constants.NEW_VERSION_ACTION:
                    presenter.checkVersion();
                    break;
                case Constants.DIAMOND_CONVERT:
                    showDiamondConvert();
                    break;
                case Constants.OFF_LINE_ACTION:
                    PushBean offLineInfo = intent.getParcelableExtra("pushBean");
                    offLineDialog(offLineInfo);
                    break;
            }
        }
    };
    private GuideView guideViewTreasure;
    private Conversation.ConversationType[] conversationTypes = new Conversation.ConversationType[]{
            Conversation.ConversationType.PRIVATE,
            Conversation.ConversationType.GROUP,
            Conversation.ConversationType.SYSTEM,
            Conversation.ConversationType.PUBLIC_SERVICE,
            Conversation.ConversationType.APP_PUBLIC_SERVICE
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTabState(MainTabEvent tabEvent) {
        setTabState(tabEvent.event);
    }

    @Override
    public void initWindows() {
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initWeight(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PRIZE_ACTION);
        intentFilter.addAction(Constants.NEW_VERSION_ACTION);
        intentFilter.addAction(Constants.DIAMOND_CONVERT);
        intentFilter.addAction(Constants.OFF_LINE_ACTION);
        registerReceiver(receiver, intentFilter);

        tvFriendTipsNumber.setOnClickListener(this);
        tvFriendTipsNumber.setDragListener(this);

        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);

        viewPagerContainer.setCanScroll(true);

        treasureFragment = new TreasureFragment();
        friendFragment = new FriendFragment();
        winAwardFragment = new WinAwardFragment();
        fightFragment = new FightFragment();
        mineFragment = new MineFragment();

        fragmentList.add(treasureFragment);
        fragmentList.add(friendFragment);
        fragmentList.add(winAwardFragment);
        fragmentList.add(fightFragment);
        fragmentList.add(mineFragment);

        viewPagerContainer.setOffscreenPageLimit(5);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPagerContainer.setAdapter(viewPagerFragmentAdapter);
        viewPagerContainer.setCurrentItem(2);
        viewPagerContainer.setCanScroll(false);
//        radioGroup.check(R.id.rb_Award);
        setTabState(2);
    }

    @Override
    public void initPresenter() {
        presenter = new MainActivityPresenter(this, this);
        presenter.checkVersion();
        checkDialogPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (!SPUtil.getBoolean(this, SPUtil.INDUCE, "isGuide") && UserData.getInstance().isLogIn()) {
            startActivity(new Intent(this, WebViewGuideActivity.class));
        }
        unReadMessageCount = 0;
        presenter.checkUnReadMessage();
    }

    private void checkDialogPermission() {
        if (!NotificationsUtil.isNotificationEnabled(this)) {
            new AlertDialog.Builder(this).setTitle("通知")
                    .setMessage("系统默认拦截了你的应用通知，您有可能错过您的中奖信息呦！")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NotificationsUtil.requestNotificationPermission(MainActivity.this);
                        }
                    }).setNegativeButton("取消", null)
                    .create()
                    .show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.re_bar_treasury, R.id.re_bar_friend, R.id.re_bar_win, R.id.re_bar_fight, R.id.re_bar_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.re_bar_treasury:
                setTabState(0);
                break;
            case R.id.re_bar_friend:
                setTabState(1);
                break;
            case R.id.re_bar_win:
                setTabState(2);
                break;
            case R.id.re_bar_fight:
                setTabState(3);
                break;
            case R.id.re_bar_mine:
                setTabState(4);
                break;
        }
    }

    private void setTabState(int position) {
        index = position;
        ivBarTreasury.setSelected(false);
        ivBarFriend.setSelected(false);
        ivBarWin.setSelected(false);
        ivBarFight.setSelected(false);
        ivBarMine.setSelected(false);
        tvBarTreasury.setSelected(false);
        tvBarFriend.setSelected(false);
        tvBarWin.setSelected(false);
        tvBarFight.setSelected(false);
        tvBarMine.setSelected(false);
        switch (position) {
            case 0:
                ivBarTreasury.setSelected(true);
                tvBarTreasury.setSelected(true);
//                radioGroup.check(R.id.rb_treasury);
                break;
            case 1:
                ivBarFriend.setSelected(true);
                tvBarFriend.setSelected(true);
//                radioGroup.check(R.id.rb_friend);
                break;
            case 2:
                ivBarWin.setSelected(true);
                tvBarWin.setSelected(true);
//                radioGroup.check(R.id.rb_Award);
                break;
            case 3:
                ivBarFight.setSelected(true);
                tvBarFight.setSelected(true);
//                radioGroup.check(R.id.rb_fight);
                break;
            case 4:
                ivBarMine.setSelected(true);
                tvBarMine.setSelected(true);
//                radioGroup.check(R.id.rb_mine);
                break;
        }
        viewPagerContainer.setCurrentItem(position);
    }

    @Override
    public void showDiamondConvert() {
        new DiamondConvertFlowDialog(this).show();
    }

    @Override
    public void showPrizeView(PushBean pushBean, String prizeId) {
        PrizeDialog prizeDialog = new PrizeDialog(this, pushBean, prizeId);
        prizeDialog.show();
    }

    @Override
    public void showNewVersionView(UpdateAppBean updateAppBean) {
        NewVersionDialog newVersionDialog = new NewVersionDialog(this, updateAppBean);
        newVersionDialog.show();
    }

    @Override
    public void offLineDialog(PushBean offLineInfo) {
        new AlertDialog.Builder(this).setTitle("下线通知")
                .setMessage(offLineInfo.des)
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishCurrentActivity();
                    }
                })
                .setPositiveButton("重新登陆", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        finishCurrentActivity();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void clearDownloadFile(boolean flag) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DataCleanManager.deleteFile(new File(Constants.UPDATE_DIR));
            }
        });
    }

    @Override
    public void setMessageCount(int count) {
        count = unReadMessageCount + count;
        if (count == 0) {
            tvFriendTipsNumber.setVisibility(View.GONE);
        } else if (count > 0 && count < 100) {
            tvFriendTipsNumber.setVisibility(View.VISIBLE);
            tvFriendTipsNumber.setText(String.valueOf(count));
            unReadMessageCount = count;
        } else {
            tvFriendTipsNumber.setVisibility(View.VISIBLE);
            tvFriendTipsNumber.setText(getString(R.string.no_read_message));
        }
    }

    @Override
    public void onCountChanged(int count) {
        if (count == 0) {
            tvFriendTipsNumber.setVisibility(View.GONE);
            unReadMessageCount = 0;
        } else if (count > 0 && count < 100) {
            tvFriendTipsNumber.setVisibility(View.VISIBLE);
            tvFriendTipsNumber.setText(String.valueOf(count));
            unReadMessageCount = count;
        } else {
            tvFriendTipsNumber.setVisibility(View.VISIBLE);
            tvFriendTipsNumber.setText(getString(R.string.no_read_message));
        }
    }

    @Override
    public void onDragOut() {
        tvFriendTipsNumber.setVisibility(View.GONE);
        presenter.clearConversationUnReadStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayUtil.destroy(this);
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
    }
}
