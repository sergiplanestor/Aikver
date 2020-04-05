package revolhope.splanes.com.aikver.presentation.feature.menu

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity

class MenuActivity : BaseActivity() {

    companion object {
        const val EXTRA_USR: String = "extra.usr"
    }

    override fun initializeViews() {
        super.initializeViews()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment_menu)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile, R.id.navigation_dashboard, R.id.navigation_add_content
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun getLayoutRes(): Int = R.layout.activity_menu
}
