package com.cuatico.campus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cuatico.campus.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
