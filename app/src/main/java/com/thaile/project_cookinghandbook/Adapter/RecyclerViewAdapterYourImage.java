package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thai Le on 9/30/2016.
 */
public class RecyclerViewAdapterYourImage extends RecyclerView.Adapter<RecyclerViewAdapterYourImage.ViewHolder>{

    private ArrayList<String> arrayList;
    private Context context;
    private LayoutInflater inflater;


    public RecyclerViewAdapterYourImage(ArrayList<String> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    //tạo một ViewHolder mới khi được gọi.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recyclerview_yourimage, parent, false);
        return new ViewHolder(view);
    }

    //gắn kết dữ liệu và view trong ViewHolder.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String path = arrayList.get(position).toString();
        Uri uri = Uri.fromFile(new File(path));
        Glide.with(context).load(uri).into(holder.img);
        AppHelper.setAnimationZoomIn(holder.img);
    }


    @Override
    public int getItemCount() {
        if (arrayList!= null){
            return arrayList.size();
        } else {
            return 0;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView)view.findViewById(R.id.img_yourfood);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
