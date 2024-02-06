package com.example.calendarapptrial;

import static com.example.calendarapptrial.CalendarUtils.daysInWeekArray;
import static com.example.calendarapptrial.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class activity_exam_page extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView recyclerViewCalendar;
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();
    }

    private void initWidgets() {
        recyclerViewCalendar = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearText);
        eventList = findViewById(R.id.eventListView);
    }

    private void setWeekView() {
        //sets weekly view
        monthYearText.setText(monthYearFromDate(CalendarUtils.dateSelected));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.dateSelected);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerViewCalendar.setLayoutManager(layoutManager);
        recyclerViewCalendar.setAdapter(calendarAdapter);
        setEventAdapter();
    }


    public void previousWeekAction(View view) {
        //goes back a week when < is pressed
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        //goes forward a week when > is pressed
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.plusWeeks(1);
        setWeekView();
    }
    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.dateSelected = date;
        setWeekView();
    }
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        List<Event> dailyEvents = Event.eventsForDate(CalendarUtils.dateSelected);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventList.setAdapter(eventAdapter);
    }

    public void newEventAction(View view) {
        //for onClick listener to create new event
        startActivity(new Intent(this, activity_exam_event.class));
    }
    //for back to home button on exams page
    public void backToHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}