package webCalendarSpring

import java.time.LocalDate

data class EventRequestDTO(
        var event: String? = null,
        var date: LocalDate? = null,
)