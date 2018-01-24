package com.screamatthewind.utility;

import com.google.maps.model.AddressComponent;
import com.screamatthewind.core.Location;

public class AddressComponentsToLocation {
	
	static AddressComponent[] m_addressComponents = null;
	
	public static Location convert(AddressComponent[] addressComponents) {
	
		m_addressComponents = addressComponents;
		
		Location location = new Location();
		String streetAddress = "";
		
		location.setStreetNumber(getAddressComponent("street_number"));
            	
    	streetAddress = location.getStreetNumber();

    	location.setStreetName(getAddressComponent("route"));

        if (streetAddress.length() == 0) 
        	streetAddress = location.getStreetName();
         else 
        	streetAddress = streetAddress + " " + location.getStreetName();

        location.setAddress1(streetAddress);
        location.setCity(getAddressComponent("locality"));
        location.setState(getAddressComponent("administrative_area_level_1"));
        location.setCounty(getAddressComponent("administrative_area_level_2"));
        location.setCountryCode(getAddressComponent("country"));
        location.setZip(getAddressComponent("postal_code"));
		
		return location;
	}
	
	public static String getAddressComponent(String findType) {

		String result = "";
		
		for (int i=0; i<m_addressComponents.length; i++) {
			for (int j=0; j<m_addressComponents[i].types.length; j++) {
				if (m_addressComponents[i].types[j].toString().equalsIgnoreCase(findType)) {
					
					if (findType.equalsIgnoreCase("country"))
						result = m_addressComponents[i].shortName;
					else
						result = m_addressComponents[i].longName;
					break;
				}
			}
		}

		return result;
	}
}
