package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.intro.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.ui.adapter.Adapter
import uz.dilnoza.finalpayme.utils.extensions.toDarkenColor

class Intro : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private val color = listOf(
        Color.parseColor("#E74C3A"),
        Color.parseColor("#F39C11"),
        Color.parseColor("#23B574"),
        Color.parseColor("#F5656C"),
        Color.parseColor("#9F76D5")
    )
   private val image = listOf(
        R.drawable.grow,
        R.drawable.hour, R.drawable.splash, R.drawable.p1, R.drawable.save
    )
  private  val info = listOf(
        "Increase your money easily and quickly",
        "Constant communication and quality service",
        "Security is strongly protected",
        "Register and activate your account",
        "Keep your fund easy and safe"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)
        adapter = Adapter(color, image, info, this)
        pager.adapter = adapter
        adapter.setBack { pager.currentItem = pager.currentItem - 1 }
        adapter.setNext {  if (pager.currentItem != info.size - 1) {
            pager.setCurrentItem(pager.currentItem + 1, true)
        } else {
            finish()
            startActivity(Intent(this, Login::class.java))
        }}
        TabLayoutMediator(tabLay, pager) { tab, position -> }.attach()
        pager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.navigationBarColor = color[position].toDarkenColor()
                window.statusBarColor = color[position].toDarkenColor()
            }
        }
    }
}