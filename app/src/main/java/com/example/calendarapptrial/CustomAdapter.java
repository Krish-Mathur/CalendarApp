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

public class CustomAdapter extends ArrayAdapter<Task> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //onClick listener
    private OnItemClickListener mListener;

    //set listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomAdapter(Context context, ArrayList<Task> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get data of item at this position
        Task task = getItem(position);

        //inflate view if existing view is not being used
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        CheckBox checkboxItem = convertView.findViewById(R.id.checkboxItem);
        TextView tvItem = convertView.findViewById(R.id.tvItem);

        //using data object to populate text
        tvItem.setText(task.getDescription());
        checkboxItem.setChecked(task.isChecked());

        //click listeners
        checkboxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update task status
                task.setChecked(!task.isChecked());
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener is told about onClick event
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //long click listener to remove item
                remove(getItem(position));
                notifyDataSetChanged();
                return true;
            }
        });

        //return completed view
        return convertView;
    }
}
