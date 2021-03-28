package uz.dilnoza.finalpayme.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.forgot_password.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.phone_number
import kotlinx.android.synthetic.main.registration.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.ForgotContract
import uz.dilnoza.finalpayme.presenter.ForgotPresenter
import uz.dilnoza.finalpayme.repository.ForgotPasswordRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class ForgotFragment : Fragment(R.layout.forgot_password), ForgotContract.View {
    private lateinit var presenter: ForgotPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    private var model: Communicator?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        presenter = ForgotPresenter(ForgotPasswordRepository(api), this)
        reset.setOnClickListener { presenter.reset() }
    }

    override fun getPhoneNumber(): String = "+998" + phone_numberFor.unmaskedText
    override fun openLoader() {
        viewFor.visibility = View.VISIBLE
        progressFor.show()
    }

    override fun closeLoader() {
        progressFor.hide()
        viewFor.visibility = View.GONE

    }

    override fun showMessage(message: String, error: String) {
        when (error) {
            "phone" -> PoN.error = message
            "" -> {
                val dialog = MessageDialog(context!!, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setBackgroundResource(R.drawable.dialog_button)
                }
                dialog.show()
            }
        }
    }

    override fun openVerify() {
        val dialog = MessageDialog(
            context!!,
            1,
            "Please verify your phone number to proceed. \n Verification code sent to your registered phone number."
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            model!!.setMsgCommunicator("+998" + phone_numberFor.unmaskedText)
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayout,ResetFragment(),"Reset")?.commit()

            /*  fragmentManager?.apply {
                  val bundle = Bundle()
                  bundle.putString("Phone", "+998" + phone_number.unmaskedText)
                  ResetFragment().arguments = bundle

                  beginTransaction().replace(R.id.fragmentLayout, ResetFragment(), "Reset")
                      .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
              }

             */
        }

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setBackgroundResource(R.drawable.dialog_button)
        }
        dialog.show()

    }
}