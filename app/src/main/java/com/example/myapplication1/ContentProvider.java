package com.example.myapplication1;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ContentProvider {
    private Activity activity;
    public ContentProvider(Activity activity) { this.activity = activity;}
    public ArrayList<User> getAllContact(){
        ArrayList<User> listContact = new ArrayList<>();
        String [] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        };
        Cursor cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,null,null,null);
        if(cursor.moveToFirst()){
            do{
                User c = new User(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3));
                listContact.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listContact;
    }
}
