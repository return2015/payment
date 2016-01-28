package com.returnsoft.payment.exception;

public class EmployeeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3776669477847376316L;
	
	public EmployeeNotFoundException() {
		super("No se encontró el empleado");
	}

}
