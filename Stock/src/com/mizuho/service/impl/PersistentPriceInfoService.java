package com.mizuho.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.mizuho.Instrument;
import com.mizuho.PriceInfo;
import com.mizuho.Vendor;
import com.mizuho.service.PriceInfoService;

// for database persistence
public class PersistentPriceInfoService implements PriceInfoService {

	@Override
	public boolean addPrice(Vendor vendor, Instrument instrument, Double info, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<PriceInfo> getPriceByVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PriceInfo> getPriceByInstrument(Instrument instrument) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purgeExpiredObjects(Collection<Vendor> vendors, Collection<Instrument>instruments) {
		// TODO Auto-generated method stub
	}
}
