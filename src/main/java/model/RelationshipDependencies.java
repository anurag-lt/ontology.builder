package model;

import java.sql.Timestamp;

/**
 * This class represents the relationship dependencies within the system, tracking specific dependencies of relationships defined between object types.
 */
public class RelationshipDependencies {

    private int id;
    private String dependencyName;
    private DependencyType dependencyType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Relationships relationship;

    /**
     * Gets the unique identifier for each record in the relationship_dependencies table.
     * 
     * @return the unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for each record in the relationship_dependencies table.
     * 
     * @param id the unique identifier to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name or identifier of the dependent entity or attribute.
     * 
     * @return the name or identifier of the dependent entity or attribute.
     */
    public String getDependencyName() {
        return dependencyName;
    }

    /**
     * Sets the name or identifier of the dependent entity or attribute.
     * 
     * @param dependencyName the name or identifier to set.
     */
    public void setDependencyName(String dependencyName) {
        this.dependencyName = dependencyName;
    }

    /**
     * Gets the type of the dependency.
     * 
     * @return the dependency type.
     */
    public DependencyType getDependencyType() {
        return dependencyType;
    }

    /**
     * Sets the type of the dependency.
     * 
     * @param dependencyType the dependency type to set.
     */
    public void setDependencyType(DependencyType dependencyType) {
        this.dependencyType = dependencyType;
    }

    /**
     * Gets the timestamp when the dependency record was created.
     * 
     * @return the creation timestamp.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the dependency record was created.
     * 
     * @param createdAt the creation timestamp to set.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp of the latest update made to the dependency record.
     * 
     * @return the update timestamp.
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp of the latest update made to the dependency record.
     * 
     * @param updatedAt the update timestamp to set.
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the relationship this dependency is associated with.
     * 
     * @return the relationship.
     */
    public Relationships getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship this dependency is associated with.
     * 
     * @param relationship the relationship to set.
     */
    public void setRelationship(Relationships relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "RelationshipDependencies{" +
                "id=" + id +
                ", dependencyName='" + dependencyName + '\'' +
                ", dependencyType=" + dependencyType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", relationship=" + relationship +
                '}';
    }

    /**
     * Enum for dependency types.
     */
    public enum DependencyType {
        OBJECT_TYPE, ATTRIBUTE, RELATIONSHIP
    }
}