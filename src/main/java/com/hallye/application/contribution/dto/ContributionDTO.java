package com.hallye.application.contribution.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hallye.application.person.model.Person;
import com.hallye.application.workflow.model.WorkflowStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDTO {

    private UUID id;
    private Person person;
    private WorkflowStep workflowStep;
    private String workflowId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
