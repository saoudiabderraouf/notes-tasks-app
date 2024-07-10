package com.notestasksapp.view.ui.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Note
import com.notestasksapp.view.dialog.ConfirmationDialog
import kotlinx.android.synthetic.main.act_add_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_note)

        initialization()

        save_btn.setOnClickListener {
            if (verifyData()){
                val currentTimeInMillis = Calendar.getInstance().timeInMillis
                val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(currentTimeInMillis)
                val note = Note(0, note_title_edt.text.toString(), note_description_edt.text.toString(), "#000000", date, date)
                dbHandler.addNote(note)

                val confirmationDialog = ConfirmationDialog(this, "Add a new note", "Do you want to add a new note?")
                confirmationDialog.ok_btn.setOnClickListener {
                    note_title_edt.setText("")
                    note_description_edt.setText("")
                    confirmationDialog.dismiss()
                }
                confirmationDialog.cancel_btn.setOnClickListener {
                    confirmationDialog.dismiss()
                    onBackPressed()
                }
                confirmationDialog.show()
            }
        }

        back_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)
    }

    private fun verifyData(): Boolean {
        if (note_title_edt.text.isNullOrEmpty()) {
            note_title_edt.error = "Field can't be empty!"
            return false
        }
        if (note_description_edt.text.isNullOrEmpty()) {
            note_description_edt.error = "Field can't be empty!"
            return false
        }

        return true
    }

}