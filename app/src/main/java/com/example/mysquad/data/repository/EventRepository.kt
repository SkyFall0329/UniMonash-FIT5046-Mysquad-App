import com.example.mysquad.data.localRoom.dao.EventDao
import com.example.mysquad.data.localRoom.dao.UserDao
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository(
    private val eventDao: EventDao,
    private val userDao: UserDao,
    private val remote: EventRemoteDataSource
) {
    suspend fun createEvent(event: EventEntity) {
        eventDao.insertEvent(event)
        remote.uploadEvent(event)
    }

    suspend fun syncEventsToLocal() {
        val remoteEvents = remote.fetchEvents()
            .map{it.copy(eventDate=normaliseDate(it.eventDate))}
        eventDao.clearAllEvents()
        remoteEvents.forEach { eventDao.insertEvent(it) }
    }

    suspend fun syncEventsToLocal2() {
        val remoteEvents = remote.fetchEvents()
        eventDao.clearAllEvents()
        remoteEvents.forEach { eventDao.insertEvent(it) }
    }

    fun getHostEvents(uid:String) =
        eventDao.getHostedBy(uid).map{it.sortedBy(EventEntity::eventDate)}

    fun getPending(uid:String) =
        eventDao.getPending(uid)

    fun getLocalEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()
    fun getRemoteEvents() = remote.fetchAllEvents()

    suspend fun getPostDetail(postId: String?): EventEntity? {
        if (postId != null) {
            return remote.getPostDetail(postId)
        }
        return null

    }

    fun eventById(id: String): Flow<EventEntity?> =
        eventDao.getEventById(id)

    suspend fun joinEvent(value: EventEntity) {
        remote.uploadEvent(value)
    }

    fun getRelevantEventsAsFlow(userId: String): Flow<List<EventEntity>> {
        val now = System.currentTimeMillis() / 1000
        val sevenDaysLater = now + 7 * 24 * 60 * 60

        return eventDao.getAllEventsInDateRange(now, sevenDaysLater)
            .map { all ->
                all.filter { it.eventHostUserId == userId || it.eventJoinList.contains(userId) }
                    .sortedByDescending { it.eventDate }
            }
    }

    suspend fun deleteEvent(eventId: String) {
        // 1. remove from Firestore
        remote.deleteEventById(eventId)

        // 2. remove from Room
        eventDao.deleteEventById(eventId)
    }

    /** Accepts seconds or millis → always returns millis. */
    private fun normaliseDate(raw: Long): Long =
        if (raw < 100_000_000_000L) raw * 1000 else raw

    suspend fun getPendingUsersForEvent(eventId: String): List<UserProfileEntity> {
        val event = eventDao.eventById(eventId)
        return if (event != null && event.eventPendingList.isNotEmpty()) {
            userDao.getUsersByIds(event.eventPendingList)
        } else {
            emptyList()
        }
    }

    suspend fun removeUserFromPendingList(eventId: String, userId: String) {
        val event = eventDao.eventById(eventId)
        if (event != null && event.eventPendingList.contains(userId)) {
            val updatedList = event.eventPendingList.filterNot { it == userId }
            val updatedEvent = event.copy(eventPendingList = updatedList)

            eventDao.updateEvent(updatedEvent)
            remote.uploadEvent(updatedEvent)
        }
    }

    suspend fun joinEvent(eventId: String, userId: String) {
        val event = eventDao.eventById(eventId)
        if (event != null && !event.eventJoinList.contains(userId)) {
            val updatedPending = event.eventPendingList.filterNot { it == userId }
            val updatedJoin = event.eventJoinList + userId

            val updatedEvent = event.copy(
                eventPendingList = updatedPending,
                eventJoinList = updatedJoin
            )

            eventDao.updateEvent(updatedEvent)
            remote.uploadEvent(updatedEvent)
        }
    }


}