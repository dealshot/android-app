package com.dealshot.dealshotandroidapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Errand(
    val owner: String = "",
    val ownerContact: String = "",
    var title: String = "",
    var pickupLocation: String = "",
    var deliveryLocation: String = "",
    var status: Status = Companion.Status.UNASSIGNED,
    var assignee: String = "",
    var assigneeContact: String = ""
) : Parcelable {
  @IgnoredOnParcel
  @get:Exclude
  var id = ""

  @IgnoredOnParcel
  @ServerTimestamp
  var lastModified: Timestamp? = null

  override fun equals(other: Any?): Boolean = other is Errand && other.id == id

  fun claim(uid: String, contact: String): Errand {
    status = Companion.Status.WIP
    assignee = uid
    assigneeContact = contact
    return this
  }

  fun release(): Errand {
    status = Companion.Status.UNASSIGNED
    return this
  }

  fun close(): Errand {
    status = Companion.Status.CLOSED
    return this
  }

  override fun hashCode(): Int {
    var result = owner.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + pickupLocation.hashCode()
    result = 31 * result + deliveryLocation.hashCode()
    result = 31 * result + status.hashCode()
    result = 31 * result + assignee.hashCode()
    result = 31 * result + (lastModified?.hashCode() ?: 0)
    return result
  }

  companion object {
    const val STATUS_KEY = "status"

    const val ASSIGNEE_KEY = "assignee"

    const val OWNER_KEY = "owner"

    enum class Status {
      UNASSIGNED,
      WIP,
      CLOSED;
    }

    fun fromSnapshot(snapshot: DocumentSnapshot): Errand {
      return snapshot.toObject(Errand::class.java)!!.apply {
        this.id = snapshot.id
      }
    }
  }
}
