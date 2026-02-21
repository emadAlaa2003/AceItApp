package com.example.studyorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
        }

        Task task = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvSubject = convertView.findViewById(R.id.tvSubject);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        ImageView imgIcon = convertView.findViewById(R.id.imgIcon); // تأكد إنك معطيه id صح في XML

        if (task != null) {
            tvTitle.setText(task.getTitle());
            tvSubject.setText(task.getSubject());
            tvDate.setText(task.getTime());

            String type = task.getType();

            if (type != null && (type.equalsIgnoreCase("Homework") || type.equalsIgnoreCase("واجب"))) {
                imgIcon.setImageResource(R.drawable.ic_homework);
            } else {
                imgIcon.setImageResource(R.drawable.ic_exam);
            }
        }

        return convertView;
    }
}