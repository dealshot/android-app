package com.dealshot.dealshotandroidapp.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.ui.adapter.UserErrandAdapter
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.dialog_errand.view.*
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

  private fun bindView(view: View) {
    view.user_center_errands_view.layoutManager = LinearLayoutManager(context)
    view.user_center_errands_view.adapter = UserErrandAdapter(sourceType!!)
    if (canAdd) {
      view.create_errand_button.visibility = VISIBLE
      view.create_errand_button.setOnClickListener {
        val dialogView = layoutInflater.inflate(R.layout.dialog_errand, null)
        val builder = AlertDialog.Builder(context)

        dialogView.assignee_input_wrapper.visibility = View.GONE

        builder.setTitle(getString(R.string.create_errand_dialog_title))
        builder.setView(dialogView)
        builder.setPositiveButton(getString(R.string.create)) { _, _ ->
          val title = dialogView.errand_title_input.text.toString()
          val pickupLocation = dialogView.pickup_location_input.text.toString()
          val deliveryLocation = dialogView.delivery_location_input.text.toString()
          ErrandDAO.createErrand(
            Errand(
              AuthController.currentUID(),
              title,
              pickupLocation,
              deliveryLocation
            )
          )
        }

        builder.show()
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
