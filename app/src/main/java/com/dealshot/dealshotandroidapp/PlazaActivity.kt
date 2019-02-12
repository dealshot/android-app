package com.dealshot.dealshotandroidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.dealshot.dealshotandroidapp.ui.adapter.PlazaErrandAdapter
import com.dealshot.dealshotandroidapp.ui.menu.AppMenuHandler
import kotlinx.android.synthetic.main.activity_plaza.*

class PlazaActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plaza)

    plaza_errand_view.adapter = PlazaErrandAdapter()
    plaza_errand_view.layoutManager = LinearLayoutManager(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean =
    AppMenuHandler.handleMenuItem(this, item)
}
