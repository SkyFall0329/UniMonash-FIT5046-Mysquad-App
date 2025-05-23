package com.example.mysquad.data.remoteFireStore

import com.example.mysquad.data.localRoom.entity.EventEntity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.flow
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

    fun fetchAllEvents() : Flow<List<EventEntity>> = flow {

        val snapshot = eventsCollection.get().await()

        if (!snapshot.isEmpty) {

            val events = snapshot.documents.mapNotNull { it.toObject(EventEntity::class.java) }


            emit(events)
        } else {

            emit(emptyList())
        }

    }

    suspend fun getPostDetail(postId: String): EventEntity? {

        val eventDocument = eventsCollection.document(postId)


        val snapshot = eventDocument.get().await()


        return if (snapshot.exists()) {
            snapshot.toObject(EventEntity::class.java)
        } else {
            null
        }
    }

    suspend fun deleteEventById(eventId: String) {
        Firebase.firestore          // or your wrapper
            .collection("events")
            .document(eventId)
            .delete()
            .await()                // kotlinx-coroutines-play-services
    }
}