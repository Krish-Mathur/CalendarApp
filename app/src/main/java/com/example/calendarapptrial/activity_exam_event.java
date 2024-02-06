package com.example.calendarapptrial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Locale;

public class activity_exam_event extends AppCompatActivity {

    Button timeButton;
    int timeHour, timeMinute;
    String timeText;
    private EditText eventNameEdit, eventLocationEdit;
    private TextView eventDateText, eventTimeText, eventLocationText;
    private LocalTime localTimeDefault;
    private LocalTime currentTime;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        currentTime = LocalTime.now();
        //set date and current time on add event page
        eventDateText.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.dateSelected));
        eventTimeText.setText("Time Right Now: " + CalendarUtils.formattedTime(currentTime));
        timeButton = findViewById(R.id.timeButton);
    }
    private void initWidgets() {
        //get events by id from xml file
        eventNameEdit = findViewById(R.id.eventNameEdit);
        eventDateText = findViewById(R.id.eventDateText);
        eventTimeText = findViewById(R.id.timeNowText);
        eventLocationText = findViewById(R.id.eventLocationText);
        eventLocationEdit = findViewById(R.id.eventLocationEdit);
    }


    public void selectTime(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                timeHour = selectedHour;
                timeMinute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", timeHour, timeMinute));
                timeText = (String) timeButton.getText();

                //converts string into LocalTime type to display
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
                localTimeDefault = LocalTime.parse(timeText);

            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, timeHour, timeMinute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameEdit.getText().toString();
        String eventLocation = eventLocationEdit.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.dateSelected, localTimeDefault, eventLocation);
        Event.eventsList.add(newEvent);
        finish();
    }
}