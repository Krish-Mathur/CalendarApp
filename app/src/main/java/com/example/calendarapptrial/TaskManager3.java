package com.example.calendarapptrial;

import java.util.ArrayList;
import java.util.List;

public class TaskManager3 {
    private static TaskManager3 instance;
    private List<Task> tasks;

    private TaskManager3() {
        tasks = new ArrayList<>();
    }

    public static synchronized TaskManager3 getInstance() {
        if (instance == null) {
            instance = new TaskManager3();
        }
        return instance;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    public void removeTask(int position) {
        tasks.remove(position);
    }

    public void editTask(int position, String description) {
        Task task = tasks.get(position);
        task.setDescription(description);
    }

    public boolean isTaskChecked(int position) {
        return tasks.get(position).isChecked();
    }

    public void setTaskChecked(int position, boolean isChecked) {
        tasks.get(position).setChecked(isChecked);
    }
}
