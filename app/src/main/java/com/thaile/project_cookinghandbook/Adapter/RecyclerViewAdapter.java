package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thaile.project_cookinghandbook.Object.ItemFood;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Thai Le on 9/5/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<ItemFood> arrayList = new ArrayList<>();
    private ArrayList<ItemFood> arrFilter = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    public int posItem;
    private CallFragmentContent callFragmentContent;


    public RecyclerViewAdapter(ArrayList<ItemFood> arrayList, Context context){
        this.arrayList = arrayList;
        this.arrFilter = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //tạo một ViewHolder mới khi được gọi.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    //gắn kết dữ liệu và view trong ViewHolder.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ItemFood itemFood = arrayList.get(position);
            Glide.with(context).load(itemFood.getImgFood1()).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.INVISIBLE);
                    return false;
                }
            }).override(80,80).centerCrop().into(holder.img);
        holder.txtv.setText(itemFood.getTitleFood());
        holder.txtvIntro.setText(itemFood.getIntroduction());
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
        CircleImageView img;
        TextView txtv, txtvIntro;
        ProgressBar progressBar;
        public ViewHolder(View view) {
            super(view);
            img = (CircleImageView)view.findViewById(R.id.img_food);
            img.setOnClickListener(this);
            txtv = (TextView) view.findViewById(R.id.txtv_name_food);
            txtv.setOnClickListener(this);
            txtvIntro = (TextView) view.findViewById(R.id.txtv_introduction);
            txtvIntro.setOnClickListener(this);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            posItem = getAdapterPosition();
            ItemFood itemFood = arrayList.get(posItem);
                    posItem = getAdapterPosition();
                    String idFood = itemFood.getId();
                    String title = itemFood.getTitleFood();
                    String imgF1 = itemFood.getImgFood1();
                    String imgF2 = itemFood.getImgFood2();
                    String imgF3 = itemFood.getImgFood3();
                    String process = itemFood.getProcessFood();
                    String material = itemFood.getMaterialFood();
                    String introduction = itemFood.getIntroduction();
                    if(callFragmentContent != null){
                        callFragmentContent.showContent(idFood,title,imgF1, imgF2, imgF3, process, material, introduction);
                    }

        }

    }

    public void setShowContent(CallFragmentContent callFragmentContent){
        this.callFragmentContent = callFragmentContent;
    }


    public interface CallFragmentContent {
        public void showContent(String id,String title, String imgF1, String imgF2,
                                String imgF3, String process, String material, String introduction);
    }
}
