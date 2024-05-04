package Codigo_registration;

public class Registration{
	
	private String name, affiliation;
	private RegistrationKind kind;
	private int amountPayed;
	private boolean validated;
	
	
	public Registration(String name, RegistrationKind kind) {
		this.kind = kind;
		this.name = name;
	}
	
	public void pay (double amount) {		
		this.amountPayed+=amount;
	}

	public double getAmountPayed() {
		return this.amountPayed;
	}

	public double getTotalAmount() {
		return this.kind.getPrice();
	}

	public String getAffiliation() {
		return this.affiliation;
	}
	
	public void setAffiliation(String aff) {
		this.affiliation = aff;
	}

	public boolean getValidated() {
		return this.validated;
	}
	
	public String toString() {
		return "Reg. of: "+this.name;
	}

	public void setValidated(boolean b) {
		this.validated = b;
	}
	
	/**
	 * Sobreescritura del método equals para tratar como iguales a objetos de 
	 * esta clase con la misma cadena de texto en el campo "name"
	 *
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Registration other = (Registration) obj;

	    return name != null ? name.equals(other.name) : other.name == null;
	}

	/**
	 * Sobreescritura necesaria para sobreescribir equals de forma correcta
	 * 
	 */
	@Override
	public int hashCode() {
	    return (name != null) ? name.hashCode() : 0;
	}

}
