package com.example.studyorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final int REQ_ADD_TASK = 100;
    private static final int REQ_DETAILS = 200;

    public static ArrayList<Task> doneList = new ArrayList<>();

    ListView listHomework, listQuiz;
    ArrayList<Task> homeworkList;
    ArrayList<Task> quizList;
    TaskAdapter homeworkAdapter;
    TaskAdapter quizAdapter;

    Button btnAdd;
    ImageView btnOpenDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initLists();

        addSampleData();

        setupAdapters();
        setupClicks();
    }

    private void initViews() {
        listHomework = findViewById(R.id.listHomework);
        listQuiz = findViewById(R.id.listQuiz);
        btnAdd = findViewById(R.id.btnAdd);
        btnOpenDone = findViewById(R.id.btnOpenDone);
    }

    private void initLists() {
        homeworkList = new ArrayList<>();
        quizList = new ArrayList<>();
        if (doneList == null) {
            doneList = new ArrayList<>();
        }
    }


    private void addSampleData() {

        if (homeworkList.isEmpty() && quizList.isEmpty()) {


            homeworkList.add(new Task("Math Exercises", "Mathematics", "05/02 - 4:00 PM", "Homework"));
            homeworkList.add(new Task("English Essay", "English", "06/02 - 6:00 PM", "Homework"));
            homeworkList.add(new Task("Physics Lab Report", "Physics", "07/02 - 8:00 PM", "Homework"));


            quizList.add(new Task("History Quiz", "History", "10/02 - 10:00 AM", "Exam"));
            quizList.add(new Task("Java Midterm", "CS 101", "15/02 - 12:00 PM", "Exam"));
        }
    }

    private void setupAdapters() {
        homeworkAdapter = new TaskAdapter(this, homeworkList);
        listHomework.setAdapter(homeworkAdapter);

        quizAdapter = new TaskAdapter(this, quizList);
        listQuiz.setAdapter(quizAdapter);
    }

    private void setupClicks() {

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AddTaskActivity.class);
            startActivityForResult(i, REQ_ADD_TASK);
        });


        btnOpenDone.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, DoneTasksActivity.class);
            startActivity(i);
        });


        listHomework.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = homeworkList.get(position);
            openDetails(selectedTask, position);
        });

        listQuiz.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = quizList.get(position);
            openDetails(selectedTask, position);
        });
    }

    private void openDetails(Task task, int position) {
        Intent i = new Intent(HomeActivity.this, TaskDetailsActivity.class);
        i.putExtra("title", task.getTitle());
        i.putExtra("subject", task.getSubject());
        i.putExtra("time", task.getTime());
        i.putExtra("type", task.getType());
        i.putExtra("position", position);
        startActivityForResult(i, REQ_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ADD_TASK && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String subject = data.getStringExtra("subject");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String type = data.getStringExtra("type");

            String fullTime = date + " - " + time;
            Task newTask = new Task(title, subject, fullTime, type);

            if (type != null && (type.equals("homework") || type.equals("Homework"))) {
                homeworkList.add(newTask);
                homeworkAdapter.notifyDataSetChanged();
            } else {
                quizList.add(newTask);
                quizAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == REQ_DETAILS && resultCode == RESULT_OK && data != null) {
            String action = data.getStringExtra("action");
            int position = data.getIntExtra("position", -1);
            String type = data.getStringExtra("type");

            if (position != -1 && action != null) {
                if (action.equals("delete")) {
                    if (type != null && (type.equals("homework") || type.equals("Homework"))) {
                        homeworkList.remove(position);
                        homeworkAdapter.notifyDataSetChanged();
                    } else {
                        quizList.remove(position);
                        quizAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();
                } else if (action.equals("update")) {
                    String newTitle = data.getStringExtra("title");
                    String newSubject = data.getStringExtra("subject");
                    String newTime = data.getStringExtra("time");

                    Task updatedTask = new Task(newTitle, newSubject, newTime, type);

                    if (type != null && (type.equals("homework") || type.equals("Homework"))) {
                        if (position < homeworkList.size()) {
                            homeworkList.set(position, updatedTask);
                            homeworkAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (position < quizList.size()) {
                            quizList.set(position, updatedTask);
                            quizAdapter.notifyDataSetChanged();
                        }
                    }
                    Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
                } else if (action.equals("done")) {
                    Task taskToMove = null;
                    if (type != null && (type.equals("homework") || type.equals("Homework"))) {
                        if (position < homeworkList.size()) {
                            taskToMove = homeworkList.get(position);
                            homeworkList.remove(position);
                            homeworkAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (position < quizList.size()) {
                            taskToMove = quizList.get(position);
                            quizList.remove(position);
                            quizAdapter.notifyDataSetChanged();
                        }
                    }

                    if (taskToMove != null) {
                        doneList.add(taskToMove);
                        Toast.makeText(this, "Moved to Done List", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}