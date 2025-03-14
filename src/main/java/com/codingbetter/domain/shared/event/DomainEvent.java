package com.codingbetter.domain.shared.event;

import java.time.LocalDateTime;
import java.util.UUID;
import java.io.Serializable;

/**
 * Interface that defines a domain event.
 * Domain events are used to notify other parts of the system
 * about changes that occurred in an aggregate.
 */
public interface DomainEvent extends Serializable {
    /**
     * Returns the unique identifier of the event.
     * @return UUID of the event
     */
    UUID getId();
    
    /**
     * Returns the date and time when the event occurred.
     * @return Date and time of occurrence
     */
    LocalDateTime getOccurredOn();
} 