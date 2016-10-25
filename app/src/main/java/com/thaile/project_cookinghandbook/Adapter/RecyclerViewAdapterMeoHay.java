package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thaile.project_cookinghandbook.Object.ItemMeo;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 9/5/2016.
 */
public class RecyclerViewAdapterMeoHay extends RecyclerView.Adapter<RecyclerViewAdapterMeoHay.ViewHolder>{

    private ArrayList<ItemMeo> arrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    public int posItem;
    private CallFragmentContentMeo callFragmentContentMeo;


    public RecyclerViewAdapterMeoHay(ArrayList<ItemMeo> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    //tạo một ViewHolder mới khi được gọi.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recyclerview_meohay, parent, false);
        return new ViewHolder(view);
    }

    //gắn kết dữ liệu và view trong ViewHolder.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ItemMeo itemMeo = arrayList.get(position);
        if (!itemMeo.getImg().isEmpty()) {
            Glide.with(context).load(itemMeo.getImg()).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    //holder.bar.setVisibility(View.INVISIBLE);
                    return false;
                }
            }).override(150,150).centerCrop().into(holder.img);
        }
        holder.txtv.setText(itemMeo.getTitle());

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView txtv;
       // ProgressBar bar;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView)view.findViewById(R.id.img_meohay);
           // bar = (ProgressBar) view.findViewById(R.id.progressBar);
            txtv = (TextView) view.findViewById(R.id.img_title_meohay);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            posItem = getAdapterPosition();
            ItemMeo itemMeo = arrayList.get(posItem);
            String title = itemMeo.getTitle();
            String img = itemMeo.getImg();
            String content = itemMeo.getContent();
            if(callFragmentContentMeo != null){
                callFragmentContentMeo.showContent(title,img,content);
            }
        }
    }

    public void setShowContent(CallFragmentContentMeo callFragmentContentMeo){
        this.callFragmentContentMeo = callFragmentContentMeo;
    }


    public interface CallFragmentContentMeo {
        public void showContent(String title, String imgF, String content);
    }
}
