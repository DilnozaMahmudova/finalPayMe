package uz.dilnoza.finalpayme.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_message.*
import kotlinx.android.synthetic.main.dialog_message.view.*
import uz.dilnoza.finalpayme.R



class MessageDialog(context: Context, pos: Int, message: String) :
    AlertDialog(context,R.style.MaterialDialog) {
    private val contentView =
        LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)

    init {
        setView(contentView)
        when (pos) {
            0 -> {
                contentView.title.text = "Error!"
                contentView.image.setBackgroundResource(R.drawable.wrong)
            }
            1 -> {
                contentView.title.text = "Success!"
               contentView.image.setBackgroundResource(R.drawable.correct)
            }
            2 -> {
                contentView.title.text = "Congratulation!"
                contentView.title.textSize=18F
                contentView.image.setBackgroundResource(R.drawable.congratulation)
            }
        }
        contentView.message.text = message

    }

}