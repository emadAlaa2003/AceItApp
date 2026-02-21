package com.example.studyorganizer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DoneTasksActivity extends AppCompatActivity {

    ListView listDone;
    ImageView btnBack, btnClearAll;
    Button btnAll, btnExam, btnHomework;


    ArrayList<Task> displayList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);

        initViews();


        displayList = new ArrayList<>();
        if (HomeActivity.doneList != null) {
            displayList.addAll(HomeActivity.doneList);
        }


        adapter = new TaskAdapter(this, displayList);
        listDone.setAdapter(adapter);


        setupClicks();
    }

    private void initViews() {
        listDone = findViewById(R.id.listDoneTasks);
        btnBack = findViewById(R.id.btnBack);
        btnClearAll = findViewById(R.id.btnClearAll);
        btnAll = findViewById(R.id.btnAll);
        btnExam = findViewById(R.id.btnExam);
        btnHomework = findViewById(R.id.btnHomework);
    }

    private void setupClicks() {

        btnBack.setOnClickListener(v -> finish());


        btnClearAll.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear All")
                    .setMessage("Delete all completed tasks?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        HomeActivity.doneList.clear();
                        displayList.clear();

                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "All Cleared!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        btnAll.setOnClickListener(v -> {
            displayList.clear();
            displayList.addAll(HomeActivity.doneList);
            adapter.notifyDataSetChanged();
        });


        btnExam.setOnClickListener(v -> {
            displayList.clear();

            for (Task t : HomeActivity.doneList) {
                if (t.getType().equals("Exam")) {
                    displayList.add(t);
                }
            }
            adapter.notifyDataSetChanged();
        });


        btnHomework.setOnClickListener(v -> {
            displayList.clear();


            for (Task t : HomeActivity.doneList) {
                if (t.getType().equals("Homework")) {
                    displayList.add(t);
                }
            }
            adapter.notifyDataSetChanged();
        });
    }
}