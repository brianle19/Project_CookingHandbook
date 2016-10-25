package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.Object.ItemSocial;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thaile.project_cookinghandbook.R.id.img_user;

/**
 * Created by Thai Le on 10/17/2016.
 */

public class SocialImageAdapter extends BaseAdapter {
    private ArrayList<ItemSocial> arrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public SocialImageAdapter(Context context, ArrayList<ItemSocial> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ItemSocial getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_social, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_social);
            holder.username = (TextView) convertView.findViewById(R.id.txtv_user);
            holder.txtv_status = (TextView) convertView.findViewById(R.id.txtv_status);
            holder.img_user = (CircleImageView) convertView.findViewById(img_user);
            holder.txtv_datetime = (TextView)convertView.findViewById(R.id.txtv_post_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemSocial social = arrayList.get(position);
        String link = social.getName();
        String usname = social.getUsername();
        String usphoto = social.getUserphoto();
        String status = social.getStatus();
        String dateTime = social.getDatetime();

        holder.txtv_status.setText(status);
        holder.username.setText(usname);
        holder.txtv_datetime.setText(dateTime);

        Glide.with(context).load(link).into(holder.img);
        Glide.with(context).load(usphoto).into(holder.img_user);
        return convertView;
    }

    public class ViewHolder{
        ImageView img;
        CircleImageView img_user;
        TextView txtv_status, username, txtv_datetime;
    }
}
