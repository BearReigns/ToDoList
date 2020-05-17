package com.reigns.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private Button addBtn;
    public ArrayList<String> list = new ArrayList();

    private ListView mlv;

    private TodoListDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlv = findViewById(R.id.listView);
        addBtn = findViewById(R.id.addbtn);
        mlv.setOnCreateContextMenuListener(MainActivity.this);
        db = new TodoListDatabaseHelper(this);

        if (db.queryAll() != null) {

            list = db.queryAll();
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
            mlv.setAdapter(adapter);
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPage.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String add = data.getStringExtra("editTextValue");
            if (!add.equals("null")) {
                if (db.queryAll() != null) {
                    list = db.queryAll();
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                    adapter.add(add);
                    db.insert(add);
                    adapter.notifyDataSetChanged();
                    mlv.setAdapter(adapter);
                } else {
                    list = new ArrayList<>();
                    adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                    adapter.add(add);
                    db.insert(add);
                    adapter.notifyDataSetChanged();
                    mlv.setAdapter(adapter);
                }
            }
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = (int) mlv.getAdapter().getItemId(menuInfo.position);
        String remove = list.remove(pos);
        db.delete(remove);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);

    }
}
