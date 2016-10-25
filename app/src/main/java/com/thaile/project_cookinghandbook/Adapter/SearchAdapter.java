package com.thaile.project_cookinghandbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.Object.ItemFood;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 10/8/2016.
 */

public class SearchAdapter extends ArrayAdapter<ItemFood>{
    ArrayList<ItemFood> customers, tempCustomer, suggestions;
    Context context;

    public SearchAdapter(Context context, ArrayList<ItemFood> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.customers = objects;
        this.tempCustomer = new ArrayList<ItemFood>(objects);
        this.suggestions = new ArrayList<ItemFood>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemFood itemFood = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search, parent, false);
        }
        TextView txtv = (TextView) convertView.findViewById(R.id.txtv_title);
        ImageView img = (ImageView) convertView.findViewById(R.id.img_food);
        if (txtv != null)
            txtv.setText(itemFood.getTitleFood());
        if (img != null)
            Glide.with(context).load(itemFood.getImgFood1()).override(80,80).centerCrop().into(img);

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ItemFood) resultValue).getTitleFood();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ItemFood itemFood : tempCustomer) {
                    if (itemFood.getTitleFood().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(itemFood);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<ItemFood> foods = (ArrayList<ItemFood>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ItemFood itemFood : foods) {
                    add(itemFood);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
