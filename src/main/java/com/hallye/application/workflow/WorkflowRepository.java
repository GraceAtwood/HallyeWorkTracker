package com.hallye.application.workflow;

import com.google.inject.Inject;
import com.hallye.application.person.model.Person;
import com.hallye.application.workflow.model.Workflow;
import com.hallye.application.workflow.model.WorkflowType;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

public class WorkflowRepository {

    private final EntityManager entityManager;

    @Inject
    public WorkflowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Workflow> loadAllWorkflows() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Workflow> cq = cb.createQuery(Workflow.class);
        Root<Workflow> rootEntry = cq.from(Workflow.class);
        CriteriaQuery<Workflow> all = cq.select(rootEntry);

        TypedQuery<Workflow> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public Workflow loadWorkflow(String workflowId) {
        return entityManager.find(Workflow.class, UUID.fromString(workflowId));

    }

    public Workflow insertWorkflow(String packageNumber, String strain, WorkflowType workflowType) {
        entityManager.getTransaction().begin();

        var workflow = Workflow.builder()
                .packageNumber(packageNumber)
                .strain(strain)
                .workflowType(workflowType)
                .build();

        entityManager.persist(workflow);

        entityManager.getTransaction().commit();

        return workflow;
    }

    public void deleteWorkflow(String workflowId) {
        throw new NotImplementedException();
    }

    public Workflow updateWorkflow(Double startingHumidity, Boolean isComplete, Double startWeight) {
        throw new NotImplementedException();
    }
}
