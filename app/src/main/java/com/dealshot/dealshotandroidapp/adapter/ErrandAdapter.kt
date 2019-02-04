package com.dealshot.dealshotandroidapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.viewholder.ErrandViewHolder

abstract class ErrandAdapter(initSourceType: ErrandDAO.SourceType) : RecyclerView.Adapter<ErrandViewHolder>() {
  private var source: ArrayList<Errand>? = ErrandDAO.selectSource(initSourceType)

  init {
    ErrandDAO.addSnapShotListener { _, _ ->
      notifyDataSetChanged()
    }
  }

  override fun getItemCount(): Int = source!!.size

  private fun getErrand(index: Int): Errand = source!![index]

  override fun onCreateViewHolder(parent: ViewGroup, index: Int): ErrandViewHolder = ErrandViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.card_errand, parent, false),
    parent.context
  )

  override fun onBindViewHolder(holder: ErrandViewHolder, index: Int) {
    val errand = getErrand(index)
    holder.bind(errand)
    updateUI(holder.itemView, holder.context, errand)
  }

  abstract fun updateUI(itemView: View, context: Context, errand: Errand)
}
