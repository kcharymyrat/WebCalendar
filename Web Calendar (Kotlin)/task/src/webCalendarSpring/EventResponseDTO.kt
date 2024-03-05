package webCalendarSpring

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.aspectj.bridge.Message
import java.time.LocalDate

data class EventResponseDTO(
        val message: String = "The event has been added!",
        val event: String,
        val date: LocalDate,
)
