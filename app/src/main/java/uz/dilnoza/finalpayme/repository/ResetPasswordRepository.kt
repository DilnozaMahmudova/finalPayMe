package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.ResetPasswordContract
import uz.dilnoza.finalpayme.datas.ResetPasswordData
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.utils.SingleBlock
import java.util.*

class ResetPasswordRepository(private val api:LoginApi):ResetPasswordContract.Model {
    private var listenerSuccess: SingleBlock<String>? = null
    private var listenerMessage: SingleBlock<String>? = null
    override fun message(block: SingleBlock<String>) {
        listenerMessage = block
    }

    override fun success(block: SingleBlock<String>) {
        listenerSuccess = block
    }

    override fun send(data: ResetPasswordData) {
        api.resetPassword(data).enqueue(object : Callback<ResponseData<Objects>> {
            override fun onResponse(
                call: Call<ResponseData<Objects>>,
                response: Response<ResponseData<Objects>>
            ) {
                val dataCode = response.body() ?: return
                if (dataCode.status != "OK") {
                    listenerMessage?.invoke(dataCode.message)
                } else{
                    listenerSuccess?.invoke(data.phoneNumber)
                }
            }

            override fun onFailure(call: Call<ResponseData<Objects>>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }

        })
    }
}