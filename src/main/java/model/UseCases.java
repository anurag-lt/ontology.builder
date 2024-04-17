package model;

import java.util.Objects;

/**
 * Represents the use cases for facilities, encapsulating various operational or project-based endeavors within the organization.
 */
public class UseCases {
    /**
     * Unique identifier for the use case.
     */
    private int id;
    
    /**
     * Title of the use case, indicating its purpose or theme.
     */
    private String title;
    
    /**
     * Detailed explanation of the use case, outlining its objectives, scope, and relevant details.
     */
    private String description;
    
    /**
     * Objectives intended to be achieved through this use case.
     */
    private String objective;
    
    /**
     * The current operational status of the use case, categorizing its lifecycle stage.
     */
    private OperationalStatuses operationalStatus;
    
    /**
     * The facility associated with the use case, linking it to a specific operational context.
     */
    private Facilities fkFacility;

    // Enum for operational statuses
    public enum OperationalStatuses {
        ACTIVE, INACTIVE, UNDER_CONSTRUCTION
    }

    public UseCases() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getObjective() {
        return objective;
    }

    public OperationalStatuses getOperationalStatus() {
        return operationalStatus;
    }

    public Facilities getFkFacility() {
        return fkFacility;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setOperationalStatus(OperationalStatuses operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public void setFkFacility(Facilities fkFacility) {
        this.fkFacility = fkFacility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UseCases)) return false;
        UseCases useCases = (UseCases) o;
        return getId() == useCases.getId() && getTitle().equals(useCases.getTitle()) && Objects.equals(getDescription(), useCases.getDescription()) && Objects.equals(getObjective(), useCases.getObjective()) && getOperationalStatus() == useCases.getOperationalStatus() && getFkFacility().equals(useCases.getFkFacility());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getObjective(), getOperationalStatus(), getFkFacility());
    }

    @Override
    public String toString() {
        return "UseCases{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", objective='" + objective + '\'' +
                ", operationalStatus=" + operationalStatus +
                ", fkFacility=" + fkFacility +
                '}';
    }
}