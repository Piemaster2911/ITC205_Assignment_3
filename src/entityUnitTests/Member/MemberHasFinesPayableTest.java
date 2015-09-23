package entityUnitTests.Member;

import static org.junit.Assert.*;
import library.entities.Member;

import org.junit.Test;

public class MemberHasFinesPayableTest {

Member member = new Member("Bob", "Smith", "012345678", "email@webmail.com", 1);
    
    /**
     * This test checks if member has fines payable AND has not reached loan limit. The test is considered 
     * successful only if the member has not exceeded loan limit.
     */
    @Test
    public void testMemberHasNoFinesPayable() {
        System.out.println("This test will demonstrate if member has no fines. "
                + "\nThe test will be valid and successful if the member has no fines.");
        Member testMember = member;
        testMember.addFine(0f);
        boolean condition = testMember.hasFinesPayable();
        
        if(condition)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has fines payable!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has no fines payable!");
        
        assertFalse(condition);
    }
    
    /**
     * This test checks if member has fines payable AND has reached loan limit. The test is considered 
     * successful only if the member has exceeded loan limit.
     */
    @Test
    public void testMemberHasFinesPayable() {
        System.out.println("This test will demonstrate if member has fines. "
                + "\nThe test will be valid and successful if the member has fines.");
        Member testMember = member;
        testMember.addFine(1f);
        boolean condition = testMember.hasFinesPayable();
        
        if(condition)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has fines payable!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has no fines payable!");
        
        assertTrue(condition);
    }

}
