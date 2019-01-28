package com.dealshot.dealshotandroidapp.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.card_errand.view.*

class ErrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bind(errand: Errand) {
    // TODO: bind errand details
    itemView.errand_title.text = errand.title
  }
}