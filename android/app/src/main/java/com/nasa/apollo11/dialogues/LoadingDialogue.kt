package com.nasa.apollo11.dialogues

import android.content.Context
import android.os.Bundle

import com.nasa.apollo11.R

class LoadingDialogue(
    context: Context
) :
    BaseDialogue(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

        setCancelable(false)
        setWidth()
    }


}