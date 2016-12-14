package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.RateException;
import rocketDomain.RateDomainModel;
/**
 * Tests the Payment calculation done using FinanceLib methods.
 * Tests if a known interest rate is returned for a known credit score.
 * Tests if a Rate Exception is thrown if there are no rates for a given credit score.
 * @author skothare
 *
 */
public class rate_test {

	@Test
	public void Paymenttest() {
		assertEquals(RateBLL.getPayment(0.04/12, 360, 300000, 0, false), 1432.25, 0.02);
	}
	
	@Test
	public void testgetRate1() throws RateException {
		
		assertEquals(3.5, RateBLL.getRate(900), 0.002);
		assertEquals(4.5, RateBLL.getRate(690), 0.002);
		assertEquals(3.75, RateBLL.getRate(770), 0.001);
		assertEquals(5, RateBLL.getRate(600), 0.002);	
	
	}
	
	@Test(expected = RateException.class)
	public void testRateException() throws RateException{
		
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		System.out.println ("Rates size: " + rates.size());
		assert(rates.size() > 0);
		assertEquals(0, RateBLL.getRate(530), 0.002);
		
	}
}
