package revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.component_member_group_picker.view.buttonAddMember
import kotlinx.android.synthetic.main.component_member_group_picker.view.recycler
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker.bottomsheet.MemberGroupPickerBottomSheet
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class MemberGroupPicker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    intDefStyle: Int = 0
) : ConstraintLayout(context, attributeSet, intDefStyle) {

    var group: UserGroup
        get() = _group ?: throw RuntimeException("${javaClass.name}: This value can not be null")
        set(value) {
            _group = value
            initialize(value)
        }
    var fragmentManager: FragmentManager? = null

    private var _group: UserGroup? = null
    private val usersPicked: MutableList<UserGroupMember> = mutableListOf()

    init {
        View.inflate(context, R.layout.component_member_group_picker, this)
        initViews()
    }

    private fun initViews() {
        // TODO: Show empty state when developed
        recycler.invisible()
        buttonAddMember.setOnClickListener {
            SnackBar.show(
                view = parent as? ViewGroup ?: this,
                model = SnackBarModel.Error(resources.getString(R.string.error_short))
            )
        }
    }

    private fun initialize(group: UserGroup) {
        buttonAddMember.setOnClickListener {
            fragmentManager?.run {
                MemberGroupPickerBottomSheet(
                    group = group,
                    usersPicked = (recycler.adapter as? MemberGroupPickerAdapter)?.members
                        ?: emptyList(),
                    onUsersAdded = ::onUsersPicked
                ).also { it.show(this) }
            }
        }
    }

    private fun onUsersPicked(users: List<UserGroupMember>) {
        usersPicked.clear()
        usersPicked.addAll(users)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = MemberGroupPickerAdapter(usersPicked)
        recycler.visible()
    }
}