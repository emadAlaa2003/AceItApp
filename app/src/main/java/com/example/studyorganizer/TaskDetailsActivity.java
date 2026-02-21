package com.example.studyorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TaskDetailsActivity extends AppCompatActivity {


    private static final int REQ_UPDATE_TASK = 300;

    TextView tvTitle, tvType, tvSubject, tvTime;
    ImageView btnBack, btnEdit;
    Button btnDelete, btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        displayTaskData();
        setupClickListeners();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvDetailsTitle);
        tvType = findViewById(R.id.tvDetailsType);
        tvSubject = findViewById(R.id.tvDetailsSubject);
        tvTime = findViewById(R.id.tvDetailsDateTime);

        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnDone = findViewById(R.id.btnDone);
    }

    private void displayTaskData() {
        Intent intent = getIntent();
        if (intent != null) {
            tvTitle.setText(intent.getStringExtra("title"));
            tvSubject.setText(intent.getStringExtra("subject"));
            tvTime.setText(intent.getStringExtra("time"));
            tvType.setText(intent.getStringExtra("type"));
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent resultIntent = new Intent();
                        int position = getIntent().getIntExtra("position", -1);
                        String type = getIntent().getStringExtra("type");

                        resultIntent.putExtra("position", position);
                        resultIntent.putExtra("type", type);
                        resultIntent.putExtra("action", "delete");

                        setResult(RESULT_OK, resultIntent);
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnDone.setOnClickListener(v -> {
            Intent resultIntent = new Intent();

            int position = getIntent().getIntExtra("position", -1);
            String type = getIntent().getStringExtra("type");

            resultIntent.putExtra("position", position);
            resultIntent.putExtra("type", type);
            resultIntent.putExtra("action", "done");

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnEdit.setOnClickListener(v -> {
            // انتبه: تأكد ان اسم الكلاس عندك UpdateTaskActivity مش UpdateTask
            Intent i = new Intent(TaskDetailsActivity.this, UpdateTask.class);
            i.putExtra("title", tvTitle.getText().toString());
            i.putExtra("subject", tvSubject.getText().toString());
            i.putExtra("time", tvTime.getText().toString());
            i.putExtra("type", tvType.getText().toString());
            i.putExtra("position", getIntent().getIntExtra("position", -1));

            startActivityForResult(i, REQ_UPDATE_TASK);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_UPDATE_TASK && resultCode == RESULT_OK && data != null) {

            String newTitle = data.getStringExtra("title");
            String newSubject = data.getStringExtra("subject");
            String newTime = data.getStringExtra("time");
            String newType = data.getStringExtra("type");

            tvTitle.setText(newTitle);
            tvSubject.setText(newSubject);
            tvTime.setText(newTime);
            tvType.setText(newType);

            Intent homeIntent = new Intent();
            homeIntent.putExtra("action", "update");
            homeIntent.putExtra("position", data.getIntExtra("position", -1));

            homeIntent.putExtra("title", newTitle);
            homeIntent.putExtra("subject", newSubject);
            homeIntent.putExtra("time", newTime);
            homeIntent.putExtra("type", newType);

            setResult(RESULT_OK, homeIntent);

            Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}