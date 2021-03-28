package uz.dilnoza.finalpayme.sourse.retrofit

import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.dilnoza.finalpayme.app.App
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage

object ApiClientAuth {
    private val logging =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val data = LocalStorage.getInstance()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(ChuckInterceptor(App.instance))
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://161.35.73.101:99/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}