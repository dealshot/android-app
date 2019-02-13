package com.dealshot.dealshotandroidapp.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.dialog_errand.view.*

class ErrandDialogBuilder(context: Context) {
  private var dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_errand, null)

  private var builder = AlertDialog.Builder(context)

  private fun bindErrand(errand: Errand) {
    dialogView.errand_title_input.setText(errand.title)
    dialogView.errand_owner_input.setText(errand.ownerContact)
    dialogView.pickup_location_input.setText(errand.pickupLocation)
    dialogView.delivery_location_input.setText(errand.deliveryLocation)
    dialogView.assignee_input.setText(errand.assigneeContact)
  }

  fun setTitle(title: CharSequence): ErrandDialogBuilder {
    builder.setTitle(title)
    return this
  }

  fun setErrand(errand: Errand): ErrandDialogBuilder {
    bindErrand(errand)
    return this
  }

  fun setViewInvisible(viewId: Int): ErrandDialogBuilder {
    dialogView.findViewById<View>(viewId).visibility = View.GONE
    return this
  }

  fun disableInput(): ErrandDialogBuilder {
    dialogView.errand_title_input.isEnabled = false
    dialogView.errand_owner_input.isEnabled = false
    dialogView.pickup_location_input.isEnabled = false
    dialogView.delivery_location_input.isEnabled = false
    dialogView.assignee_input.isEnabled = false
    return this
  }

  fun setPositiveButton(
      text: CharSequence,
      listener: ((View) -> Unit)
  ): ErrandDialogBuilder {
    builder.setPositiveButton(text) { _, _ ->
      listener(dialogView)
    }
    return this
  }

  fun setNegativeButton(
      text: CharSequence,
      listener: ((View) -> Unit)
  ): ErrandDialogBuilder {
    builder.setNegativeButton(text) { _, _ ->
      listener(dialogView)
    }
    return this
  }

  fun show() {
    builder.setView(dialogView)
    builder.show()
  }
}
