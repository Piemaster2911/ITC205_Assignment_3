package entityUnitTests.Member;

import static org.junit.Assert.*;
import library.entities.Member;
import org.junit.Test;

public class MemberHasReachedFineLimitTest {

    Member member = new Member("Bob", "Smith", "012345678", "email@webmail.com", 1);
    
    /**
     * This test checks if member has fines payable AND has not reached loan limit. The test is considered 
     * successful only if the member has not exceeded loan limit.
     */
    @Test
    public void testMemberHasPayableNotReachedFinesLimit() {
        System.out.println("This test will demonstrate if member has not reached the fines limit. "
                + "\nThe test will be valid and successful if the member has not exceeded the fines limit.");
        Member testMember = member;
        testMember.addFine(5f);
        boolean condition = testMember.hasReachedFineLimit();
        
        if(condition)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has exceeded fine limit!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has not exceeded fine limit!");
        
        assertFalse(condition);
    }
    
    /**
     * This test checks if member has fines payable AND has reached loan limit. The test is considered 
     * successful only if the member has exceeded loan limit.
     */
    @Test
    public void testMemberHasPayableReachedFinesLimit() {
        System.out.println("This test will demonstrate if member has exceeded the fines limit. "
                + "\nThe test will be valid and successful if the member exceeds the fines limit.");
        Member testMember = member;
        testMember.addFine(13f);
        boolean condition = testMember.hasReachedFineLimit();
        
        if(condition)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has exceeded fine limit!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has not exceeded fine limit!");
        
        assertTrue(condition);
    }

}