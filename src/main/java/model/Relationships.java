package model;

import java.util.Objects;

/**
 * Represents the relationships between various object types in the system.
 * Stores data such as the relationship's name, its type, a detailed description, and additional attributes.
 */
public class Relationships {

    // Enums for relationship types
    public enum RelationshipTypes {
        one_to_one, one_to_many, many_to_many
    }

    private int id;
    private String relationshipName;
    private RelationshipTypes relationshipType;
    private String relationshipDescription;
    private String attributesJson;
    private int sortOrder;
    private RelationshipTypes filterByType;
    // Assuming filterByObjectTypeID correlates with ObjectTypes entity
    private ObjectTypes filterByObjectType;

    // No args constructor
    public Relationships() {}

    // All args constructor
    public Relationships(int id, String relationshipName, RelationshipTypes relationshipType, String relationshipDescription, String attributesJson, int sortOrder, RelationshipTypes filterByType, ObjectTypes filterByObjectType) {
        this.id = id;
        this.relationshipName = relationshipName;
        this.relationshipType = relationshipType;
        this.relationshipDescription = relationshipDescription;
        this.attributesJson = attributesJson;
        this.sortOrder = sortOrder;
        this.filterByType = filterByType;
        this.filterByObjectType = filterByObjectType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public RelationshipTypes getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipTypes relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRelationshipDescription() {
        return relationshipDescription;
    }

    public void setRelationshipDescription(String relationshipDescription) {
        this.relationshipDescription = relationshipDescription;
    }

    public String getAttributesJson() {
        return attributesJson;
    }

    public void setAttributesJson(String attributesJson) {
        this.attributesJson = attributesJson;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public RelationshipTypes getFilterByType() {
        return filterByType;
    }

    public void setFilterByType(RelationshipTypes filterByType) {
        this.filterByType = filterByType;
    }

    public ObjectTypes getFilterByObjectType() {
        return filterByObjectType;
    }

    public void setFilterByObjectType(ObjectTypes filterByObjectType) {
        this.filterByObjectType = filterByObjectType;
    }

    // toString method
    @Override
    public String toString() {
        return "Relationships{" +
                "id=" + id +
                ", relationshipName='" + relationshipName + '\'' +
                ", relationshipType=" + relationshipType +
                ", relationshipDescription='" + relationshipDescription + '\'' +
                ", attributesJson='" + attributesJson + '\'' +
                ", sortOrder=" + sortOrder +
                ", filterByType=" + filterByType +
                ", filterByObjectType=" + filterByObjectType +
                '}';
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationships that = (Relationships) o;
        return id == that.id &&
                sortOrder == that.sortOrder &&
                Objects.equals(relationshipName, that.relationshipName) &&
                relationshipType == that.relationshipType &&
                Objects.equals(relationshipDescription, that.relationshipDescription) &&
                Objects.equals(attributesJson, that.attributesJson) &&
                filterByType == that.filterByType &&
                Objects.equals(filterByObjectType, that.filterByObjectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, relationshipName, relationshipType, relationshipDescription, attributesJson, sortOrder, filterByType, filterByObjectType);
    }
}