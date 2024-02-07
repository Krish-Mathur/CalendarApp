package com.example.calendarapptrial;

public class Task {
    private String description;
    private boolean isChecked;

    public Task(String description) {
        this.description = description;
        this.isChecked = false; //the default value for isChecked
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
