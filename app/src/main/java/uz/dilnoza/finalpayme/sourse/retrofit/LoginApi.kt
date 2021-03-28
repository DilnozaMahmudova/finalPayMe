package uz.dilnoza.finalpayme.sourse.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import uz.dilnoza.finalpayme.datas.*
import java.util.*

interface LoginApi {
    /**
     * 5. post a login
     * */
    @POST("contact/login")
    fun login(@Body loginData: LoginData): Call<ResponseData<String>>

    /**
     * 6. post a reg
     * */
    @POST("contact/register")
    fun register(@Body contactUserData: ContactUserData): Call<ResData>

    /**
     * 7. post a verify
     * */
    @POST("contact/verify")
    fun verify(@Body smsCodeData: SmsCodeData): Call<ResponseData<String>>

    /**
     * 8.post reset
     */
    @POST("contact/reset")
    fun reset(@Body loginData: LoginData): Call<ResData>
    /**
     * 9 forgot password
     */
    @POST("contact/password")
    fun resetPassword(@Body passwordData: ResetPasswordData): Call<ResponseData<Objects>>



}