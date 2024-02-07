package com.example.calendarapptrial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class class_action_view extends AppCompatActivity implements CustomAdapter2.OnItemClickListener {
    private CustomAdapter2 itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_action_activity);
        lvItems = findViewById(R.id.lvItems);

        TaskManager2 taskManager = TaskManager2.getInstance(); // Initialize TaskManager2

        itemsAdapter = new CustomAdapter2(this, (ArrayList<Task>) taskManager.getTasks());
        itemsAdapter.setOnItemClickListener(this);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        TaskManager2.getInstance().removeTask(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                showEditTaskDialog(position);
            }
        });
    }

    private void showEditTaskDialog(final int position) {
        // Retrieve the task from TaskManager2 based on position
        Task currentTask = TaskManager2.getInstance().getTasks().get(position);
        String currentTaskDescription = currentTask.getDescription();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        final EditText input = new EditText(this);
        input.setText(currentTaskDescription);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedText = input.getText().toString();

                TaskManager2.getInstance().editTask(position, editedText);

                itemsAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void onAddItem(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_class, null);

        EditText etCourseName = dialogView.findViewById(R.id.etCourseName);
        EditText etCourseSection = dialogView.findViewById(R.id.etCourseSection);
        EditText etTime = dialogView.findViewById(R.id.etTime);
        EditText etProfessor = dialogView.findViewById(R.id.etProfessor);
        EditText etDays = dialogView.findViewById(R.id.etDays);
        EditText etLocation = dialogView.findViewById(R.id.etLocation);

        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Class");
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = etCourseName.getText().toString();
                String courseSection = etCourseSection.getText().toString();
                String time = etTime.getText().toString();
                String professor = etProfessor.getText().toString();
                String days = etDays.getText().toString();
                String location = etLocation.getText().toString();

                String classDetails = "Class: " + courseName + "\n" + "Section: " + courseSection + "\n" + "Professor: " + professor + "\n" + "Time: " + time + "\n" + "Days: " + days + "\n" + "Location: " + location; // Concatenate other details as needed
                TaskManager2.getInstance().addTask(classDetails);

                //refresh adapter
                itemsAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onItemClick(int position) {
        showEditTaskDialog(position);
    }

    //used for back to home button
    public void backToHomeClasses(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
