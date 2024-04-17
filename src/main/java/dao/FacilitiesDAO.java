package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class FacilitiesDAO {

	
	/*
	 * Creates a new facility record in the database with the provided details.
	 * Used in 'Add Facility' page's 'Facility Details Form' section.
	 */
	public Facilities createFacility(String name, String location, BigDecimal sizeInSquareFootage, Facilities.FacilityType facilityType, Facilities.OperationalStatus operationalStatus, String organizationId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Facilities facility = new Facilities();
	
	    try {
	        connection = DatabaseUtility.connect();
	        String SQL_INSERT = "INSERT INTO facilities (name, location, size_in_square_footage, facility_type, operational_status, organization_id) VALUES (?, ?, ?, ?, ?, ?);";
	        preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, location);
	        preparedStatement.setBigDecimal(3, sizeInSquareFootage);
	        preparedStatement.setString(4, facilityType.name());
	        preparedStatement.setString(5, operationalStatus.name());
	        preparedStatement.setString(6, organizationId);
	        int affectedRows = preparedStatement.executeUpdate();
	
	        if (affectedRows == 0) {
	            throw new SQLException("Creating facility failed, no rows affected.");
	        }
	
	        resultSet = preparedStatement.getGeneratedKeys();
	        if (resultSet.next()) {
	            facility.setId(resultSet.getInt(1));
	        } else {
	            throw new SQLException("Creating facility failed, no ID obtained.");
	        }
	        facility.setName(name);
	        facility.setLocation(location);
	        facility.setSizeInSquareFootage(sizeInSquareFootage);
	        facility.setFacilityType(facilityType);
	        facility.setOperationalStatus(operationalStatus);
	    } catch (SQLException ex) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return facility;
	}
	
	/**
	 * Updates the details of a specific facility based on user inputs from the form.
	 * @param facilityId to identify the facility, 
	 * @param name updated name of the facility, 
	 * @param location updated location of the facility, 
	 * @param sizeInSquareFootage updated size of the facility in square footage, 
	 * @param facilityType updated type of the facility, 
	 * @param operationalStatus updated operational status of the facility.
	 * @return boolean indicating success (true) or failure (false) of the update operation.
	 */
	public boolean updateFacilityDetails(int facilityId, String name, String location, BigDecimal sizeInSquareFootage, Facilities.FacilityType facilityType, Facilities.OperationalStatus operationalStatus) {
	    Connection connection = DatabaseUtility.connect();
	    String sql = "UPDATE facilities SET name = ?, location = ?, size_in_square_footage = ?, facility_type = ::facility_types, operational_status = ::operational_statuses WHERE id = ?;";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, name);
	        statement.setString(2, location);
	        statement.setBigDecimal(3, sizeInSquareFootage);
	        statement.setString(4, facilityType.name());
	        statement.setString(5, operationalStatus.name());
	        statement.setInt(6, facilityId);
	        int rowsAffected = statement.executeUpdate();
	        DatabaseUtility.disconnect(connection);
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        DatabaseUtility.disconnect(connection);
	        return false;
	    }
	}
	
	/**
	 * Fetches and returns the details of a facility by its unique identifier.
	 * Used in 'Facility Details' page for fetching details of a facility to display and edit.
	 *
	 * @param facilityId Unique identifier of the facility.
	 * @return A Facilities object containing the facility's details, or null if not found.
	 */
	public Facilities getFacilityDetailsById(int facilityId) {
	    Facilities facility = null;
	    String sql = "SELECT * FROM facilities WHERE id = ?;";
	    Connection connection = DatabaseUtility.connect();
	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, facilityId);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            facility = new Facilities();
	            facility.setId(resultSet.getInt("id"));
	            facility.setName(resultSet.getString("name"));
	            facility.setLocation(resultSet.getString("location"));
	            facility.setSizeInSquareFootage(resultSet.getBigDecimal("size_in_square_footage"));
	            
	            String facilityTypeStr = resultSet.getString("facility_type");
	            Facilities.FacilityType facilityType = Facilities.FacilityType.valueOf(facilityTypeStr.toUpperCase());
	            facility.setFacilityType(facilityType);
	            
	            String operationalStatusStr = resultSet.getString("operational_status");
	            Facilities.OperationalStatus operationalStatus = Facilities.OperationalStatus.valueOf(operationalStatusStr.toUpperCase());
	            facility.setOperationalStatus(operationalStatus);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(FacilitiesDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	    }
	    return facility;
	}
	
	/**
	 * Deletes a specific facility identified by its unique ID.
	 * @param facilityId The unique identifier of the facility to be deleted.
	 * Used in the 'Organization List > Delete Confirmation Modal' section to delete a facility.
	 */
	public boolean deleteFacility(int facilityId) {
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    boolean isDeleted = false;
	    String sql = "DELETE FROM facilities WHERE id = ?;";
	    try {
	        connection = DatabaseUtility.connect();
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(1, facilityId);
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            isDeleted = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error executing delete for facility ID: " + facilityId, e);
	    } finally {
	        try {
	            if (pstmt != null) {
	                pstmt.close();
	            }
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing resources", e);
	        }
	    }
	    return isDeleted;
	}
}
