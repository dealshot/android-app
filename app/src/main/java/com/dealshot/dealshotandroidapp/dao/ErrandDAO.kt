package com.dealshot.dealshotandroidapp.dao

import com.dealshot.dealshotandroidapp.model.Errand
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

object ErrandDAO {
  private const val INIT_PLAZA_CAPACITY = 20

  private const val ERRAND_COLLECTION = "ERRAND"

  private val userErrandList: ArrayList<Errand> = arrayListOf()

  private val plazaErrandList: ArrayList<Errand> = arrayListOf()

  private var plazaCapacity = INIT_PLAZA_CAPACITY

  private val errandDump: HashMap<String, Errand> = hashMapOf()

  private val errandRef = FirebaseFirestore.getInstance().collection(ERRAND_COLLECTION)

  init {
    errandRef
      .orderBy(Errand.LAST_MODIFIED_KEY)
      .addSnapshotListener { querySnapshot, _ ->
        for (documentChange in querySnapshot!!.documentChanges) {
          val errand = Errand.fromSnapshot(documentChange.document)
          when (documentChange.type) {
            DocumentChange.Type.ADDED -> {
              when {
                errand.owner == AuthDAO.currentOwnerId() ->
                  userErrandList.add(errand)
                hasPlazaCapacity() ->
                  plazaErrandList.add(errand)
                else ->
                  return@addSnapshotListener
              }
              errandDump[errand.id] = errand
            }
            DocumentChange.Type.REMOVED -> {
              if (!errandDump.containsKey(errand.id)) {
                return@addSnapshotListener
              }
              errandDump.remove(errand.id)
              if (errand.owner == AuthDAO.currentOwnerId()) {
                userErrandList.remove(errand)
              } else {
                plazaErrandList.remove(errand)
              }
            }
            DocumentChange.Type.MODIFIED -> {
              if (!errandDump.containsKey(errand.id)) {
                return@addSnapshotListener
              }
              if (errand.owner == AuthDAO.currentOwnerId()) {
                userErrandList.remove(errand)
                userErrandList.add(0, errand)
              } else {
                plazaErrandList.remove(errand)
                userErrandList.add(0, errand)
              }
            }
          }
        }
      }
  }

  private fun hasPlazaCapacity(): Boolean = plazaErrandList.size < plazaCapacity

  fun addSnapShotListener(listener: (QuerySnapshot?, FirebaseFirestoreException?) -> Unit) {
    errandRef.addSnapshotListener(EventListener<QuerySnapshot>(listener))
  }

  fun getUserErrandTotal(): Int = userErrandList.size

  fun getPlazaErrandTotal(): Int = plazaErrandList.size

  fun getUserErrand(index: Int): Errand = userErrandList[index]

  fun getPlazaErrand(index: Int): Errand = plazaErrandList[index]

  fun createErrand(errand: Errand) {
    errandRef.add(errand)
  }

  fun modifyErrand(errand: Errand) {
    errandRef.document(errand.id).set(errand)
  }

  fun deleteErrand(errand: Errand) {
    errandRef.document(errand.id).delete()
  }
}