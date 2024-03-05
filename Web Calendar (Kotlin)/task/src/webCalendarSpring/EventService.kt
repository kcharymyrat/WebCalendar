package webCalendarSpring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class EventService(
        @Autowired private val eventRepository: EventRepository
) {

    fun getAllEvents(startTime: String?, endTime: String?): ResponseEntity<List<EventEntity>> {
        val eventEntityList = eventRepository.findAll()
        println("eventEntityList = $eventEntityList")
        if (startTime == null && endTime == null) {
            if (eventEntityList.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            return ResponseEntity.status(HttpStatus.OK).body(eventEntityList)
        }

        try {
            val starLocalDate = LocalDate.parse(startTime)
            val startTimeEvents = eventEntityList.filter { it?.date != null && it.date >= starLocalDate }
            if (endTime == null) return ResponseEntity.status(204).body(startTimeEvents)

            val endLocalDate = LocalDate.parse(endTime)
            val endTimeEvents = startTimeEvents.filter { it.date <= endLocalDate }
            return ResponseEntity.status(204).body(endTimeEvents)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }


    }

    fun getEventEntity(id: Long) : ResponseEntity<Any> {
        val eventEntityOption: Optional<EventEntity> = eventRepository.findById(id)

        if (eventEntityOption.isEmpty) {
            val res = mapOf("message" to "The event doesn't exist!")
            return ResponseEntity(res, HttpStatus.NOT_FOUND)
        }
        val eventEntity = eventEntityOption.get()
        return ResponseEntity(eventEntity, HttpStatus.OK)
    }

    fun deleteEventEntity(id: Long) : ResponseEntity<Any> {
        val eventEntityOption: Optional<EventEntity> = eventRepository.findById(id)

        if (eventEntityOption.isEmpty) {
            val res = mapOf("message" to "The event doesn't exist!")
            return ResponseEntity(res, HttpStatus.NOT_FOUND)
        }

        val eventEntity = eventEntityOption.get()
        eventRepository.delete(eventEntity)
        return ResponseEntity(eventEntity, HttpStatus.OK)
    }

    fun getEventsForToday(): ResponseEntity<List<EventEntity>> {
        val today = LocalDate.now()
        val todayEventEntityList = eventRepository.findAll().filter { it?.date == LocalDate.now() }
        println("eventRepository.findAll() = ${eventRepository.findAll()}")
        println("todayEventEntityList = $todayEventEntityList")
        return ResponseEntity.status(HttpStatus.OK).body(todayEventEntityList)
    }

    fun saveEvent(eventRequestDTO: EventRequestDTO): ResponseEntity<Any> {
        if (!isEventValid(eventRequestDTO.event) || !isDateValid(eventRequestDTO.date)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }

        // Create new EventEntity
        val eventEntity = EventEntity(
                event = eventRequestDTO.event!!,
                date = eventRequestDTO.date!!
        )
        eventRepository.save(eventEntity)
        println("eventEntity = $eventEntity")

        val responseDTO = EventResponseDTO(
                event = eventEntity.event,
                date = eventEntity.date
        )

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO)
    }

    fun isEventValid(event: String?) : Boolean {
        return !(event.isNullOrEmpty() || event.isBlank())
    }

    fun isDateValid(date: LocalDate?) : Boolean {
        if (date == null) {
            return false
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        try {
            val formattedDate = date.format(formatter)
            // Parse the formatted date to ensure it matches the expected format
            LocalDate.parse(formattedDate, formatter)
            return true // Format is valid
        } catch (e: Exception) {
            return false
        }
    }

}