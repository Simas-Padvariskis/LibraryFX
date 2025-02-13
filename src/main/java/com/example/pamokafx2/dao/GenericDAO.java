package com.example.pamokafx2.dao;

import java.util.List;

public interface GenericDAO<T> {
    /**
     * Find entity by ID
     */
    T findById(int id);

    /**
     * Create a new entity
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param city
     */

    void create(String firstName, String lastName, String email, String city);

    /**
     * Udate entity
     * @param entity
     */

    void update(T entity);

    /**
     * Delete an entity
     *
     * @param id
     */

    void delete(int id);

    /**
     * Find all entities by type
     */

    List<T> findAll();
}
