package revolhope.splanes.com.aikver.presentation.feature.menu

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_menu.navView
import kotlinx.android.synthetic.main.activity_menu.toolbar
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity

class MenuActivity : BaseActivity() {

    override fun initViews() {
        super.initViews()

        setSupportActionBar(toolbar)
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
