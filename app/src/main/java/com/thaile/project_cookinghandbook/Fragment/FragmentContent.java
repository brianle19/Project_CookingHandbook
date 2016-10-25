package com.thaile.project_cookinghandbook.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.Adapter.RecyclerViewAdapterYourImage;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.DBManager.DBManager;
import com.thaile.project_cookinghandbook.Dialog.DialogImage;
import com.thaile.project_cookinghandbook.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thai Le on 9/7/2016.
 */
public class FragmentContent extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1995;
    private View rootView;
    private DBManager db;
    private RecyclerViewAdapterYourImage yourImageAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> arrItemImage = new ArrayList<>();
    private ImageView imgFood1, imgFood2, imgFood3, imgHeart, img_camera, img_back;
    private Context context;
    private TextView txtvMaterial, txtvProcess, txtvTitle, txtvIntro;
    private String idFood, imgF1, imgF2, imgF3, material, process, title, intro;
    private String mImageFileLocation;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_content, container, false);
        db = new DBManager(context);
        initView();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initView() {
        AppHelper.setAnimationFadeIn(rootView.findViewById(R.id.frg_main_content));
        imgFood1 = (ImageView) rootView.findViewById(R.id.img_food1);
        imgFood2 = (ImageView) rootView.findViewById(R.id.img_food2);
        imgFood3 = (ImageView) rootView.findViewById(R.id.img_food3);
        img_camera = (ImageView) rootView.findViewById(R.id.img_camera);
        img_camera.setOnClickListener(this);
        img_camera.setVisibility(View.VISIBLE);
        img_back = (ImageView) rootView.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        txtvTitle = (TextView)rootView.findViewById(R.id.txtv_title_content);
        txtvIntro = (TextView) rootView.findViewById(R.id.txtv_introduction);
        txtvMaterial = (TextView) rootView.findViewById(R.id.txtv_material_detail);
        txtvProcess = (TextView) rootView.findViewById(R.id.txtv_process_detail);
        imgHeart = (ImageView) rootView.findViewById(R.id.img_heart_bar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_yourimage);
        imgHeart.setOnClickListener(this);

        imgFood1.setOnLongClickListener(this);
        imgFood2.setOnLongClickListener(this);
        imgFood3.setOnLongClickListener(this);
        // scrollView.setNestedScrollingEnabled(false);

        getData();

        Glide.with(context).load(imgF1).into(imgFood1);
        Glide.with(context).load(imgF2).into(imgFood2);
        Glide.with(context).load(imgF3).into(imgFood3);
        txtvIntro.setText(intro);
        txtvMaterial.setText(material);
        txtvProcess.setText(process);
        txtvTitle.setText(title);
        txtvTitle.setSelected(true);


        boolean checkIDisExisted = isExisted();
        if (checkIDisExisted){
            AppHelper.setImageBitmap(context, imgHeart, "ic_heart.png");
        } else {
            AppHelper.setImageBitmap(context, imgHeart, "ic_heart_full.png");
        }


        File folder[] = new File(Environment.getExternalStorageDirectory()+
                File.separator+"CookingHandbook"+
                File.separator+idFood+"/").listFiles();

        if (folder == null){
            return;
        }
        for (File myFile : folder){
            arrItemImage.add(myFile.getPath());
            Log.i("HIU", myFile.getPath());
        }

        yourImageAdapter = new RecyclerViewAdapterYourImage(arrItemImage, context);
        recyclerView.setAdapter(yourImageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void refreshFragment(){
        arrItemImage.clear();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

    }

    private void getData() {
        imgF1 = getArguments().getString(AppHelper.KEY_IMG1);
        imgF2 = getArguments().getString(AppHelper.KEY_IMG2);
        imgF3 = getArguments().getString(AppHelper.KEY_IMG3);
        material = getArguments().getString(AppHelper.KEY_MATERIAL);
        process = getArguments().getString(AppHelper.KEY_PROCESS);
        title = getArguments().getString(AppHelper.KEY_TITLE);
        idFood = getArguments().getString(AppHelper.KEY_ID);
        intro = getArguments().getString(AppHelper.KEY_INTRODUCTION);
    }

    public boolean isExisted(){
        ArrayList<String> arrID = db.rawSQLgetID("FavoriteFood");
        String idFavorite;
        if (arrID == null){
            return true;
        } else {
            for (int i = 0; i < arrID.size(); i++) {
                idFavorite = arrID.get(i).toString();
                if (idFood.equals(idFavorite)) {
                    return false;
                }
            }
            return true;
        }
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
            case R.id.img_heart_bar:
                boolean checkIDisExisted = isExisted();
                if (checkIDisExisted){
                    db.addItem(idFood,title, imgF1, imgF2, imgF3, process, material, intro);
                    AppHelper.setImageBitmap(context, imgHeart, "ic_heart_full.png");
                    AppHelper.setAnimationZoomInUp(imgHeart);

                } else {
                    Toast.makeText(context, "Bạn đã thêm vào yêu thích trước đó", Toast.LENGTH_SHORT).show();
                    AppHelper.setAnimationShake(imgHeart);
                }
                break;
            case R.id.img_camera:
                activeTakePhoto();
                break;
            case R.id.img_back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    public void activeTakePhoto(){
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");

        File photoFile = createImageFile();

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    public File createImageFile(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE"+timeStamp+"_"+idFood+ ".jpg";
        File storageDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+"CookingHandbook"+File.separator+idFood+File.separator);
        if (!storageDirectory.exists()){
            storageDirectory.mkdir();
        }
        File image = null;
        try {
            image = new File(storageDirectory + File.separator + imageFileName);
            mImageFileLocation = image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            arrItemImage.add(mImageFileLocation);
            Log.i("TEEO", mImageFileLocation);
            refreshFragment();
        }

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.img_food1:
                (new DialogImage(getActivity(), imgF1)).show();
                break;
            case R.id.img_food2:
                (new DialogImage(getActivity(), imgF2)).show();
                break;
            case R.id.img_food3:
                (new DialogImage(getActivity(), imgF3)).show();
                break;
            default:
                break;
        }
        return true;
    }
}
