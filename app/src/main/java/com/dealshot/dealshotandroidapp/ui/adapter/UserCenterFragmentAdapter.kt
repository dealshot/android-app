package com.dealshot.dealshotandroidapp.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.ui.fragment.UserCenterSectionFragment

class UserCenterFragmentAdapter(fm: FragmentManager, val context: Context) : FragmentPagerAdapter(fm) {
  override fun getItem(position: Int): Fragment {
    return UserCenterSectionFragment.newInstance(SOURCE_TYPE_OPTION[position])
  }

  override fun getCount(): Int = SOURCE_TYPE_OPTION.size

  override fun getPageTitle(position: Int): CharSequence? = context.getString(
    when (SOURCE_TYPE_OPTION[position]) {
      ErrandDAO.SourceType.USER_OWNED -> R.string.owned_errand_title
      ErrandDAO.SourceType.USER_WIP -> R.string.wip_errand_title
      ErrandDAO.SourceType.USER_CLOSED -> R.string.closed_errand_title
      else -> R.string.unknown
    }
  )

  companion object {
    private val SOURCE_TYPE_OPTION = arrayListOf(
      ErrandDAO.SourceType.USER_OWNED,
      ErrandDAO.SourceType.USER_WIP,
      ErrandDAO.SourceType.USER_CLOSED
    )
  }
}
