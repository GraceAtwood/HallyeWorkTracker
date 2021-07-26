package com.hallye.application.person;

import com.google.inject.Inject;
import com.hallye.application.person.model.Person;
import com.hallye.application.workflow.model.Workflow;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

public class PersonRepository {

    private final EntityManager entityManager;

    @Inject
    public PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Person loadPersonById(String id) {
        return entityManager.find(Person.class, UUID.fromString(id));
    }

    public List<Person> loadPersons() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> rootEntry = cq.from(Person.class);
        CriteriaQuery<Person> all = cq.select(rootEntry);

        TypedQuery<Person> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public Person createNewPerson(String name) {
        var person = Person.builder().name(name).build();

        entityManager.getTransaction().begin();

        entityManager.persist(person);

        entityManager.getTransaction().commit();

        return person;
    }
}
