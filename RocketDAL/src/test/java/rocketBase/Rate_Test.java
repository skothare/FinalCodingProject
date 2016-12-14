package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rocketDomain.RateDomainModel;

/**
 * Tests if all known Credit Scores is returned using getiMinCreditScore
 * Tests if all known Interest Rates is returned using getdInterestRate
 * @author skothare
 *
 */
public class Rate_Test {
	
	// RateException test is included in the rate_test class under RocketBLL
	
	@Test
	public void testKnown() {
		
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		System.out.println ("Rates size: " + rates.size());
		assert(rates.size() > 0);
		assertEquals(rates.get(3).getdInterestRate(),3.75, 0.002);
		assertEquals(rates.get(4).getdInterestRate(),3.5, 0.002);
		assertEquals(rates.get(2).getdInterestRate(),4.0, 0.002);
		assertEquals(rates.get(1).getdInterestRate(),4.5, 0.002);
		assertEquals(rates.get(0).getdInterestRate(),5.0, 0.002);
		
	}
	@Test
	public void testiMinCreditScore(){
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		System.out.println ("Rates size: " + rates.size());
		assert(rates.size() > 0);
		
		assertEquals(rates.get(0).getiMinCreditScore(), 600);
		assertEquals(rates.get(1).getiMinCreditScore(), 650);
		assertEquals(rates.get(2).getiMinCreditScore(), 700);
		assertEquals(rates.get(3).getiMinCreditScore(), 750);
		assertEquals(rates.get(4).getiMinCreditScore(), 800);
	}
	
	
}
