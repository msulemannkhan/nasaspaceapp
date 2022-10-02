package com.nasa.apollo11.dialogues

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.nasa.apollo11.R


abstract class BaseDialogue(context: Context) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

    }


    fun setWidth() {
        window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


}