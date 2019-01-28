package com.dealshot.dealshotandroidapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.viewholder.ErrandViewHolder

abstract class ErrandAdapter : RecyclerView.Adapter<ErrandViewHolder>() {
  init {
    ErrandDAO.addSnapShotListener { _, _ ->
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, index: Int): ErrandViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.card_errand, parent, false)
    return ErrandViewHolder(view)
  }

  override fun onBindViewHolder(holder: ErrandViewHolder, index: Int) {
    val errand = getErrand(index)
    holder.bind(errand)
    updateUI(holder.itemView, errand)
  }

  open fun updateUI(itemView: View, errand: Errand) {}

  abstract fun getErrand(index: Int): Errand
}