package com.dealshot.dealshotandroidapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.adapter.UserErrandAdapter
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import kotlinx.android.synthetic.main.fragment_user_center_section.view.*

class UserCenterSectionFragment : Fragment() {
  private var sourceType: ErrandDAO.SourceType? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      sourceType = it.getParcelable(ARGS_SOURCE_TYPE)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_user_center_section, container, false)
    bindView(view)
    return view
  }

  private fun bindView(view: View) {
    view.user_center_errands_view.layoutManager = LinearLayoutManager(context)
    view.user_center_errands_view.adapter = UserErrandAdapter(sourceType!!)
  }

  companion object {
    private const val ARGS_SOURCE_TYPE = "source_type"

    @JvmStatic
    fun newInstance(type: ErrandDAO.SourceType) = UserCenterSectionFragment().apply {
      arguments = Bundle().apply {
        putParcelable(ARGS_SOURCE_TYPE, type)
      }
    }
  }
}
