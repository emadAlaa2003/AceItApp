package com.example.studyorganizer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateTask extends AppCompatActivity {

    EditText etTitle, etSubject;
    TextView tvDate, tvTime;
    RadioGroup rgType;
    RadioButton rbExam, rbHomework;
    Button btnUpdate;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        initViews();
        getAndSetData();
        setupClickListeners(); // ✅ ضفنا هاي الدالة عشان تشغل الازرار
    }

    private void initViews() {
        etTitle = findViewById(R.id.etUpdateTitle);
        etSubject = findViewById(R.id.etUpdateSubject);
        tvDate = findViewById(R.id.tvUpdateDate);
        tvTime = findViewById(R.id.tvUpdateTime);
        rgType = findViewById(R.id.rgUpdateType);
        rbExam = findViewById(R.id.rbUpdateExam);
        rbHomework = findViewById(R.id.rbUpdateHomework);
        btnUpdate = findViewById(R.id.btnUpdateTask);
        btnBack = findViewById(R.id.btnBack);
    }

    private void getAndSetData() {
        Intent i = getIntent();
        if(i != null) {
            etTitle.setText(i.getStringExtra("title"));
            etSubject.setText(i.getStringExtra("subject"));

            // استقبال الوقت الكامل وفصله (مؤقتاً بنحط النص كامل في الوقت إذا بدك)
            // أو بنعرض قيم افتراضية إذا كانت النصوص فاضية
            String fullTime = i.getStringExtra("time");
            tvDate.setText("Select Date"); // قيمة افتراضية حتى يختار المستخدم
            tvTime.setText(fullTime);      // بنعرض الوقت القديم هنا

            String type = i.getStringExtra("type");
            if (type != null && (type.equals("Homework") || type.equals("homework"))) {
                rbHomework.setChecked(true);
            } else {
                rbExam.setChecked(true);
            }
        }
    }

    // ✅ دالة جديدة لبرمجة الضغطات
    private void setupClickListeners() {

        // 1. برمجة اختيار التاريخ
        tvDate.setOnClickListener(v -> {
            // بنجيب التاريخ الحالي عشان يفتح عليه
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    UpdateTask.this,
                    (view, year1, month1, dayOfMonth) -> {
                        tvDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        tvTime.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    UpdateTask.this,
                    (view, hourOfDay, minute1) -> {
                        String amPm;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                            if (hourOfDay > 12) hourOfDay -= 12;
                        } else {
                            amPm = "AM";
                            if (hourOfDay == 0) hourOfDay = 12;
                        }
                        String formattedMinute = (minute1 < 10) ? "0" + minute1 : String.valueOf(minute1);

                        tvTime.setText(hourOfDay + ":" + formattedMinute + " " + amPm);
                    },
                    hour, minute, false);
            timePickerDialog.show();
        });

        btnUpdate.setOnClickListener(v -> {
            saveAndReturnData();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void saveAndReturnData() {
        String newTitle = etTitle.getText().toString();
        String newSubject = etSubject.getText().toString();

        String newDate = tvDate.getText().toString();
        String newTime = tvTime.getText().toString();
        String fullDateTime = newDate + " | " + newTime;

        if(newDate.equals("Select Date")) {
            fullDateTime = newTime;
        }

        String newType = rbExam.isChecked() ? "Exam" : "Homework";

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", newTitle);
        resultIntent.putExtra("subject", newSubject);
        resultIntent.putExtra("time", fullDateTime);
        resultIntent.putExtra("type", newType);

        resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}