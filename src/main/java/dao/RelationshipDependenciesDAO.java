package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.util.ArrayList;
import java.util.List;


public class RelationshipDependenciesDAO {

	
	/**
	 * Creates a new relationship dependency in the system. This method is crucial for tracking specific dependencies when defining new relationships between object types.
	 * @param dependencyName The name or identifier of the dependent entity or attribute.
	 * @param dependencyType The type of the dependency (object_type, attribute, relationship).
	 * @param relationshipId The unique identifier of the relationship this dependency is associated with.
	 * @return The created RelationshipDependencies object, or null if creation failed.
	 */
	public RelationshipDependencies createRelationshipDependency(String dependencyName, RelationshipDependencies.DependencyType dependencyType, int relationshipId) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement preparedStatement = null;
	    ResultSet generatedKeys = null;
	    RelationshipDependencies relationshipDependency = null;
	
	    String sql = "INSERT INTO relationship_dependencies (dependency_name, dependency_type, relationship_id) VALUES (?, ?, ?)";
	    try {
	        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, dependencyName);
	        preparedStatement.setString(2, dependencyType.name());
	        preparedStatement.setInt(3, relationshipId);
	
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating dependency failed, no rows affected.");
	        }
	
	        generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            relationshipDependency = new RelationshipDependencies();
	            relationshipDependency.setId(generatedKeys.getInt(1));
	            relationshipDependency.setDependencyName(dependencyName);
	            relationshipDependency.setDependencyType(dependencyType);
	            relationshipDependency.setRelationship(new Relationships());
	            relationshipDependency.getRelationship().setId(relationshipId);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error creating relationship dependency", e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) { /* ignored */ }
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { /* ignored */ }
	    }
	    return relationshipDependency;
	}
	
	/**
	 * Deletes a specific relationship dependency by its ID.
	 * This method is used in the 'Delete Confirmation Modal' section of the 'Relationship List' page.
	 * It enables the removal of dependencies to maintain data integrity when deleting relationships.
	 *
	 * @param id The unique identifier for each record in the relationship_dependencies table.
	 */
	public boolean deleteRelationshipDependencyById(int id) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM relationship_dependencies WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	
	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error deleting relationship dependency with ID: " + id, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to close prepared statement.", e);
	            }
	        }
	    }
	    return isDeleted;
	}
	
	/**
	 * Finds all relationship dependencies associated with a given relationship ID.
	 * Used in the 'Relationship List' page for informing users about
	 * potential impacts on data prior to executing deletion commands.
	 * 
	 * @param relationshipId The unique identifier of the relationship to fetch dependencies for.
	 * @return A list of RelationshipDependencies associated with the given relationship ID.
	 */
	public List<RelationshipDependencies> findAllDependenciesByRelationshipId(int relationshipId) {
	    List<RelationshipDependencies> dependencies = new ArrayList<>();
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        String sql = "SELECT * FROM relationship_dependencies WHERE relationship_id = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, relationshipId);
	        resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	            RelationshipDependencies dependency = new RelationshipDependencies();
	            dependency.setId(resultSet.getInt("id"));
	            dependency.setDependencyName(resultSet.getString("dependency_name"));
	            dependency.setDependencyType(RelationshipDependencies.DependencyType.valueOf(resultSet.getString("dependency_type")));
	            dependency.setCreatedAt(resultSet.getTimestamp("created_at"));
	            dependency.setUpdatedAt(resultSet.getTimestamp("updated_at"));
	            // Assuming Relationships class has a method to fetch by ID.
	            // dependency.setRelationship(fetchRelationshipById(resultSet.getInt("relationship_id")));
	            dependencies.add(dependency);
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(RelationshipDependenciesDAO.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.closeQuietly(resultSet);
	        DatabaseUtility.closeQuietly(preparedStatement);
	        DatabaseUtility.disconnect(connection);
	    }
	    return dependencies;
	}
	
	/**
	 * Updates the details of an existing relationship dependency in the system.
	 * Used in the 'Edit Relationship Modal' to modify dependency details,
	 * ensuring data integrity and accuracy of relationship mappings.
	 *
	 * @param id The unique identifier for each record in the relationship_dependencies table.
	 * @param dependencyName The updated name or identifier of the dependent entity or attribute.
	 * @param dependencyType The updated type of the dependency.
	 * @return boolean indicating success or failure of the update.
	 */
	public boolean updateRelationshipDependency(int id, String dependencyName, RelationshipDependencies.DependencyType dependencyType) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    boolean success = false;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "UPDATE relationship_dependencies SET dependency_name = ?, dependency_type = ?::dependency_type WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, dependencyName);
	        preparedStatement.setString(2, dependencyType.name());
	        preparedStatement.setInt(3, id);
	
	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) {
	            try {
	                preparedStatement.close();
	            } catch (SQLException e) {
	                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	            }
	        }
	    }
	    return success;
	}
}
