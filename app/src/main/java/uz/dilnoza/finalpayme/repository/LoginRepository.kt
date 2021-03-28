package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.LoginContract
import uz.dilnoza.finalpayme.datas.LoginData
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.utils.SingleBlock

class LoginRepository(private val api: LoginApi) : LoginContract.Model {
    private var listenerSuccess: SingleBlock<String>? = null
    private var listenerMessage: SingleBlock<String>? = null
    private var storage = LocalStorage.getInstance()
    override fun message(block: SingleBlock<String>) {
        listenerMessage = block
    }


    override fun success(block: SingleBlock<String>) {
        listenerSuccess = block
    }

    override fun login(data: LoginData) {
        api.login(data).enqueue(object : Callback<ResponseData<String>> {
            override fun onResponse(
                call: Call<ResponseData<String>>,
                response: Response<ResponseData<String>>
            ) {
                val datas = response.body() ?: return
                if (datas.status == "OK" && datas.data != null){
                    listenerSuccess?.invoke(datas.data)
                }
                else listenerMessage?.invoke(datas.message)

            }

            override fun onFailure(call: Call<ResponseData<String>>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }

        })
    }

    override fun rememberMe(rem: Boolean, data: LoginData) {
        storage.setRemember(rem)
    }

    override fun setToken(token: String) {
        storage.setToken(token)
    }
}