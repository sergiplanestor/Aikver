package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.punctuation

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_punctuation_bottom_sheet.scoreView
import kotlinx.android.synthetic.main.fragment_punctuation_bottom_sheet.sendPunctuationButton
import kotlinx.android.synthetic.main.fragment_punctuation_bottom_sheet.title
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet
import revolhope.splanes.com.aikver.presentation.common.toLowerCase
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class PunctuationBottomSheet(
    private val currentUser: User,
    private val customContent: CustomContent<ContentDetails>,
    private val onPunctuationUpdated: (List<Pair<UserGroupMember, Float>>) -> Unit
) : BaseBottomSheet() {

    private val viewModel: PunctuationBottomSheetViewModel by viewModel()

    override fun getLayoutResource(): Int = R.layout.fragment_punctuation_bottom_sheet

    override fun bindView(view: View) {
        initObservers()
        title.text = getString(
            R.string.share_your_score,
            getString(if (customContent.content is SerieDetails) R.string.serie else R.string.film).toLowerCase()
        )
        sendPunctuationButton.setOnClickListener {
            scoreView.getScore()?.let {
                viewModel.sendPunctuation(
                    currentUser = currentUser,
                    customContent = customContent,
                    punctuation = it
                )
            } ?: viewModel.postError(getString(R.string.error_punctuation_blank_field))
        }
    }

    private fun initObservers() {
        observe(viewModel.onPunctuationSend) {
            if (it.isNotEmpty()) {
                dismiss()
                onPunctuationUpdated.invoke(it)
            }
            else {
                SnackBar.show(
                    view = requireView() as? ViewGroup ?: requireView(),
                    model = SnackBarModel.Error(resources.getString(R.string.error_short))
                )
            }
        }
    }
}