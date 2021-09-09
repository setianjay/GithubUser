package com.setianjay.githubuser.screens.common.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.setianjay.githubuser.R

class DialogsNavigators(private val context: Context) {

    fun dialogServerError() {
        val customDialog = Dialog(context).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setContentView(R.layout.dialog_server_error)
            this.window?.setBackgroundDrawable(AppCompatResources.getDrawable(context, R.drawable.background_dialog_server_error))
            this.window?.attributes?.windowAnimations = R.style.dialogAnimations
            this.setCancelable(false)
        }

        val ivClose = customDialog.findViewById<ImageView>(R.id.iv_close)
        val btnTryAgain = customDialog.findViewById<AppCompatButton>(R.id.btn_try_again)

        ivClose.setOnClickListener { customDialog.dismiss() }
        btnTryAgain.setOnClickListener { customDialog.dismiss() }

        customDialog.show()
    }
}