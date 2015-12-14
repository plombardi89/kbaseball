package kbaseball.user.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.joda.time.DateTime
import java.util.*


data class PendingUser @JsonCreator constructor(
    val id: Int,
    val token: UUID
)