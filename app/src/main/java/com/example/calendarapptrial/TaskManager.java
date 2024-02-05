package com.example.calendarapptrial;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static TaskManager instance;
    private List<Task> tasks;

    private TaskManager() {
        tasks = new ArrayList<>();
    }

    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
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

    // Add a method to get the checked state of a task
    public boolean isTaskChecked(int position) {
        return tasks.get(position).isChecked();
    }

    // Add a method to set the checked state of a task
    public void setTaskChecked(int position, boolean isChecked) {
        tasks.get(position).setChecked(isChecked);
    }
}
