package explorador.test.runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class JUnitRunnerHelper {
	public static Object runJUnit4WithTimeout(JUnitVersion version, Class<?> clazz, int timeout) {
		JUnitRunner jUnitRunner = null;
		boolean ret = false;
		try {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			jUnitRunner = new JUnitRunner(executor, clazz, JUnitVersion.JUNIT4);
			executor.execute(jUnitRunner);
			//if you need to block current thread
			ret = executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			if (!ret) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object result = ret && (jUnitRunner != null) ? jUnitRunner.getResult() : null;
		return result;
	}
	
	public static Object runJUnit5WithTimeout(JUnitVersion version, Class<?> clazz, SummaryGeneratingListener listener, int timeout) {
		JUnitRunner jUnitRunner = null;
		boolean ret = false;
		try {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			jUnitRunner = new JUnitRunner(executor, clazz, JUnitVersion.JUNIT5, listener);
			executor.execute(jUnitRunner);
			//if you need to block current thread
			ret = executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			if (!ret) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object result = ret && (jUnitRunner != null) ? jUnitRunner.getResult() : null;
		return result;
	}
}