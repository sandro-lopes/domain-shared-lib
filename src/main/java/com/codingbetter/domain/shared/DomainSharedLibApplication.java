package com.codingbetter.domain.shared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * Esta classe é necessária para que o Spring Boot possa inicializar o contexto da aplicação
 * e configurar os componentes necessários.
 */
@SpringBootApplication
public class DomainSharedLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainSharedLibApplication.class, args);
    }
} 