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

public class assignment_list extends AppCompatActivity implements CustomAdapter.OnItemClickListener {
    private CustomAdapter itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);
        lvItems = findViewById(R.id.lvItems);

        TaskManager3 taskManager = TaskManager3.getInstance(); // initialize TaskManager

        //get tasks from TaskManager
        itemsAdapter = new CustomAdapter(this, (ArrayList<Task>) taskManager.getTasks());
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
                        //remove item at position
                        TaskManager3.getInstance().removeTask(pos);
                        //refresh adapter
                        itemsAdapter.notifyDataSetChanged();
                        //returning true makes the long click handled
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                //edit task
                showEditTaskDialog(position);
            }
        });
    }

    private void showEditTaskDialog(final int position) {
        Task currentTask = TaskManager3.getInstance().getTasks().get(position);
        String currentTaskDescription = currentTask.getDescription();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Assignment");

        final EditText input = new EditText(this);
        input.setText(currentTaskDescription);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedText = input.getText().toString();

                TaskManager3.getInstance().editTask(position, editedText);

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
        //inflate dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_assignment, null);

        //get the text edit fields from the pop up
        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etClass = dialogView.findViewById(R.id.etClass);


        //create save and cancel buttons here
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Assignment");
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get values from inputs
                String assignment = etTitle.getText().toString();
                String courseName = etClass.getText().toString();
                String date = etDate.getText().toString();

                //new entry with inputs
                String classDetails = "Assignment: " + assignment + "\n" + "Course: " + courseName + "\n" + "Due Date: " + date; // Concatenate other details as needed
                TaskManager3.getInstance().addTask(classDetails);

                //adapter refresh
                itemsAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //dismiss dialog
            }
        });

        dialog.show();
    }


    @Override
    public void onItemClick(int position) {
        //handle item click
        showEditTaskDialog(position);
    }
    public void backToHomeClasses(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
