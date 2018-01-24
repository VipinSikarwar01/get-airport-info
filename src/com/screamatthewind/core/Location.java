package com.screamatthewind.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.screamatthewind.utility.Coordinates;

@Entity
@Table(name = "location")
@NamedQueries({
    @NamedQuery(name = "Location.findAllLocations", query = "SELECT l FROM Location l where userId = :userId ORDER BY locationName"),
    @NamedQuery(name = "Location.findByLocationId", query = "SELECT l FROM Location l where id = :id and userId = :userId"),
    @NamedQuery(name = "Location.findByLocationName", query = "SELECT l FROM Location l where userId = :userId AND upper(locationName) = :locationName")
})

public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "place_id")
	private String placeId;
	
	@Column(name = "location_type")
	private String locationType;
	
	//	@NotEmpty
	@Column(name = "location_name")
	private String locationName;

	//	@NotEmpty
	@Column(name = "address_1")
	private String address1;

	@Column(name = "address_2")
	private String address2;

	//	@NotEmpty
	@Column(name = "city")
	private String city;

	//	@NotEmpty
	@Column(name = "state")
	private String state;

	@Column(name = "zip")
	private String zip;

	@Column(name = "street_number")
	private String streetNumber;

	@Column(name = "street_name")
	private String streetName;

	@Column(name = "county")
	private String county;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "formatted_address")
	private String formattedAddress;
	
	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "formatted_coordinates")
	private String formattedCoordinates;
	
	@Column(name = "website")
	private String webSite;

	@Column(name = "icon")
	private String icon;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(String placeId){
		if (placeId != null)
			this.placeId = placeId.trim();
	}

	public String getLocationType() {
		return this.locationType;
	}
	
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		if (locationName != null)
			this.locationName = locationName.trim();
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		if (address1 != null)
			this.address1 = address1.trim();
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		if (address2 != null)
			this.address2 = address2.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city != null)
			this.city = city.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state != null)
			this.state = state.trim();
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		if (zip != null)
			this.zip = zip.trim();
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		if (streetNumber != null)
			this.streetNumber = streetNumber.trim();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		if (streetName != null)
			this.streetName = streetName.trim();
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		if (county != null)
			this.county = county.trim();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		if (countryCode != null)
			this.countryCode = countryCode.trim();
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		if (emailAddress != null)
			this.emailAddress = emailAddress.trim();
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		if (formattedAddress != null)
			this.formattedAddress = formattedAddress.trim();
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getFormattedCoordinates() {
		return formattedCoordinates;
	}

	public void setFormattedCoordinates(Double latitude, Double longitude) {
		this.formattedCoordinates = Coordinates.getFormattedLocationInDegree(latitude, longitude);
	}

	public String getWebSite() {
		return this.webSite;
	}
	
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
		
	public String toString(boolean dropAddress2) {
		String result = "";

		result = Long.toString(id);
		
		if (result.length() > 0)
			result += ",";
			
		result += locationName;
		
		if (result.length() > 0)
			result += ",";
			
		result += address1;			

		if (result.length() > 0)
			result += ",";
			
		if (dropAddress2 == false) {
			result += address2;			
			
			if (result.length() > 0)
				result += ",";
		}
		
		result += city;			
		
		if (result.length() > 0)
			result += ",";
		
		result += state;			
		
		if (result.length() > 0)
			result += ",";
		
		result += zip;			
		
		if (result.length() > 0)
			result += ",";
		
		result += countryCode;			
		
		return result;
	}
}
