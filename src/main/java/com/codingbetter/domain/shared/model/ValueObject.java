package com.codingbetter.domain.shared.model;

import java.io.Serializable;

/**
 * Interface que define um objeto de valor no contexto do DDD.
 * Objetos de valor são imutáveis e são definidos por seus atributos,
 * não por sua identidade.
 */
public interface ValueObject extends Serializable {
    
    /**
     * Verifica se este objeto de valor é igual a outro objeto de valor.
     * Dois objetos de valor são considerados iguais se todos os seus atributos são iguais.
     *
     * @param other O outro objeto de valor a ser comparado
     * @return true se os objetos de valor são iguais, false caso contrário
     */
    boolean equals(Object other);
    
    /**
     * Retorna o código hash do objeto de valor, baseado em seus atributos.
     * @return O código hash do objeto de valor
     */
    int hashCode();
    
    /**
     * Retorna uma representação em string do objeto de valor.
     * @return A representação em string do objeto de valor
     */
    String toString();
} 