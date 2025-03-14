package com.codingbetter.domain.shared.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.codingbetter.domain.shared.event.DomainEvent;

/**
 * Abstract implementation of the AggregateRoot interface.
 * Provides basic functionality for domain event management.
 */
public abstract class AbstractAggregateRoot implements AggregateRoot {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    @Override
    public void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }
} 