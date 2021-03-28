package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.VerifyContract
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.datas.SmsCodeData
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage
import uz.dilnoza.finalpayme.utils.SingleBlock
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi

class VerifyRepository(private val api: LoginApi) : VerifyContract.Model {
    private var listenerSuccess: SingleBlock<String>? = null
    private var listenerMessage: SingleBlock<String>? = null
    private var storage = LocalStorage.getInstance()
    override fun message(block: SingleBlock<String>) {
        listenerMessage = block
    }

    override fun success(block: SingleBlock<String>) {
        listenerSuccess = block
    }

    override fun send(data: SmsCodeData) {
        api.verify(data).enqueue(object : Callback<ResponseData<String>> {
            override fun onResponse(
                call: Call<ResponseData<String>>,
                response: Response<ResponseData<String>>
            ) {
                val dataCode = response.body() ?: return
                if (dataCode.status == "OK") {
                    storage.setToken(dataCode.data)
                    listenerSuccess?.invoke(dataCode.data ?: "")
                } else listenerMessage?.invoke(dataCode.message)
            }

            override fun onFailure(call: Call<ResponseData<String>>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }

        })
    }
}