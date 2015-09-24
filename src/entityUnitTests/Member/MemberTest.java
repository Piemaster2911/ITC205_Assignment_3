package entityUnitTests.Member;

import static org.junit.Assert.*;

import library.entities.Member;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberTest {

    IMember memberMock;
    Member member;
    String firstName = "Bob";
    String lastName = "Smith";
    String contactPhone = "01234567";
    String emailAddress = "email@webmail.com";
    int id = 1;
    
    
    @Before
    public void setUp() {
        memberMock = EasyMock.createMock(IMember.class);
        member = new Member(firstName, lastName, contactPhone, emailAddress, id);
    }
    
    @After
    public void refreshMocks() {
        EasyMock.reset(memberMock);
    }
    
    @Test
    public void testMemberConstructorValid() {
        EasyMock.expect(memberMock.getFirstName()).andReturn(member.getFirstName());
        EasyMock.expect(memberMock.getLastName()).andReturn(member.getLastName());
        EasyMock.expect(memberMock.getContactPhone()).andReturn(member.getContactPhone());
        EasyMock.expect(memberMock.getEmailAddress()).andReturn(member.getEmailAddress());
        EasyMock.expect(memberMock.getID()).andReturn(member.getID());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test will demonstrate construction of a member class in a valid manner"
                + "\nThis test simulates the creation of a new Member class by storing its visible names, "
                + "\nthen comparing them to the variables that carried the names that are used in storing "
                + "\nto the inputs for the member class construction.");
        
        String s = memberMock.getFirstName();
        assertEquals(firstName, s);
        if(firstName.equals(s))
            System.out.println("First Name: " + firstName + ", mock result: " + s + " -- PASS");
        else 
            System.out.println("First Name: " + firstName + ", mock result: " + s + " -- FAIL");
        
        s = memberMock.getLastName();
        assertEquals(lastName, s);
        if(lastName.equals(s))
            System.out.println("Last Name: " + lastName + ", mock result: " + s + " -- PASS");
        else
            System.out.println("Last Name: " + lastName + ", mock result: " + s + " -- FAIL");
        
        s = memberMock.getContactPhone();
        assertEquals(contactPhone, s);
        if(contactPhone.equals(s))
            System.out.println("Contact Phone: " + contactPhone + ", mock result: " + s + " -- PASS");
        else 
            System.out.println("Contact Phone: " + contactPhone + ", mock result: " + s + " -- FAIL");
        
        s = memberMock.getEmailAddress();
        assertEquals(emailAddress, s);
        if(emailAddress.equals(s))
            System.out.println("Email Address: " + emailAddress + ", mock result: " + s + " -- PASS");
        else
            System.out.println("Email Address: " + emailAddress + ", mock result: " + s + " -- FAIL");
        
        int i = memberMock.getID();
        assertEquals(id, i);
        if(id == i)
            System.out.println("Last Name: " + id + ", mock result: " + i + " -- PASS");
        else
            System.out.println("Last Name: " + id + ", mock result: " + i + " -- FAIL");
    }
    
    @Test
    public void testHasNoOverDueLoans() {
        Member testMember = member;
        
        ILoan loan = EasyMock.createMock(ILoan.class);
        testMember.addLoan(loan);
        
        EasyMock.expect(loan.isOverDue()).andReturn(false);
        EasyMock.replay(loan);
        
        EasyMock.expect(memberMock.hasOverDueLoans()).andReturn(testMember.hasOverDueLoans());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has no overdue loans, which "
                + "\nshould result in the test being true if the boolean for having overdue loans is false.");
        
        boolean b = memberMock.hasOverDueLoans();
        
        assertFalse(b);
        
        if(b)
            System.out.println("This member has overdue loans! -- FAIL");
        else
            System.out.println("This member has no overdue loans! -- PASS");
        
    }
    
   @Test
   public void testHasOverDueLoans() {
       Member testMember = member;
       
       ILoan loan = EasyMock.createMock(ILoan.class);
       testMember.addLoan(loan);
       
       EasyMock.expect(loan.isOverDue()).andReturn(true);
       EasyMock.replay(loan);
       
       EasyMock.expect(memberMock.hasOverDueLoans()).andReturn(testMember.hasOverDueLoans());
       EasyMock.replay(memberMock);
        
       System.out.println("\nThis test demonstrates a scenario where the member has overdue loans, which "
                + "\nshould result in the test being true if the boolean for having overdue loans is true.");
        
       boolean b = memberMock.hasOverDueLoans();
        
       assertTrue(b);
        
       if(b)
            System.out.println("This member has overdue loans! -- PASS");
       else
            System.out.println("This member has no overdue loans! -- FAIL");
    }
   
   @Test
    public void testHasReachedLoanLimit() {
       EasyMock.reset(memberMock);
       Member testMember = member;
       
       ILoan loan = EasyMock.createMock(ILoan.class);
       testMember.addLoan(loan);
       testMember.addLoan(loan);
       testMember.addLoan(loan);
       testMember.addLoan(loan);
       testMember.addLoan(loan);
       testMember.addLoan(loan);
       
       EasyMock.expect(memberMock.hasReachedLoanLimit()).andReturn(testMember.hasReachedLoanLimit());
       EasyMock.replay(memberMock);
       
       System.out.println("\nThis test demonstrates a scenario where the member has not exceeded the limit "
               + "\nimposed on the number of loans a member can have at any one time.");
       
       boolean b = memberMock.hasReachedLoanLimit();
       
       assertTrue(b);
       
       if(b)
           System.out.println("This member has exceeded the loan limit! -- PASS");
       else
           System.out.println("This member has not exceeded the loan limit! -- FAIL");
    }

    @Test
    public void testHasNotReachedLoanLimit() {
        Member testMember = member;
        
        ILoan loan = EasyMock.createMock(ILoan.class);
        testMember.addLoan(loan);
        
        EasyMock.expect(memberMock.hasReachedLoanLimit()).andReturn(testMember.hasReachedLoanLimit());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has not exceeded the limit "
                + "\nimposed on the number of loans a member can have at any one time.");
        
        boolean b = memberMock.hasReachedLoanLimit();
        
        assertFalse(b);
        
        if(b)
            System.out.println("This member has exceeded the loan limit! -- FAIL");
        else
            System.out.println("This member has not exceeded the loan limit! -- PASS");
    }

    @Test
    public void testHasNoFinesPayable() {
        Member testMember = member;
        float fine = 0f;
        testMember.addFine(fine);
        
        EasyMock.expect(memberMock.hasFinesPayable()).andReturn(testMember.hasFinesPayable());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has no fines on this class instance,"
                + "\nand the test will be passed if the function results with false.");
        
        boolean b = memberMock.hasFinesPayable();
        
        assertFalse(b);
        
        if(b)
            System.out.println("This member has fines payable! -- FAIL");
        else
            System.out.println("This member has no fines payable! -- PASS");
    }
    
    @Test
    public void testHasFinesPayable() {
        Member testMember = member;
        float fine = 8f;
        testMember.addFine(fine);
        
        EasyMock.expect(memberMock.hasFinesPayable()).andReturn(testMember.hasFinesPayable());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has any fines on this class instance,"
                + "\nand the test will be passed if the function results with true.");
        
        boolean b = memberMock.hasFinesPayable();
        
        assertTrue(b);
        
        if(b)
            System.out.println("This member has fines payable! -- PASS");
        else
            System.out.println("This member has no fines payable! -- FAIL");
    }

    @Test
    public void testHasReachedFineLimit() {
        // TODO
    }

    @Test
    public void testGetFineAmount() {
        // TODO
    }

    @Test
    public void testAddFine() {
        // TODO
    }

    @Test
    public void testPayFine() {
        // TODO
    }

    @Test
    public void testAddLoan() {
        // TODO
    }

    @Test
    public void testGetLoans() {
        // TODO
    }

    @Test
    public void testRemoveLoan() {
        // TODO
    }

    @Test
    public void testGetState() {
        // TODO
    }

    @Test
    public void testGetFirstName() {
        // TODO
    }

    @Test
    public void testGetLastName() {
        // TODO
    }

    @Test
    public void testGetContactPhone() {
        // TODO
    }

    @Test
    public void testGetEmailAddress() {
        // TODO
    }

    @Test
    public void testGetID() {
        // TODO
    }

}
