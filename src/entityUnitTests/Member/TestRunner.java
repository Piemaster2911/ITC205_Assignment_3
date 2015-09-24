package entityUnitTests.Member;

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
   //     Result result = JUnitCore.runClasses(MemberConstructorTest.class);
   //     runTest(JUnitCore.runClasses(MemberConstructorTest.class));
       /* runTest(JUnitCore.runClasses(MemberHasOverDueLoansTest.class));
        runTest(JUnitCore.runClasses(MemberHasReachedFineLimitTest.class));
        runTest(JUnitCore.runClasses(MemberHasReachedLoanLimitTest.class));
        runTest(JUnitCore.runClasses(MemberHasFinesPayableTest.class));
        runTest(JUnitCore.runClasses(MemberAddFineTest.class));
        runTest(JUnitCore.runClasses(MemberPayFineTest.class));*/
        runTest(JUnitCore.runClasses(MemberTest.class));
        
        System.out.println("Test Runner completed!");
    }
    
    public static void runTest(Result result) {
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("\nTest success for test class (true if no failures): " + result.wasSuccessful() + "\n");
    }
    
}
