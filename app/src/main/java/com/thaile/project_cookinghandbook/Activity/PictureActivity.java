package com.thaile.project_cookinghandbook.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thaile.project_cookinghandbook.Adapter.GalleryAdapter;
import com.thaile.project_cookinghandbook.Fragment.FragmentImage;
import com.thaile.project_cookinghandbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thai Le on 10/20/2016.
 */

public class PictureActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String KEY_POSITION = "KEY_POS";
    private GridView gridView;
    private TextView textViewTitle;
    private ImageView img_back;
    private GalleryAdapter adapter;
    private ArrayList<String> arrList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_collection);

        initView();
    }

    private void initData() {
        File folder[] = new File(Environment.getExternalStorageDirectory()+
                File.separator+"CookingHandbook"+"/").listFiles();

        if (folder == null){
            return ;
        } else {
            for (File listAll: folder){
                if (listAll.isDirectory()){
                    File file[] = new File(listAll.getPath()).listFiles();
                    for (File listFile : file){
                        String pathName = listFile.getPath();
                        arrList.add(pathName);
                    }
                }
            }
        }

    }
    private void initView() {
        gridView = (GridView) findViewById(R.id.grid_view_image);
        textViewTitle = (TextView)findViewById(R.id.txtv_title_content);
        textViewTitle.setText("Thư viện ảnh");
        img_back = (ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();
        if (arrList.size() == 0){
            return ;
        }
        adapter = new GalleryAdapter(this, arrList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(PictureActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentImage fragmentImage = new FragmentImage();
        Bundle argsPath = new Bundle();
        String pathPhoto = arrList.get(position);
        argsPath.putString(KEY_POSITION, pathPhoto);

        fragmentImage.setArguments(argsPath);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_pic_collection, fragmentImage);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
