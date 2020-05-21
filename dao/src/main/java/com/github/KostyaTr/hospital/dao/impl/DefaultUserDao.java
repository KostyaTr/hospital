package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.UserDao;
import com.github.KostyaTr.hospital.dao.converter.UserConverter;
import com.github.KostyaTr.hospital.dao.entity.UserEntity;
import com.github.KostyaTr.hospital.model.User;
import org.hibernate.Session;

public class DefaultUserDao implements UserDao {
    private static class SingletonHolder {
        static final UserDao HOLDER_INSTANCE = new DefaultUserDao();
    }

    public static UserDao getInstance() {
        return DefaultUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public Long saveUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();
        return userEntity.getUserId();
    }

    @Override
    public boolean removeUser(Long userId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete UserEntity where id = :id")
                .setParameter("id", userId)
                .executeUpdate();
        session.getTransaction().commit();
        return getUserById(userId) == null;
    }

    @Override
    public User getUserById(Long userId) {
       Session session = HibernateUtil.getSession();
       session.beginTransaction();
        UserEntity userEntity = session.get(UserEntity.class, userId);
       session.getTransaction().commit();
       return UserConverter.fromEntity(userEntity);
    }
}
