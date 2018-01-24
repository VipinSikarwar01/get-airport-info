package com.screamatthewind.utility;

public class TitleCase {

	public static String convert(String realName) {
	    String space = " ";
	    String[] names = realName.split(space);
	    StringBuilder b = new StringBuilder();
	    for (String name : names) {
	        if (name == null || name.isEmpty()) {
	            b.append(space);
	            continue;
	        }
	        b.append(name.substring(0, 1).toUpperCase())
	                .append(name.substring(1).toLowerCase())
	                .append(space);
	    }
	    
	    return b.toString();
	}
}
