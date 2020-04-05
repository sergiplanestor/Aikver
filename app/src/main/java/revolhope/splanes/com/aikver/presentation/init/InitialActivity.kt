package revolhope.splanes.com.aikver.presentation.init

import android.animation.LayoutTransition
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_initial.*
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.visibility

class InitialActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        rootLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        back.setOnClickListener { onBackClick() }
    }

    fun changeTitle(title: String) { titleTextView.text = title }

    fun setBackVisibility(show: Boolean) = back.visibility(show)

    fun onBackClick() { onBackPressed() }

    fun navigate(action: Int, args: Bundle? = null) =
        navController.navigate(action, args, NavOptions.Builder().run {
            setEnterAnim(R.anim.enter)
            setExitAnim(R.anim.exit)
            setPopEnterAnim(R.anim.slide_left_to_right)
            setPopExitAnim(R.anim.slide_right_to_left)
            build()
        })
}