package com.thaile.project_cookinghandbook.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.thaile.project_cookinghandbook.Object.ItemFood;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thai Le on 9/7/2016.
 */
public class DBManager {
    private SQLiteDatabase sqLiteDatabase ;
    private static final String PATH = "/data/data/com.thaile.project_cookinghandbook/databases";
    private final static String DATABASE_NAME = "FoodData.sqlite";
    private Context context;
    private UpdateList changeList;

    public DBManager(Context context){
        this.context = context;
        copyDatabaseToInternal();
    }

    private void open(){
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void close (){
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
    }


    private void copyDatabaseToInternal() {
        (new File(PATH)).mkdir();

        File file = new File(PATH + DATABASE_NAME);
        if (file.exists()){
            return;
        }

        try {
            DataInputStream input = new DataInputStream(context.getAssets().open(DATABASE_NAME));

            DataOutputStream output = new DataOutputStream(new FileOutputStream(PATH + DATABASE_NAME));

            byte[] b = new byte[1024];
            int length;

            while ((length = input.read(b)) != -1){
                output.write(b, 0, length);
            }

            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(String idFood){
        open();
            long result = sqLiteDatabase.delete("FavoriteFood", "Id=?", new String[]{idFood});

        if (result == -1){
            Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
            changeList.updateList();
        }
        close();
    }

    public void addItem(String id, String name, String img1,
                        String img2, String img3, String process, String material, String intro){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", id);
        contentValues.put("Name", name);
        contentValues.put("Img1", img1);
        contentValues.put("Img2", img2);
        contentValues.put("Img3", img3);
        contentValues.put("Process", process);
        contentValues.put("Material", material);
        contentValues.put("Introduction", intro);

        long result = sqLiteDatabase.insert("FavoriteFood", null, contentValues);
        if (result == -1){
            Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    public ArrayList<ItemFood> rawSQL(String tableName){
        open();
        String sql = "SELECT * FROM "+tableName;
        ArrayList<ItemFood> arrItem = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() == 0){
            return null;
        }
        cursor.moveToFirst();
        ItemFood itemFood;
        //
        int idFood = cursor.getColumnIndex("Id");
        int titleFood = cursor.getColumnIndex("Name");
        int imgFood1 = cursor.getColumnIndex("Img1");
        int imgFood2 = cursor.getColumnIndex("Img2");
        int imgFood3 = cursor.getColumnIndex("Img3");
        int materialFood = cursor.getColumnIndex("Material");
        int processFood = cursor.getColumnIndex("Process");
        int introFood = cursor.getColumnIndex("Introduction");

        while (!cursor.isAfterLast()){

            String id = cursor.getString(idFood);
            String title = cursor.getString(titleFood);
            String material = cursor.getString(materialFood);
            String img1 = cursor.getString(imgFood1);
            String img2 = cursor.getString(imgFood2);
            String img3 = cursor.getString(imgFood3);
            String process = cursor.getString(processFood);
            String introduction = cursor.getString(introFood);

            itemFood = new ItemFood(id,img1, img2, img3, process,material, title, introduction, 0);
            arrItem.add(itemFood);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return arrItem;
    }

    public ArrayList<String> rawSQLgetID(String tableName){
        open();
        String sql = "SELECT Id FROM "+tableName;
        ArrayList<String> arrId = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() == 0){
            return null;
        }
        cursor.moveToFirst();
        int idFood = cursor.getColumnIndex("Id");

        while (!cursor.isAfterLast()){
            String id = cursor.getString(idFood);
            arrId.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return arrId;
    }

    public void setUpdateList(UpdateList changeList){
        this.changeList = changeList;
    }

    public interface UpdateList{
        public void updateList();
    }
}
