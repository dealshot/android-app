package com.dealshot.dealshotandroidapp.ui.fragment

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.dialog_errand_manipulation.view.*
import kotlinx.android.synthetic.main.fragment_user_center_section.view.*

class UserCenterSectionFragment : Fragment() {
  private var sourceType: ErrandDAO.SourceType? = null
  private var canAdd: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      sourceType = it.getParcelable(ARGS_SOURCE_TYPE)
      canAdd = it.getBoolean(ARGS_CAN_ADD)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_user_center_section, container, false)
    bindView(view)
    return view
  }

  @SuppressLint("RestrictedApi")
  private fun bindView(itemView: View) {
    itemView.user_center_errands_view.layoutManager = LinearLayoutManager(context)
    itemView.user_center_errands_view.adapter = UserErrandAdapter(sourceType!!, fragmentManager!!)
    if (canAdd) {
      itemView.create_errand_button.visibility = View.VISIBLE
      itemView.create_errand_button.setOnClickListener {
        ErrandManipulationDialogBuilder(context!!, fragmentManager!!)
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
    }
  }

  companion object {
    private const val ARGS_SOURCE_TYPE = "source_type"

    private const val ARGS_CAN_ADD = "can_add"

    @JvmStatic
    fun newInstance(type: ErrandDAO.SourceType, canAdd: Boolean) = UserCenterSectionFragment().apply {
      arguments = Bundle().apply {
        putParcelable(ARGS_SOURCE_TYPE, type)
        putBoolean(ARGS_CAN_ADD, canAdd)
      }
    }
  }
}
