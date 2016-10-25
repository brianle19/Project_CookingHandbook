package com.thaile.project_cookinghandbook.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.Activity.PictureActivity;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.Dialog.DialogGallery;
import com.thaile.project_cookinghandbook.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Thai Le on 10/20/2016.
 */

public class FragmentImage extends Fragment implements View.OnClickListener {
    private ImageView img, img_share;
    private View rootView;
    private Context context;
    private File file;
    private String path;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_image, container, false);
        img = (ImageView) rootView.findViewById(R.id.img_view);
        img_share = (ImageView) rootView.findViewById(R.id.img_share);
        img_share.setOnClickListener(this);

        //
        path = getArguments().getString(PictureActivity.KEY_POSITION);
        if (path == null){
            return null;
        }
        file = new File(path);

        //
        Uri uri = Uri.fromFile(file);
        Glide.with(context).load(uri).into(img);

        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_share:
                boolean isLogin = AppHelper.checkIsLogin();
                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = img.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                if (isLogin == true){
                    (new DialogGallery(getContext(), path, data)).show();
                } else {
                    Toast.makeText(context, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

}
