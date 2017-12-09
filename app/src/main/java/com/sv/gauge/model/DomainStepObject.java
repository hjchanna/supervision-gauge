package com.sv.gauge.model;

/**
 * Created by mohan on 3/2/2016.
 */
public class DomainStepObject {
    //visual types
    public static final String VISUAL_TYPE_NONE = "NONE";
    public static final String VISUAL_TYPE_IMAGE = "IMAGE";
    public static final String VISUAL_TYPE_VIDEO = "VIDEO";

    //value types
    public static final String VALUE_TYPE_NONE = "NONE";
    public static final String VALUE_TYPE_BOOLEAN = "BOOLEAN";
    public static final String VALUE_TYPE_DECIMAL = "DECIMAL";
    public static final String VALUE_TYPE_INTEGER = "INTEGER";

    //identification attributes
    private int id;
    private int domainId;
    private String number;
    private String title;
    private String mainGroup;
    private String subGroup;
    //visual type attributes
    private String visualType;
    private String visualLink;
    private String visualDescription;
    //next step attributes
    private int nextStep;
    private boolean showMap;
    private boolean lastStep;
    private String mapFromPreviousLink;
    //skip attributes
    private boolean enableSkip;
    private int skipToStep;
    private String mapFromBeginningLink;
    //value attributes
    private String valueType;
    private String valueHint;
    private String unit;
    private String defaultValue;
    private String defaultFromValue;
    private String defaultToValue;
    //report attributes
    private String reportDescription;

    public DomainStepObject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getVisualType() {
        return visualType;
    }

    public void setVisualType(String visualType) {
        this.visualType = visualType;
    }

    public String getVisualLink() {
        return visualLink;
    }

    public void setVisualLink(String visualLink) {
        this.visualLink = visualLink;
    }

    public String getVisualDescription() {
        return visualDescription;
    }

    public void setVisualDescription(String visualDescription) {
        this.visualDescription = visualDescription;
    }

    public boolean isLastStep() {
        return lastStep;
    }

    public void setLastStep(boolean lastStep) {
        this.lastStep = lastStep;
    }

    public int getNextStep() {
        return nextStep;
    }

    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    public String getMapFromPreviousLink() {
        return mapFromPreviousLink;
    }

    public void setMapFromPreviousLink(String mapFromPreviousLink) {
        this.mapFromPreviousLink = mapFromPreviousLink;
    }

    public boolean isEnableSkip() {
        return enableSkip;
    }

    public void setEnableSkip(boolean enableSkip) {
        this.enableSkip = enableSkip;
    }

    public int getSkipToStep() {
        return skipToStep;
    }

    public void setSkipToStep(int skipToStep) {
        this.skipToStep = skipToStep;
    }

    public String getMapFromBeginningLink() {
        return mapFromBeginningLink;
    }

    public void setMapFromBeginningLink(String mapFromBeginningLink) {
        this.mapFromBeginningLink = mapFromBeginningLink;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueHint() {
        return valueHint;
    }

    public void setValueHint(String valueHint) {
        this.valueHint = valueHint;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultFromValue() {
        return defaultFromValue;
    }

    public void setDefaultFromValue(String defaultFromValue) {
        this.defaultFromValue = defaultFromValue;
    }

    public String getDefaultToValue() {
        return defaultToValue;
    }

    public void setDefaultToValue(String defaultToValue) {
        this.defaultToValue = defaultToValue;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
}
