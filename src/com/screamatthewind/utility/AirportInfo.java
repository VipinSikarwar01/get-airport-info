package com.screamatthewind.utility;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.screamatthewind.core.AirportContacts;

public class AirportInfo {

	final static Logger logger = LogManager.getLogger(AirportInfo.class);

	public static PlaceDetails getAirportLocation(GeoApiContext context, String partialAddress) throws Exception {

		final PlacesSearchResponse predictions = PlacesApi.textSearchQuery(context, partialAddress).await();

		if(predictions.results.length == 0)
			return null;
		
		// only look at the first prediction.  Based on analysis, it is the best one
		String placeId = predictions.results[0].placeId;
		PlaceDetails placeDetails = PlacesApi.placeDetails(context, placeId).await();
		
		return placeDetails;
	}

	public static AirportContacts getAirportContacts(String airportCode) {
		
        String airportDetailUrl = "http://www.airnav.com/airport/%s";
        AirportContacts contactInfo = new AirportContacts();
        
		try {
			
			Document doc = Jsoup.connect(String.format(airportDetailUrl, airportCode)).get();
			
			Elements h3s = doc.select("h3");
			for (Element h3: h3s) {
				if (h3.html().contains("Airport Ownership and Management")) {
					Elements tds = h3.nextElementSibling().select("td");
					
					for (Element td: tds) {
						if (td.html().contains("Owner:")) {
							Element owner = td.nextElementSibling();
							String[] info = owner.html().split("<br>");
							
							String name = info[0];
							String phoneNumber = PhoneNumberFormatter.formatPhoneNumber(info);
							
							if (phoneNumber.length() > 1) {
								contactInfo.ownerName = TitleCase.convert(name);
								contactInfo.ownerPhone = phoneNumber;
							}
						}
						
						if (td.html().contains("Manager:")) {
							Element manager = td.nextElementSibling();
							String mgr = manager.html();
							String[] info = mgr.split("<br>");
							
							String name = info[0];
							String phoneNumber = PhoneNumberFormatter.formatPhoneNumber(info);

							if (phoneNumber.length() > 1) {
								contactInfo.managerName = TitleCase.convert(name);
								contactInfo.managerPhone = phoneNumber;
							}
						}
					}
				}
			}
				
		} catch (IOException e) {
			logger.error("AirportInfo.getAirportContacts: " + airportCode + ": " + e.getMessage());
			e.printStackTrace();
        }

		return contactInfo;
	}
}
