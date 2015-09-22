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
   //     Result result = JUnitCore.runClasses(MemberConstructorTest.class);
        runTest(JUnitCore.runClasses(MemberConstructorTest.class));
        runTest(JUnitCore.runClasses(MemberHasOverDueLoansTest.class));
        runTest(JUnitCore.runClasses(MemberHasReachedFineLimitTest.class));
        runTest(JUnitCore.runClasses(MemberHasReachedLoanLimitTest.class));
    }
    
    public static void runTest(Result result) {
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Test overall (true if no failures): " + result.wasSuccessful() + "\n");
    }
}
