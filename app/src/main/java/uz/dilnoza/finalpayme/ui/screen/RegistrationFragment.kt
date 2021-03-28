package uz.dilnoza.finalpayme.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.registration.*
import kotlinx.android.synthetic.main.registration.view.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.RegistrationContract
import uz.dilnoza.finalpayme.presenter.RegistrationPresenter
import uz.dilnoza.finalpayme.repository.RegRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class RegistrationFragment : Fragment(R.layout.registration), RegistrationContract.View {
    private lateinit var presenter: RegistrationPresenter
    private lateinit var regView: View
    private var model: Communicator?=null
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        regView = view
        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)
        presenter = RegistrationPresenter(RegRepository(api), this)
        reg.setOnClickListener {
            presenter.reg()
        }
    }

    override fun getLastName(): String = lastName.text.toString()

    override fun getFirstName() = first_name.text.toString()

    override fun getPhoneNumber(): String = "+998" + phone_number.unmaskedText

    override fun getPassword(): String = password.text.toString().trim().replace(" ", "")
    override fun getConfirmPassword(): String = confirmPass.text.toString().trim()

    override fun openLoading() {
        regView.view.visibility = View.VISIBLE
        progress.show()

    }

    override fun closeLoading() {
        progress.hide()
        regView.view.visibility = View.GONE

    }

    override fun openSmsCode(phone: String) {
        val dialog = MessageDialog(
            context!!,
            1,
            "You've completed Registration SuccessFully! \n Please verify your phone number to proceed. \n Verification code sent to your registered phone number."
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            model!!.setMsgCommunicator("+998" + phone_number.unmaskedText)
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayout,VerifyFragment(),"Verify")?.commit()


            /* fragmentManager?.apply {
                 val bundle = Bundle()
                 bundle.putString("Phone", "+998" + phone_number.unmaskedText)
                 RegistrationFragment().arguments = bundle
                 beginTransaction().replace(R.id.fragmentLayout, VerifyFragment(), "Verify")
                     .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()

             */
            }
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setBackgroundResource(R.drawable.dialog_button)
            }
            dialog.show()
    }

    override fun showMessage(message: String, error: String) {
        when (error) {
            "confirm" -> confPass.error = message
            "first" -> firstN.error = message
            "last" -> lastN.error = message
            "password" -> passW.error = message
            "phone" -> PN.error = message
            "" -> {
                val dialog = MessageDialog(context!!, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> dialog.dismiss()}
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setBackgroundResource(R.drawable.dialog_shape)
                }
                dialog.show()
            }
        }
    }
}