package com.leyou.game.presenter.mine;

import android.content.Context;

import com.leyou.game.Constants;
import com.leyou.game.bean.GamePrizeResult;
import com.leyou.game.bean.ResultArray;
import com.leyou.game.ipresenter.mine.IWinResultActivity;
import com.leyou.game.util.HttpUtil;
import com.leyou.game.util.ToastUtils;
import com.leyou.game.util.api.WinAwardApi;

import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * Description : 上期获奖结果activity 处理层
 *
 * @author : rocky
 * @Create Time : 2017/4/24 下午8:03
 * @Modified Time : 2017/4/24 下午8:03
 */
public class WinResultPresenter {
    private Context context;
    private IWinResultActivity iWinResultActivity;
    private Subscription subscribe;
    Observer<ResultArray<GamePrizeResult>> observer = new Observer<ResultArray<GamePrizeResult>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.showToastShort("请检查网络是否可用");
            e.printStackTrace();
        }

        @Override
        public void onNext(ResultArray<GamePrizeResult> gamePrizeResultResultArray) {
            if (null != gamePrizeResultResultArray) {
                int result = gamePrizeResultResultArray.result;
                if (0 != result) {
                    List<GamePrizeResult> data = gamePrizeResultResultArray.data;
                    iWinResultActivity.showData(data);
                } else {
                    iWinResultActivity.showError();
                }
            }
        }
    };

    public WinResultPresenter(Context context, IWinResultActivity iWinResultActivity) {
        this.context = context;
        this.iWinResultActivity = iWinResultActivity;
        getWinResultTitleList();
    }

    /**
     * 获取游戏tab栏
     */
    public void getWinResultTitleList() {
        subscribe = HttpUtil.subscribe(HttpUtil.createApi(WinAwardApi.class, Constants.URL).getPrizeResultList(), observer);
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
