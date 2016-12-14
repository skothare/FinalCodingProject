package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	/**
	 * Calculates an interest rate given a Credit Score.
	 * @param GivenCreditScore
	 * @return
	 * @throws RateException
	 */
	public static double getRate(int GivenCreditScore) throws RateException
	{
		
		RateDomainModel rt2 = new RateDomainModel();
		
		double Rate = 0;
		for (RateDomainModel rt : RateDAL.getAllRates() ){
			if(GivenCreditScore >= rt.getiMinCreditScore()){
				Rate = rt.getdInterestRate();
				System.out.println("Rate " + Rate);	
			}
		}
		if (Rate == 0.0){
			throw new RateException(rt2);
		}
		return Rate;		
	}
	/**
	 * Calculates the payment given the following variables.	
	 * @param r Percent Interest Rate per month.
	 * @param n Number of payments
	 * @param p Present value
	 * @param f Future value
	 * @param t Boolean
	 * @return
	 */
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return Math.abs(FinanceLib.pmt(r, n, p, f, t));
	}
}
