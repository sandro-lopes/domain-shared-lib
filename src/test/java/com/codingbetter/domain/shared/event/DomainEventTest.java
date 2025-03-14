package com.codingbetter.domain.shared.event;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {

    @Test
    void domainEventShouldHaveIdAndOccurredOn() {
        // Given
        UUID id = UUID.randomUUID();
        LocalDateTime occurredOn = LocalDateTime.now();
        
        // When
        DomainEvent event = new TestDomainEvent(id, occurredOn);
        
        // Then
        assertEquals(id, event.getId());
        assertEquals(occurredOn, event.getOccurredOn());
    }
    
    // Test implementation for DomainEvent
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