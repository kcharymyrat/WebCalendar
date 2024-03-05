package webCalendarSpring

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface EventRepository : JpaRepository<EventEntity, Long> {
    fun findAllByDate(date: LocalDate): List<EventEntity>
}