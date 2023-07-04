package maryam.data.visit;

import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository @Transactional
public class CustomVisitRepositoryImpl implements CustomVisitRepository{
    EntityManager entityManager;
    public CustomVisitRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Visit> getVisitsGroupByUserAndOrderByCreateAt(User user) {
        return entityManager.createQuery("select w from Visit w group by w.user order by w.createdAt",Visit.class).getResultList();
    }
}
