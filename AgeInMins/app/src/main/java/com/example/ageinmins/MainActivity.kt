package com.example.ageinmins

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener { view ->
            clickDatePicker(view)
            //Toast.makeText( this, "Button works",Toast.LENGTH_LONG).show()
        }


    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDatePicker(View: View){

        val myCalendar = Calendar.getInstance() //all down give us the current date; months start from 0
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->    //waits a date to be selected; ig we click cancel the code after -> will not be executed
                                                                                    //year, month, dayOfMonth are these which we have selected in the calendar
                //Toast.makeText( this, "Date picker works",Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectorDate.setText(selectedDate)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) // pattern for the date format; common in english language; letters in the date are important
                val theDate = sdf.parse(selectedDate) //the selected date will be in the format above; object of type Date

                /** Here we have get the time in milliSeconds from Date object
                 * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                 * And the seconds can be converted to minutes by dividing it by 60.
                 * So now in the selected date into minutes.
                 */
                val selectedDateToMinutes = theDate!!.time / 60000

                // Here we have parsed the current date with the Date Formatter which is used above.
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                // Current date in to minutes.
                val currentDateToMinutes = currentDate!!.time / 60000

                /**
                 * Now to get the difference into minutes.
                 * We will subtract the selectedDateToMinutes from currentDateToMinutes.
                 * Which will provide the difference in minutes.
                 */
                val differenceInMinutes = currentDateToMinutes - selectedDateToMinutes

                // Set the difference in minutes to textview to show the user.
                tvResultInMin.setText(differenceInMinutes.toString())
            },
            year,
            month,
            day
        )

        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate The maximal supported date.
         */
        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user to select today and future day.
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show() // It is used to show the datePicker Dialog.


    }

}