package model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents the object types entity, defining various types of objects within the system.
 */
public class ObjectTypes {
    private int id;
    private String name;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Attributes> attributesList;

    public ObjectTypes() {
    }

    /**
     * Gets the unique identifier of the object type.
     *
     * @return An integer representing the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the object type.
     *
     * @param id An integer containing the id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the object type.
     *
     * @return A string representing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the object type.
     *
     * @param name A string containing the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the object type.
     *
     * @return A string representing the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the object type.
     *
     * @param description A string containing the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation timestamp of the object type record.
     *
     * @return A Timestamp representing the creation time.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the object type record.
     *
     * @param createdAt A Timestamp containing the creation time.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the update timestamp of the object type record.
     *
     * @return A Timestamp representing the update time.
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the update timestamp of the object type record.
     *
     * @param updatedAt A Timestamp containing the update time.
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the list of attributes associated with the object type.
     *
     * @return A List of Attributes objects.
     */
    public List<Attributes> getAttributesList() {
        return attributesList;
    }

    /**
     * Sets the list of attributes associated with the object type.
     *
     * @param attributesList A List containing Attributes objects.
     */
    public void setAttributesList(List<Attributes> attributesList) {
        this.attributesList = attributesList;
    }

    @Override
    public String toString() {
        return "ObjectTypes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", attributesList=" + attributesList +
                '}';
    }
}