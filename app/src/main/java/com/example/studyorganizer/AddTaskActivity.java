package com.example.studyorganizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTitle, etSubject;
    TextView tvDate, tvTime;
    RadioButton rbExam, rbHomework;
    Button btnAddTask;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1) ربط العناصر
        etTitle = findViewById(R.id.etTitle);
        etSubject = findViewById(R.id.etSubject);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        rbExam = findViewById(R.id.rbExam);
        rbHomework = findViewById(R.id.rbHomework);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(v -> finish());


        tvDate.setOnClickListener(v -> openDatePicker());


        tvTime.setOnClickListener(v -> openTimePicker());


        btnAddTask.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String subject = etSubject.getText().toString().trim();
            String date = tvDate.getText().toString().trim();
            String time = tvTime.getText().toString().trim();


            String type = "exam";
            if (rbHomework.isChecked()) type = "homework";


            if (title.isEmpty() || subject.isEmpty()) {
                Toast.makeText(this, "Please enter Title and Subject", Toast.LENGTH_SHORT).show();
                return;
            }

            if (date.equals("Select Date") || time.equals("Select Time")) {
                Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show();
                return;
            }


            Intent data = new Intent();
            data.putExtra("title", title);
            data.putExtra("subject", subject);
            data.putExtra("date", date);
            data.putExtra("time", time);
            data.putExtra("type", type);

            setResult(RESULT_OK, data);
            finish();
        });
    }

    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {

            String date = d + "/" + (m + 1) + "/" + y;
            tvDate.setText(date);
        }, year, month, day);

        dialog.show();
    }

    private void openTimePicker() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, (view, h, min) -> {
            String time = h + ":" + (min < 10 ? "0" + min : min);
            tvTime.setText(time);
        }, hour, minute, false);

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
