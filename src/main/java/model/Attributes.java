package model;

import java.util.Objects;

/**
 * Represents the attributes associated with object types in a dynamic data model.
 * This class encapsulates various properties of attributes including their data type,
 * requirement status, indexing status, usage count, and respective object type.
 */
public class Attributes {
    private int id;
    private ObjectType objectTypeId;
    private String name;
    private DataTypes dataType;
    private boolean isRequired;
    private String defaultValue;
    private boolean isIndexed;
    private int attributeUsageCount;
    private ObjectType fkObjectType;

    // Enum for data types
    public enum DataTypes {
        INTEGER,
        FLOAT,
        VARCHAR,
        TEXT,
        DATE,
        DATETIME,
        ENUM
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjectType getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(ObjectType objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataTypes getDataType() {
        return dataType;
    }

    public void setDataType(DataTypes dataType) {
        this.dataType = dataType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public int getAttributeUsageCount() {
        return attributeUsageCount;
    }

    public void setAttributeUsageCount(int attributeUsageCount) {
        this.attributeUsageCount = attributeUsageCount;
    }

    public ObjectType getFkObjectType() {
        return fkObjectType;
    }

    public void setFkObjectType(ObjectType fkObjectType) {
        this.fkObjectType = fkObjectType;
    }

    // toString method
    @Override
    public String toString() {
        return "Attributes{" +
                "id=" + id +
                ", objectTypeId=" + objectTypeId +
                ", name='" + name + '\'' +
                ", dataType=" + dataType +
                ", isRequired=" + isRequired +
                ", defaultValue='" + defaultValue + '\'' +
                ", isIndexed=" + isIndexed +
                ", attributeUsageCount=" + attributeUsageCount +
                ", fkObjectType=" + fkObjectType +
                '}';
    }

    // Equals and hashCode based on 'id' for unique identification
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attributes)) return false;
        Attributes that = (Attributes) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}