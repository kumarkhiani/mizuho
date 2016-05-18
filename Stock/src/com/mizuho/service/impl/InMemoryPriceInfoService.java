package com.mizuho.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.mizuho.Instrument;
import com.mizuho.PriceInfo;
import com.mizuho.Vendor;
import com.mizuho.service.PriceInfoService;
import com.mizuho.utility.Utility;

public class InMemoryPriceInfoService implements PriceInfoService {

	@Override
	public Set<PriceInfo> getPriceByVendor(Vendor vendor) {
		return vendor.getPrices();
	}

	@Override
	public Set<PriceInfo> getPriceByInstrument(Instrument instrument) {
		return instrument.getPrices();
	}

	@Override
	public void purgeExpiredObjects(Collection<Vendor> vendors, Collection<Instrument> instruments) {

		for(Vendor v : vendors){
			Utility.purgeExpiredObjects(v.getPrices());
		}
		
		for(Instrument i : instruments){
			Utility.purgeExpiredObjects(i.getPrices());
		}

	}

	@Override
	public boolean addPrice(Vendor vendor, Instrument instrument, Double price, Date date) {
		
		PriceInfo info = new PriceInfo(vendor, instrument, price, date);

		return vendor.getPrices().add(info) && instrument.getPrices().add(info);
		
	}

}
