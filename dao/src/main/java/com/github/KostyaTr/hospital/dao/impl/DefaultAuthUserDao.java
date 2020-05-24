package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
import com.github.KostyaTr.hospital.dao.converter.AuthUserConverter;
import com.github.KostyaTr.hospital.dao.entity.AuthUserEntity;
import com.github.KostyaTr.hospital.model.AuthUser;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class DefaultAuthUserDao implements AuthUserDao {

    private static class SingletonHolder{
        final static AuthUserDao INSTANCE_HOLDER = new DefaultAuthUserDao();
    }

    public static AuthUserDao getInstance(){return DefaultAuthUserDao.SingletonHolder.INSTANCE_HOLDER;}


    @Override
    public AuthUser getByLogin(String login) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        AuthUserEntity authUserEntity;
        try {
            authUserEntity = session.createQuery("select au from AuthUserEntity au where login = :login", AuthUserEntity.class)
                    .setParameter("login", login).getSingleResult();
        } catch (NoResultException e){
            authUserEntity = null;
        }
        session.getTransaction().commit();
        final AuthUser authUser = AuthUserConverter.fromEntity(authUserEntity);
        session.close();
        return authUser;
    }

    @Override
    public Long saveAuthUser(AuthUser authUser) {
        AuthUserEntity authUserEntity = AuthUserConverter.toEntity(authUser);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(authUserEntity);
        session.getTransaction().commit();
        return authUserEntity.getAuthUserId();
    }
}
