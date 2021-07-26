package com.hallye.application.contribution;

import com.google.inject.Inject;
import com.hallye.application.contribution.model.Contribution;
import com.hallye.application.person.PersonRepository;
import com.hallye.application.person.model.Person;
import com.hallye.application.workflow.WorkflowRepository;
import com.hallye.application.workflow.model.Workflow;
import com.hallye.application.workflow.model.WorkflowStep;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ContributionRepository {

    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final WorkflowRepository workflowRepository;

    @Inject
    public ContributionRepository(EntityManager entityManager, PersonRepository personRepository, WorkflowRepository workflowRepository) {
        this.entityManager = entityManager;
        this.personRepository = personRepository;
        this.workflowRepository = workflowRepository;
    }

    public List<Contribution> loadContributionsForWorkflow(String workflowId, String workflowType, String personId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contribution> cq = cb.createQuery(Contribution.class);
        Root<Contribution> rootEntry = cq.from(Contribution.class);
        CriteriaQuery<Contribution> all = cq.select(rootEntry);

        TypedQuery<Contribution> allQuery = entityManager.createQuery(all);
        var resultList = allQuery.getResultList();

        return resultList.stream()
                .filter(contribution -> {

                    if (!contribution.getWorkflow().getId().toString().equals(workflowId))
                        return false;

                    if (workflowType != null)
                        if (!contribution.getWorkflow().getWorkflowType().name().equalsIgnoreCase(workflowType))
                            return false;

                    if (personId != null) {
                        return contribution.getPerson().getId().toString().equals(personId);
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    public Contribution createNewContribution(String workflowId, WorkflowStep workflowStep, String personId, LocalDateTime startTime) {

        var person = personRepository.loadPersonById(personId);

        if (person == null)
            throw new NotFoundException("Person not found");

        var workflow = workflowRepository.loadWorkflow(workflowId);

        if (workflow == null)
            throw new NotFoundException("Workflow not found");

        entityManager.getTransaction().begin();

        var contribution = Contribution.builder()
                .workflowStep(workflowStep)
                .startTime(startTime)
                .person(person)
                .workflow(workflow)
                .build();

        entityManager.persist(contribution);

        entityManager.getTransaction().commit();

        return contribution;
    }
}
