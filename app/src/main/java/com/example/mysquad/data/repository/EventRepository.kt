import com.example.mysquad.data.localRoom.dao.EventDao
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val eventDao: EventDao,
    private val remote: EventRemoteDataSource
) {
    suspend fun createEvent(event: EventEntity) {
        eventDao.insertEvent(event)
        remote.uploadEvent(event)
    }

    suspend fun syncEventsToLocal() {
        val remoteEvents = remote.fetchEvents()
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
}