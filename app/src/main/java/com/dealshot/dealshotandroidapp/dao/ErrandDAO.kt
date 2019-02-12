package com.dealshot.dealshotandroidapp.dao

import android.os.Parcelable
import com.dealshot.dealshotandroidapp.model.Errand
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import kotlinx.android.parcel.Parcelize

object ErrandDAO {
  private const val ERRAND_COLLECTION = "ERRAND"

  @Parcelize
  enum class SourceType : Parcelable {
    USER_OWNED,
    USER_WIP,
    USER_CLOSED,
    PLAZA
  }

  private class RefContent {
    val source: ArrayList<Errand> = ArrayList()
    var sourceListener: ListenerRegistration? = null

    fun clear() {
      source.clear()
      sourceListener?.remove()
      sourceListener = null
    }
  }

  private val sourceMap: Map<SourceType, RefContent> = mapOf(
      Pair(ErrandDAO.SourceType.USER_OWNED, RefContent()),
      Pair(ErrandDAO.SourceType.USER_WIP, RefContent()),
      Pair(ErrandDAO.SourceType.USER_CLOSED, RefContent()),
      Pair(ErrandDAO.SourceType.PLAZA, RefContent())
  )

  private val errandRef = FirebaseFirestore.getInstance().collection(ERRAND_COLLECTION)

  init {
    AuthController.addAuthStateListener {
      clearContent()
      if (AuthController.hasUser()) {
        syncUserErrand()
        syncPlazaErrand()
      }
    }
  }

  private fun getContent(type: SourceType) = sourceMap.getValue(type)

  private fun clearContent() {
    sourceMap.values.map { it.clear() }
  }

  private fun syncUserErrand() {
    val userOwnedContent = getContent(ErrandDAO.SourceType.USER_OWNED)
    userOwnedContent.sourceListener =
        errandRef
            .whereEqualTo(Errand.OWNER_KEY, AuthController.currentUID())
            .addSnapshotListener { querySnapshot, _ ->
              querySnapshot!!.documentChanges.map {
                val errand = Errand.fromSnapshot(it.document)
                syncIncomingDocument(errand, it.type, userOwnedContent.source)
              }
            }

    val userWIPContent = getContent(ErrandDAO.SourceType.USER_WIP)
    userWIPContent.sourceListener =
        errandRef
            .whereEqualTo(Errand.ASSIGNEE_KEY, AuthController.currentUID())
            .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.WIP)
            .addSnapshotListener { querySnapshot, _ ->
              querySnapshot!!.documentChanges.map {
                val errand = Errand.fromSnapshot(it.document)
                syncIncomingDocument(errand, it.type, userWIPContent.source)
              }
            }

    val userClosedContent = getContent(ErrandDAO.SourceType.USER_CLOSED)
    userClosedContent.sourceListener =
        errandRef
            .whereEqualTo(Errand.ASSIGNEE_KEY, AuthController.currentUID())
            .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.CLOSED)
            .addSnapshotListener { querySnapshot, _ ->
              querySnapshot!!.documentChanges.map {
                val errand = Errand.fromSnapshot(it.document)
                syncIncomingDocument(errand, it.type, userClosedContent.source)
              }
            }
  }

  private fun syncPlazaErrand() {
    val content = getContent(ErrandDAO.SourceType.PLAZA)
    content.sourceListener =
        errandRef
            .whereEqualTo(Errand.STATUS_KEY, Errand.Companion.Status.UNASSIGNED)
            .addSnapshotListener { querySnapshot, _ ->
              querySnapshot!!.documentChanges.map {
                val errand = Errand.fromSnapshot(it.document)
                if (errand.owner != AuthController.currentUID()) {
                  syncIncomingDocument(errand, it.type, content.source)
                }
              }
            }
  }

  private fun syncIncomingDocument(
      errand: Errand,
      type: DocumentChange.Type,
      source: ArrayList<Errand>
  ) {
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

  fun selectSource(type: SourceType): ArrayList<Errand> = getContent(type).source

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
