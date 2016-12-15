package rocket.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import eNums.eAction;
import exceptions.RateException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController implements Initializable{

	private MainApp mainApp;
	
	
	@FXML
	private TextField txtIncome;
	@FXML
	private TextField txtExpenses;
	@FXML
	private TextField txtCreditScore;
	@FXML
	private TextField txtHouseCost;
	
	@FXML
	private ComboBox cmbTerm;
	@FXML
	private ComboBox cmbDP;
	
	@FXML
	private Label creditScoreLabel;
	@FXML
	private Label houseCostLabel;
	@FXML
	private Label expensesLabel;
	@FXML
	private Label termLabel;
	@FXML
	private Label incomeLabel;
	@FXML
	private Label downPaymentLabel;
	@FXML
	private Label lblDownPaymentCombo;
	@FXML
	private Label showErrorMessagesLabel;
	@FXML
	private Label lblMortgagePayment;
	
	@FXML
	private Button btnCalculateLoanPayment;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void btnCalculatePayment(ActionEvent event) throws NumberFormatException, 
	RateException
	{
		Object message = null;
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		
		try {
		// Set Income
		lq.setIncome(Double.parseDouble(txtIncome.getText()));
		// Set Credit Score
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		// Set term
		lq.setiTerm(Integer.parseInt(cmbTerm.getValue().toString()));
		// Set Down Payment: House Cost * Percent Down Payment
		lq.setiDownPayment((int)(Integer.parseInt(txtHouseCost.getText())
				*Double.parseDouble(cmbDP.getValue().toString())/100));
		// Set Expenses
		lq.setExpenses(Double.parseDouble(txtExpenses.getText()));
		
		
		// Set Rate
		lq.setdRate(RateBLL.getRate(Integer.parseInt(txtCreditScore.getText())));
		
		// Amount owed = Cost of the House - Down Payment
		double dAmount = Double.parseDouble(txtHouseCost.getText())-
				lq.getiDownPayment();
		lq.setdAmount(dAmount);
		}
		catch (RateException e){
			showErrorMessagesLabel.setText("NO RATE IS AVAILABLE FOR "
					+ "THIS CREDIT SCORE.");
			return;
		}
		catch (NumberFormatException e){
			showErrorMessagesLabel.setText("PLEASE ROUND THE"
					+ " NUMBER TO THE NEAREST INTEGER.");
		}
		//	Set the loan request details...  rate, term, amount, credit score, 
		//downpayment

		a.setLoanRequest(lq);
		
		//	Sent lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		LoanRequest lq = new LoanRequest();
		double dPayment = Math.round(lRequest.getdPayment() * 100.0)/100.0;
		System.out.println(dPayment);
		double dRate = lRequest.getdRate();
		if (dPayment < lRequest.getIncome() * 0.28){
			if (dPayment < (lRequest.getIncome() * lRequest.getExpenses()) * 0.36){
				lblMortgagePayment.setText("Your Mortgage Payment  per month will be $ " 
						+ dPayment + " at a rate of " + lRequest.getdRate() + 
						"% per annum");
				downPaymentLabel.setText("Your down payment as per " + 
						Double.parseDouble(cmbDP.getValue().toString()) + 
						"% of the House Cost will be " + 
						"$" + lRequest.getiDownPayment());
			}
		}
		else{
			lblMortgagePayment.setText("HOUSE COST IS TOO HIGH.");
		}
	}
	
	/**
	 * Add items in the ComboBox via an Observable List.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    cmbTerm.getItems().addAll("180", "360");
	    cmbDP.getItems().addAll("0", "5", "15", "20", "25");
	}

	
}
