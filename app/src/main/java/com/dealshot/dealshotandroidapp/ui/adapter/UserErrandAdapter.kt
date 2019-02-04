package com.dealshot.dealshotandroidapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.dialog_errand.view.*

class UserErrandAdapter(private var sourceType: ErrandDAO.SourceType) : ErrandAdapter(sourceType) {
  private fun isEditable(errand: Errand) =
    sourceType == ErrandDAO.SourceType.USER_OWNED && errand.status == Errand.Companion.Status.UNASSIGNED

  override fun updateErrandCardView(context: Context, cardView: View, errand: Errand) {
    val isErrandEditable = isEditable(errand)

    cardView.setOnClickListener {
      val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_errand, null)
      val builder = AlertDialog.Builder(context)

      dialogView.errand_title_input.setText(errand.title)
      dialogView.errand_title_input.isEnabled = isErrandEditable
      dialogView.pickup_location_input.setText(errand.pickupLocation)
      dialogView.pickup_location_input.isEnabled = isErrandEditable
      dialogView.delivery_location_input.setText(errand.deliveryLocation)
      dialogView.delivery_location_input.isEnabled = isErrandEditable
      if (errand.status == Errand.Companion.Status.UNASSIGNED) {
        dialogView.assignee_input_wrapper.visibility = GONE
      } else {
        dialogView.assignee_input.setText(errand.assignee)
        dialogView.assignee_input.isEnabled = false
      }

      builder.setTitle(context.getString(R.string.errand_detail_title))
      builder.setView(dialogView)
      when (sourceType) {
        ErrandDAO.SourceType.USER_OWNED -> {
          if (errand.status == Errand.Companion.Status.UNASSIGNED) {
            builder.setPositiveButton(context.getString(R.string.edit)) { _, _ ->
              val title = dialogView.errand_title_input.text.toString()
              val pickupLocation = dialogView.pickup_location_input.text.toString()
              val deliveryLocation = dialogView.delivery_location_input.text.toString()
              errand.title = title
              errand.pickupLocation = pickupLocation
              errand.deliveryLocation = deliveryLocation
              ErrandDAO.updateErrand(errand)
            }
            builder.setNegativeButton(context.getString(R.string.remove)) { _, _ ->
              ErrandDAO.deleteErrand(errand)
            }
          }
        }
        ErrandDAO.SourceType.USER_WIP -> {
          builder.setPositiveButton(context.getString(R.string.close)) { _, _ ->
            ErrandDAO.updateErrand(errand.close())
          }
          builder.setNegativeButton(context.getString(R.string.release)) { _, _ ->
            ErrandDAO.updateErrand(errand.release())
          }
        }
        else -> {
          builder.setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
        }
      }
      builder.setCancelable(true)
      builder.show()
    }
  }
}
