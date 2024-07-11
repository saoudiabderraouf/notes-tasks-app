package com.notestasksapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.widget.RelativeLayout
import android.widget.TextView
import com.notestasksapp.R

class DiscardDialog(context: Context) : Dialog(context) {

    constructor(context: Context ,title : String , description: String) : this(context) {
        this.setContentView(R.layout.dialog_discard)
        this.title = this.findViewById(R.id.dialog_title)
        this.description = this.findViewById(R.id.dialog_description)
        this.ok_btn = this.findViewById(R.id.ok_btn)
        this.cancel_btn = this.findViewById(R.id.cancel_btn)
        this.title.text = title
        this.description.text = description
        this.window!!.setBackgroundDrawableResource(R.color.transparent)
        this.setCanceledOnTouchOutside(true)

    }
    lateinit var title: TextView
    lateinit var description: TextView
    lateinit var ok_btn: RelativeLayout
    lateinit var cancel_btn: RelativeLayout

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setMessage(description: String) {
       this.description.text = description
    }

}