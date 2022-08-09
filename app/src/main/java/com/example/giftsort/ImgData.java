package com.example.giftsort;

public class ImgData {

    private int iv_1;
    private String tv_name;
    private String tv_content;

    public ImgData(int iv_1, String tv_name, String tv_content) {
        this.iv_1 = iv_1;
        this.tv_name = tv_name;
        this.tv_content = tv_content;
    }

    public int getIv_1() {
        return iv_1;
    }

    public void setIv_1(int iv_1) {
        this.iv_1 = iv_1;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }
}
