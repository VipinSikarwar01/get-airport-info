package com.screamatthewind.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.twilio.Twilio;
import com.twilio.rest.lookups.v1.PhoneNumber;

public class PhoneNumberFormatter {

	final static Logger logger = LogManager.getLogger(PhoneNumberFormatter.class);

	static String formatPhoneNumber(String[] info) {
		
		for (int i=0; i<info.length; i++) {
			if (info[i].toUpperCase().contains("PHONE")) {
				String phoneNumber = info[i].toUpperCase().replace("PHONE", "").trim();

				PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
				
				try {
					  com.google.i18n.phonenumbers.Phonenumber.PhoneNumber parsedPhoneNumber = phoneUtil.parse(phoneNumber, "US");
					  return phoneUtil.format(parsedPhoneNumber, PhoneNumberFormat.NATIONAL);
					} catch (NumberParseException e) {
					  System.err.println("NumberParseException was thrown: " + e.toString());
					}
				
				return lookupViaTwilio(phoneNumber);
			}
		}
		return "";
	}

	private static String lookupViaTwilio(String phoneNumber) {
		
	    final String ACCOUNT_SID = "AC8e5860a76ead33bd206ff1d722dcd5ae";
	    final String AUTH_TOKEN = "c2e99912dea9607d3528a74e66ce589d";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
        	
            PhoneNumber number = PhoneNumber
                .fetcher(new com.twilio.type.PhoneNumber(phoneNumber))
                .setType("carrier")
                .fetch();
            
            return number.getNationalFormat();

        } catch(com.twilio.exception.ApiException e) {
        	try {
	            if(e.getStatusCode() == 404) {
	                System.out.println("Phone number not found.");
	                return "";
	            }
            } catch(Exception ex) {
    			logger.error("PhoneNumberFormatter.lookupViaTwilio: " + phoneNumber + ": " + e.getMessage());
    			e.printStackTrace();
                return "";
            }
        } catch(Exception e) {
			logger.error("PhoneNumberFormatter.lookupViaTwilio: " + phoneNumber + ": " + e.getMessage());
			e.printStackTrace();
            return "";
        }

        return "";
	}
}
