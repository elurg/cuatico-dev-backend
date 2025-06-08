package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.cuatico.campus.entities.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Long> {
    T findByEmail(String email);
}

//Por ahora es un poco inútil porque al usar los EntityGraph, cada repositorio específico tiene que manejar
//la gestión de las relaciones concretas. Pero en el futuro puede servir para agrupar métodos más secillos
// que comunes a todos los users