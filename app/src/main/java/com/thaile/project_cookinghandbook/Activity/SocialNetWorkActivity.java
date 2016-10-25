package com.thaile.project_cookinghandbook.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thaile.project_cookinghandbook.Adapter.SocialImageAdapter;
import com.thaile.project_cookinghandbook.Object.ItemSocial;
import com.thaile.project_cookinghandbook.R;

import java.util.ArrayList;

/**
 * Created by Thai Le on 9/27/2016.
 */
public class SocialNetWorkActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference mData;
    private SocialImageAdapter adapter;
    private ArrayList<ItemSocial> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=  null){
            String userID = user.getUid();
            Log.i("ID", userID);
        } else {
            Toast.makeText(SocialNetWorkActivity.this, "Bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
            finish();
        }

        initView();
    }

    private void initView() {
        getImage();
        listView  = (ListView) findViewById(R.id.listview_social);
    }

    public void getImage(){
        mData = FirebaseDatabase.getInstance().getReference();
        Query query = mData.child("Photos").orderByChild("timestamp");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ItemSocial itemSocial = data.getValue(ItemSocial.class);
                    arrayList.add(new ItemSocial(itemSocial.getName(), itemSocial.getUsername(), itemSocial.getUserphoto(),
                            itemSocial.getStatus(),itemSocial.getTimestamp(), itemSocial.getDatetime()));
                }
                adapter = new SocialImageAdapter(SocialNetWorkActivity.this, arrayList);
                listView.setAdapter(adapter);
                if (adapter != null){
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
