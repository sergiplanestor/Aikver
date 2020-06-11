package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_serie_details.addButton
import kotlinx.android.synthetic.main.activity_serie_details.rootLayout
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.exec
import revolhope.splanes.com.aikver.framework.app.findByTag
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class SerieDetailsActivity : BaseActivity() {

    private var addButtonState: Int = STATE_ADD

    companion object {
        private const val EXTRA_CONTENT = "serieDetailsActivity.extra.content"
        private const val STATE_ADD = 0
        private const val STATE_CLOSE = 1

        fun start(baseActivity: BaseActivity?, serie: Serie) {
            baseActivity?.startActivity(
                Intent(baseActivity, SerieDetailsActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_CONTENT to serie
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {

        addButton.setOnClickListener {

            if (addButtonState == STATE_ADD) {
                supportFragmentManager.exec { transaction ->
                    transaction
                        .add(
                            R.id.container,
                            SerieDetailsSlaveFragment.newInstance(
                                x = addButton.x + addButton.width / 2,
                                y = addButton.y + addButton.height / 2,
                                width = rootLayout.width,
                                height = rootLayout.height
                            ),
                            SerieDetailsSlaveFragment::javaClass.name
                        )
                        .addToBackStack(SerieDetailsSlaveFragment::javaClass.name)
                }
                changeFabState(STATE_CLOSE)

            } else {
                (supportFragmentManager.findByTag(SerieDetailsSlaveFragment::javaClass.name)
                        as? SerieDetailsSlaveFragment)?.run {
                    playAnimationOut {
                        supportFragmentManager.popBackStackImmediate(
                            SerieDetailsSlaveFragment::javaClass.name,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }
                }
                changeFabState(STATE_ADD)
            }
        }
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getContent()?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    fun getContent(): Serie? =
        intent.extras?.getSerializable(EXTRA_CONTENT) as Serie?

    private fun changeFabState(newState: Int) {
        addButton.backgroundTintList = ColorStateList.valueOf(
            getColor(
                if (newState != STATE_ADD) android.R.color.white else R.color.colorAccent
            )
        )
        addButton.setImageResource(
            if (newState != STATE_ADD) R.drawable.ic_close else R.drawable.ic_add
        )
        addButtonState = newState
    }

    override fun getLayoutRes(): Int = R.layout.activity_serie_details
}