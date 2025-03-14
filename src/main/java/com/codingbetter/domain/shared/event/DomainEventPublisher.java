package com.codingbetter.domain.shared.event;

/**
 * Interface para publicação de eventos de domínio.
 * Responsável por publicar eventos de domínio para que possam ser
 * consumidos por outros componentes do sistema.
 */
public interface DomainEventPublisher {
    
    /**
     * Publica um evento de domínio.
     * @param event O evento de domínio a ser publicado
     */
    void publish(DomainEvent event);
} 