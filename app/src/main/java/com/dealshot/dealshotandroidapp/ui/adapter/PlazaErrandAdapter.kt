package com.dealshot.dealshotandroidapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.dialog_errand.view.*

class PlazaErrandAdapter : ErrandAdapter(ErrandDAO.SourceType.PLAZA) {
  override fun updateErrandCardView(context: Context, cardView: View, errand: Errand) {
    cardView.setOnClickListener {
      val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_errand, null)
      dialogView.errand_title_input.setText(errand.title)
      dialogView.errand_title_input.isEnabled = false
      dialogView.pickup_location_input.setText(errand.pickupLocation)
      dialogView.pickup_location_input.isEnabled = false
      dialogView.delivery_location_input.setText(errand.deliveryLocation)
      dialogView.delivery_location_input.isEnabled = false
      dialogView.assignee_input_wrapper.visibility = GONE

      AlertDialog.Builder(context)
          .setTitle(context.getString(R.string.errand_detail_title))
          .setView(dialogView)
          .setPositiveButton(context.getText(R.string.claim)) { _, _ ->
            ErrandDAO.updateErrand(errand.claim(AuthController.currentUID()))
          }
          .show()
    }
  }
}
