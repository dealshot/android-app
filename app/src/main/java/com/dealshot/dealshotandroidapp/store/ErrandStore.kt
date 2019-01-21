package com.dealshot.dealshotandroidapp.store

import com.dealshot.dealshotandroidapp.model.Errand

object ErrandStore {
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