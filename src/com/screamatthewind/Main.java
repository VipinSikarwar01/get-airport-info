package com.screamatthewind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.maps.GeoApiContext;
import com.google.maps.model.PlaceDetails;
import com.screamatthewind.core.AirportContacts;
import com.screamatthewind.core.Contact;
import com.screamatthewind.core.Location;
import com.screamatthewind.db.ContactDAO;
import com.screamatthewind.db.HibernateUtil;
import com.screamatthewind.db.LocationDAO;
import com.screamatthewind.utility.AirportInfo;
import com.screamatthewind.utility.PlaceDetailsToLocation;

public class Main {

	static String apiKey = "AIzaSyCMdpL6N6CjCa-61g8_gqDIBH_Ilf4g3JI";

	static GeoApiContext context = null;
	
	public static void main(String[] args) {

		final Logger logger = LogManager.getLogger(Main.class);
		
		Document doc;
		Session session = null;
		
        try {
        	context = new GeoApiContext.Builder().apiKey(apiKey).build();
        	
            // doc = Jsoup.connect("http://www.airportcodes.us/primary-airports.htm").get();
        	doc = Jsoup.connect("http://www.airportcodes.us/commercial-service-airports.htm").get();
        		
            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements tables = doc.select("table");
            Element table = tables.get(1);
            Elements header = table.select("th");
            
/*            if (!(header.toString().contains("USA Primary Airports"))) {
            	System.out.println("Header should be USA Primary Airports.  Instead it is: " + header.toString());
            	System.exit(0);
            }
*/
            if (!(header.toString().contains("USA Commercial Service Airports"))) {
            	System.out.println("Header should be USA Commercial Service Airports.  Instead it is: " + header.toString());
            	System.exit(0);
            }

    		session = HibernateUtil.getSessionFactory().openSession();
            LocationDAO locationDao = new LocationDAO();
            
boolean startLoading = false;            
            
            Elements rows = table.select("tr");
            for (Element row: rows) {
            	Elements data = row.select("td");
            	
            	if (data.size() == 4) {
            		String airportCode = data.get(0).html();
            		String airportName = data.get(1).html();
            		String airportCity = data.get(2).html();
            		String airportState = data.get(3).html();

/*if (airportCode.equals("GGV")) {
	startLoading = true;
}

if (!startLoading)
	continue;
*/            		
            		System.out.print(airportCode + ", " + airportName + ", " + airportCity + ", " + airportState + ", ");
            		
            		String partialAddress = airportName + ",+" + airportCity + ",+" + airportState;

            		partialAddress = partialAddress.replace("  ", "+");
            		partialAddress = partialAddress.replace(" ", "+");
            	
            		PlaceDetails placeDetails = AirportInfo.getAirportLocation(context, partialAddress);
            		Location location = PlaceDetailsToLocation.convert(placeDetails);
            		
            		if (location == null) {
            			logger.warn("Airport not found: " + airportCode + ": " + partialAddress);
            			continue;
            		}
            		
            		AirportContacts airportContacts = AirportInfo.getAirportContacts(airportCode);

            		airportContacts.mainName = "Main Number";
            		airportContacts.mainPhone = placeDetails.formattedPhoneNumber;            		
            		
            		System.out.print(airportContacts.ownerName + ", " + airportContacts.ownerPhone + ", ");
            		System.out.println(airportContacts.managerName + ", " + airportContacts.managerPhone);

        			Transaction transaction = session.getTransaction();
        			transaction.begin();

        			location.setUserId(1);            		
            		Location savedLocation = locationDao.save(location, session);
            		
            		if (savedLocation == null) {
            			logger.error("Unable to save airport: " + airportCode + ": " + partialAddress);
            			transaction.rollback();
            			continue;
            		}
            		
            		Contact contact = new Contact();
            		
            		contact.setContactType("Main");
            		contact.setContactName(airportContacts.mainName);
            		contact.setPhoneNumber(airportContacts.mainPhone);
            		contact.setPriority(1);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);
            		
            		contact.setContactType("Manager");
            		contact.setContactName(airportContacts.managerName);
            		contact.setPhoneNumber(airportContacts.managerPhone);
            		contact.setPriority(1);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);
            		
            		contact.setContactType("Owner");
            		contact.setContactName(airportContacts.ownerName);
            		contact.setPhoneNumber(airportContacts.ownerPhone);
            		contact.setPriority(1);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);
            		
            		contact.setContactType("TSA");
            		contact.setContactName("Transportation Security Administration");
            		contact.setPhoneNumber("(866) 289-9673");
            		contact.setPriority(2);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);
            		
            		contact.setContactType("FAA");
            		contact.setContactName("Federal Aviation Administration");
            		contact.setPhoneNumber("(202) 366-2220");
            		contact.setPriority(2);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);

            		contact.setContactType("HLS");
            		contact.setContactName("Homeland Security");
            		contact.setPhoneNumber("(202) 282-8000");
            		contact.setPriority(2);
            		contact.setLocationId(savedLocation.getId());
            		contact.setUserId(savedLocation.getUserId());

            		ContactDAO.save(contact, session);

            		transaction.commit();
            	}
            }

			if (session != null)
				session.close();
            
        } catch (Exception e) {
			logger.error("Main.main: " + e.getMessage());
			e.printStackTrace();
        } 
        
        System.out.println("Done");

		System.exit(0);

	}
	
}
