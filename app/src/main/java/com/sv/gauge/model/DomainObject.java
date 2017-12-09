package com.sv.gauge.model;

/**
 * Created by mohan on 3/2/2016.
 */
public class DomainObject {
    private int id;
    private String title;
    private String description;
    private int firstStep;
    private String reportDescription;

    public DomainObject() {
    }

    public DomainObject(int id, String title, String description, int firstStep) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.firstStep = firstStep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(int firstStep) {
        this.firstStep = firstStep;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
}
