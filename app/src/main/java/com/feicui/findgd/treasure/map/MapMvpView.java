package com.feicui.findgd.treasure.map;

import com.feicui.findgd.treasure.Treasure;

import java.util.List;

/**
 * Created by LINS on 2017/1/11.
 * Please Try Hard
 */
public interface MapMvpView {
    void showMessage(String msg);// 弹吐司
    void setData(List<Treasure> list);// 设置数据
}