package com.izapolsky.auction;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AuctionTest {
	private Auction auction;

	private String auctionName;
	private String winnerName;
	private long winningPayment;
	private Bid[] bids;

	@Parameters
	public static List<Object[]> data() {
		List<Object[]> result = new ArrayList<Object[]>();
		result.add(new Object[] {
				"Bicycle",
				"Amanda",
				85,
				new Bid[] { new Bid("Alice", 80, 50, 3),
						new Bid("Aaron", 82, 60, 2),
						new Bid("Amanda", 85, 55, 5) } });
		result.add(new Object[] {
				"Scooter",
				"Alice",
				722,
				new Bid[] { new Bid("Alice", 725, 700, 2),
						new Bid("Aaron", 725, 599, 15),
						new Bid("Amanda", 725, 625, 8) } });
		result.add(new Object[] {
				"Boat",
				"Aaron",
				3001,
				new Bid[] { new Bid("Alice", 3000, 2500, 500),
						new Bid("Aaron", 3100, 2800, 201),
						new Bid("Amanda", 3200, 2501, 247) } });
		result.add(new Object[] { "Test", "Monkey D Luffy", 0,
				new Bid[] { new Bid("Monkey D Luffy", 2, 0, 1) } });

		return Collections.unmodifiableList(result);
	}

	public AuctionTest(String auctionName, String winnerName,
			long winningPayment, Bid[] bids) {
		super();
		this.auctionName = auctionName;
		this.winnerName = winnerName;
		this.winningPayment = winningPayment;
		this.bids = bids;
	}

	@Before
	public void setUp() {
		auction = new Auction(auctionName);
		for (Bid bid : bids) {
			auction.add(bid);
		}
	}

	@Test
	public void testWinner() {
		assertEquals(winnerName, auction.getWinningBid().getBidder());
	}

	@Test
	public void testWinningAmount() {
		assertEquals(winningPayment, auction.getPayment());
	}
}
