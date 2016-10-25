package com.thaile.project_cookinghandbook.Object;

/**
 * Created by Thai Le on 9/12/2016.
 */
public class ItemMeo {
    private String title;
    private String img;
    private String content;

    public ItemMeo(String img, String content, String title) {
        this.img = img;
        this.content = content;
        this.title = title;
    }

    public ItemMeo(){

    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }
}
