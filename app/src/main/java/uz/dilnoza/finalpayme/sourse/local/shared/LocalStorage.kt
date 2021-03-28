package uz.dilnoza.finalpayme.sourse.local.shared

import android.content.Context
import android.content.SharedPreferences
import uz.dilnoza.finalpayme.app.App


class LocalStorage(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private var data: LocalStorage? = null
        fun getInstance(): LocalStorage {
            if (data == null) {
                data = LocalStorage(App.instance)
            }
            return data!!
        }
    }

    fun setToken(token: String?) {
        editor.putString("TOKEN", token)
        editor.apply()
    }

    fun getToken(): String? = pref.getString("TOKEN", "")
    fun setRemember(rem: Boolean) {
        editor.putBoolean("REMEMBER", rem)
        editor.apply()
    }
    fun getRemember():Boolean=pref.getBoolean("REMEMBER",false)

}