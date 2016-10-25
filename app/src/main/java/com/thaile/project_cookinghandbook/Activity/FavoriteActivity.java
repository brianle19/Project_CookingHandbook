package com.thaile.project_cookinghandbook.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thaile.project_cookinghandbook.Adapter.SearchAdapter;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.DBManager.DBManager;
import com.thaile.project_cookinghandbook.Object.ItemFood;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 10/13/2016.
 */

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DBManager.UpdateList, View.OnClickListener{
    public String tableName;
    private ItemFood itemFood;
    private SearchAdapter searchAdapter;
    private DBManager db;
    private ImageView img_delete_text;
    private AutoCompleteTextView search_view;
    private GridView gridView;
    private TextView empty;
    private ImageView img_back;
    private ArrayList<ItemFood> arrayList;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        initView();
    }

    private void initView() {
        gridView = (GridView)findViewById(R.id.gridView);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        empty = (TextView) findViewById(R.id.empty);
        search_view = (AutoCompleteTextView)findViewById(R.id.search_view);
        img_delete_text = (ImageView)findViewById(R.id.img_delete_text);
        search_view.setOnClickListener(this);
        img_delete_text.setOnClickListener(this);
        db = new DBManager(this);
        arrayList = db.rawSQL("FavoriteFood");
        adapter = new ListViewAdapter(FavoriteActivity.this, arrayList);
        if (arrayList == null) {
            empty.setVisibility(View.VISIBLE);
            return ;
        }
        db.setUpdateList(FavoriteActivity.this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(FavoriteActivity.this);


        search_view.setThreshold(1);
        searchAdapter = new SearchAdapter(FavoriteActivity.this, arrayList);
        search_view.setAdapter(searchAdapter);
        final ArrayList<ItemFood> finalItem = arrayList;
        search_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFood itemFood = finalItem.get(position);
                String idFood = itemFood.getId();
                String title = itemFood.getTitleFood();
                String imgF1 = itemFood.getImgFood1();
                String imgF2 = itemFood.getImgFood2();
                String imgF3 = itemFood.getImgFood3();
                String process = itemFood.getProcessFood();
                String material = itemFood.getMaterialFood();
                String introduction = itemFood.getIntroduction();
                AppHelper.moveData(R.id.activity_yeuthich,getSupportFragmentManager(), idFood, title, imgF1, imgF2, imgF3, process, material, introduction);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        itemFood = arrayList.get(position);
        String idF = itemFood.getId();
        String title = itemFood.getTitleFood();
        String imgF1 = itemFood.getImgFood1();
        String imgF2 = itemFood.getImgFood2();
        String imgF3 = itemFood.getImgFood3();
        String process = itemFood.getProcessFood();
        String material = itemFood.getMaterialFood();
        String introduction = itemFood.getIntroduction();
        AppHelper.moveData(R.id.activity_yeuthich,
                getSupportFragmentManager(),
                idF, title, imgF1, imgF2, imgF3, process, material, introduction);
    }


    public void createPopup(View view, final String id) {
        PopupMenu popup = new PopupMenu(FavoriteActivity.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_more_option, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog dialog = new AlertDialog.Builder(FavoriteActivity.this).create();
                dialog.setTitle("Thông báo");
                dialog.setMessage("Xóa khỏi danh sách yêu thích");
                //dialog.setIcon(R.drawable.doremon);
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       db.deleteItem(id);
                        adapter.notifyDataSetChanged();
                    }
                });

                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
        popup.show();//showing popup menu
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ON", "pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ON", "restart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                onBackPressed();
                break;

            case R.id.img_delete_text:
                search_view.setText("");
                break;
            case R.id.search_view:
                img_delete_text.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void updateList() {
        arrayList.clear();
        arrayList = db.rawSQL("FavoriteFood");
        adapter = new ListViewAdapter(FavoriteActivity.this, arrayList);
        gridView.setAdapter(adapter);
    }

    public class ListViewAdapter extends BaseAdapter {

        private ArrayList<ItemFood> arrayList;
        private Context context;
        private LayoutInflater inflater;

        public ListViewAdapter(Context context, ArrayList arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (arrayList != null) {
                return arrayList.size();
            } else {
                return 0;
            }
        }

        @Override
        public ItemFood getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gridview_yeuthich, parent, false);
                holder = new ViewHolder();
                holder.img = (ImageView) convertView.findViewById(R.id.img_food);
                holder.more_option = (ImageView) convertView.findViewById(R.id.img_more_option);
                holder.txtv = (TextView) convertView.findViewById(R.id.txtv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ItemFood food = arrayList.get(position);
            Glide.with(context).load(food.getImgFood1()).override(80, 80).centerCrop().into(holder.img);
            holder.txtv.setText(food.getTitleFood());
            holder.more_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            createPopup(holder.more_option, food.getId());
                        }
                    }, 300);

                }
            });

            AppHelper.setAnimationZoomIn(convertView);
            return convertView;
        }


        public class ViewHolder {
            ImageView img;
            TextView txtv;
            ImageView more_option;
        }
    }
}
