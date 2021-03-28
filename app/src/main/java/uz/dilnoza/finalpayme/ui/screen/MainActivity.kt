package uz.dilnoza.finalpayme.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var storage = LocalStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(2000)
            if (storage.getRemember()) {
                runOnUiThread { openCard() }
            } else {
                runOnUiThread { openIntro() }
            }
        }
    }

    private fun openCard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, CardFragment(), "Main")
            .commit()
    }

    private fun openIntro() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentLayout, IntroFragment(), "Intro")
            .commit()
    }
}