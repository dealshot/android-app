package com.dealshot.dealshotandroidapp.ui.adapter

import android.content.Context
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.ui.dialog.ErrandDialogBuilder

class PlazaErrandAdapter : ErrandAdapter(ErrandDAO.SourceType.PLAZA) {
  override fun updateErrandCardView(context: Context, cardView: View, errand: Errand) {
    cardView.setOnClickListener {
      ErrandDialogBuilder(context)
          .setTitle(context.getString(R.string.errand_detail_title))
          .setErrand(errand)
          .setViewInvisible(R.id.assignee_input_wrapper)
          .disableInput()
          .setPositiveButton(context.getText(R.string.claim)) {
            ErrandDAO.updateErrand(
                errand.claim(
                    AuthController.currentUID(),
                    AuthController.currentContact()
                )
            )
          }
          .show()
    }
  }
}
