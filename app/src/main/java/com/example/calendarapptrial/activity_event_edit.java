package com.example.calendarapptrial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class activity_event_edit extends AppCompatActivity {

    Button timeButton;
    int hour, minute;
    String timeText;
    private EditText eventNameET, eventLocationET;
    private TextView eventDateTV, eventTimeTV, eventLocationTV;
    private LocalTime localTimeDefault;
    private LocalTime time;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time Right Now: " + CalendarUtils.formattedTime(time));
        timeButton = findViewById(R.id.timeButton);
    }
    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        eventLocationTV = findViewById(R.id.eventLocationTV);
        eventLocationET = findViewById(R.id.eventLocationET);
    }


    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
                timeText = (String) timeButton.getText();

                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
                localTimeDefault = LocalTime.parse(timeText);

            }
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        String eventLocation = eventLocationET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, localTimeDefault, eventLocation);
        Event.eventsList.add(newEvent);
        finish();
    }
}