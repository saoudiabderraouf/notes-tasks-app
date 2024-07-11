package com.notestasksapp.view.ui.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.view.dialog.DiscardDialog
import com.notestasksapp.view.dialog.SaveDialog
import kotlinx.android.synthetic.main.act_edit_note.*
import java.text.SimpleDateFormat
import java.util.*

class EditNoteAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler

    private var mode = "View"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_edit_note)

        initialization()

        edit_save_btn.setOnClickListener {
            if (mode == "View"){
                mode = "Edit"
                title_txt.text = "Edit note"
                edit_save_img.setImageResource(R.drawable.ic_save)

                note_title_edt.isEnabled = true
                note_description_edt.isEnabled = true
                note_title_edt.requestFocus()
            }else{
                if (verifyData()){
                    val saveDialog = SaveDialog(this, "Save Changes", "Are your sure you want\nsave your changes ?")
                    saveDialog.ok_btn.setOnClickListener {
                        val currentTimeInMillis = Calendar.getInstance().timeInMillis
                        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(currentTimeInMillis)
                        Helper.selected_note!!.last_updated = date
                        Helper.selected_note!!.title = note_title_edt.text.toString()
                        Helper.selected_note!!.description = note_description_edt.text.toString()

                        dbHandler.updateNote(Helper.selected_note!!)
                        onBackPressed()
                    }
                    saveDialog.cancel_btn.setOnClickListener {
                        saveDialog.dismiss()
                    }
                    saveDialog.show()
                }
            }
        }

        back_btn.setOnClickListener {
            if (mode == "View"){
                onBackPressed()
            }else{
                val discardDialog = DiscardDialog(this, "Discard Changes", "Are your sure you want\ndiscard your changes ?")
                discardDialog.ok_btn.setOnClickListener {
                    discardDialog.dismiss()
                }
                discardDialog.cancel_btn.setOnClickListener {
                    discardDialog.dismiss()
                    onBackPressed()
                }
                discardDialog.show()
            }
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)

        note_title_edt.setText(Helper.selected_note!!.title)
        note_description_edt.setText(Helper.selected_note!!.description)

        note_title_edt.isEnabled = false
        note_description_edt.isEnabled = false
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