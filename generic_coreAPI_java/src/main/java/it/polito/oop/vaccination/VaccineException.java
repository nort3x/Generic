package it.polito.oop.vaccination;

public class VaccineException extends Exception {
	private static final long serialVersionUID = 1L;
	public VaccineException() {super("Vaccine system error");}
	public VaccineException(String msg ) {super(msg);}
}
