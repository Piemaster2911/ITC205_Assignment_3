package unitTest.scenarioTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Use this class to run the test.
 * @author Norb
 *
 */
public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Initializing Test...\n\n");

        runTest(JUnitCore.runClasses(BBConfirmLoans.class));
        runTest(JUnitCore.runClasses(BBScanBook.class));
        
        System.out.println("Test Runner completed!");
    }
    
    public static void runTest(Result result) {
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("\nTest success for test class (true if no failures): " + result.wasSuccessful() + "\n");
    }
    
}
