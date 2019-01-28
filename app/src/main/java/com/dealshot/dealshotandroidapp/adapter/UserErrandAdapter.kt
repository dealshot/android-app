package com.dealshot.dealshotandroidapp.adapter

import android.view.View
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand

class UserErrandAdapter : ErrandAdapter() {
  override fun getErrand(index: Int): Errand = ErrandDAO.getUserErrand(index)

  override fun getItemCount(): Int = ErrandDAO.getUserErrandTotal()

  override fun updateUI(itemView: View, errand: Errand) {

  }
}