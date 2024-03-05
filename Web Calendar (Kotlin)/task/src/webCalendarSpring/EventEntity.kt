package webCalendarSpring

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name="events")
class EventEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        var id: Long? = null,

        @NotNull
        @Column(name = "event")
        var event: String,

        @NotNull
        @Column(name = "date")
        var date: LocalDate
)