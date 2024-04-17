package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ObjectTypesDAO {

	
	/**
	 * Creates a new object type in the system with the specified name, description, and a list of attributes.
	 * @param name the name of the object type.
	 * @param description a detailed explanation of what the object type is used for.
	 * @param attributes a list of attributes associated with the object type, each including details such as name, type, required status, default value, and constraints.
	 * @return The id of the newly created object type.
	 */
	public int createObjectType(String name, String description, List<Attributes> attributes) {
	   Connection connection = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   int objectId = 0;
	   try {
	       connection = DatabaseUtility.connect();
	       String sql = "INSERT INTO object_types (name, description) VALUES (?, ?) RETURNING id;";
	       pstmt = connection.prepareStatement(sql);
	       pstmt.setString(1, name);
	       pstmt.setString(2, description);
	       rs = pstmt.executeQuery();
	       if (rs.next()) {
	           objectId = rs.getInt(1);
	           // Process attributes here if needed, you may need additional methods to handle this.
	       }
	   } catch (SQLException e) {
	       Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	   } finally {
	       DatabaseUtility.disconnect(connection);
	       try {
	         if (rs != null) { rs.close(); }
	         if (pstmt != null) { pstmt.close(); }
	       } catch (SQLException e) {
	           Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	       }
	   }
	   return objectId;
	}
	
	/**
	 * Updates an existing object type's details including its name, description, and attributes based on the provided id.
	 * Used in 'Edit Object Type Form' for updating an existing object type.
	 *
	 * @param id         the unique identifier of the object type to be updated
	 * @param name       the new name of the object type
	 * @param description a new description for the object type
	 * @param attributes a list of updated or new attributes for the object type
	 * @return boolean   returns true if the update was successful, otherwise false
	 */
	public boolean updateObjectTypeById(Integer id, String name, String description, List<Attributes> attributes) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean updateSuccess = false;
	
	    try {
	        connection = DatabaseUtility.connect();
	        // Start transaction block
	        connection.setAutoCommit(false);
	
	        // Update the object_types table
	        String updateObjectTypeQuery = "UPDATE object_types SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
	        pstmt = connection.prepareStatement(updateObjectTypeQuery);
	        
	        pstmt.setString(1, name);
	        pstmt.setString(2, description);
	        pstmt.setInt(3, id);
	
	        int rowAffected = pstmt.executeUpdate();
	        if (rowAffected == 1) {
	            // If object type is updated successfully, update its attributes
	            AttributesDao attributesDao = new AttributesDao();
	            boolean attributesUpdated = attributesDao.updateAttributesByObjectTypeId(id, attributes, connection);
	
	            if (attributesUpdated) {
	                // If both object type and attributes are successfully updated, commit the changes
	                connection.commit();
	                updateSuccess = true;
	            } else {
	                // If attributes update fails, rollback transaction
	                connection.rollback();
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	        try {
	            if (connection != null) {
	                connection.rollback(); // Rollback connection on errors
	            }
	        } catch (SQLException se) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, se);
	        }
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return updateSuccess;
	}
	
	/**
	 * Deletes an object type from the system identified by its id.
	 * @param id the unique identifier of the object type to be deleted
	 * @return true if the deletion was successful, otherwise false.
	 */
	public boolean deleteObjectTypeById(Integer id) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM object_types WHERE id = ?;";
	    try {
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	        DatabaseUtility.disconnect(connection);
	    }
	    return isDeleted;
	}
	
	/**
	 * Fetches a list of all object types present in the system.
	 * Used in 'Object Type List Table' to fetch a list for display and in 'Define Relationship Form' for dropdown population.
	 * @return A List of ObjectTypes containing all object types in the system.
	 */
	public List<ObjectTypes> getAllObjectTypes() {
	    List<ObjectTypes> objectTypes = new ArrayList<>();
	    String sql = "SELECT id, name, description, created_at, updated_at FROM object_types";
	    Connection connection = DatabaseUtility.connect();
	    try (PreparedStatement statement = connection.prepareStatement(sql);) {
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            ObjectTypes objectType = new ObjectTypes();
	            objectType.setId(resultSet.getInt("id"));
	            objectType.setName(resultSet.getString("name"));
	            objectType.setDescription(resultSet.getString("description"));
	            objectType.setCreatedAt(resultSet.getTimestamp("created_at"));
	            objectType.setUpdatedAt(resultSet.getTimestamp("updated_at"));
	            objectTypes.add(objectType);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return objectTypes;
	}
}
