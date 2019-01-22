package com.dealshot.dealshotandroidapp.dao

import com.dealshot.dealshotandroidapp.model.Errand

object ErrandDAO {
  private val errandList: ArrayList<Errand> = arrayListOf()

  fun getTotal(): Int {
    return errandList.size
  }

  fun getErrand(index: Int): Errand {
    return errandList[index]
  }

  fun addErrand(errand: Errand) {
    errandList.add(errand)
  }
}