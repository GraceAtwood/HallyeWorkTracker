package com.hallye.application.workflow.dto;

import com.hallye.application.workflow.model.WorkflowStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateContributionRequest {

    private String personId;
    private WorkflowStep workflowStep;
}
