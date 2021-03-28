package uz.dilnoza.finalpayme.ui.screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.intro.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.ui.adapter.Adapter
import uz.dilnoza.finalpayme.ui.transformers.HorizontalFlip
import uz.dilnoza.finalpayme.utils.extensions.toDarkenColor

class IntroFragment : Fragment(R.layout.intro) {
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
    private val info = listOf(
        "Increase your money easily and quickly",
        "Constant communication and quality service",
        "Security is strongly protected",
        "Register and activate your account",
        "Keep your fund easy and safe"
    )
    private lateinit var currentView: View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentView = view
        adapter = Adapter(color, image, info, activity!!)
        pager.adapter = adapter
        adapter.setBack { pager.currentItem = pager.currentItem - 1 }
        adapter.setNext {
            if (pager.currentItem != info.size - 1) {
                pager.setCurrentItem(pager.currentItem + 1, true)
            } else {
                openLogin()
            }
        }
        TabLayoutMediator(tabLay, pager) { tab, position -> }.attach()
        pager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity?.window?.navigationBarColor = color[position].toDarkenColor()
                activity?.window?.statusBarColor = color[position].toDarkenColor()
            }
        }
    }

    private fun openLogin() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentLayout, LoginFragment(), "Login")
            ?.commit()
    }
}