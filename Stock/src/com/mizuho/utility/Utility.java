package com.mizuho.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;

import com.mizuho.PriceInfo;

public class Utility {
	
	/*
	 * Number of Days to determine if the price is considered to be cleared
	 */
	public static final int EXPIRY_DAYS = 30; 
	
	/*
	 * Expiry Date 
	 */
	private static Date expiryDate;
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	/*
	 * Remove the expired dates from the collection
	 */
	public static void purgeExpiredObjects(Set<PriceInfo> prices){
		Iterator<PriceInfo> pIterator = prices.iterator();
		while(pIterator.hasNext()){
			if(pIterator.next().isExpired()){
				pIterator.remove();
			}
		}
	}
	
	/*
	 * Calculate the expiry date
	 */
	
	private static void setExpiryDate(){
		
		expiryDate = DateUtils.truncate(DateUtils.addDays(new Date(), -EXPIRY_DAYS + 1), Calendar.DAY_OF_MONTH);
	}
	
	public static Date getExpiryDate(){
		
		if(expiryDate==null){
			setExpiryDate();
		}
		
		return expiryDate;
	}
}
