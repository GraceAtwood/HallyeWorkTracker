package com.hallye.application.workflow.model;

import lombok.Getter;

public enum WorkflowStep {

    CLEANING(false),
    DRYING(true),
    LOADING(false),
    WEIGHING(false),
    PACKAGING(false),
    COUNTING(false),
    STICKERING(false),
    LABELING(true),
    BOXING(false);

    @Getter
    private boolean isOptional;

    WorkflowStep(boolean isOptional) {

        this.isOptional = isOptional;
    }
}
