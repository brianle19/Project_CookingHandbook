package com.thaile.project_cookinghandbook.Object;

import android.graphics.Bitmap;

/**
 * Created by Thai Le on 10/12/2016.
 */

public class ItemImage {
    private Bitmap bitmap;
    private String pathImage;

    public ItemImage(Bitmap bitmap, String pathImage) {
        this.bitmap = bitmap;
        this.pathImage = pathImage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getPathImage() {
        return pathImage;
    }
}
