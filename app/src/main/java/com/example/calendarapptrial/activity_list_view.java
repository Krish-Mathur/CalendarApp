package com.example.calendarapptrial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_list_view extends Activity implements CustomAdapter.OnItemClickListener {
    private ArrayList<String> items;
    private CustomAdapter itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lvItems = findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new CustomAdapter(this, items);
        itemsAdapter.setOnItemClickListener(this);
        lvItems.setAdapter(itemsAdapter);

        // Sample items
        items.add("First Item");
        items.add("Second Item");

        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        final EditText input = new EditText(this);
        input.setText(items.get(position));
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedText = input.getText().toString();
                items.set(position, editedText);
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
        itemsAdapter.add(itemText);
        etNewItem.setText("");
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click here (edit task)
        showEditTaskDialog(position);
    }
}
