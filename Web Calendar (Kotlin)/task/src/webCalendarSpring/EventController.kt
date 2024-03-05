package webCalendarSpring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class EventController(
        @Autowired private val service: EventService
) {


    @PostMapping("/event")
    fun addEvent(@RequestBody eventRequestDTO: EventRequestDTO) : ResponseEntity<Any> {
        return service.saveEvent(eventRequestDTO)
    }

    @GetMapping("/event")
    fun getAllEvents(
        @RequestParam("start_time") startTime: String?,
        @RequestParam("end_time") endTime: String?,
    ): ResponseEntity<List<EventEntity>> {
        return service.getAllEvents(startTime, endTime)
    }

    @GetMapping("event/{id}")
    fun getEvent(@PathVariable("id") id: Long) : ResponseEntity<Any> {
        return service.getEventEntity(id)
    }

    @DeleteMapping("event/{id}")
    fun deleteEvent(@PathVariable("id") id: Long) : ResponseEntity<Any> {
        return service.deleteEventEntity(id)
    }

    @GetMapping("/event/today")
    fun getEventsForToday(): ResponseEntity<List<EventEntity>>{
        return service.getEventsForToday()
    }
}