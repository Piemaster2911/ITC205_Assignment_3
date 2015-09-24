package entityUnitTests.Member;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import library.entities.Member;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.easymock.EasyMock;
import org.easymock.Capture;
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
    public void memberConstructorValid() {
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
        ILoan loan = EasyMock.createMock(ILoan.class);
        member.addLoan(loan);
        
        EasyMock.expect(loan.isOverDue()).andReturn(false);
        EasyMock.replay(loan);
        
        EasyMock.expect(memberMock.hasOverDueLoans()).andReturn(member.hasOverDueLoans());
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
       
       ILoan loan = EasyMock.createMock(ILoan.class);
       member.addLoan(loan);
       
       EasyMock.expect(loan.isOverDue()).andReturn(true);
       EasyMock.replay(loan);
       
       EasyMock.expect(memberMock.hasOverDueLoans()).andReturn(member.hasOverDueLoans());
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
       
       ILoan loan = EasyMock.createMock(ILoan.class);
       member.addLoan(loan);
       member.addLoan(loan);
       member.addLoan(loan);
       member.addLoan(loan);
       member.addLoan(loan);
       member.addLoan(loan);
       
       EasyMock.expect(memberMock.hasReachedLoanLimit()).andReturn(member.hasReachedLoanLimit());
       EasyMock.replay(memberMock);
       
       System.out.println("\nThis test demonstrates a scenario where the member has not exceeded the limit "
               + "\nimposed on the number of loans a member can have at any one time.");
       
       boolean b = memberMock.hasReachedLoanLimit();
       
       assertTrue(b);
       
       if(b)
           System.out.println("This member has exceeded the loan limit with " + member.getLoans().size() + " loans! -- PASS");
       else
           System.out.println("This member has not exceeded the loan limit with " + member.getLoans().size() + " loans! -- FAIL");
    }

    @Test
    public void testHasNotReachedLoanLimit() {
        
        ILoan loan = EasyMock.createMock(ILoan.class);
        member.addLoan(loan);
        
        EasyMock.expect(memberMock.hasReachedLoanLimit()).andReturn(member.hasReachedLoanLimit());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has not exceeded the limit "
                + "\nimposed on the number of loans a member can have at any one time.");
        
        boolean b = memberMock.hasReachedLoanLimit();
        
        assertFalse(b);
        
        if(b)
            System.out.println("This member has exceeded the loan limit with " + member.getLoans().size() + " loans! -- FAIL");
        else
            System.out.println("This member has not exceeded the loan limit with " + member.getLoans().size() + " loans! -- PASS");
    }

    @Test
    public void testHasNoFinesPayable() {
        float fine = 0f;
        member.addFine(fine);
        
        EasyMock.expect(memberMock.hasFinesPayable()).andReturn(member.hasFinesPayable());
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
        float fine = 8f;
        member.addFine(fine);
        
        EasyMock.expect(memberMock.hasFinesPayable()).andReturn(member.hasFinesPayable());
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
        float fine = 12f;
        member.addFine(fine);
        
        EasyMock.expect(memberMock.hasReachedFineLimit()).andReturn(member.hasReachedFineLimit());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has reached over the fine"
                + "\nlimit with the fines incurred.");
        
        boolean b = memberMock.hasReachedFineLimit();
        
        assertTrue(b);
        
        if(b)
            System.out.println("This member has exceeded the fine limit of " + Member.FINE_LIMIT + " with " + member.getFineAmount() + "! -- PASS");
        else
            System.out.println("This member has not exceeded the fine limit of " + Member.FINE_LIMIT + " with " + member.getFineAmount() + "! -- FAIL");
    }
    
    @Test
    public void testHasNotReachedFineLimit() {
        float fine = 2f;
        member.addFine(fine);
        
        EasyMock.expect(memberMock.hasReachedFineLimit()).andReturn(member.hasReachedFineLimit());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a scenario where the member has not reached over the fine limit with the"
                + "\nfines incurred.");
        
        boolean b = memberMock.hasReachedFineLimit();
        
        assertFalse(b);
        
        if(b)
            System.out.println("This member has exceeded the fine limit of " + Member.FINE_LIMIT + " with " + member.getFineAmount() + "! -- FAIL");
        else
            System.out.println("This member has not exceeded the fine limit of " + Member.FINE_LIMIT + " with " + member.getFineAmount() + "! -- PASS");
    }

    @Test
    public void testGetFineAmount() {
        float fines = 2f;
        member.addFine(fines);
        
        EasyMock.expect(memberMock.getFineAmount()).andReturn(member.getFineAmount());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test will return the value of a fine amount. The test is successful if the returned fine amount is equal to the"
                + "\nvariable fine amount");
        
        boolean b;
        if(fines == memberMock.getFineAmount()) {
            b = true;
            System.out.println("The amount " + member.getFineAmount() + " match the fine given from variable fines " + fines + "! -- PASS");
        }
        else {
            b = false;
            System.out.println("The amount " + member.getFineAmount() + " does not match the fine given from variable fines " + fines + "! -- FAIL");
        }
        assertTrue(b);
    }

    @Test
    public void testAddFine() {
        float fines = 2f;
        
        float previousFine = member.getFineAmount();
        member.addFine(fines);
        
        System.out.println("\nThis test will add a fine value into the member class, then the fine value of the member class is compared to its value before"
                + "\nthe fine value is added.");
        
        assertNotEquals(previousFine, member.getFineAmount());
        
        System.out.print("Fine added: " + fines + ", fine value before addFine(): " + previousFine + ", fine value after addFine(): " + member.getFineAmount());
        if(fines == previousFine)
            System.out.println(" -- FAIL");
        else
            System.out.println(" -- PASS");
    }

    @Test
    public void testPaySmallFine() {
        float fines = 5f;
        float payment = 2f;
        
        member.addFine(fines);
        
        System.out.println("\nThus test demonstrates a partial payment of a fine. The test is successful if the fine value after payment is lower than its "
                + "\nprevious value.");
        
        boolean b;
        member.payFine(payment);
        
        System.out.print("Fines value: " + fines + ", Payment value: " + payment + ", Fines value after payment: " + member.getFineAmount());
        
        if(member.getFineAmount() < fines) {
            b = true;
            System.out.println(" -- PASS");
        }
        else {
            b = false;
            System.out.println(" -- FAIL");
        }
        
        assertTrue(b);
    }
    
    @Test
    public void testPayNoFine() {
        float fines = 5f;
        float payment = 0f;
        
        member.addFine(fines);
        
        System.out.println("\nThus test demonstrates a 0 value payment of a fine. The test is successful if the fine value after payment remains the same.");
        
        boolean b;
        member.payFine(payment);
        
        System.out.print("Fines value: " + fines + ", Payment value: " + payment + ", Fines value after payment: " + member.getFineAmount());
        
        if(member.getFineAmount() == fines) {
            b = true;
            System.out.println(" -- PASS");
        }
        else {
            b = false;
            System.out.println(" -- FAIL");
        }
        
        assertTrue(b);
    }
    
    @Test
    public void testPayCompleteFine() {
        float fines = 5f;
        float payment = 5f;
        
        member.addFine(fines);
        
        System.out.println("\nThus test demonstrates a complete payment of a fine. The test is successful if the fine value after payment is at 0.");
        
        boolean b;
        member.payFine(payment);
        
        System.out.print("Fines value: " + fines + ", Payment value: " + payment + ", Fines value after payment: " + member.getFineAmount());
        
        if(member.getFineAmount() == 0f) {
            b = true;
            System.out.println(" -- PASS");
        }
        else {
            b = false;
            System.out.println(" -- FAIL");
        }
        
        assertTrue(b);
    }
    
    @Test
    public void testPayOverCompleteFine() throws IllegalArgumentException {
        float fines = 5f;
        float payment = 10f;
        
        member.addFine(fines);
        
        System.out.println("\nThus test demonstrates an over payment of a fine. The test is successful if the function "
                + "\ngives out an exception and does not change the fine value.");
        
        boolean b;
        member.payFine(payment);
        
        System.out.print("Fines value: " + fines + ", Payment value: " + payment + ", Fines value after payment: " + member.getFineAmount());
        
        if(member.getFineAmount() == fines) {
            b = true;
           
            System.out.println(" -- PASS");
        }
        else {
            b = false;
            System.out.println(" -- FAIL");
        }
        
        assertTrue(b);
    }
    
    @Test
    public void testPayNegativeFine() {
        float fines = 5f;
        float payment = -5f;
        
        member.addFine(fines);
        
        System.out.println("\nThus test demonstrates a negative payment of a fine. The test is successful if the function "
                + "\ngives out an exception and does not change the fine value.");
        
        boolean b;
        member.payFine(payment);
        
        System.out.print("Fines value: " + fines + ", Payment value: " + payment + ", Fines value after payment: " + member.getFineAmount());
        
        if(member.getFineAmount() == fines) {
            b = true;
           
            System.out.println(" -- PASS");
        }
        else {
            b = false;
            System.out.println(" -- FAIL");
        }
        
        assertTrue(b);
    }

    @Test
    public void testAddLoan() {
        ILoan loan = EasyMock.createMock(ILoan.class);
        member.addLoan(loan);
        
        EasyMock.expect(memberMock.getLoans()).andReturn(member.getLoans());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates the process where a loan is added.");
        
        List<ILoan> loan2 = memberMock.getLoans();
        boolean b;
        
        if(loan2.isEmpty()) {
            b = false;
            System.out.println("Loan created but does not exist! -- FAIL");
        }
        else {
            b = true;
            System.out.println("Loan created and exist! -- PASS");
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetLoans() {
        ILoan loan = EasyMock.createMock(ILoan.class);
        member.addLoan(loan);
        member.addLoan(loan);
        member.addLoan(loan);
        
        EasyMock.expect(memberMock.getLoans()).andReturn(member.getLoans());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates a function process where"
                + "\nthe list of loans are returned. This test is successful "
                + "\nif the loans list is returned and exists.");
        
        List<ILoan> loansList = memberMock.getLoans();
        boolean b;
        
        if(loansList.isEmpty()) {
            b = false;
            System.out.println("The loans list is empty. -- FAIL");
        }
        else {
            b = true;
            System.out.println("The loans list is not empty and contains " + loansList.size() + " elements. -- PASS");
        }
        
        assertTrue(b);
    }

    @Test
    public void testRemoveLoan() {
        ILoan loan = EasyMock.createMock(ILoan.class);
        member.addLoan(loan);
        member.addLoan(loan);
        member.addLoan(loan);
        
        float initLoanAmount = member.getLoans().size();
        
        member.removeLoan(loan);
        
        float loanAmount = member.getLoans().size();
        
        System.out.println("\nThis test demonstrates the procedure for removing a loan that is identical to the"
                + "\nloan provided as input to the function removeLoan().");
        
        boolean b;
        
        if(loanAmount < initLoanAmount) {
            b = true;
            System.out.println("There are now only " + loanAmount + " loans remaining for member out of an initial of " + initLoanAmount + " loans! -- PASS");
        }
        else {
            b = false;
            System.out.println("There are now only " + loanAmount + " loans remaining for member out of an initial of " + initLoanAmount + " loans! -- FAIL");
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetState() {
        EasyMock.expect(memberMock.getState()).andReturn(member.getState());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates obtainment and sending of the state of member class.");
        
        boolean b;
        EMemberState memberState = memberMock.getState();
        
        if(memberState.equals(member.getState())) {
            System.out.println("Member state " + memberState.toString() + " matches the origin member state. -- PASS");
            b = true;
        }
        else {
            System.out.println("Member state " + memberState.toString() + " does not match the origin member state. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetFirstName() {
        EasyMock.expect(memberMock.getFirstName()).andReturn(member.getFirstName());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates sending of firstName variable's value from origin to destination.");
        
        boolean b;
        String s = memberMock.getFirstName();
        
        if(member.getFirstName().equals(s)) {
            System.out.println("First Name " + s + " match origin string value. -- PASS");
            b = true;
        }
        else {
            System.out.println("First Name " + s + " does not match origin string value. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetLastName() {
        EasyMock.expect(memberMock.getLastName()).andReturn(member.getLastName());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates sending of lastName variable's value from origin to destination.");
        
        boolean b;
        String s = memberMock.getLastName();
        
        if(member.getLastName().equals(s)) {
            System.out.println("Last Name " + s + " match origin string value. -- PASS");
            b = true;
        }
        else {
            System.out.println("Last Name " + s + " does not match origin string value. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetContactPhone() {
        EasyMock.expect(memberMock.getContactPhone()).andReturn(member.getContactPhone());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates sending of contactPhone variable's value from origin to destination.");
        
        boolean b;
        String s = memberMock.getContactPhone();
        
        if(member.getContactPhone().equals(s)) {
            System.out.println("Contact Phone " + s + " match origin string value. -- PASS");
            b = true;
        }
        else {
            System.out.println("Contact Phone " + s + " does not match origin string value. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetEmailAddress() {
        EasyMock.expect(memberMock.getEmailAddress()).andReturn(member.getEmailAddress());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates sending of emailAddress variable's value from origin to destination.");
        
        boolean b;
        String s = memberMock.getEmailAddress();
        
        if(member.getEmailAddress().equals(s)) {
            System.out.println("Email Address " + s + " match origin string value. -- PASS");
            b = true;
        }
        else {
            System.out.println("Email Address " + s + " does not match origin string value. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testGetID() {
        EasyMock.expect(memberMock.getID()).andReturn(member.getID());
        EasyMock.replay(memberMock);
        
        System.out.println("\nThis test demonstrates sending of id variable's value from origin to destination.");
        
        boolean b;
        int i = memberMock.getID();
        
        if(member.getID() == i) {
            System.out.println("ID " + i + " match origin string value. -- PASS");
            b = true;
        }
        else {
            System.out.println("ID " + i + " does not match origin string value. -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

}
