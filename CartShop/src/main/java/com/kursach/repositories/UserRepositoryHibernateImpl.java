package com.kursach.repositories;

import com.kursach.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class UserRepositoryHibernateImpl implements UserRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void create(User user) {
        Query query = em.createNativeQuery("insert into user_table(name, surname, vk_id, photo_url ) values (:name, :surname, :id, :photo_url);");
        query.setParameter("name", user.getName());
        query.setParameter("surname", user.getSurname());
        query.setParameter("id", user.getVkId());
        query.setParameter("photo_url", user.getPhotoURL());
        query.executeUpdate();
    }

    @Override
    public User readOne(Integer id) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =:id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
