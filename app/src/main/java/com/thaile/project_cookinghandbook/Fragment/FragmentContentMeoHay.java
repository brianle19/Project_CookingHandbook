package com.thaile.project_cookinghandbook.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.R;

/**
 * Created by Thai Le on 9/7/2016.
 */
public class FragmentContentMeoHay extends Fragment {
    private View rootView;
    private ScrollView scrollView;
    private ImageView imgFood, imgHeart, imgCamera;
    private Context context;
    private TextView txtvContent, txtvTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content_meohaynhabep, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        AppHelper.setAnimationFadeIn(rootView.findViewById(R.id.frg_meohay));
        imgFood = (ImageView) rootView.findViewById(R.id.img_food);
        txtvContent = (TextView)rootView.findViewById(R.id.txtv_content);
        txtvTitle = (TextView)rootView.findViewById(R.id.txtv_title_content);
        imgHeart = (ImageView) rootView.findViewById(R.id.img_heart_bar);
        imgCamera = (ImageView) rootView.findViewById(R.id.img_camera);
        imgCamera.setVisibility(View.INVISIBLE);
        imgHeart.setVisibility(View.INVISIBLE);

        // scrollView.setNestedScrollingEnabled(false);

        String img = getArguments().getString(AppHelper.KEY_IMG1);
        String content = getArguments().getString(AppHelper.KEY_CONTENT);
        String title = getArguments().getString(AppHelper.KEY_TITLE);

        Glide.with(context).load(img).into(imgFood);
        txtvContent.setText(content);
        txtvTitle.setText(title);
        txtvTitle.setMarqueeRepeatLimit(-1);
        txtvTitle.setSelected(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
