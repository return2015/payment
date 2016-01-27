package com.returnsoft.payment.exception;

import java.io.Serializable;

public class PaymentNotFoundException extends Exception implements Serializable {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6793254603900438499L;

	public PaymentNotFoundException() {
		super("No se encontró el pago");
	}


}
