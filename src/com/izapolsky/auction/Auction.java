package com.izapolsky.auction;

import java.util.TreeSet;

/**
 * This class represents an auction. At any given time it has one winner (unless
 * there are no bids)
 * 
 * @author ignat
 */
public class Auction {
	private TreeSet<Bid> bids = new TreeSet<Bid>();
	private String name;

	public Auction(String name) {
		this.name = name;
	}

	/**
	 * Adds a bid to current auction. Return auction for convenience to chain
	 * calls
	 * 
	 * @param bid
	 * @return current auction
	 */
	public Auction add(Bid bid) {
		bids.add(bid);
		return this;
	}

	/**
	 * Returns winning bid. If there are nore bids will return null
	 * 
	 * @return
	 */
	public Bid getWinningBid() {
		return bids.size() > 0 ? bids.first() : null;
	}

	/**
	 * Returns payment for a winning bidder.
	 * 
	 * @return
	 */
	public long getPayment() {
		Bid winner = getWinningBid();
		if (winner != null) {
			return winner.calculateWinnerBid(bids.higher(winner));
		}
		throw new IllegalStateException("No bidders in auction!");
	}

	@Override
	public String toString() {
		return String.format("Auction: %1$s, bids :", this, bids);
	}
}
