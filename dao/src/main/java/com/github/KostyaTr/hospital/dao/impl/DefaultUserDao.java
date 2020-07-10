package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.converter.UserConverter;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long saveUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        Session session = sessionFactory.getCurrentSession();
        session.save(userEntity);
        return userEntity.getUserId();
    }

    @Override
    public boolean removeUser(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete UserEntity where id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        session.clear();
        return getUserById(userId) == null;
    }

    @Override
    public User getUserById(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity userEntity = session.get(UserEntity.class, userId);
        return UserConverter.fromEntity(userEntity);
    }
}
