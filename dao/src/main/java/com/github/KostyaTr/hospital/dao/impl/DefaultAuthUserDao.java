package com.github.KostyaTr.hospital.dao.impl;

import com.github.KostyaTr.hospital.dao.AuthUserDao;
import com.github.KostyaTr.hospital.dao.HibernateUtil;
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
        AuthUser authUser;
        try {
            authUser = session.createQuery("select au from AuthUser au where login = :login", AuthUser.class)
                    .setParameter("login", login).getSingleResult();
        } catch (NoResultException e){
            authUser = null;
        }
        session.getTransaction().commit();
        return authUser;
    }

    @Override
    public Long saveAuthUser(AuthUser user) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        return user.getAuthUserId();
    }
}
