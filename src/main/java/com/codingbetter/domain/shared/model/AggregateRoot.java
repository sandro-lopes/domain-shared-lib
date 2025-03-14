package com.codingbetter.domain.shared.model;

import java.util.List;

import com.codingbetter.domain.shared.event.DomainEvent;

/**
 * Interface that defines the behavior of an Aggregate Root in DDD.
 * Aggregates are responsible for ensuring the consistency of changes
 * to entities within their boundaries.
 */
public interface AggregateRoot {
    /**
     * Returns the domain events accumulated by this aggregate.
     * @return Immutable list of domain events
     */
    List<DomainEvent> getDomainEvents();

    /**
     * Clears the list of events after they have been published.
     */
    void clearDomainEvents();

    /**
     * Adds a domain event to the aggregate.
     * @param event The domain event to be added
     */
    void addDomainEvent(DomainEvent event);
} 