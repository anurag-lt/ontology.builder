package model;

import java.math.BigDecimal;

/**
 * This class represents the Facilities entity as per the given database schema. 
 * It encapsulates details about facilities including their location, size, type, and operational status.
 */
public class Facilities {
    
    private int id;
    private String name;
    private String location;
    private BigDecimal sizeInSquareFootage;
    private FacilityType facilityType;
    private OperationalStatus operationalStatus;

    public Facilities() {
        // Default constructor
    }

    // Enum for facility types
    public enum FacilityType {
        MANUFACTURING_PLANT, WAREHOUSE, R_D_CENTER
    }

    // Enum for operational statuses
    public enum OperationalStatus {
        ACTIVE, INACTIVE, UNDER_CONSTRUCTION
    }

    // Getters and Setters

    /**
     * Gets the unique identifier for each facility record.
     * @return the id of the facility
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for each facility record.
     * @param id the id to set for the facility
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the facility.
     * @return the name of the facility
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the facility.
     * @param name the name to set for the facility
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the location details of the facility including geographical coordinates or address.
     * @return the location of the facility
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location details of the facility.
     * @param location the location to set for the facility
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the size of the facility in square footage.
     * @return the size of the facility
     */
    public BigDecimal getSizeInSquareFootage() {
        return sizeInSquareFootage;
    }

    /**
     * Sets the size of the facility in square footage.
     * @param sizeInSquareFootage the size to set for the facility
     */
    public void setSizeInSquareFootage(BigDecimal sizeInSquareFootage) {
        this.sizeInSquareFootage = sizeInSquareFootage;
    }

    /**
     * Gets the type of the facility.
     * @return the facilityType
     */
    public FacilityType getFacilityType() {
        return facilityType;
    }

    /**
     * Sets the type of the facility.
     * @param facilityType the facilityType to set
     */
    public void setFacilityType(FacilityType facilityType) {
        this.facilityType = facilityType;
    }

    /**
     * Gets the operational status of the facility.
     * @return the operationalStatus
     */
    public OperationalStatus getOperationalStatus() {
        return operationalStatus;
    }

    /**
     * Sets the operational status of the facility.
     * @param operationalStatus the operationalStatus to set
     */
    public void setOperationalStatus(OperationalStatus operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    @Override
    public String toString() {
        return "Facilities{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", sizeInSquareFootage=" + sizeInSquareFootage +
                ", facilityType=" + facilityType +
                ", operationalStatus=" + operationalStatus +
                '}';
    }
}