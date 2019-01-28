package com.dealshot.dealshotandroidapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.adapter.UserErrandAdapter
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_create_errand.view.*
import kotlinx.android.synthetic.main.fragment_plaza.view.*

class PlazaFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_plaza, container, false)
    bindView(view)
    return view
  }

  private fun bindView(view: View) {
    val adapter = UserErrandAdapter()

    view.plaza_errand_view.adapter = adapter
    view.plaza_errand_view.layoutManager = LinearLayoutManager(context)

    view.create_errand_button.setOnClickListener {
      val dialogView = layoutInflater.inflate(R.layout.dialog_create_errand, null)

      val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
      builder.setTitle(getString(R.string.create_errand_dialog_title))
      builder.setView(dialogView)
      builder.setPositiveButton(getString(R.string.create)) { _, _ ->
        val title = dialogView.errand_title_input.text
        ErrandDAO.createErrand(Errand(title.toString()))
        adapter.notifyDataSetChanged()
      }

      builder.show()
    }
  }

  companion object {
    @JvmStatic
    fun newInstance() = PlazaFragment()
  }
}