package com.mizuho;

import java.util.Set;
import java.util.TreeSet;

/**
 * <code>Vendor</code> represents a Vendor for a traded security.
 * <p/>
 * It holds the pricing information of various instruments.
 * 
 * @see PriceInfo
 * 
 * @author Kumar
 * 
 */

public class Vendor implements Comparable<Vendor> {

	/**
	 * The identifier of this vendor.
	 */
	private String vendorId = null;

	/**
	 * The name of this vendor.
	 */
	private String vendorName = null;

	/**
	 * Price information of various Instruments
	 */
	private Set<PriceInfo> prices = null;

	/**
	 * Constructor
	 * 
	 * @param vendorId
	 *            the vendor Id
	 * @param vendorName
	 *            the vendor Name
	 */
	public Vendor(String vendorId, String vendorName) {

		if (vendorId == null) {
			throw new IllegalArgumentException("Vendor Id cannot be null");
		}

		if (vendorName == null) {
			throw new IllegalArgumentException("Vendor Name cannot be null");
		}

		this.vendorId = vendorId;
		this.vendorName = vendorName;

		prices = new TreeSet<PriceInfo>();
	}
	
	/**
	 * Returns the vendor Id
	 * 
	 * @return the vendor Id
	 */

	public String getVendorId() {

		return vendorId;
	}
	
	/**
	 * Returns the vendor name
	 * 
	 * @return the vendor name
	 */

	public String getVendorName() {

		return vendorName;
	}
	
	/**
	 * Returns the pricing information
	 * 
	 * @return the pricing information
	 */

	public Set<PriceInfo> getPrices() {

		return prices;
	}
	
	/**
	 * Compares vendor based on the vendor Id
	 */

	@Override
	public int compareTo(Vendor other) {

		return vendorId.compareTo(other.getVendorId());
	}
}
