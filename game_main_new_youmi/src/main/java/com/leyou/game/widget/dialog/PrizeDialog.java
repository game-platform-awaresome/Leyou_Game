package com.leyou.game.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.leyou.game.R;
import com.leyou.game.activity.mine.AwardDetailActivity;
import com.leyou.game.base.BaseDialog;
import com.leyou.game.bean.PushBean;
import com.leyou.game.util.NumberFormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description : com.leyou.game.widget.dialog
 *
 * @author : rocky
 * @Create Time : 2017/6/23 下午3:28
 * @Modified Time : 2017/6/23 下午3:28
 */
public class PrizeDialog extends BaseDialog {

    private String prizeId;
    private PushBean pushBean;
    private Context context;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_to_detail)
    TextView tvToDetail;

    public PrizeDialog(Context context, PushBean pushBean, String prizeId) {
        super(context);
        this.context = context;
        this.pushBean = pushBean;
        this.prizeId = prizeId;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_dialog_prize_yes;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText(pushBean.des);
        tvContent.setText("奖品名称：" + pushBean.awardName + "");
    }

    @OnClick({R.id.iv_close, R.id.tv_to_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_to_detail:
                Intent intent = new Intent(context, AwardDetailActivity.class);
                intent.putExtra("winId", prizeId);
                context.startActivity(intent);
                dismiss();
                break;
        }
    }
}
