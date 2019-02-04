package com.dealshot.dealshotandroidapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dealshot.dealshotandroidapp.ui.adapter.PlazaErrandAdapter
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.dao.ErrandDAO
import com.dealshot.dealshotandroidapp.model.Errand
import kotlinx.android.synthetic.main.activity_plaza.*
import kotlinx.android.synthetic.main.dialog_errand.view.*

class PlazaActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plaza)

    plaza_errand_view.adapter = PlazaErrandAdapter()
    plaza_errand_view.layoutManager = LinearLayoutManager(this)

    create_errand_button.setOnClickListener {
      val dialogView = layoutInflater.inflate(R.layout.dialog_errand, null)
      val builder = AlertDialog.Builder(this)

      dialogView.assignee_input_wrapper.visibility = View.GONE

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

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    menu.findItem(R.id.action_switch_segment).title = getString(R.string.user_center_tag)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_switch_segment -> {
        finish()
        startActivity(Intent(this, UserCenterActivity::class.java))
        return true
      }
      R.id.action_sign_out -> {
        AuthController.signOut()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
