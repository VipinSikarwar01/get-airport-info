package com.screamatthewind.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.screamatthewind.Main;
import com.screamatthewind.core.Location;

public class LocationDAO {

	final Logger logger = LogManager.getLogger(ContactDAO.class);

	public LocationDAO() {
		super();
	}

	public Location save(Location location, Session session) {

		if (location.getLocationName() == null)
			return null;
		
		if (location.getLocationName().length() < 2)
			return null;
		
		if (location.getAddress1() == null)
			return null;
		
		if (location.getAddress1().length() < 2)
			return null;
		
		if (location.getCity() == null)
			return null;
		
		if (location.getCity().length() < 2)
			return null;
		
		if (location.getState() == null)
			return null;
		
		if (location.getState().length() < 2)
			return null;
		
		Location savedLocation = null;
		
		try {

			savedLocation = (Location) session.merge(location); 

		} catch (Exception e) {
			session.clear();
			logger.error("Location.save: " + location.toString() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		return savedLocation;
	}
}
