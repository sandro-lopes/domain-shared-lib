package com.codingbetter.domain.shared.model;

import java.io.Serializable;

/**
 * Interface que define um identificador no contexto do DDD.
 * Identificadores são usados para identificar entidades de forma única.
 */
public interface Identity extends Serializable {
    
    /**
     * Retorna o valor do identificador.
     * @return O valor do identificador
     */
    Object getValue();
    
    /**
     * Verifica se este identificador é igual a outro identificador.
     * Dois identificadores são considerados iguais se seus valores são iguais.
     *
     * @param other O outro identificador a ser comparado
     * @return true se os identificadores são iguais, false caso contrário
     */
    boolean equals(Object other);
    
    /**
     * Retorna o código hash do identificador, baseado em seu valor.
     * @return O código hash do identificador
     */
    int hashCode();
    
    /**
     * Retorna uma representação em string do identificador.
     * @return A representação em string do identificador
     */
    String toString();
} 