package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.Object.ItemTopicBar;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Le on 8/29/2016.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private ArrayList<ItemTopicBar> arrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private MoveFragment move;

    public TopicAdapter(Context context, ArrayList arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_listview_navigation_bar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ItemTopicBar itemTopicBar = arrayList.get(position);
        AppHelper.setImageBitmap(context, viewHolder.img, itemTopicBar.getImgTopic());
        viewHolder.title.setText(itemTopicBar.getTitle());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (arrayList!= null){
            return arrayList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView title;

        public ViewHolder(View view) {
            super(view);
            img= (ImageView)view.findViewById(R.id.img_title);
            title = (TextView)view.findViewById(R.id.txtv_title);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            move.moveToFragment(pos);
        }
    }

    public void setMoveFragment(MoveFragment move){
        this.move = move;
    }

    public interface MoveFragment{
        public void moveToFragment(int pos);
    }

}
