package com.example.calendarapptrial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// CustomAdapter.java
public class CustomAdapter2 extends ArrayAdapter<Task> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Add a field to hold the listener
    private OnItemClickListener mListener;

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomAdapter2(Context context, ArrayList<Task> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout2, parent, false);
        }

        // Get the data item for this position
        Task task = getItem(position);

        // Lookup view for data population
        TextView tvItem = convertView.findViewById(R.id.tvItem);

        // Populate the data into the template view using the data object
        if (task != null) {
            tvItem.setText(task.getDescription());
        }

        // Set click listeners for both short and long clicks
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Notify the listener about the click event
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Handle long click here (delete task)
                // You can remove the item from the list and notify the adapter
                remove(getItem(position));
                notifyDataSetChanged();
                return true;
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
