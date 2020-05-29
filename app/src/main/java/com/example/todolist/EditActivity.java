package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    public static final String EXTRA_TASK = "EXTRA_TASK";
    public Task task;
    public EditText editText;
    public Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        editText = findViewById(R.id.editTask);

        if (getIntent().hasExtra(EXTRA_TASK)) {
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            editText.setText(task.text);
        } else {
            task = new Task();
        }
        btn = findViewById(R.id.buttonSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() > 0) {
                    task.text = editText.getText().toString();
                    task.done = false;
                    task.time = System.currentTimeMillis();
                    if (getIntent().hasExtra(EXTRA_TASK)) {
                        App.getInstance().getTasksDao().update(task);
                    } else {
                        App.getInstance().getTasksDao().insert(task);
                    }
                    finish();
                }
            }
        });
    }

}



