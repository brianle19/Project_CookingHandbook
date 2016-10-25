package com.thaile.project_cookinghandbook.Fragment.FragmentTopic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thaile.project_cookinghandbook.Adapter.RecyclerViewAdapterMeoHay;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.DBManager.FirebaseManager;
import com.thaile.project_cookinghandbook.Fragment.FragmentContentMeoHay;
import com.thaile.project_cookinghandbook.Object.ItemMeo;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 9/12/2016.
 */
public class FragmentMeoNhaBep extends Fragment implements RecyclerViewAdapterMeoHay.CallFragmentContentMeo, FirebaseManager.CallChangeData {
    private Context context;
    private FirebaseManager firebaseManager;
    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<ItemMeo> arrList;
    private RecyclerViewAdapterMeoHay adapter;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    public void initData(){
        firebaseManager = new FirebaseManager();
        arrList = firebaseManager.getDataMeoHay("MeoHayNhaBep");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meohaynhabep, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewList);
        initData();
        adapter = new RecyclerViewAdapterMeoHay(arrList, context);
        adapter.notifyDataSetChanged();
        adapter.setShowContent(this);
        firebaseManager.setChange(this);

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
    public void showContent(String title, String imgF, String content) {
        FragmentContentMeoHay fragmentContent = new FragmentContentMeoHay ();
        Bundle args = new Bundle();
        args.putString(AppHelper.KEY_TITLE, title);
        args.putString(AppHelper.KEY_IMG1, imgF);
        args.putString(AppHelper.KEY_CONTENT, content);

        fragmentContent.setArguments(args);
        //Inflate the fragment

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentContent);
        fragmentTransaction.addToBackStack("Hello");
        fragmentTransaction.commit();
    }

    @Override
    public void changeData() {
        adapter.notifyDataSetChanged();
    }


}
