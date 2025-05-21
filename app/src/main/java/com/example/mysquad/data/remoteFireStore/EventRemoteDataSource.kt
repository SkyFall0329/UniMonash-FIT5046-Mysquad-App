package com.example.mysquad.data.remoteFireStore

import com.example.mysquad.data.localRoom.entity.EventEntity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class EventRemoteDataSource(
    private val firestore: FirebaseFirestore = Firebase.firestore
) {
    private val eventsCollection = firestore.collection("event")

    suspend fun uploadEvent(event: EventEntity) {
        eventsCollection.document(event.eventId).set(event).await()
    }

    suspend fun fetchEvents(): List<EventEntity> {
        val snapshot = eventsCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(EventEntity::class.java) }
    }
}