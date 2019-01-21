package com.dealshot.dealshotandroidapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.store.ErrandStore
import kotlinx.android.synthetic.main.card_errand.view.*

class ErrandAdapter : RecyclerView.Adapter<ErrandAdapter.ErrandViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, index: Int): ErrandViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.card_errand, parent, false)
    return ErrandViewHolder(view)
  }

  override fun getItemCount(): Int {
    return ErrandStore.getTotal()
  }

  override fun onBindViewHolder(holder: ErrandViewHolder, index: Int) {
    holder.bind(ErrandStore.getErrand(index))
  }

  inner class ErrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(errand: Errand) {
      // TODO: bind errand details
      itemView.errand_title.text = errand.title
    }
  }
}