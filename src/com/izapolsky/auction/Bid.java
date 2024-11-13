package com.izapolsky.auction;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This class defines a bid in auction
 * 
 * @author ignat
 */
public class Bid implements Comparable<Bid> {

	private static final AtomicLong sequencer = new AtomicLong(0L);

	private String bidder;
	private long maxBid;
	// Normalised bid value - e.g. maximal value reachable by integer increments
	// from start
	private long maxBidNorm;
	private long sequenceNo;
	private long initialBid;
	private int autoIncrement;

	/**
	 * Creates bid object from given parameters.
	 * 
	 * @param bidder
	 * @param maxBid
	 * @param initialBid
	 * @param autoIncrement
	 * @throws IllegalArgumentException
	 *             in case initialBid > maxBid
	 */
	public Bid(String bidder, long maxBid, long initialBid, int autoIncrement)
			throws IllegalArgumentException {
		if (initialBid > maxBid) {
			throw new IllegalArgumentException(
					String
							.format(
									"Tried to create bid with starting amount [%1$s] greater than maxAmount [%2$s]",
									initialBid, maxBid));
		}

		if (autoIncrement <= 0) {
			throw new IllegalArgumentException(
					"Bids with negative increments are not allowed.");
		}
		this.bidder = bidder;
		this.maxBid = maxBid;
		this.initialBid = initialBid;
		this.autoIncrement = autoIncrement;
		sequenceNo = sequencer.incrementAndGet();
		maxBidNorm = ((maxBid - initialBid) / autoIncrement) * autoIncrement
				+ this.initialBid;
	}

	/**
	 * this method computes comparison to other bid, it will return negative
	 * value if our bid wins (e.g. has better parameters according to maxBidNorm
	 * or if it is a tie - by sequence number)
	 * 
	 * @throws IllegalArgumentException
	 *             in case other bid has same sequence no
	 */
	@Override
	public int compareTo(Bid other) throws IllegalArgumentException {
		if (this == other) {
			return 0;
		}
		if (sequenceNo == other.sequenceNo) {
			throw new IllegalArgumentException(
					String
							.format(
									"Presented with bid that was enetered with same sequence number. My : %1$s, other : %2$s",
									this, other));
		}
		if (maxBidNorm > other.maxBidNorm)
			return -1;
		if (maxBidNorm < other.maxBidNorm)
			return 1;
		if (sequenceNo < other.sequenceNo)
			return -1;
		if (sequenceNo > other.sequenceNo)
			return 1;

		// should be never reached
		return 0;
	}

	/**
	 * Calculates winning amount considering that we can only win if our max bid
	 * is bigger or we have lower sequence number with same max bid
	 * 
	 * @param nextBid
	 * @return
	 * @throws IllegalArgumentException
	 *             in case other bid wins against this bid
	 */
	public long calculateWinnerBid(Bid nextBid) throws IllegalArgumentException {

		if (nextBid == null) {
			return initialBid;
		}

		if (nextBid.maxBidNorm > maxBidNorm
				|| (nextBid.maxBidNorm == maxBidNorm && (sequenceNo > nextBid.sequenceNo || sequenceNo == nextBid.sequenceNo))) {
			throw new IllegalArgumentException(String.format(
					"Cannot win with %1$s against %2$s", this, nextBid));
		}
		if (nextBid.maxBidNorm == maxBidNorm) {
			return maxBidNorm;
		}
		long result = ((nextBid.maxBidNorm - initialBid) / autoIncrement + 1)
				* autoIncrement + initialBid;
		if (result > maxBidNorm) {
			result = maxBidNorm;
		}
		return result;
	}

	public String getBidder() {
		return bidder;
	}

	public long getMaxBidNorm() {
		return maxBidNorm;
	}

	@Override
	public String toString() {
		return String
				.format(
						"Bid [bidder :%1$s, initial amount:%2$s, increment:%3$s, maxBid:%4$s, sequence:%5$s, max bid normalised:%6$s]",
						bidder, initialBid, autoIncrement, maxBid, sequenceNo,
						maxBidNorm);
	}

}
