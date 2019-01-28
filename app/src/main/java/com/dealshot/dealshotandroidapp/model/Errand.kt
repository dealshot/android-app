package com.dealshot.dealshotandroidapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Errand(var title: String = "", val owner: String = "") : Parcelable {
  @get:Exclude
  var id = ""

  @ServerTimestamp
  var lastModified: Timestamp? = null

  companion object {
    const val LAST_MODIFIED_KEY = "lastModified"

    fun fromSnapshot(snapshot: DocumentSnapshot): Errand {
      return snapshot.toObject(Errand::class.java)!!.apply {
        this.id = snapshot.id
      }
    }
  }
}
