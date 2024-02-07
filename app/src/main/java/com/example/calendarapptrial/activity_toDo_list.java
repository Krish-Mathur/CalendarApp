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
    private ListView listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listItems = findViewById(R.id.lvItems);

        TaskManager taskManager = TaskManager.getInstance();

        itemsAdapter = new CustomAdapter((Context) this, (ArrayList<Task>) taskManager.getTasks());
        itemsAdapter.setOnItemClickListener(this);
        listItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        listItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // remove item in task manager
                        TaskManager.getInstance().removeTask(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                //when item is clicked, then edit task
                showEditTaskDialog(position);
            }
        });
    }

    private void showEditTaskDialog(final int position) {
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

                TaskManager.getInstance().editTask(position, editedText);

                //adapter refresh (required)
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

        //check if the itemText is not empty before add
        if (!itemText.isEmpty()) {
            //itemsAdapter.add(itemText);

            //add task to TaskManager
            TaskManager.getInstance().addTask(itemText);

            etNewItem.setText("");
        }
    }


    @Override
    public void onItemClick(int position) {
        //handle item click
        showEditTaskDialog(position);
    }
    public void backToHomeToDo(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}