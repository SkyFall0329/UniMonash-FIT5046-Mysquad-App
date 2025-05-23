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
        // 获取事件集合的引用
        val snapshot = eventsCollection.get().await()

        if (!snapshot.isEmpty) {
            // 将 Firestore 快照转换为 EventEntity 列表
            val events = snapshot.documents.mapNotNull { it.toObject(EventEntity::class.java) }

            // 使用 emit 正确地发射数据
            emit(events)
        } else {
            // 如果没有数据，可以选择发射空列表或其他逻辑
            emit(emptyList())
        }

    }

    suspend fun getPostDetail(postId: String): EventEntity? {
        // 获取事件文档引用
        val eventDocument = eventsCollection.document(postId)

        // 获取文档快照
        val snapshot = eventDocument.get().await()

        // 检查文档是否存在并返回事件实体
        return if (snapshot.exists()) {
            snapshot.toObject(EventEntity::class.java)  // 将文档数据转换为 EventEntity 对象
        } else {
            null  // 如果文档不存在，返回 null
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