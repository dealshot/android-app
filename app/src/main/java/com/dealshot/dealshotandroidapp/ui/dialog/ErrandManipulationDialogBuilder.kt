package com.dealshot.dealshotandroidapp.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.dialog_errand_manipulation.view.*

class ErrandManipulationDialogBuilder(private val context: Context, private val fragmentManager: FragmentManager) {
  private var dialogView = makeDialogView()

  private var builder = AlertDialog.Builder(context)

  private var positiveListener: ((View) -> Unit)? = null

  private var negativeListener: ((View) -> Unit)? = null

  private fun bindErrand(errand: Errand) {
    dialogView.errand_title_input.setText(errand.title)
    dialogView.errand_owner_input.setText(errand.ownerContact)
    dialogView.pickup_location_input.setText(errand.pickupLocation)
    dialogView.delivery_location_input.setText(errand.deliveryLocation)
    dialogView.assignee_input.setText(errand.assigneeContact)
  }

  fun setTitle(title: CharSequence): ErrandManipulationDialogBuilder {
    builder.setTitle(title)
    return this
  }

  fun setErrand(errand: Errand): ErrandManipulationDialogBuilder {
    bindErrand(errand)
    return this
  }

  fun setViewInvisible(viewId: Int): ErrandManipulationDialogBuilder {
    dialogView.findViewById<View>(viewId).visibility = View.GONE
    return this
  }

  fun setEnableEdit(enabled: Boolean): ErrandManipulationDialogBuilder {
    dialogView.errand_title_input.isEnabled = enabled
    dialogView.errand_owner_input.isEnabled = enabled
    dialogView.pickup_location_input.isEnabled = enabled
    dialogView.delivery_location_input.isEnabled = enabled
    dialogView.assignee_input.isEnabled = enabled

    if (enabled) {
      dialogView.errand_owner_input_wrapper.visibility = View.GONE
      dialogView.assignee_input_wrapper.visibility = View.GONE
    }

    return this
  }

  fun setPositiveButton(
    text: CharSequence,
    listener: ((View) -> Unit)
  ): ErrandManipulationDialogBuilder {
    dialogView.errand_dialog_positive_button.visibility = View.VISIBLE
    dialogView.errand_dialog_positive_button.text = text
    positiveListener = listener
    return this
  }

  fun setNegativeButton(
    text: CharSequence,
    listener: ((View) -> Unit)
  ): ErrandManipulationDialogBuilder {
    dialogView.errand_dialog_negative_button.visibility = View.VISIBLE
    dialogView.errand_dialog_negative_button.text = text
    negativeListener = listener
    return this
  }

  fun launch() {
    val dialog =
      builder
        .setView(dialogView)
        .create()

    dialogView.errand_dialog_positive_button.setOnClickListener {
      positiveListener?.invoke(dialogView)
      dialog.dismiss()
    }

    dialogView.errand_dialog_negative_button.setOnClickListener {
      negativeListener?.invoke(dialogView)
      dialog.dismiss()
    }

    dialog.show()
  }

  private fun makeDialogView(): View =
    LayoutInflater
      .from(context)
      .inflate(R.layout.dialog_errand_manipulation, null, false)
}
