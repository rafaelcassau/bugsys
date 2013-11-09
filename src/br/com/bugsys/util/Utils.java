package br.com.bugsys.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static Date convertDateStringToDate(String date) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date dateConvert = null;
		try {
			dateConvert = (Date) formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return dateConvert;
	}
	
	public static String convertDateToString(Date date) {

		String dateConvert = new SimpleDateFormat("dd/MM/yyyy").format(date);
		return dateConvert;
	}
	
}
