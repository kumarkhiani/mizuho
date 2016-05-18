package com.mizuho;

import java.util.Date;

import com.mizuho.utility.Utility;

/**
 * <code>PriceInfo</code> represents a price and recorded date of an instrument.
 * <p/>
 * It represents the price of an instrument from a particular vendor on a
 * specific point of time
 * 
 * @author Kumar
 *
 */
public class PriceInfo implements Comparable<PriceInfo> {

	/**
	 * Instrument whose price is recorded
	 */
	private Instrument instrument = null;

	/**
	 * Vendor who quoted the price
	 */
	private Vendor vendor = null;

	/**
	 * Instance of time when the price was recorded
	 */
	private Date date = null;

	/**
	 * Price of the instrument
	 */
	private Double price = null;

	/**
	 * Constant used to specify the Expiry Date when the price should be
	 * considered as expired
	 */
	public static final int EXPIRY_DAYS = 30;

	/**
	 * Constructor
	 * 
	 * @param vendor
	 *            Vendor who quoted the price
	 * @param instrument
	 *            Instrument whose price is being quoted
	 * @param price
	 *            Price of the instrument
	 * @param date
	 *            Time instance when the price is recorded in the system
	 */

	public PriceInfo(Vendor vendor, Instrument instrument, Double price, Date date) {

		if (vendor == null) {
			throw new IllegalArgumentException("Vendor cannot be null");
		}

		if (instrument == null) {
			throw new IllegalArgumentException("Instrument cannot be null");
		}

		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}

		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}

		this.instrument = instrument;
		this.vendor = vendor;
		this.price = price;
		this.date = date;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * Price will be considered as equal when vendorId, instrumentId and date
	 * (upto second) are same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		PriceInfo other = (PriceInfo) obj;

		return ((instrument != null && instrument.equals(other.getInstrument()))
				&& (vendor != null && vendor.equals(other.getVendor()))
				&& (date != null && date.equals(other.getDate())));
	}

	@Override
	public int hashCode() {
		final int prime = 17;
		int result = 1;
		result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());

		return result;
	}

	@Override
	public String toString() {

		return "Price - vendor=" + vendor.getVendorName() + ", instrument=" + instrument.getInstrumentName()
				+ ", price=" + price + ", date= " + Utility.sdf.format(date) + "\n";
	}

	/**
	 * Used to make sure that data is sorted in the following order 1) Date 2)
	 * Vendor 3) Instrument
	 */

	@Override
	public int compareTo(PriceInfo other) {

		if (other == this) {
			return 0;
		}

		final int EQUAL = 0;

		int dComparison = other.getDate().compareTo(this.getDate());

		int vComparison = vendor.getVendorId().compareTo(other.vendor.getVendorId());

		int iComparison = instrument.getInstrumentId().compareTo(other.getInstrument().getInstrumentId());

		if (dComparison == EQUAL && vComparison == EQUAL && iComparison == EQUAL)
			return EQUAL;

		if (dComparison != EQUAL) {
			return dComparison;
		}

		if (vComparison != EQUAL) {
			return vComparison;
		}

		if (iComparison != EQUAL) {
			return iComparison;
		}

		return EQUAL;
	}

	/**
	 * To Determine if the current Price is considered to be expired or not
	 * 
	 * @return boolean if the current Price is considered to be expired or not
	 */

	public boolean isExpired() {

		return (this.getDate().compareTo(Utility.getExpiryDate()) < 0);
	}
}
