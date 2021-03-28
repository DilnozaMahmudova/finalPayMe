package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.ForgotContract
import uz.dilnoza.finalpayme.datas.LoginData
import uz.dilnoza.finalpayme.datas.ResData
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.utils.SingleBlock

class ForgotPasswordRepository(private  val api:LoginApi):ForgotContract.Model {
    private var listenerSuccess: SingleBlock<String>? = null
    private var listenerMessage: SingleBlock<String>? = null
    override fun message(block: SingleBlock<String>) {
        listenerMessage = block
    }


    override fun success(block: SingleBlock<String>) {
        listenerSuccess = block
    }

    override fun reset(data: LoginData) {
        api.reset(data).enqueue(object : Callback<ResData> {
            override fun onResponse(
                call: Call<ResData>,
                response: Response<ResData>
            ) {
                val datas = response.body() ?: return
                if (datas.status == "OK"){
                    listenerSuccess?.invoke(data.phoneNumber)
                }
                else listenerMessage?.invoke(datas.message)

            }

            override fun onFailure(call: Call<ResData>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }

        })
    }
}