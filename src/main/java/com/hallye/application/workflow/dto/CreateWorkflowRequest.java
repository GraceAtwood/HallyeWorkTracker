package com.hallye.application.workflow.dto;

import com.hallye.application.workflow.model.WorkflowType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.FormParam;

@Data
public class CreateWorkflowRequest {

    private String packageNumber;

    private String strain;

    private WorkflowType workflowType;
}
