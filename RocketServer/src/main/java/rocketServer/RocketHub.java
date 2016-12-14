package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;

/**
 * 
 * @author skothare
 *
 */
public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}
	/**
	 * Updates the instance of LoanRequest; assigns values to the key variables 
	 * in LoanRequest.
	 * Assumes a future value of 0; all loan will be paid.
	 * Uses the Amount for the present value of the loan to be paid, namely the Loan due.
	 * Monthly Percent Interest Rate.
	 */
	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			try {
				lq.setdRate(RateBLL.getRate(lq.getiCreditScore()));
				lq.setdPayment(Math.abs(RateBLL.getPayment(lq.getdRate()/(12*100),
						lq.getiTerm(), lq.getdAmount(), 0, false)));
				
			}
			catch (RateException e){
				System.out.println("Warning: Interest Rate for the given "
						+ "Credit Score isn't available.");
				sendToAll(e);
				return;
			}
			catch (Exception e){
				System.out.println("Warning: Check your Values. " + e);
				sendToAll(e);
				return;
			}
			sendToAll(lq);
		}
	}
}
