package com.progzesp.stalking.persistance.repo.impl;

import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import com.progzesp.stalking.persistance.repo.CustomAnswerRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomAnswerRepoImpl implements CustomAnswerRepo {

    @PersistenceContext
    EntityManager em;

    public List<AnswerEntity> findAnswersByCriteria(Optional<Long> gameId, Optional<Boolean> checked) {
        CriteriaBuilder qb = this.em.getCriteriaBuilder();
        CriteriaQuery<AnswerEntity> cq = qb.createQuery(AnswerEntity.class);
        Root<AnswerEntity> root = cq.from(AnswerEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        gameId.ifPresent(aLong -> predicates.add(qb.equal(root.get("game").get("id"), aLong)));
        checked.ifPresent(s -> predicates.add(qb.equal(root.get("checked"), s)));

        cq.select(root).where(predicates.toArray(new Predicate[]{}));
        return this.em.createQuery(cq).getResultList();
    }
}
