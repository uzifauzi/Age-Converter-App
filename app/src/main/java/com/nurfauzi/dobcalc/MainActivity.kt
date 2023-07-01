package com.nurfauzi.dobcalc

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    private var tvAgeInDays: TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tv_selected_date)
        tvAgeInMinutes = findViewById(R.id.tv_age_in_minutes)
        tvAgeInDays = findViewById(R.id.tv_age_in_days)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)

        btnDatePicker.setOnClickListener {
            Toast.makeText(this, "btnDatePickerClicked", Toast.LENGTH_SHORT).show()
            clickDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayofMonth ->
                Toast.makeText(
                    this,
                    "Year was $selectedYear, month was ${selectedMonth + 1}" + ", day of month was $selectedDayofMonth",
                    Toast.LENGTH_LONG
                ).show()

                val selectedDate = "$selectedDayofMonth/${selectedMonth + 1}/$selectedYear"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                //format date dari string mjd Date
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    //properti getTime() memberi kita waktu milliseconds sejak January 1, 1970 sampai dgn tgl yang dipilih. Untuk merubah dari milisecond ke minutes perlu dibagi dengan 60000
                    val selectedDateInMinutes = theDate.time / 60000
                    val selectedDateInDays = theDate.time / 86400000
                    //currentDate bernilai waktu yg sudah dilalui sejak January 1, 1970 dalam miliseconds
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val currentDateInDays = currentDate.time / 86400000
                        //hitung selisih
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInDays = currentDateInDays - selectedDateInDays
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvAgeInDays?.text = differenceInDays.toString()
                    }
                }
            }, year, month, day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000
        dpd.show()
    }
}