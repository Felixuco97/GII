package explorador.test.runner;

import java.util.List;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import explorador.test.CaminoTester;
import explorador.test.ExploradorTester;
import explorador.test.HadaTester;
import explorador.test.HechiceroTester;
import explorador.test.PosadaTester;
import explorador.test.SimulacionTester;
import explorador.test.TrampaTester;

public class ExecJUnitRunner {
	
	private static final int TEST_TIMEOUT = 100000;
	private static SummaryGeneratingListener listener;
	private static void lanzarPruebas(Class<?> clazz) {
		System.out.println("Testeando la clase "+ clazz.getName());
		listener = new SummaryGeneratingListener();
		Object ret = JUnitRunnerHelper.runJUnit5WithTimeout(JUnitVersion.JUNIT5, clazz, listener,TEST_TIMEOUT);
		if(ret == null) {
			System.err.println("Saliendo porque el resultado es null");
			return;
		}
		if (!(ret instanceof TestExecutionSummary)) {
			System.err.println("Saliendo porque el resultado no es TestExecutionSummary");
			return;
		}
		TestExecutionSummary result = (TestExecutionSummary) ret;
		List<Failure> failures = result.getFailures();
		/** FAILED TESTS **/
		 for (Failure failure : failures) {
	            System.err.println("---- " + failure.getTestIdentifier().getDisplayName());
	            System.err.println("---- " + failure.getException().getMessage());
	    }
		 System.out.println(result.getTestsSucceededCount() + " fueron correctos y " + result.getTestsFailedCount() + " fallaron");
	}
	public static void main(String[] args) {
		
		 Class<?>[] clases = {ExploradorTester.class, CaminoTester.class, HadaTester.class, HechiceroTester.class, PosadaTester.class, TrampaTester.class, SimulacionTester.class};
		 for(Class<?> c: clases) {
			 lanzarPruebas(c);
		 }
		
	}
}
