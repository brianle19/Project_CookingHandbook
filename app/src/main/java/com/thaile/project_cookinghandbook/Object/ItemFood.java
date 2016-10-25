package com.thaile.project_cookinghandbook.Object;

import java.io.Serializable;

/**
 * Created by Thai Le on 9/8/2016.
 */
public class ItemFood implements Serializable{
    private String id;
    private String imgFood1, imgFood2, imgFood3;
    private String titleFood;
    private String materialFood;
    private String processFood;
    private String introduction;
    private long timestamp;

    public ItemFood(){

    }

    public String getIntroduction() {
        return introduction;
    }

    public ItemFood(String id, String imgFood1, String imgFood2, String imgFood3, String processFood,
                    String materialFood, String titleFood, String introduction, long timestamp) {
        this.id = id;
        this.imgFood1 = imgFood1;
        this.imgFood2 = imgFood2;
        this.imgFood3 = imgFood3;
        this.processFood = processFood;
        this.materialFood = materialFood;
        this.titleFood = titleFood;
        this.introduction = introduction;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getImgFood1() {
        return imgFood1;
    }

    public String getImgFood3() {
        return imgFood3;
    }

    public String getImgFood2() {
        return imgFood2;
    }

    public String getProcessFood() {
        return processFood;
    }

    public String getMaterialFood() {
        return materialFood;
    }

    public String getTitleFood() {
        return titleFood;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
