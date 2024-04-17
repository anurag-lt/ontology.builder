package dao;


import model.*;
import utils.DatabaseUtility;
import java.sql.*;
import java.util.logging.*;import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FacilitiesDAO {

	
	/**
	 * Creates a new facility record in the system with the given details.
	 * @param name the name of the new facility
	 * @param location the geographical location or address of the new facility
	 * @param sizeInSquareFootage the size of the new facility in square footage
	 * @param facilityType the type of the new facility, as defined in the Facilities.FacilityType enum
	 * @param operationalStatus the operational status of the new facility, as defined in the Facilities.OperationalStatus enum
	 * @param organizationId the unique identifier of the organization this facility belongs to
	 * @return true if the facility was successfully created, false otherwise
	 */
	public boolean createFacility(String name, String location, BigDecimal sizeInSquareFootage, Facilities.FacilityType facilityType, Facilities.OperationalStatus operationalStatus, String organizationId) {
	    Connection connection = DatabaseUtility.connect();
	    PreparedStatement pstmt = null;
	    boolean success = false;
	    String sql = "INSERT INTO facilities (name, location, size_in_square_footage, facility_type, operational_status, organization_id) VALUES (?, ?, ?, ?, ?, ?)";
	    try {
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setString(1, name);
	        pstmt.setString(2, location);
	        pstmt.setBigDecimal(3, sizeInSquareFootage);
	        pstmt.setString(4, facilityType.toString());
	        pstmt.setString(5, operationalStatus.toString());
	        pstmt.setString(6, organizationId);
	
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            success = true;
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error creating facility: ", e);
	    } finally {
	        DatabaseUtility.closeStatement(pstmt);
	        DatabaseUtility.disconnect(connection);
	    }
	    return success;
	}
	
	/**
	 * Fetches and returns a list of predefined facility types from the database to populate the 'Facility Type' dropdown in the form.
	 * Used in the 'Add Facility' page to provide a selection of facility types to users adding a new facility.
	 * @return A list of strings representing the different facility types.
	 */
	public List<String> getFacilityTypes() {
	    List<String> facilityTypes = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String query = "SELECT DISTINCT facility_type FROM facilities ORDER BY facility_type ASC;";
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            facilityTypes.add(resultSet.getString("facility_type"));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (statement != null) statement.close();
	            DatabaseUtility.disconnect(connection);
	        } catch (SQLException e) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	        }
	    }
	    return facilityTypes;
	}
	
	/**
	 * Retrieves and returns the details of a specific facility identified by its unique identifier.
	 * It is used in the 'Facility Details' page to display the details of a facility for review or editing by facility managers.
	 * @param facilityId the unique identifier for the facility whose details are being retrieved
	 * @return Facilities the facility details or null if not found
	 */
	public Facilities getFacilityDetailsById(int facilityId) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Facilities facility = null;
	    try {
	        connection = DatabaseUtility.connect();
	        String sql = "SELECT * FROM facilities WHERE id = ?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, facilityId);
	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            facility = new Facilities();
	            facility.setId(resultSet.getInt("id"));
	            facility.setName(resultSet.getString("name"));
	            facility.setLocation(resultSet.getString("location"));
	            facility.setSizeInSquareFootage(resultSet.getBigDecimal("size_in_square_footage"));
	            String facilityType = resultSet.getString("facility_type");
	            facility.setFacilityType(Facilities.FacilityType.valueOf(facilityType.toUpperCase().replace(' ', '_')));
	            String operationalStatus = resultSet.getString("operational_status");
	            facility.setOperationalStatus(Facilities.OperationalStatus.valueOf(operationalStatus.toUpperCase().replace(' ', '_')));
	        }
	    } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
	    } finally {
	        DatabaseUtility.disconnect(connection);
	        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e); }
	    }
	    return facility;
	}
	
	/*
	* Updates the details of a specific facility in the database.
	* @param facilityId the unique identifier of the facility being updated.
	* @param name the new name of the facility.
	* @param location the new location of the facility.
	* @param sizeInSquareFootage the new size of the facility in square footage.
	* @param facilityType the new type of the facility, as defined in the Facilities.FacilityType enum.
	* @param operationalStatus the new operational status of the facility, as defined in the Facilities.OperationalStatus enum.
	* @return boolean indicating whether the update was successful.
	*/
	public boolean updateFacilityDetails(int facilityId, String name, String location, BigDecimal sizeInSquareFootage, Facilities.FacilityType facilityType, Facilities.OperationalStatus operationalStatus) {
	  Connection connection = null;
	  PreparedStatement pstmt = null;
	  String updateQuery = "UPDATE facilities SET name = ?, location = ?, size_in_square_footage = ?, facility_type = ::facility_types, operational_status = ::operational_statuses WHERE id = ?;";
	  try {
	    connection = DatabaseUtility.connect();
	    pstmt = connection.prepareStatement(updateQuery);
	    pstmt.setString(1, name);
	    pstmt.setString(2, location);
	    pstmt.setBigDecimal(3, sizeInSquareFootage);
	    pstmt.setString(4, facilityType.name());
	    pstmt.setString(5, operationalStatus.name());
	    pstmt.setInt(6, facilityId);
	    int affectedRows = pstmt.executeUpdate();
	    return affectedRows > 0;
	  } catch (SQLException e) {
	    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to update facility details", e);
	    return false;
	  } finally {
	    DatabaseUtility.disconnect(connection);
	    if (pstmt != null) {
	      try {
	        pstmt.close();
	      } catch (SQLException e) {
	        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to close PreparedStatement", e);
	      }
	    }
	  }
	}
}
