package com.dealshot.dealshotandroidapp.ui.fragment

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.ui.adapter.UserErrandAdapter
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.ui.dialog.ErrandManipulationDialogBuilder
import kotlinx.android.synthetic.main.activity_user_center.*
import kotlinx.android.synthetic.main.activity_user_center.view.*
import kotlinx.android.synthetic.main.dialog_errand_manipulation.view.*
import kotlinx.android.synthetic.main.fragment_user_center_section.view.*

class UserCenterSectionFragment : Fragment() {
  private var sourceType: ErrandDAO.SourceType? = null
  private var userOwned: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      sourceType = it.getParcelable(ARGS_SOURCE_TYPE)
      userOwned = it.getBoolean(ARGS_USER_OWNED)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_user_center_section, container, false)
    bindView(view)
    return view
  }

  @SuppressLint("RestrictedApi")
  private fun bindView(itemView: View) {
    val adapter = UserErrandAdapter(sourceType!!)

    itemView.user_center_errands_view.layoutManager = LinearLayoutManager(context)
    itemView.user_center_errands_view.adapter = adapter
    itemView.user_center_errands_view.setHasFixedSize(true)
    if (userOwned) {
      itemView.create_errand_button.visibility = View.VISIBLE
      itemView.create_errand_button.setOnClickListener {
        ErrandManipulationDialogBuilder(context!!)
          .setTitle(getString(R.string.create_errand_dialog_title))
          .setEnableEdit(true)
          .setViewInvisible(R.id.assignee_input_wrapper)
          .setViewInvisible(R.id.errand_owner_input_wrapper)
          .setPositiveButton(getString(R.string.create)) {
            val title = it.errand_title_input.text.toString()
            val pickupLocation = it.pickup_location_input.text.toString()
            val deliveryLocation = it.delivery_location_input.text.toString()
            ErrandDAO.createErrand(
              Errand(
                AuthController.currentUID(),
                AuthController.currentContact(),
                title,
                pickupLocation,
                deliveryLocation
              )
            )
          }
          .setNegativeButton(getString(R.string.leave)) {}
          .launch()
      }

      itemView.navigation.visibility = View.VISIBLE
      adapter.setFilterBase(Errand.Companion.Status.UNASSIGNED)
      itemView.navigation.setOnNavigationItemReselectedListener {
        adapter.setFilterBase(
          when (it.itemId) {
            R.id.navigation_unassigned -> Errand.Companion.Status.UNASSIGNED
            R.id.navigation_wip -> Errand.Companion.Status.WIP
            R.id.navigation_closed -> Errand.Companion.Status.CLOSED
            else -> null
          }
        )
      }
    }
  }

  companion object {
    private const val ARGS_SOURCE_TYPE = "source_type"

    private const val ARGS_USER_OWNED = "user_owned"

    @JvmStatic
    fun newInstance(type: ErrandDAO.SourceType, userOwned: Boolean) =
      UserCenterSectionFragment().apply {
        arguments = Bundle().apply {
          putParcelable(ARGS_SOURCE_TYPE, type)
          putBoolean(ARGS_USER_OWNED, userOwned)
        }
      }
  }
}
