package com.gmail.sasha.myproject.dao.dao;

import org.hibernate.Session;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends Serializable> {

    T findOne(final long entityId);

    List<T> findAll();

    void create(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);

    Session getCurrentSession();
}
