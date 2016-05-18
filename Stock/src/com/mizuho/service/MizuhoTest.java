package com.mizuho.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.mizuho.Instrument;
import com.mizuho.PriceInfo;
import com.mizuho.Vendor;
import com.mizuho.service.impl.InMemoryPriceInfoService;
import com.mizuho.utility.Utility;

public class MizuhoTest {
	
	Map<String, Vendor> vendors = new HashMap<>();;
	Map<String, Instrument> instruments = new HashMap<>();
	
	PriceInfoService service;  // if using database service, instantiate PersistentPriceInfoService

	List<String> inputData = new ArrayList<>();
	static Date today = new Date();	
	static int EXTRA_PRICES = 1;
	
	static DecimalFormat df = new DecimalFormat("0");
	
	static String sep = "_";
	
	@Before
	public void setUpBefore() throws Exception {
	
		Vendor vendorA = new Vendor("A", "ABC");
		Vendor vendorB = new Vendor("B", "BBC");
		
		vendors.put(vendorA.getVendorId(),vendorA);
		vendors.put(vendorB.getVendorId(),vendorB);
		
		Instrument hsbc = new Instrument("1", "hsbc");
		Instrument boc = new Instrument("2", "boc");	
		Instrument hdfc = new Instrument("3", "hdfc");	
		
		instruments.put(hsbc.getInstrumentId(), hsbc);
		instruments.put(boc.getInstrumentId(), boc);
		instruments.put(hdfc.getInstrumentId(), hdfc);
		
		for(Vendor vendor : vendors.values()){
			for(Instrument insturment: instruments.values()){
				for(int i=0; i<Utility.EXPIRY_DAYS + EXTRA_PRICES ; i++){
					inputData.add(vendor.getVendorId() + sep + insturment.getInstrumentId() + sep 
							      + df.format(Math.random()*100) + sep + Utility.sdf.format(DateUtils.addDays(new Date(), -i)));
					//Thread.sleep(100);
				}
			}
		}
		
		System.out.println(inputData);

		service = new InMemoryPriceInfoService();
		Vendor v = null;
		Instrument i = null;
		Double p = null;
		Date d = null;
		for(String input : inputData){
			String[] token = input.split(sep);
			
			v = vendors.get(token[0]);
			i = instruments.get(token[1]); 
			p = Double.valueOf(token[2]);
			try {
				d = Utility.sdf.parse(token[3]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			service.addPrice(v, i, p, d);
		}
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testAddPriceArgumentVendor(){

		service.addPrice(vendors.get("X"), instruments.get("1"), 10.00, today);
	}

	@Test(expected=IllegalArgumentException.class) 
	public void testAddPriceArgumentInstrument(){

		service.addPrice(vendors.get("A"), instruments.get("x"), 10.00, today);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testAddPriceArgumentPrice(){

		service.addPrice(vendors.get("A"), instruments.get("1"), -10.00, today);
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void testAddPriceArgumentDate(){

		service.addPrice(vendors.get("A"), instruments.get("1"), 10.00, null);
	}

	@Test
	public void testGetPriceByVendor() {
		
		int expectedPrices = instruments.values().size() * (Utility.EXPIRY_DAYS + EXTRA_PRICES);
		assertEquals(expectedPrices, service.getPriceByVendor(vendors.get("A")).size());
		System.out.println(service.getPriceByVendor(vendors.get("A")).size());
	}
	
	@Test
	public void testGetPriceByInstrument() {

		int expectedPrices = vendors.values().size() * (Utility.EXPIRY_DAYS + EXTRA_PRICES);
		assertEquals(expectedPrices, service.getPriceByInstrument(instruments.get("1")).size());
	}

	@Test 
	public void testPurgeExpiredObjects(){

		for(Vendor v : vendors.values()){
			assertTrue(containsExpiredObject(v.getPrices()));
		}
		
		for(Instrument i : instruments.values()){
			assertTrue(containsExpiredObject(i.getPrices()));
		}
		
		System.out.println(service.getPriceByVendor(vendors.get("A")));
		service.purgeExpiredObjects(vendors.values(), instruments.values());
		System.out.println(service.getPriceByVendor(vendors.get("A")));
		System.out.println(Utility.getExpiryDate());
		for(Vendor v : vendors.values()){
			assertFalse(containsExpiredObject(v.getPrices()));
		}
		
		for(Instrument i : instruments.values()){
			assertFalse(containsExpiredObject(i.getPrices()));
		}
	}
	
	private boolean containsExpiredObject(Set<PriceInfo> prices){
		
		for(PriceInfo p : prices){
			if(p.isExpired()){
				return true;
			}
		}
		return false;
	}
}
