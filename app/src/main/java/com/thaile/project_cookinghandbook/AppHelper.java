package com.thaile.project_cookinghandbook;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thaile.project_cookinghandbook.Fragment.FragmentContent;

import java.io.IOException;
import java.io.InputStream;

public abstract class AppHelper {

    public static final String KEY_IMG1 = "KEY_IMG1";
    public static final String KEY_IMG2 = "KEY_IMG2";
    public static final String KEY_IMG3 = "KEY_IMG3";
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_PROCESS = "KEY_PROCESS";
    public static final String KEY_CONTENT = "KEY_CONTENT";
    public static final String KEY_MATERIAL= "KEY_MATERIAL";
    public static final String KEY_ID= "KEY_ID";
    public static final String KEY_INTRODUCTION = "KEY_INTRODUCTION";
    public static final String TAG_FRAGMENT = "TAG_CONTENT_FRAGMENT";

    public static void ZoomOutImage(Context context, ImageView img){
       Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_anim);
        img.setAnimation(anim);
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static void moveData(int idLayout, FragmentManager fragmentManager, String id,
                                String title, String imgF1, String imgF2, String imgF3,
                                String process, String material, String introduction){
        FragmentContent fragmentContent = new FragmentContent ();
        Bundle args = new Bundle();
        args.putString(AppHelper.KEY_TITLE, title);
        args.putString(AppHelper.KEY_IMG1, imgF1);
        args.putString(AppHelper.KEY_IMG2, imgF2);
        args.putString(AppHelper.KEY_IMG3, imgF3);
        args.putString(AppHelper.KEY_PROCESS, process);
        args.putString(AppHelper.KEY_MATERIAL, material);
        args.putString(AppHelper.KEY_ID, id);
        args.putString(AppHelper.KEY_INTRODUCTION, introduction);

        fragmentContent.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idLayout, fragmentContent, TAG_FRAGMENT);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    public static void setImageBitmap(Context context,ImageView img, String name){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("img/"+name);
            Bitmap src = BitmapFactory.decodeStream(inputStream);
            img.setImageBitmap(src);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAnimationZoomInUp(View view){
        YoYo.with(Techniques.ZoomInUp)
                .duration(500)
                .playOn(view);
    }

    public static void setAnimationShake(View view){
        YoYo.with(Techniques.Shake)
                .duration(800)
                .playOn(view);
    }

    public static void setAnimationFadeIn(View view){
        YoYo.with(Techniques.FadeIn)
                .duration(500)
                .playOn(view);
    }

    public static void setAnimationZoomIn(View view){
        YoYo.with(Techniques.ZoomIn)
                .duration(800)
                .playOn(view);
    }

    public static boolean checkIsLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=  null){
            return true;
        } else {
           return false;
        }
    }

}