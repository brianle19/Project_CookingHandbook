package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thai Le on 10/13/2016.
 */
public class GalleryAdapter extends BaseAdapter{
    private ArrayList<String> arrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public GalleryAdapter(Context context, ArrayList<String> arrayList){
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_thuvienanh, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_gallary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String path = arrayList.get(position).toString();

        Uri uri = Uri.fromFile(new File(path));
        Glide.with(context).load(uri).into(holder.img);
        AppHelper.setAnimationZoomIn(holder.img);
        return convertView;
    }

    public class ViewHolder{
        ImageView img;
    }
}
