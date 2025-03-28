package com.codingbetter.domain.shared.model;

/**
 * Interface que define uma entidade no contexto do DDD.
 * Entidades são objetos que têm uma identidade única e são definidos por sua continuidade
 * e identidade, não por seus atributos.
 *
 * @param <ID> O tipo do identificador da entidade
 */
public interface Entity<ID extends Identity> {
    
    /**
     * Retorna o identificador único da entidade.
     * @return O identificador da entidade
     */
    ID getId();
    
    /**
     * Verifica se esta entidade é igual a outra entidade.
     * Duas entidades são consideradas iguais se seus identificadores são iguais.
     *
     * @param other A outra entidade a ser comparada
     * @return true se as entidades são iguais, false caso contrário
     */
    boolean equals(Object other);
    
    /**
     * Retorna o código hash da entidade, baseado em seu identificador.
     * @return O código hash da entidade
     */
    int hashCode();
} 