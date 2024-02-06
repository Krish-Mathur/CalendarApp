package com.example.calendarapptrial;

import static com.example.calendarapptrial.CalendarUtils.daysInMonthArray;
import static com.example.calendarapptrial.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.dateSelected = LocalDate.now();
        setMonthView();
    }
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearText);
    }
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.dateSelected));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.dateSelected);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }
    public void previousMonthAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.dateSelected = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, activity_exam_page.class));
    }

    public void listAction(View view) {
        startActivity(new Intent(this, activity_toDo_list.class));
    }
    public void classAction(View view) {
        startActivity(new Intent(this, class_action_view.class));
    }
    public void assignmentAction(View view) {
        startActivity(new Intent(this, assignment_list.class));
    }
}