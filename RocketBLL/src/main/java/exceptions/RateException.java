package exceptions;



public class RateException extends Exception {

	private rocketDomain.RateDomainModel RateDomainModel;
	
	public RateException(rocketDomain.RateDomainModel RDM){
		this.RateDomainModel = RDM;
	}

	public rocketDomain.RateDomainModel getRateDomainModel() {
		return RateDomainModel;
	}
	
}
