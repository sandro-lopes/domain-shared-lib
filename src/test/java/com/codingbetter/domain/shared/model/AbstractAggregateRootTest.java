package com.codingbetter.domain.shared.model;

import com.codingbetter.domain.shared.event.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAggregateRootTest {

    private TestAggregateRoot aggregateRoot;

    @BeforeEach
    void setUp() {
        aggregateRoot = new TestAggregateRoot();
    }

    @Test
    void shouldAddDomainEvent() {
        // Given
        TestDomainEvent event = new TestDomainEvent(UUID.randomUUID(), LocalDateTime.now());

        // When
        aggregateRoot.addDomainEvent(event);

        // Then
        List<DomainEvent> events = aggregateRoot.getDomainEvents();
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }

    @Test
    void shouldClearDomainEvents() {
        // Given
        TestDomainEvent event1 = new TestDomainEvent(UUID.randomUUID(), LocalDateTime.now());
        TestDomainEvent event2 = new TestDomainEvent(UUID.randomUUID(), LocalDateTime.now());
        aggregateRoot.addDomainEvent(event1);
        aggregateRoot.addDomainEvent(event2);

        // When
        aggregateRoot.clearDomainEvents();

        // Then
        List<DomainEvent> events = aggregateRoot.getDomainEvents();
        assertTrue(events.isEmpty());
    }

    @Test
    void shouldReturnUnmodifiableList() {
        // Given
        TestDomainEvent event = new TestDomainEvent(UUID.randomUUID(), LocalDateTime.now());
        aggregateRoot.addDomainEvent(event);

        // When
        List<DomainEvent> events = aggregateRoot.getDomainEvents();

        // Then
        assertThrows(UnsupportedOperationException.class, () -> events.add(event));
    }

    // Helper classes for testing
    private static class TestAggregateRoot extends AbstractAggregateRoot {
        // Empty implementation for testing
    }

    private static class TestDomainEvent implements DomainEvent {
        private final UUID id;
        private final LocalDateTime occurredOn;

        public TestDomainEvent(UUID id, LocalDateTime occurredOn) {
            this.id = id;
            this.occurredOn = occurredOn;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public LocalDateTime getOccurredOn() {
            return occurredOn;
        }
    }
} 