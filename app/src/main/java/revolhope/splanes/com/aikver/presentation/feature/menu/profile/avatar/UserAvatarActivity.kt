package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_user_avatar.avatarPreview
import kotlinx.android.synthetic.main.activity_user_avatar.colorRecyclerView
import kotlinx.android.synthetic.main.activity_user_avatar.doneButton
import kotlinx.android.synthetic.main.activity_user_avatar.eyesInput
import kotlinx.android.synthetic.main.activity_user_avatar.eyesText
import kotlinx.android.synthetic.main.activity_user_avatar.mouthInput
import kotlinx.android.synthetic.main.activity_user_avatar.mouthText
import kotlinx.android.synthetic.main.activity_user_avatar.noseInput
import kotlinx.android.synthetic.main.activity_user_avatar.noseText
import kotlinx.android.synthetic.main.activity_user_avatar.toolbar
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.typeselector.TypeSelectorBottomSheetFragment
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.typeselector.TypeSelectorUiModel
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes

class UserAvatarActivity : BaseActivity() {

    private val viewModel: UserAvatarViewModel by viewModel()
    private val eyesSelectorUiModel: MutableList<TypeSelectorUiModel> = mutableListOf()
    private val noseSelectorUiModel: MutableList<TypeSelectorUiModel> = mutableListOf()
    private val mouthSelectorUiModel: MutableList<TypeSelectorUiModel> = mutableListOf()
    private lateinit var userAvatar: UserAvatar

    companion object {

        private const val REQ_CODE = 0x123

        fun start(baseActivity: BaseActivity?, onActivityResult: (Boolean) -> Unit) {
            baseActivity?.startActivityForResult(
                intent = Intent(baseActivity, UserAvatarActivity::class.java).apply {
                    putExtras(bundleOf(EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL))
                },
                requestCode = REQ_CODE,
                onActivityResult = { _, requestCode, resultCode ->
                    onActivityResult.invoke(requestCode == REQ_CODE && resultCode == Activity.RESULT_OK)
                }
            )
        }
    }

    override fun initViews() {
        super.initViews()

        toolbar.setOnCloseClick(::onCloseClick)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.profile_avatar_activity_title)

        doneButton.setOnClickListener { onDoneClick() }
    }

    override fun initObservers() {
        super.initObservers()

        observe(viewModel.user) {
            userAvatar = it.avatar.copy()
            avatarPreview.loadAvatar(userAvatar)
            viewModel.fetchAvatarTypes()
        }

        observe(viewModel.avatarTypes) {
            mapAvatarTypes(it)
            bindViews()
        }

        observe(viewModel.insertAvatarResult) {
            hideLoader()
            if (it) {
                setResult(Activity.RESULT_OK)
                onBackPressed()
            } else {
                popupError(context = this, fm = supportFragmentManager) {
                    setResult(Activity.RESULT_CANCELED)
                    onBackPressed()
                }
            }
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    private fun bindViews() {

        eyesInput.setEndIconOnClickListener { onInputClick(id = R.id.eyesInput) }
        noseInput.setEndIconOnClickListener { onInputClick(id = R.id.noseInput) }
        mouthInput.setEndIconOnClickListener { onInputClick(id = R.id.mouthInput) }

        colorRecyclerView.layoutManager = AutoSizeLayoutManager(this, 48f)
        colorRecyclerView.adapter = UserAvatarColorAdapter(
            resources.getStringArray(R.array.colors).toList(),
            userAvatar.color,
            ::onColorSelected
        )
    }

    private fun onInputClick(id: Int) {
        val title: String
        val items: List<TypeSelectorUiModel>

        when (id) {
            R.id.eyesInput -> {
                title = getString(R.string.eyes)
                items = eyesSelectorUiModel
            }
            R.id.noseInput -> {
                title = getString(R.string.nose)
                items = noseSelectorUiModel
            }
            else /* R.id.mouthInput */ -> {
                title = getString(R.string.mouth)
                items = mouthSelectorUiModel
            }
        }

        TypeSelectorBottomSheetFragment(
            title = title,
            items = items,
            callback = { onItemSelected(id, it) }
        ).show(supportFragmentManager)
    }

    private fun onItemSelected(parent: Int, typeSelectorUiModel: TypeSelectorUiModel?) {
        if (typeSelectorUiModel == null) return
        when (parent) {
            R.id.eyesInput -> {
                eyesText.setText(typeSelectorUiModel.title)
                userAvatar.eyes = typeSelectorUiModel.avatar.eyes
            }
            R.id.noseInput -> {
                noseText.setText(typeSelectorUiModel.title)
                userAvatar.nose = typeSelectorUiModel.avatar.nose
            }
            R.id.mouthInput -> {
                mouthText.setText(typeSelectorUiModel.title)
                userAvatar.mouth = typeSelectorUiModel.avatar.mouth
            }
        }
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun mapAvatarTypes(avatarTypes: UserAvatarTypes) {
        avatarTypes.eyes.forEachIndexed { index, item ->
            eyesSelectorUiModel.add(
                index = index,
                element = TypeSelectorUiModel(
                    title = "Ojos ${index + 1}",
                    avatar = userAvatar.copy().apply { eyes = item },
                    isChecked = index == 0
                )
            )
        }

        avatarTypes.nose.forEachIndexed { index, item ->
            noseSelectorUiModel.add(
                index = index,
                element = TypeSelectorUiModel(
                    title = "Nariz ${index + 1}",
                    avatar = userAvatar.copy().apply { nose = item },
                    isChecked = index == 0
                )
            )
        }

        avatarTypes.mouth.forEachIndexed { index, item ->
            mouthSelectorUiModel.add(
                index = index,
                element = TypeSelectorUiModel(
                    title = "Boca ${index + 1}",
                    avatar = userAvatar.copy().apply { mouth = item },
                    isChecked = index == 0
                )
            )
        }
    }

    private fun onColorSelected(color: String) {
        userAvatar.color = color
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun onDoneClick() {
        showLoader()
        viewModel.insertAvatar(userAvatar)
    }

    private fun onCloseClick() {
        setResult(Activity.RESULT_OK)
        onBackPressed()
    }

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutRes(): Int = R.layout.activity_user_avatar
}