package com.dealshot.dealshotandroidapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.View.GONE
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.adapter.PlazaErrandAdapter
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.model.Errand
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import kotlinx.android.synthetic.main.dialog_errand.view.*
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
    view.plaza_errand_view.adapter = PlazaErrandAdapter()
    view.plaza_errand_view.layoutManager = LinearLayoutManager(context)

    view.create_errand_button.setOnClickListener {
      val dialogView = layoutInflater.inflate(R.layout.dialog_errand, null)
      val builder = AlertDialog.Builder(context!!)

      dialogView.assignee_input_wrapper.visibility = GONE

      builder.setTitle(getString(R.string.create_errand_dialog_title))
      builder.setView(dialogView)
      builder.setPositiveButton(getString(R.string.create)) { _, _ ->
        val title = dialogView.errand_title_input.text.toString()
        val pickupLocation = dialogView.pickup_location_input.text.toString()
        val deliveryLocation = dialogView.delivery_location_input.text.toString()
        ErrandDAO.createErrand(
          Errand(
            AuthController.currentUID(),
            title,
            pickupLocation,
            deliveryLocation
          )
        )
      }

      builder.show()
    }
  }

  companion object {
    @JvmStatic
    fun newInstance() = PlazaFragment()
  }
}
