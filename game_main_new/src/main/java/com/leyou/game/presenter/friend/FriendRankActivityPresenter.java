package com.leyou.game.presenter.friend;

import android.content.Context;

import com.leyou.game.Constants;
import com.leyou.game.bean.ResultArray;
import com.leyou.game.bean.game.GameBean;
import com.leyou.game.ipresenter.friend.IFriendRankActivity;
import com.leyou.game.util.HttpUtil;
import com.leyou.game.util.ToastUtils;
import com.leyou.game.util.newapi.GameApi;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Description : com.leyou.game.presenter
 *
 * @author : rocky
 * @Create Time : 2017/7/26 上午9:41
 * @Modified Time : 2017/7/26 上午9:41
 */
public class FriendRankActivityPresenter {
    private Context context;
    private IFriendRankActivity iFriendRankActivity;
    private Subscription subscribe;
    Observer<ResultArray<GameBean>> observer = new Observer<ResultArray<GameBean>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.showToastShort("请检查网络是否可用");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResultArray<GameBean> gamePrizeResultResultArray) {
            if (null != gamePrizeResultResultArray) {
                int result = gamePrizeResultResultArray.result;
                if (0 != result) {
                    List<GameBean> data = gamePrizeResultResultArray.data;
                    iFriendRankActivity.showData(data);
                } else {
                    iFriendRankActivity.showError();
                }
            }
        }
    };

    public FriendRankActivityPresenter(Context context, IFriendRankActivity iFriendRankActivity) {
        this.context = context;
        this.iFriendRankActivity = iFriendRankActivity;
        getWinResultTitleList();
    }

    /**
     * 获取游戏tab栏
     */
    public void getWinResultTitleList() {
        subscribe = HttpUtil.subscribe(HttpUtil.createApi(GameApi.class, Constants.URL).getWinGameTabList(), observer);
    }

    /**
     * 解除订阅，停止数据请求
     */
    public void destroy() {
        if (null != subscribe && subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }
}
