package com.example.myapplication1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB  extends SQLiteOpenHelper {
    public static final String TableName = "ContactTable";
    public static final String Id = "Id";
    public static final String Name = "Fullname";
    public static final String Phone = "Phonenumber";
    public static final String Image = "Image";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer Primary key, "
                + Name + " Text, "
                + Phone + " Text, "
                + Image + " Text)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
    }

    public void resetData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete from " + TableName;
        db.execSQL(sql);
    }



    public void addContact(User contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id,contact.getId());
        value.put(Name,contact.getName());
        value.put(Phone,contact.getPhone());
        value.put(Image,contact.getImg());
        db.insert(TableName,null,value);
        db.close();
    }

    public ArrayList<User> getAllContact(){
        ArrayList<User> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                User contact = new User(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3));
                list.add(contact);
            }
        }
        return list;
    }

    public void updateContact(int id,User contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id,contact.getId());
        value.put(Name,contact.getName());
        value.put(Phone,contact.getPhone());
        value.put(Image,contact.getImg());
        db.update(TableName,value,Id + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
