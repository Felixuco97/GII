package AditionalTesters;

import java.util.List;
import Codigo_registration.*;
import Objetos.*;
import static Codigo_registration.RegistrationState.*;
import static Codigo_registration.RegistrationKind.*;

public class Testers {

    protected ObjectStateTracker<Registration, RegistrationState> regState;
    protected Registration juanDiaz, joseAntonio, antonioFernandez, elisaMorales, nachoLopez;

    public static void main(String[] args){

        Testers tests = new Testers();
        tests.StateChangesTester();
        tests.RepeatedObjectsTester();
        tests.TrajectoriesTester();
    }
    
    public void StateChangesTester(){

        this.regState = new ObjectStateTracker<>(RegistrationState.values());
        regState.withState(PAYED, r -> r.getAmountPayed() == r.getTotalAmount() && !r.getValidated())
                .withState(STARTED, r -> r.getAffiliation() == null && !r.getValidated())
                .withState(FILLED, r -> r.getAffiliation() != null && !r.getValidated())
                .withState(VALIDATED, r -> r.getAmountPayed() == 0 && r.getValidated())
                .withState(FINISHED, r -> r.getAmountPayed() == r.getTotalAmount() && r.getValidated())
                .elseState(REJECTED);
            
        this.juanDiaz = new Registration("Juan Diaz", FULL);
        this.joseAntonio = new Registration("Jose Antonio", STUDENT);
        this.antonioFernandez = new Registration("Antonio Fernandez", MEMBER);
        this.elisaMorales = new Registration("Elisa Morales", FULL);
        this.nachoLopez = new Registration("Nacho Lopez", MEMBER);
        this.regState.addObjects(juanDiaz, joseAntonio, antonioFernandez, elisaMorales, nachoLopez);

        System.out.println(this.regState);

        this.juanDiaz.setAffiliation("Autonomous University of Madrid"); //now it is filled
        this.joseAntonio.setValidated(true); //becomes validated
        this.antonioFernandez.pay(MEMBER.getPrice()); //becomes payed
        this.elisaMorales.pay(FULL.getPrice());
        this.elisaMorales.setValidated(true); //becomes finished
        this.nachoLopez.setValidated(true);
        this.nachoLopez.pay(556); //becomes rejected
        this.regState.updateStates();

        System.out.println(this.regState);
    }

    public void RepeatedObjectsTester(){

        this.regState.addObjects(new Registration("Juan Diaz", FULL)); // Discarded, since repeated
        this.regState.addObjects(new Registration("Jose Antonio", STUDENT)); // Discarded, since repeated
        this.regState.addObjects(new Registration("Nacho Lopez", MEMBER)); // Discarded, since repeated
        System.out.println(this.regState);
    }

    public void TrajectoriesTester(){

		this.elisaMorales.setValidated(true);
        this.nachoLopez.pay(344);
		this.regState.updateStates();

        System.out.println(this.regState);

		for (Registration r : List.of(juanDiaz, joseAntonio, antonioFernandez, elisaMorales, nachoLopez))
			System.out.println(r+": "+this.regState.trajectory(r));
    }
}
