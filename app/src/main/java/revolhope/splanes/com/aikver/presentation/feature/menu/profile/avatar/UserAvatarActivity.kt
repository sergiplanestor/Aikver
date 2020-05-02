package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_user_avatar.avatarPreview
import kotlinx.android.synthetic.main.activity_user_avatar.colorRecyclerView
import kotlinx.android.synthetic.main.activity_user_avatar.doneButton
import kotlinx.android.synthetic.main.activity_user_avatar.eyesSpinner
import kotlinx.android.synthetic.main.activity_user_avatar.mouthSpinner
import kotlinx.android.synthetic.main.activity_user_avatar.noseSpinner
import kotlinx.android.synthetic.main.activity_user_avatar.toolbar
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes

class UserAvatarActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel: UserAvatarViewModel by viewModel()
    private val mapEyes: MutableMap<String, String> = mutableMapOf()
    private val mapNose: MutableMap<String, String> = mutableMapOf()
    private val mapMouth: MutableMap<String, String> = mutableMapOf()
    private lateinit var userAvatar: UserAvatar

    companion object {

        fun start(baseActivity: BaseActivity?) {
            baseActivity?.startActivity(
                Intent(baseActivity, UserAvatarActivity::class.java).apply {
                    putExtras(bundleOf(EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL))
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

        observe(viewModel.user) { user ->
            user?.let {
                viewModel.fetchAvatarTypes()
                userAvatar = it.avatar.copy()
                avatarPreview.loadAvatar(userAvatar)
            }
        }

        observe(viewModel.avatarTypes) {
            if (it != null) {
                mapAvatarTypes(it)
                bindViews()
            } else {
                popupError(this, supportFragmentManager)
            }
        }

        observe(viewModel.insertAvatarResult) {
            hideLoader()
            if (it) {
                onBackPressed()
            }
            else {
                popupError(context = this, fm = supportFragmentManager) { onBackPressed() }
            }
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    private fun bindViews() {

        eyesSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapEyes.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        noseSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapNose.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        mouthSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapMouth.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        colorRecyclerView.layoutManager = AutoSizeLayoutManager(this, 48f)

        eyesSpinner.setSelection(getIndexOf(mapEyes, userAvatar.eyes), true)
        noseSpinner.setSelection(getIndexOf(mapNose, userAvatar.nose), true)
        mouthSpinner.setSelection(getIndexOf(mapMouth, userAvatar.mouth), true)
        colorRecyclerView.adapter = UserAvatarColorAdapter(
            resources.getStringArray(R.array.colors).toList(),
            userAvatar.color,
            ::onColorSelected
        )

        eyesSpinner.onItemSelectedListener = this
        noseSpinner.onItemSelectedListener = this
        mouthSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { /* Nothing to do */ }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent) {
            eyesSpinner -> userAvatar.eyes = mapEyes["Tipo ${position + 1}"] ?: ""
            noseSpinner -> userAvatar.nose = mapNose["Tipo ${position + 1}"] ?: ""
            mouthSpinner -> userAvatar.mouth = mapMouth["Tipo ${position + 1}"] ?: ""
        }
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun mapAvatarTypes(avatarTypes: UserAvatarTypes) {
        avatarTypes.eyes.forEachIndexed { index, item ->
            mapEyes["Tipo ${index + 1}"] = item
        }

        avatarTypes.nose.forEachIndexed { index, item ->
            mapNose["Tipo ${index + 1}"] = item
        }

        avatarTypes.mouth.forEachIndexed { index, item ->
            mapMouth["Tipo ${index + 1}"] = item
        }
    }

    private fun getIndexOf(map: Map<String, String>, value: String) : Int =
        map.keys.find { map[it] == value }?.let { map.keys.indexOf(it) } ?: 0

    private fun onColorSelected(color: String) {
        userAvatar.color = color
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun onDoneClick() {
        showLoader()
        viewModel.insertAvatar(userAvatar)
    }

    private fun onCloseClick() = onBackPressed()

    override fun getLayoutRes(): Int = R.layout.activity_user_avatar
}