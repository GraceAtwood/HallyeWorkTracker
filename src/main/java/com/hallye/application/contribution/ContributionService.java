package com.hallye.application.contribution;

import com.google.inject.Inject;
import com.hallye.application.contribution.model.Contribution;
import com.hallye.application.workflow.model.WorkflowStep;
import org.apache.commons.lang3.NotImplementedException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class ContributionService {

    private final ContributionRepository contributionRepository;

    @Inject
    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    public List<Contribution> getWorkflowContributions(String workflowId, String workflowType, String personId) {
        return contributionRepository.loadContributionsForWorkflow(workflowId, workflowType, personId);
    }

    public Contribution createNewContribution(String workflowId, WorkflowStep workflowStep, String personId, LocalDateTime startTime) {
        return contributionRepository.createNewContribution(workflowId, workflowStep, personId, startTime);
    }
}
