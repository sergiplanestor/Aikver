package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.content.Intent
import android.transition.TransitionManager
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_custom_info_serie.closeButton
import kotlinx.android.synthetic.main.activity_custom_info_serie.infoTextView
import kotlinx.android.synthetic.main.activity_custom_info_serie.punctuationGroup
import kotlinx.android.synthetic.main.activity_custom_info_serie.rootLayout
import kotlinx.android.synthetic.main.activity_custom_info_serie.serieSeenSwitcher
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class SerieCustomInfoActivity : BaseActivity() {

    companion object {
        private const val EXTRA_CONTENT = "serieDetailsActivity.extra.content"

        fun start(baseActivity: BaseActivity?, serie: Serie) {
            baseActivity?.startActivity(
                Intent(baseActivity, SerieCustomInfoActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.FADE,
                            EXTRA_CONTENT to serie
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {
        closeButton.setOnClickListener { onBackPressed() }
        infoTextView.justify()
        serieSeenSwitcher.setOnCheckedChangeListener { _, checked ->
            punctuationGroup.visibility(checked)
            TransitionManager.beginDelayedTransition(rootLayout)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_custom_info_serie
}