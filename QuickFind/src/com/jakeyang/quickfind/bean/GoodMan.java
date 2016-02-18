package com.jakeyang.quickfind.bean;

import com.jakeyang.quickfind.utils.PinYinUtil;


public class GoodMan implements Comparable<GoodMan>{

    public String mName;
    public String mPinyin;
    public GoodMan(String name) {
        mName = name;
        mPinyin = PinYinUtil.toPinyin(name);
    }
    @Override
    public int compareTo(GoodMan another) {
        return mPinyin.compareTo(another.mPinyin);
    }
}
