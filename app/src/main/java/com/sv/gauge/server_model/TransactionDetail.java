package com.sv.gauge.server_model;
// Generated Mar 25, 2016 2:28:59 PM by Hibernate Tools 4.3.1


/**
 * TransactionDetail generated by hbm2java
 */
public class TransactionDetail  implements java.io.Serializable {


     private Integer id;
     private int transaction;
     private String mainGroup;
     private String subGroup;
     private int domainStep;
     private String number;
     private String description;
     private String value;
     private String unit;
     private String defaultValue;
     private String comment;
     private int warning;
     private String time;

    public TransactionDetail() {
    }

	
    public TransactionDetail(int transaction, int domainStep, String description, String value, int warning) {
        this.transaction = transaction;
        this.domainStep = domainStep;
        this.description = description;
        this.value = value;
        this.warning = warning;
    }
    public TransactionDetail(int transaction, String mainGroup, String subGroup, int domainStep, String number, String description, String value, String unit, String defaultValue, String comment, int warning, String time) {
       this.transaction = transaction;
       this.mainGroup = mainGroup;
       this.subGroup = subGroup;
       this.domainStep = domainStep;
       this.number = number;
       this.description = description;
       this.value = value;
       this.unit = unit;
       this.defaultValue = defaultValue;
       this.comment = comment;
       this.warning = warning;
       this.time = time;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public int getTransaction() {
        return this.transaction;
    }
    
    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public String getMainGroup() {
        return this.mainGroup;
    }
    
    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public String getSubGroup() {
        return this.subGroup;
    }
    
    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public int getDomainStep() {
        return this.domainStep;
    }
    
    public void setDomainStep(int domainStep) {
        this.domainStep = domainStep;
    }

    public String getNumber() {
        return this.number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getWarning() {
        return this.warning;
    }
    
    public void setWarning(int warning) {
        this.warning = warning;
    }

    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
}


