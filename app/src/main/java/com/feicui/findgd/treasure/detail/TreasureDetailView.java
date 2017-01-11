package com.feicui.findgd.treasure.detail;

import java.util.List;

/**
 * Created by LINS on 2017/1/11.
 * Please Try Hard
 */
public interface TreasureDetailView {
    // 显示信息
    void showMessage(String msg);

    // 设置数据
    void setData(List<TreasureDetailResult> resultList);

}
