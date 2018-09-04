package com.leyou.game.presenter.friend;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.leyou.game.Constants;
import com.leyou.game.bean.ContactBean;
import com.leyou.game.bean.Result;
import com.leyou.game.bean.ResultArray;
import com.leyou.game.bean.UserData;
import com.leyou.game.dao.Crowd;
import com.leyou.game.dao.Friend;
import com.leyou.game.event.CrowdEvent;
import com.leyou.game.event.FriendDeleteEvent;
import com.leyou.game.ipresenter.friend.ICrowdDetailActivity;
import com.leyou.game.util.HttpUtil;
import com.leyou.game.util.LogUtil;
import com.leyou.game.util.ToastUtils;
import com.leyou.game.util.api.FriendApi;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import rx.Observer;

import static io.rong.imlib.model.Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;

/**
 * Description : com.leyou.game.presenter
 *
 * @author : rocky
 * @Create Time : 2017/8/3 上午11:42
 * @Modified Time : 2017/8/3 上午11:42
 */
public class CrowdDetailPresenter {
    private static final String TAG = "CrowdDetailPresenter";
    private String crowdId;
    private Context context;
    private ICrowdDetailActivity iCrowdDetailActivity;

    public CrowdDetailPresenter(Context context, ICrowdDetailActivity iCrowdDetailActivity, String crowdId) {
        this.context = context;
        this.iCrowdDetailActivity = iCrowdDetailActivity;
        this.crowdId = crowdId;
        getCrowdDetail();
        getCrowdNumber();
    }

    public void getCrowdDetail() {
        HttpUtil.subscribe(HttpUtil.createApi(FriendApi.class, Constants.URL).getCrowdInfo(crowdId), new Observer<Result<Crowd>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, e.toString());
            }

            @Override
            public void onNext(Result<Crowd> crowdBeanResult) {
                if (crowdBeanResult.result == 1) {
                    Crowd crowd = crowdBeanResult.data;
                    if (null != crowd) {
                        iCrowdDetailActivity.setCrowdInfo(crowd);
                    }
                }
            }
        });
    }

    public void getCrowdNumber() {
        HttpUtil.subscribe(HttpUtil.createApi(FriendApi.class, Constants.URL).getCrowdMember(crowdId), new Observer<ResultArray<Friend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, e.toString());
            }

            @Override
            public void onNext(ResultArray<Friend> contactBeanResultArray) {
                if (contactBeanResultArray.result == 1) {
                    List<Friend> data = contactBeanResultArray.data;
                    iCrowdDetailActivity.showCrowdMemberAdapter(data);
                }
            }
        });
    }

    public void editCrowdInfo(final Crowd crowd) {
        RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(crowd.getCrowdId(), UserData.getInstance().getId(), crowd.getMyName()));
        HttpUtil.subscribe(HttpUtil.createApi(FriendApi.class, Constants.URL).editCrowdInfo(crowd.getCrowdId(), crowd.getListenMessage(), crowd.getTop(), crowd.getMyName(), crowd.getCrowdName()), new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, e.toString());
            }

            @Override
            public void onNext(Result result) {
                if (result.result == 1) {
                    RongIM.getInstance().refreshGroupInfoCache(new Group(crowdId, crowd.getCrowdName(), Uri.parse(crowd.getPictureUrl())));
                }
            }
        });
    }

    public void setMessageTips(String targetId, final Conversation.ConversationNotificationStatus isRemind) {
        RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, targetId, isRemind, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    public void setMessageTop(String targetId, final boolean isTop) {
        RongIM.getInstance().setConversationToTop(Conversation.ConversationType.GROUP, targetId, isTop, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    public void quitCrowd() {
        HttpUtil.subscribe(HttpUtil.createApi(FriendApi.class, Constants.URL).deleteCrowd(crowdId), new Observer<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d(TAG, e.toString());
            }

            @Override
            public void onNext(Result result) {
                if (result.result == 1) {
                    ToastUtils.showToastShort("退出群聊");
                    deleteCrowdConversation();
                    EventBus.getDefault().post(new FriendDeleteEvent(true));
                    EventBus.getDefault().post(new CrowdEvent(true, false));
                }
            }
        });
    }

    private void deleteCrowdConversation() {
        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, crowdId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                LogUtil.d(TAG, "----delete  crowd:" + aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.e(TAG, "----delete friend:" + errorCode.getValue() + "---msg:" + errorCode.getMessage());
            }
        });
    }
}
