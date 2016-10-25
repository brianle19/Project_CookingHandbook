package com.thaile.project_cookinghandbook.DBManager;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thaile.project_cookinghandbook.Object.ItemMeo;

import java.util.ArrayList;

public class FirebaseManager {
    private DatabaseReference mData;
    private  ArrayList<ItemMeo> arrMeo = new ArrayList<>();
    private CallChangeData callChangeData;

    public FirebaseManager(){
        mData = FirebaseDatabase.getInstance().getReference();
    }

    public ArrayList<ItemMeo> getDataMeoHay(String tableName){
        mData.child(tableName).addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                ItemMeo itemMeo = dataSnapshot.getValue(ItemMeo.class);
                arrMeo.add(new ItemMeo(itemMeo.getImg(), itemMeo.getContent(), itemMeo.getTitle()));
                callChangeData.changeData();
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                ItemMeo itemMeo = dataSnapshot.getValue(ItemMeo.class);
                arrMeo.add(new ItemMeo(itemMeo.getImg(), itemMeo.getContent(), itemMeo.getTitle()));
                callChangeData.changeData();

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return arrMeo;
    }

    public void setChange(CallChangeData callChangeData){
        this.callChangeData = callChangeData;
    }

    public interface CallChangeData{
        public void changeData();
    }
}