package com.example.calendarapptrial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class activity_toDo_list extends AppCompatActivity implements CustomAdapter.OnItemClickListener {
    private CustomAdapter itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lvItems = findViewById(R.id.lvItems);

        TaskManager taskManager = TaskManager.getInstance(); // Initialize TaskManager

        // Retrieve tasks from TaskManager
        itemsAdapter = new CustomAdapter((Context) this, (ArrayList<Task>) taskManager.getTasks());
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
                        // Remove the item within TaskManager at position
                        TaskManager.getInstance().removeTask(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                // Edit the task when an item is clicked
                showEditTaskDialog(position);
            }
        });
    }

    private void showEditTaskDialog(final int position) {
        // Retrieve the task from TaskManager based on position
        Task currentTask = TaskManager.getInstance().getTasks().get(position);
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

                // Update TaskManager after editing
                TaskManager.getInstance().editTask(position, editedText);

                // Refresh the adapter
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
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        // Check if the itemText is not empty before adding
        if (!itemText.isEmpty()) {
            //itemsAdapter.add(itemText);

            // Add task to TaskManager
            TaskManager.getInstance().addTask(itemText);

            etNewItem.setText("");
        }
    }


    @Override
    public void onItemClick(int position) {
        // Handle item click here (edit task)
        showEditTaskDialog(position);
    }
    public void backToHomeToDo(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}