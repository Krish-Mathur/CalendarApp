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

import com.example.calendarapptrial.R;

import java.util.ArrayList;

// CustomAdapter.java
public class CustomAdapter extends ArrayAdapter<String> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Add a field to hold the listener
    private OnItemClickListener mListener;

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        // Lookup view for data population
        CheckBox checkboxItem = convertView.findViewById(R.id.checkboxItem);
        TextView tvItem = convertView.findViewById(R.id.tvItem);

        // Populate the data into the template view using the data object
        tvItem.setText(item);

        // Set click listeners for both short and long clicks
        checkboxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle checkbox click here (update task completion status)
            }
        });

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