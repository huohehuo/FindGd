package com.feicui.findgd.treasure.hide;

/**
 * Created by LINS on 2017/1/12.
 * Please Try Hard
 */
public interface HideTreasureView {
    // 宝藏上传中视图的交互

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void navigationToHome();
}
