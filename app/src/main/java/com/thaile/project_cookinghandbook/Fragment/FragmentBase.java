package com.thaile.project_cookinghandbook.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thaile.project_cookinghandbook.Adapter.RecyclerViewAdapter;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.Object.ItemFood;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 9/17/2016.
 */
public class FragmentBase extends Fragment implements RecyclerViewAdapter.CallFragmentContent{
    public Context context;
    private DatabaseReference mData;
    public String tableName;
    public View rootView;
    private ProgressBar progressBar;
    public RecyclerView recyclerView;
    public ArrayList<ItemFood> arrItem = new ArrayList<>();
    public RecyclerViewAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    public ArrayList<ItemFood> getArrItem() {
        return arrItem;
    }

    public String getTableName() {
        return tableName;
    }

    public void initData(String tableName){
        mData = FirebaseDatabase.getInstance().getReference();
        this.tableName = tableName;
        Query query = mData.child(tableName).orderByChild("timestamp");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrItem.clear();
                (new DataAsync()).execute(dataSnapshot);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_base, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewList);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        initData(tableName);

        adapter = new RecyclerViewAdapter(arrItem, context);
        Log.i("ARR", arrItem.size()+"--");
        adapter.setShowContent(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return rootView;
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
    public void showContent(String id, String title, String imgF1, String imgF2, String imgF3, String process, String material, String introduction) {
        AppHelper.moveData(R.id.activity_home,this.getFragmentManager(), id,title, imgF1, imgF2, imgF3, process, material, introduction);
    }


    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public class DataAsync extends AsyncTask<Object, Void, ArrayList<ItemFood>> {

        @Override
        protected void onPreExecute() {
            Log.i("TAG", "Loading");
            progressBar.setVisibility(View.VISIBLE);
        }

        //do in background: cannot update views
        @Override
        protected ArrayList<ItemFood> doInBackground(Object... params) {
            DataSnapshot dataSnapshot = (DataSnapshot)params[0];;
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ItemFood itemFood = snapshot.getValue(ItemFood.class);
                arrItem.add(new ItemFood(snapshot.getKey().toString(),itemFood.getImgFood1(),
                        itemFood.getImgFood2(), itemFood.getImgFood3(),
                        itemFood.getProcessFood(), itemFood.getMaterialFood(),
                        itemFood.getTitleFood(), itemFood.getIntroduction(), itemFood.getTimestamp()));
            }
            return arrItem;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemFood> itemFoods) {
            Log.i("TAG", "Done!");
            progressBar.setVisibility(View.INVISIBLE);
            getArrItem();
            adapter.notifyDataSetChanged();
        }
    }

}
