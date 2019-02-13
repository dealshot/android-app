package com.dealshot.dealshotandroidapp.ui.adapter

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.ui.viewholder.ErrandViewHolder

abstract class ErrandAdapter(
  initSourceType: ErrandDAO.SourceType,
  protected val fragmentManager: FragmentManager
) :
  RecyclerView.Adapter<ErrandViewHolder>() {
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
    updateErrandCardView(holder.context, holder.itemView, errand)
  }

  abstract fun updateErrandCardView(context: Context, cardView: View, errand: Errand)
}
