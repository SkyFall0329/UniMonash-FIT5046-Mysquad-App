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
        eventDao.clearAllEvents()
        remoteEvents.forEach { eventDao.insertEvent(it) }
    }

    fun getLocalEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()
    fun getRemoteEvents() = remote.fetchAllEvents()

    suspend fun getPostDetail(postId: String?): EventEntity? {
        if (postId != null) {
            return remote.getPostDetail(postId)
        }
        return null

    }

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

    suspend fun getPendingUsersForEvent(eventId: String): List<UserProfileEntity> {
        val event = eventDao.getEventById(eventId)
        return if (event != null && event.eventPendingList.isNotEmpty()) {
            userDao.getUsersByIds(event.eventPendingList)
        } else {
            emptyList()
        }
    }

    suspend fun removeUserFromPendingList(eventId: String, userId: String) {
        val event = eventDao.getEventById(eventId)
        if (event != null && event.eventPendingList.contains(userId)) {
            val updatedList = event.eventPendingList.filterNot { it == userId }
            val updatedEvent = event.copy(eventPendingList = updatedList)
            eventDao.updateEvent(updatedEvent)
        }
    }

    suspend fun joinEvent(eventId: String, userId: String) {
        val event = eventDao.getEventById(eventId)
        if (event != null && !event.eventJoinList.contains(userId)) {
            val updatedEvent = event.copy(
                eventJoinList = event.eventJoinList + userId
            )
            eventDao.updateEvent(updatedEvent)
        }
    }


}