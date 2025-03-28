package com.codingbetter.domain.shared.model;


/**
 * Interface que define um identificador no contexto do DDD.
 * Identificadores são usados para identificar entidades de forma única.
 */
public interface Identity extends ValueObject {
    
    /**
     * Retorna o valor do identificador.
     * @return O valor do identificador
     */
    Object getValue();
} 