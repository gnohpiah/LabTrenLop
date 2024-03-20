package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int SelectedItemId;
    private ListView lvcontact;
    private EditText etsearch;
    FloatingActionButton flbtn;
    ArrayList<User> listUser;
    Adapter listUserAdapter;
    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvcontact = findViewById(R.id.lvContact);
        etsearch = findViewById(R.id.etSearch);
        flbtn = findViewById(R.id.flbtAdd);
        listUser = new ArrayList<>();
        db = new MyDB(this,"ContactDB2",null,1);
        db.addContact(new User(1,"Nguyen Van An","456446","img1"));
        db.addContact(new User(2,"Nguyen Van B","4789512","img2"));
        db.addContact(new User(3,"Nguyen Van C","12023","img3"));
        db.addContact(new User(4,"Nguyen Van D","798451","img4"));
        db.addContact(new User(5,"Nguyen Van E","324234","img5"));
        listUser = db.getAllContact();
       /* listUser.add(new User(1,"Nam","132132",""));
        listUser.add(new User(2,"Abc","1456465",""));
        listUser.add(new User(3,"Hung","45646",""));*/
        listUserAdapter = new Adapter(this,listUser);
        lvcontact.setAdapter(listUserAdapter);

        flbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                startActivityForResult(intent,100);
            }
        });

        registerForContextMenu(lvcontact);
        lvcontact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemId = position;
                return false;
            }
        });
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listUserAdapter.getFilter().filter(s.toString());
                lvcontact.setAdapter(listUserAdapter);
                listUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b = data.getExtras();
        int id = b.getInt("Id");
        String name = b.getString("Name");
        String phone = b.getString("Phone");
        User newuser = new User(id,name,phone,"Image");
        if(requestCode == 100 && resultCode == 150) {
            listUser.add(newuser);
            db.addContact(newuser);
            listUserAdapter.notifyDataSetChanged();
        } else if (requestCode == 200 && resultCode == 150){
            listUser.set(SelectedItemId,newuser);
            Toast.makeText(this, newuser.getName(), Toast.LENGTH_SHORT).show();
            lvcontact.setAdapter(listUserAdapter);
            listUserAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuSortByName){
            Toast.makeText(this,"Sort by name",Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mnuSortByPhone){
            Toast.makeText(this,"Sort by phone",Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mnuBroadcast){
           /* Intent intent = new Intent("com.example.listview2023.SOME_ACTION");
            sendBroadcast(intent);*/
            Toast.makeText(this,"Broadcast",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.actionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        User c = listUser.get(SelectedItemId);
        if(item.getItemId() == R.id.mnuEdit){
            Toast.makeText(this,"Edit",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,SubActivity.class);
            Bundle b = new Bundle();
            b.putInt("Id",c.getId());
            b.putString("Image",c.getImg());
            b.putString("Name",c.getName());
            b.putString("Phone",c.getPhone());
            intent.putExtras(b);
            startActivityForResult(intent,200);
        } else if (item.getItemId() == R.id.mnuDelete) {
            Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.mnuCall){
            Intent in = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" +
                            c.getPhone()));
            startActivity(in);
        } else if(item.getItemId() == R.id.mnuSms){
            Intent intent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("sms:" + c.getPhone()));
            startActivity(intent);
            Toast.makeText(this,"Sms",Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }


}