package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.converter.AuthUserConverter;
import com.github.KostyaTr.hospital.dao.entity.AuthUserEntity;
import com.github.KostyaTr.hospital.model.AuthUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;

public class DefaultAuthUserDao implements AuthUserDao {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public AuthUser getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        AuthUserEntity authUserEntity;
        try {
            authUserEntity = session.createQuery("select au from AuthUserEntity au where login = :login", AuthUserEntity.class)
                    .setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            authUserEntity = null;
        }
        return AuthUserConverter.fromEntity(authUserEntity);
    }

    @Override
    public Long saveAuthUser(AuthUser authUser) {
        AuthUserEntity authUserEntity = AuthUserConverter.toEntity(authUser);
        Session session = sessionFactory.getCurrentSession();
        session.save(authUserEntity);
        return authUserEntity.getAuthUserId();
    }
}
