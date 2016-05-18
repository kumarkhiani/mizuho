package com.mizuho.service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.mizuho.Instrument;
import com.mizuho.PriceInfo;
import com.mizuho.Vendor;

public interface PriceInfoService {

	/*
	 * Add price information to the vendor as well as to the instrument
	 */
	boolean addPrice(Vendor vendor, Instrument instrument, Double info, Date date) throws IllegalArgumentException;
	
	/*
	 * Get all the price information belonging to a particular vendor
	 */
	
	Set<PriceInfo> getPriceByVendor(Vendor vendor);

	/*
	 * Get the prices of a particular instrument from all the vendors
	 */
	Set<PriceInfo> getPriceByInstrument(Instrument instrument);
	
	/*
	 * Remove the expired price information from all the vendors for all the instruments
	 */
	void purgeExpiredObjects(Collection<Vendor> vendors, Collection<Instrument>instruments);

}
