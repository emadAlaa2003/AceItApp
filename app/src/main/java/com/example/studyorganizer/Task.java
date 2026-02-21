package com.example.studyorganizer;

public class Task {

    private String title;
    private String subject;
    private String time;
    private String type;

    public Task(String title, String subject, String time, String type) {
        this.title = title;
        this.subject = subject;
        this.time = time;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
