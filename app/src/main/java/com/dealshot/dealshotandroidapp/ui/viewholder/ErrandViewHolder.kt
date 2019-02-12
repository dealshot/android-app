package com.dealshot.dealshotandroidapp.ui.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.card_errand.view.*

class ErrandViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
  fun bind(errand: Errand) {
    itemView.errand_title.text = errand.title
    itemView.errand_status.text = context.getString(
      R.string.status_locale,
      errand.status.toString()
    )
    itemView.errand_pick_up_location.text = context.getString(
      R.string.pick_up_locale,
      errand.pickupLocation
    )
    itemView.errand_delivery_up_location.text = context.getString(
      R.string.deliver_locale,
      errand.deliveryLocation
    )
  }
}
