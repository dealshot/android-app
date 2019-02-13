package com.dealshot.dealshotandroidapp.ui.adapter

import android.content.Context
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.ui.dialog.ErrandDialogBuilder
import kotlinx.android.synthetic.main.dialog_errand.view.*

class UserErrandAdapter(private var sourceType: ErrandDAO.SourceType) : ErrandAdapter(sourceType) {
  private fun isEditable(errand: Errand) =
      sourceType == ErrandDAO.SourceType.USER_OWNED && errand.status == Errand.Companion.Status.UNASSIGNED

  override fun updateErrandCardView(context: Context, cardView: View, errand: Errand) {
    cardView.setOnClickListener {
      val builder = ErrandDialogBuilder(context)
      
      builder.setErrand(errand)
      
      if (errand.status == Errand.Companion.Status.CLOSED) {
        builder.setTitle(context.getString(R.string.errand_completed_tag))
      } else {

        builder.setTitle(context.getString(R.string.errand_detail_title))
      }
      
      if (!isEditable(errand)) {
        builder.disableInput()
      }

      if (errand.status == Errand.Companion.Status.UNASSIGNED ||
          errand.assignee == AuthController.currentUID()) {
        builder.setViewInvisible(R.id.assignee_input_wrapper)
      }

      when (sourceType) {
        ErrandDAO.SourceType.USER_OWNED -> {
          if (errand.status == Errand.Companion.Status.UNASSIGNED) {
            builder
                .setPositiveButton(context.getString(R.string.edit)) {
                  val title = it.errand_title_input.text.toString()
                  val pickupLocation = it.pickup_location_input.text.toString()
                  val deliveryLocation = it.delivery_location_input.text.toString()
                  errand.title = title
                  errand.pickupLocation = pickupLocation
                  errand.deliveryLocation = deliveryLocation
                  ErrandDAO.updateErrand(errand)
                }
                .setNegativeButton(context.getString(R.string.remove)) {
                  ErrandDAO.deleteErrand(errand)
                }
          }
        }
        ErrandDAO.SourceType.USER_WIP -> {
          builder
              .setPositiveButton(context.getString(R.string.close)) {
                ErrandDAO.updateErrand(errand.close())
              }
              .setNegativeButton(context.getString(R.string.release)) {
                ErrandDAO.updateErrand(errand.release())
              }
        }
        else -> {
          builder.setNegativeButton(context.getString(R.string.cancel)) { }
        }
      }

      builder.show()
    }
  }
}
