package com.setianjay.githubuser.screens.common.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.setianjay.githubuser.R

class DialogsNavigators(private val context: Context) {

    interface IOnDialogsNavigator {
        fun positiveButton(dialog: Dialog)
        fun negativeButton(dialog: Dialog)
    }

    private fun dialogBuilder(layoutResId: Int): Dialog{
        return Dialog(context).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setContentView(layoutResId)
            this.window?.setBackgroundDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.background_dialog
                )
            )
            this.window?.attributes?.windowAnimations = R.style.dialogAnimations
            this.setCancelable(false)
        }
    }

    fun dialogServerError(listener: IOnDialogsNavigator) {
        val customDialog = dialogBuilder(R.layout.dialog_server_error)

        val ivClose = customDialog.findViewById<ImageView>(R.id.iv_close)
        val btnTryAgain = customDialog.findViewById<AppCompatButton>(R.id.btn_try_again)

        ivClose.setOnClickListener {
            listener.negativeButton(customDialog)
        }
        btnTryAgain.setOnClickListener {
            listener.positiveButton(customDialog)
        }

        customDialog.show()
    }

    fun dialogWarning(username: String, listener: IOnDialogsNavigator) {
        val customDialog = dialogBuilder(R.layout.dialog_warning_information)
        
        val tvMessage = customDialog.findViewById<TextView>(R.id.tv_message1)
        tvMessage.text = context.getString(R.string.do_you_want_to_remove, username)

        val btnDelete = customDialog.findViewById<AppCompatButton>(R.id.btn_delete)
        val btnCancel = customDialog.findViewById<AppCompatButton>(R.id.btn_cancel)

        btnCancel.setOnClickListener {
            listener.negativeButton(customDialog)
        }
        btnDelete.setOnClickListener {
            listener.positiveButton(customDialog)
        }

        customDialog.show()
    }
}