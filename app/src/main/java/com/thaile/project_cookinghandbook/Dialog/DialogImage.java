package com.thaile.project_cookinghandbook.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.R;

public class DialogImage extends Dialog implements View.OnClickListener {
    ImageView img;
    String foodImg;
    Context context;
    public DialogImage(Context context, String foodImg) {
        super(context);
        //title mất: requestWindowFeature
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_image);
        this.context = context;
        this.foodImg = foodImg;
        initView();
       // setCancelable(false);
        //my dialog thuộc về cái thằng nào này....
        //lấy activity
        //setOwnerActivity((AppCompatActivity) context);
    }
    private void initView() {
        img = (ImageView) findViewById(R.id.img_show);
        Glide.with(context).load(foodImg).into(img);
        AppHelper.ZoomOutImage(context, img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }
}
