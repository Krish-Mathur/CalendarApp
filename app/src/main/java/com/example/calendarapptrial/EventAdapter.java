package com.example.calendarapptrial;

import static com.example.calendarapptrial.Event.deletedList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        }
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = event.getName();

        TextView secondCellTV = convertView.findViewById(R.id.secondCellTV);
        String subTitle = "Exam Time: " + CalendarUtils.formattedTime(event.getTime());

        TextView locationCellTV = convertView.findViewById(R.id.locationCellTV);
        String locationTitle = "Location: " + event.getEventLocation();

        eventCellTV.setText(eventTitle);

        secondCellTV.setText(subTitle);
        locationCellTV.setText(locationTitle);
        View finalConvertView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Implement your delete logic here
                deletedList.add(event);
                remove(event);
                notifyDataSetChanged(); // Notify adapter about the change
                Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                return true; // Consume the long click event
            }
        });

        return convertView;
    }
    @Override
    public void remove(@Nullable Event object) {
        super.remove(object);
    }


}
