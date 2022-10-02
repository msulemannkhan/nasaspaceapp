package com.nasa.apollo11.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

infix fun TextView.onTextChange(function: (str: String) -> Unit) {

    //  this.removeTextChangedListener()
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            function(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })

}

fun ImageView.getLocalBitmapUri(context: Context): Uri? {
    // Extract Bitmap from ImageView drawable
    val drawable: Drawable = getDrawable()
    var bmp: Bitmap? = null
    bmp = if (drawable is BitmapDrawable) {
        (getDrawable() as BitmapDrawable).bitmap
    } else {
        return null
    }
    // Store image to default external storage directory
    var bmpUri: Uri? = null
    try {
        // This way, you don't need to request external read/write permission.
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "share_image_" + System.currentTimeMillis() + ".png"
        )
        val out = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()

        bmpUri = FileProvider.getUriForFile(
            context,
            context.applicationContext?.packageName.toString() + ".provider",
            file
        )

        //    bmpUri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}