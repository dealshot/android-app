package com.dealshot.dealshotandroidapp.ui.adapter

import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.ui.dialog.ErrandManipulationDialogBuilder

class PlazaErrandAdapter(
  fragmentManager: FragmentManager
) : ErrandAdapter(ErrandDAO.SourceType.PLAZA, fragmentManager) {
  override fun updateErrandCardView(context: Context, cardView: View, errand: Errand) {
    cardView.setOnClickListener {
      ErrandManipulationDialogBuilder(context, fragmentManager)
        .setTitle(context.getString(R.string.errand_detail_title))
        .setErrand(errand)
        .setViewInvisible(R.id.assignee_input_wrapper)
        .setEnableEdit(false)
        .setPositiveButton(context.getText(R.string.claim)) {
          ErrandDAO.updateErrand(
            errand.claim(
              AuthController.currentUID(),
              AuthController.currentContact()
            )
          )
        }
        .launch()
    }
  }
}
