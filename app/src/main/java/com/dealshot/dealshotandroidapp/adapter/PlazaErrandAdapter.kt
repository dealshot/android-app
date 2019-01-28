package com.dealshot.dealshotandroidapp.adapter

import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand

class PlazaErrandAdapter : ErrandAdapter() {
  override fun getErrand(index: Int): Errand = ErrandDAO.getPlazaErrand(index)

  override fun getItemCount(): Int = ErrandDAO.getPlazaErrandTotal()
}