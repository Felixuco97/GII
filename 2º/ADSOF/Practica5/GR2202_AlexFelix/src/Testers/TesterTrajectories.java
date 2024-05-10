package Testers;

import java.util.List;

import Codigo_registration.Registration;

public class TesterTrajectories extends TesterStateChanges{
	public static void main(String[] args) {
		TesterTrajectories tsc = new TesterTrajectories();
		tsc.createRegistrations();
		tsc.changeRegistrations();
		tsc.displayTrajectories();
	}
	@Override
	protected void changeRegistrations() {
		super.changeRegistrations();
		this.johnDoe.setValidated(true);
		this.lisaMartin.setAffiliation("Arkham College");
		this.regState.updateStates();
	}
	protected void displayTrajectories() {
		for (Registration r : List.of(annSmith, johnDoe, lisaMartin))
			System.out.println(r+": "+this.regState.trajectory(r));
	}
}