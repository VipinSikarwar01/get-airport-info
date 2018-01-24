package com.screamatthewind.utility;

import com.google.maps.model.PlaceDetails;
import com.screamatthewind.core.Location;

public class PlaceDetailsToLocation {

	public static Location convert(PlaceDetails placeDetails) {
		
		if (placeDetails == null)
			return null;
		
		Location location = AddressComponentsToLocation.convert(placeDetails.addressComponents);            		

		location.setPlaceId(placeDetails.placeId);
		location.setLocationType("Airport");
		location.setLocationName(placeDetails.name);
		location.setFormattedAddress(placeDetails.formattedAddress);
		location.setLatitude(placeDetails.geometry.location.lat);
		location.setLongitude(placeDetails.geometry.location.lng);
		location.setFormattedCoordinates(location.getLatitude(), location.getLongitude());
	
		if (placeDetails.website != null)
			location.setWebSite(placeDetails.website.toString());

		if (placeDetails.icon != null)
			location.setIcon(placeDetails.icon.toString());

		return location;
	}

}
