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
        //name of exam
        TextView eventCellText = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = event.getName();

        //time of exam
        TextView secondCellText = convertView.findViewById(R.id.secondCellTV);
        String subTitle = "Exam Time: " + CalendarUtils.formattedTime(event.getTime());

        //location of exam
        TextView locationCellText = convertView.findViewById(R.id.locationCellTV);
        String locationTitle = "Location: " + event.getEventLocation();

        eventCellText.setText(eventTitle);

        secondCellText.setText(subTitle);
        locationCellText.setText(locationTitle);
        View finalConvertView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //delete after long click
                deletedList.add(event);
                remove(event);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return convertView;
    }
    @Override
    public void remove(@Nullable Event object) {
        super.remove(object);
    }


}
