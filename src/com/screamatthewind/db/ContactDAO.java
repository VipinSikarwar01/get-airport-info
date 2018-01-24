package com.screamatthewind.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.screamatthewind.Main;
import com.screamatthewind.core.Contact;

public class ContactDAO  {

	final static Logger logger = LogManager.getLogger(ContactDAO.class);

	public ContactDAO() {
        super();

	}

    public static Contact save(Contact contact, Session session) {

		if (contact.getPhoneNumber() == null)
			return null;
		
		if (contact.getPhoneNumber().length() < 7)
			return null;
		
		Contact savedContact = null;
		
		try {
			savedContact = (Contact) session.merge(contact);
		} catch (Exception e) {
			session.clear();
			logger.error("Contact.save: " + contact.toString() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		return savedContact;
    }
}
