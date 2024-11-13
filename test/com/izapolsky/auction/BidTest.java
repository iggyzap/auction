package com.izapolsky.auction;

import static junit.framework.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class BidTest {

	private int num = 0;

	@Test
	public void testNormalise() {
		Bid bid = new Bid(getBidName(), 5, 0, 2);
		assertEquals(4, bid.getMaxBidNorm());
	}

	@Test
	public void testNormaliseMax() {
		Bid bid = new Bid(getBidName(), 5, 1, 2);
		assertEquals(5, bid.getMaxBidNorm());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStartValue() {
		Bid bid = new Bid(getBidName(), 5, 6, 2);
	}

	@Test
	public void testCompareGreaterByMaxBid() {
		Bid greater = new Bid(getBidName(), 5, 1, 2);
		Bid loser = new Bid(getBidName(), 4, 1, 1);
		assertEquals(-1, greater.compareTo(loser));
	}

	@Test
	public void testCompareGreaterBySequence() {
		Bid greater = new Bid(getBidName(), 5, 1, 2);
		Bid loser = new Bid(getBidName(), 5, 1, 2);
		assertEquals(-1, greater.compareTo(loser));
	}

	@Test
	public void testCompareEqualsToSame() {
		Bid bid = new Bid(getBidName(), 5, 1, 1);
		assertEquals(0, bid.compareTo(bid));
	}

	@Test
	public void testCompareLessByMaxBid() {
		Bid one = new Bid(getBidName(), 4, 1, 1);
		Bid greater = new Bid(getBidName(), 5, 1, 2);
		assertEquals(1, one.compareTo(greater));
	}

	@Test
	public void testCompareLessBySequence() {
		Bid greater = new Bid(getBidName(), 5, 1, 2);
		Bid loser = new Bid(getBidName(), 5, 1, 2);
		assertEquals(1, loser.compareTo(greater));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCompareSanityCheck() throws Exception {
		Field f = Bid.class.getDeclaredField("sequenceNo");
		f.setAccessible(true);
		Bid bid = new Bid(getBidName(), 5, 1, 1);
		Bid bid1 = new Bid(getBidName(), 5, 2, 2);
		f.set(bid, 1);
		f.set(bid1, 1);
		bid.compareTo(bid1);
	}

	@Test
	public void testCalculateWinningBid() {
		Bid winner = new Bid(getBidName(), 5, 0, 1);
		Bid nextCool = new Bid(getBidName(), 3, 2, 1);
		assertEquals(4, winner.calculateWinnerBid(nextCool));
	}

	@Test
	public void testCalculateWinningBidNonMax() {
		Bid winner = new Bid(getBidName(), 7, 3, 2);
		Bid nextCool = new Bid(getBidName(), 3, 2, 1);
		assertEquals(5, winner.calculateWinnerBid(nextCool));
	}

	@Test
	public void testCalculateWinningBidMax() {
		Bid winner = new Bid(getBidName(), 9, 3, 5);
		Bid nextCool = new Bid(getBidName(), 3, 2, 1);
		assertEquals(8, winner.calculateWinnerBid(nextCool));
	}

	@Test
	public void testCalculateWinningBySequence() {
		Bid winner = new Bid(getBidName(), 3, 2, 1);
		Bid nextCool = new Bid(getBidName(), 3, 2, 1);
		assertEquals(3, winner.calculateWinnerBid(nextCool));
	}

	@Test
	public void testCalculateWinningOneBid() {
		Bid winner = new Bid(getBidName(), 3, 1, 1);
		assertEquals(1, winner.calculateWinnerBid(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeIncrement() {
		Bid bid = new Bid("Strange person", 1, 0, -1);
	}

	private String getBidName() {
		return String.valueOf(num++);
	}
}
