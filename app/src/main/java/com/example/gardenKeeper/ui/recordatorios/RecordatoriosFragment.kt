package com.example.gardenkeeper.ui.recordatorios

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.semantics.text
import androidx.fragment.app.Fragment
import com.example.gardenkeeper.R
import java.util.Calendar

class RecordatoriosFragment : Fragment(R.layout.fragment_recordatorios) {

    private lateinit var btnOpenCalendar: Button
    private lateinit var etNote: EditText
    private lateinit var remindersContainer: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnOpenCalendar = view.findViewById(R.id.btnOpenCalendar)
        etNote = view.findViewById(R.id.etNote)
        remindersContainer = view.findViewById(R.id.remindersContainer)

        btnOpenCalendar.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                val note = etNote.text.toString()
                val reminderText = "$selectedDate - $note"

                addReminderToContainer(reminderText)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun addReminderToContainer(reminderText: String) {
        val reminderTextView = TextView(requireContext())
        reminderTextView.text = reminderText
        reminderTextView.setPadding(16, 8, 16, 8) // Increased padding for better visibility
        remindersContainer.addView(reminderTextView)
    }
}