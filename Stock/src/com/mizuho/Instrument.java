package com.mizuho;

import java.util.Set;
import java.util.TreeSet;

/**
 * <code>Instrument</code> represents a traded security.
 * <p/>
 * It holds the pricing information from various vendors.
 * 
 * @see PriceInfo
 * 
 * @author Kumar
 * 
 */
public class Instrument implements Comparable<Instrument>{

	/**
	 * The identifier of this instrument.
	 */
	String instrumentId = null;
	/**
	 * The name of the instrument.
	 */
	String instrumentName = null;
	/**
	 * The pricing information from various vendors.
	 */
	Set<PriceInfo> prices = null;

	/**
	 * Constructor.
	 * 
	 * @param instrumentId
	 *            the instrument identifier
	 * @param instrumentName
	 *            the name of this instrument
	 */
	public Instrument(String instrumentId, String instrumentName) {

		if (instrumentId == null) {
			throw new IllegalArgumentException("Instrument Id cannot be null");
		}

		if (instrumentName == null) {
			throw new IllegalArgumentException("Instrument Name cannot be null");
		}

		this.instrumentId = instrumentId;
		this.instrumentName = instrumentName;

		prices = new TreeSet<PriceInfo>();
	}

	/**
	 * Returns the instrument id.
	 * 
	 * @return the instrument id
	 */
	public String getInstrumentId() {
		return instrumentId;
	}

	/**
	 * Returns the instrument name.
	 * 
	 * @return the instrument name
	 */
	public String getInstrumentName() {
		return instrumentName;
	}

	/**
	 * Returns the pricing information.
	 * 
	 * @return the price list
	 */
	public Set<PriceInfo> getPrices() {
		return prices;
	}

	/**
	 * Compares instrument based on the instrument Id
	 */

	@Override
	public int compareTo(Instrument other) {

		return instrumentId.compareTo(other.getInstrumentId());
	}

}
