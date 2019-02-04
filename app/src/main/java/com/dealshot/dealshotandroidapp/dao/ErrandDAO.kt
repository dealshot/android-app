package com.dealshot.dealshotandroidapp.dao

import android.os.Parcelable
import com.dealshot.dealshotandroidapp.model.Errand
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.parcel.Parcelize

object ErrandDAO {
  private const val ERRAND_COLLECTION = "ERRAND"

  private val errandRef = FirebaseFirestore.getInstance().collection(ERRAND_COLLECTION)

  private var userOwnedErrandList: ArrayList<Errand> = arrayListOf()

  private var userWIPErrandList: ArrayList<Errand> = arrayListOf()

  private var userClosedErrandList: ArrayList<Errand> = arrayListOf()

  private var plazaErrandList: ArrayList<Errand> = arrayListOf()

  @Parcelize
  enum class SourceType : Parcelable {
    USER_OWNED,
    USER_WIP,
    USER_CLOSED,
    PLAZA
  }

  init {
    AuthController.addAuthStateListener {
      userOwnedErrandList = arrayListOf()
      userWIPErrandList = arrayListOf()
      userClosedErrandList = arrayListOf()
      plazaErrandList = arrayListOf()

      if (AuthController.hasUser()) {
        syncUserErrand()
        syncPlazaErrand()
      }
    }
  }

  private fun syncUserErrand() {
    errandRef
      .whereEqualTo(Errand.OWNER_KEY, AuthController.currentUID())
      .addSnapshotListener { querySnapshot, _ ->
        querySnapshot!!.documentChanges.map {
          val errand = Errand.fromSnapshot(it.document)
          syncIncomingDocument(errand, it.type, userOwnedErrandList)
        }
      }

    errandRef
      .whereEqualTo(Errand.ASSIGNEE_KEY, AuthController.currentUID())
      .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.WIP)
      .addSnapshotListener { querySnapshot, _ ->
        querySnapshot!!.documentChanges.map {
          val errand = Errand.fromSnapshot(it.document)
          syncIncomingDocument(errand, it.type, userWIPErrandList)
        }
      }

    errandRef
      .whereEqualTo(Errand.ASSIGNEE_KEY, AuthController.currentUID())
      .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.CLOSED)
      .addSnapshotListener { querySnapshot, _ ->
        querySnapshot!!.documentChanges.map {
          val errand = Errand.fromSnapshot(it.document)
          syncIncomingDocument(errand, it.type, userClosedErrandList)
        }
      }
  }

  private fun syncPlazaErrand() {
    errandRef
      .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.UNASSIGNED)
      .addSnapshotListener { querySnapshot, _ ->
        querySnapshot!!.documentChanges.map {
          val errand = Errand.fromSnapshot(it.document)
          if (errand.owner != AuthController.currentUID()) {
            syncIncomingDocument(errand, it.type, plazaErrandList)
          }
        }
      }
  }

  private fun syncIncomingDocument(errand: Errand, type: DocumentChange.Type, source: ArrayList<Errand>) {
    when (type) {
      DocumentChange.Type.ADDED -> {
        source.add(0, errand)
      }
      DocumentChange.Type.REMOVED -> {
        source.remove(errand)
      }
      DocumentChange.Type.MODIFIED -> {
        source.remove(errand)
        source.add(0, errand)
      }
    }
  }

  fun addSnapShotListener(listener: (QuerySnapshot?, FirebaseFirestoreException?) -> Unit) {
    errandRef.addSnapshotListener(EventListener<QuerySnapshot>(listener))
  }

  fun selectSource(type: SourceType): ArrayList<Errand> = when (type) {
    ErrandDAO.SourceType.USER_OWNED -> userOwnedErrandList
    ErrandDAO.SourceType.USER_WIP -> userWIPErrandList
    ErrandDAO.SourceType.USER_CLOSED -> userClosedErrandList
    ErrandDAO.SourceType.PLAZA -> plazaErrandList

  }

  fun createErrand(errand: Errand) {
    errandRef.add(errand)
  }

  fun updateErrand(errand: Errand) {
    errand.lastModified = Timestamp.now()
    errandRef.document(errand.id).set(errand)
  }

  fun deleteErrand(errand: Errand) {
    errandRef.document(errand.id).delete()
  }
}
