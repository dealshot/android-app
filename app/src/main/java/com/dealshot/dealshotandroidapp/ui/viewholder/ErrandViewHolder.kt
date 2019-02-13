package com.dealshot.dealshotandroidapp.ui.viewholder

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.card_errand.view.*

class ErrandViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
  fun bind(errand: Errand) {
    itemView.background.setTint(getCardColor(errand))

    itemView.errand_title.text = errand.title
    itemView.errand_pick_up_location.text = context.getString(
      R.string.pick_up_locale,
      errand.pickupLocation
    )
    itemView.errand_delivery_up_location.text = context.getString(
      R.string.deliver_locale,
      errand.deliveryLocation
    )

    itemView.errand_title.setTextColor(getCardTextColor(errand))
    itemView.errand_pick_up_location.setTextColor(getCardTextColor(errand))
    itemView.errand_delivery_up_location.setTextColor(getCardTextColor(errand))
  }

  companion object {
    private fun getCardColor(errand: Errand) = when (errand.status) {
      Errand.Companion.Status.UNASSIGNED -> Color.WHITE
      Errand.Companion.Status.WIP -> Color.rgb(0xB0, 0x00, 0x20)
      Errand.Companion.Status.CLOSED -> Color.rgb(0x41, 0x69, 0xE1)
    }

    private fun getCardTextColor(errand: Errand) = when (errand.status) {
      Errand.Companion.Status.UNASSIGNED -> Color.BLACK
      else -> Color.WHITE
    }
  }
}
