package com.screamatthewind.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "contact")
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c where locationId = :locationId AND userId = :userId ORDER BY priority DESC, contactType"),
    @NamedQuery(name = "Contact.findById", query = "SELECT c FROM Contact c where id = :id AND userId = :userId")
})

public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "location_id")
	private long locationId;

	@Column(name = "user_id")
	private long userId;
	
	@NotEmpty
	@Column(name = "contact_type")
	private String contactType;

	@Column(name = "contact_name")
	private String contactName;

	@NotEmpty
	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email_address")
	private String emailAddress;
	
	@Column(name = "priority")
	private int priority;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getLocationId() {
		return locationId;
	}
	
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getContactType() {
		return contactType;
	}
	
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public String toString() {
		String result;
		
		result = Long.toString(id);
		
		if (result.length() > 0)
			result += ",";
			
		result += locationId;
		
		if (result.length() > 0)
			result += ",";
			
		result += userId;
		
		if (result.length() > 0)
			result += ",";
			
		result += contactType;
		
		if (result.length() > 0)
			result += ",";
			
		result += contactName;
		
		if (result.length() > 0)
			result += ",";
			
		result += phoneNumber;
		
		if (result.length() > 0)
			result += ",";
			
		result += emailAddress;
		
		return result;
	}
}
