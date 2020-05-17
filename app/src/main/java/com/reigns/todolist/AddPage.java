package com.reigns.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPage extends AppCompatActivity {
    private Button mbtn;
    private EditText medit;
    private TodoListDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        mbtn = findViewById(R.id.btn_add);
        medit = findViewById(R.id.edit_add);
        db = new TodoListDatabaseHelper(this);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!medit.getText().toString().trim().equals("")) {

                    if (!medit.getText().toString().trim().equals(db.queryByTask(medit.getText().toString().trim()))) {
                        String add = medit.getText().toString().trim();
                        Intent data = new Intent(AddPage.this, MainActivity.class);
                        data.putExtra("editTextValue", add);
                        setResult(1, data);
                        finish();
                    } else {

                        Toast.makeText(AddPage.this, "请勿重复添加！", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(AddPage.this, "待办事项不可为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override

    public void onBackPressed() {


        Intent intent = new Intent();


        intent.putExtra("editTextValue", "null");


        setResult(1, intent);

        finish();

    }
}
