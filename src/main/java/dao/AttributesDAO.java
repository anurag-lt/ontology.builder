package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;



public class AttributesDAO {

	
	/**
	 * Used in the 'Create Object Type' page for adding new attributes to an object type.
	 */
	public boolean createAttribute(String name, Attributes.DataTypes dataType, boolean isRequired, String defaultValue, boolean isIndexed, int attributeUsageCount, ObjectType fkObjectType) {
	    Connection connection = DatabaseUtility.connect();
	    try {
	        String sql = "INSERT INTO attributes (name, data_type, is_required, default_value, is_indexed, attribute_usage_count, fk_object_type_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	            statement.setString(1, name);
	            statement.setString(2, dataType.name());
	            statement.setBoolean(3, isRequired);
	            statement.setString(4, defaultValue);
	            statement.setBoolean(5, isIndexed);
	            statement.setInt(6, attributeUsageCount);
	            statement.setInt(7, fkObjectType.getId());
	
	            int affectedRows = statement.executeUpdate();
	            return affectedRows > 0;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating attribute", e);
	        return false;
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	}
	
	/*
	 Method to update the details of an existing attribute.
	 Used in the 'Edit Object Type' section for updating attribute details.
	*/
	public boolean updateAttribute(int id, String name, Attributes.DataTypes dataType, boolean isRequired, String defaultValue, boolean isIndexed, int attributeUsageCount) {
	    Connection connection = DatabaseUtility.connect();
	    boolean updateStatus = false;
	    String query = "UPDATE attributes SET name = ?, data_type = ?, is_required = ?, default_value = ?, is_indexed = ?, attribute_usage_count = ? WHERE id = ?;";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, dataType.name());
	        preparedStatement.setBoolean(3, isRequired);
	        preparedStatement.setString(4, defaultValue);
	        preparedStatement.setBoolean(5, isIndexed);
	        preparedStatement.setInt(6, attributeUsageCount);
	        preparedStatement.setInt(7, id);
	        updateStatus = preparedStatement.executeUpdate() > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error updating attribute with ID: " + id, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return updateStatus;
	}
	
	
	/**
	 * Deletes an attribute from an existing object type based on its unique ID.
	 *
	 * @param id The unique identifier of the attribute to be deleted.
	 */
	public boolean deleteAttributeById(int id) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "DELETE FROM attributes WHERE id = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting attribute by ID: " + id, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error closing preparedStatement", e);
	            }
	        }
	    }
	    return isDeleted;
	}
	
	/**
	 * Fetches attributes associated with a specific object type.
	 * Used in both the 'Object Type List' and 'Edit Object Type' pages.
	 * @param fkObjectType The ObjectType to filter attributes by.
	 * @return A list of Attributes associated with the given object type.
	 */
	public ArrayList<Attributes> findAttributesByObjectTypeId(ObjectType fkObjectType) {
	    ArrayList<Attributes> attributesList = new ArrayList<>();
	    String query = "SELECT * FROM attributes WHERE fk_object_type_id = ?;";
	    Connection connection = null;
	    try {
	        connection = DatabaseUtility.connect();
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setInt(1, fkObjectType.getId());
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Attributes attribute = new Attributes();
	            attribute.setId(rs.getInt("id"));
	            attribute.setName(rs.getString("name"));
	            attribute.setDataType(Attributes.DataTypes.valueOf(rs.getString("data_type").toUpperCase()));
	            attribute.setRequired(rs.getBoolean("is_required"));
	            attribute.setDefaultValue(rs.getString("default_value"));
	            attribute.setIndexed(rs.getBoolean("is_indexed"));
	            attribute.setAttributeUsageCount(rs.getInt("attribute_usage_count"));
	            attribute.setFkObjectType(fkObjectType);
	            attributesList.add(attribute);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return attributesList;
	}
}
