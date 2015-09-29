package unitTest.integrationTest;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberEntityIntegrationTest {
    Member member;
    String firstName;
    String lastName;
    String contactPhone;
    String emailAddress;
    int id;
    List<ILoan> loanList;

    
    @Before
    public void setUp() throws Exception {
        firstName = "Bob";
        lastName = "Smith";
        contactPhone = "012345678";
        emailAddress = "email@webmail.com";
        id = 1;
        member = new Member(firstName, lastName, contactPhone, emailAddress, id);
        loanList = new ArrayList<ILoan>();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testCheckConstructorException() throws IllegalArgumentException {
        System.out.println("\nThis test will provoke an IllegalArgumentException to "
                + "\ncheck that error detection is working where first name is null");
        
        member = new Member(null, lastName, contactPhone, emailAddress, id);
        
        boolean testValid = true;
        if(member.getFirstName() != null) {
            testValid = false;
            System.out.println("Member's first name is not null! -- FAIL");
        }
        else {
            System.out.println("Member's first name is null! -- PASS");
        }
        
        assertTrue(testValid);
    }

    @Test
    public void testHasNoOverDueLoans() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-09-29"), Date.valueOf("2015-10-29"));
        loan.commit(1);
        loan.checkOverDue(Date.valueOf("2015-09-30"));
        member.addLoan(loan);
        
        System.out.println("\nThis test demonstrates a case where this member has"
                + "\nno overdue loans.");
        
        boolean b = member.hasOverDueLoans();
        
        if(b)
            System.out.println("Member has overdue loans! -- FAIL");
        else
            System.out.println("Member has no overdue loans! -- PASS");
        
        assertFalse(b);
    }
    
    @Test
    public void testHasOverDueLoans() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-08-29"), Date.valueOf("2015-09-29"));
        loan.commit(1);
        loan.checkOverDue(Date.valueOf("2015-09-30"));
        member.addLoan(loan);
        
        System.out.println("\nThis test demonstrates a case where this member has"
                + "\noverdue loans.");
        
        boolean b = member.hasOverDueLoans();
        
        if(b)
            System.out.println("Member has overdue loans! -- PASS");
        else
            System.out.println("Member has no overdue loans! -- FAIL");
        
        assertTrue(b);
    }
    
    @Test
    public void testHasNotReachedLoanLimit() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-08-29"), Date.valueOf("2015-09-29"));
        loan.commit(1);
        loan.checkOverDue(Date.valueOf("2015-09-30"));
        
        for(int i = 0; i < 3; i++)
            member.addLoan(loan);
        
        System.out.println("\nThis test demonstrates a case where member has not"
                + "\nreached loan limit.");
        
        boolean b = member.hasReachedLoanLimit();
        
        if(b)
            System.out.println("Member has reached loan limit! -- FAIL");
        else
            System.out.println("Member has not reached loan limit! -- PASS");
        
        assertFalse(b);
    }
    
    @Test
    public void testHasReachedLoanLimit() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-08-29"), Date.valueOf("2015-09-29"));
        loan.commit(1);
        loan.checkOverDue(Date.valueOf("2015-09-30"));
        
        for(int i = 0; i < 7; i++)
            member.addLoan(loan);
        
        System.out.println("\nThis test demonstrates a case where member has"
                + "\nreached loan limit.");
        
        boolean b = member.hasReachedLoanLimit();
        
        if(b)
            System.out.println("Member has reached loan limit! -- PASS");
        else
            System.out.println("Member has not reached loan limit! -- FAIL");
        
        assertTrue(b);
    }
    
    @Test
    public void testHasNoFinesPayable() {
        member.addFine(0f); // has no fines added to fine variable of 0f
        
        System.out.println("\nThis test demonstrates a case where member has no"
                + "\nfines payable.");
        
        boolean b = member.hasFinesPayable();
        
        if(b)
            System.out.println("Member has fines payable! -- FAIL");
        else
            System.out.println("Member has no fines payable! -- PASS");
        
        assertFalse(b);
    }
    
    @Test
    public void testHasFinesPayable() {
        member.addFine(5f);
        
        System.out.println("\nThis test demonstrates a case where member has"
                + "\nfines payable.");
        
        boolean b = member.hasFinesPayable();
        
        if(b)
            System.out.println("Member has fines payable! -- PASS");
        else
            System.out.println("Member has no fines payable at amount " + member.getFineAmount() + "! -- FAIL");
        
        assertTrue(b);
    }
    
    @Test
    public void testHasNotReachedFineLimit() {
        member.addFine(5f);
        
        System.out.println("\nThis test demonstrates a case where member has"
                + "\nnot reached the fine limit");
        
        boolean b = member.hasReachedFineLimit();
        
        if(b)
            System.out.println("Member has exceeded fine limit! -- FAIL");
        else
            System.out.println("Member has not exceeded fine limit! -- PASS");
        
        assertFalse(b);
    }
    
    @Test
    public void testHasReachedFineLimit() {
        member.addFine(15f);
        System.out.println("\nThis test demonstrates a case where member has"
                + "\nexceeded the fine limit");
        
        boolean b = member.hasReachedFineLimit();
        
        if(b)
            System.out.println("Member has exceeded fine limit! -- PASS");
        else
            System.out.println("Member has not exceeded fine limit! -- FAIL");
        
        assertTrue(b);
    }
    
    @Test
    public void testGetFineAmount() {
        float fineAmount = 5f;
        member.addFine(fineAmount);
        
        System.out.println("\nThis test demonstrates the getFineAmount() function"
                + "\nby returning the fine amount that the member has.");
        
        boolean testValid = true;
        
        if(!(member.getFineAmount() == fineAmount)) {
            testValid = false;
            System.out.println("Member fine amount " + member.getFineAmount() + " does not equal to " + fineAmount + "! -- FAIL");
        }
        else
            System.out.println("Member fine amount matches fineAmount! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testPayValidFineAmount() {
        float fineAmount = 5f;
        float paymentAmount = 5f;
        
        member.addFine(fineAmount);
        
        System.out.println("\nThis test demonstrates a case where the payment "
                + "\namount is equal to or less than the fines needed to be paid.");
        
        member.payFine(paymentAmount);
        
        boolean testValid = true;
        if(!(member.getFineAmount() < fineAmount)) {
            testValid = false;
            System.out.println("Member fines of " + fineAmount + " did not deduct by " + paymentAmount + "! -- FAIL");
        }
        else
            System.out.println("Member fines reduced by " + paymentAmount + ", now at " + member.getFineAmount() + "! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testPayOverFineAmount() {
        float fineAmount = 5f;
        float paymentAmount = 10f;
        
        member.addFine(fineAmount);
        
        System.out.println("\nThis test demonstrates a case where the payment "
                + "\namount is greater than the fines needed to be paid. This"
                + "\nshould provoke an IllegalArgumentException error.");
        
        member.payFine(paymentAmount);
        
        boolean testValid = true;
        if(member.getFineAmount() < fineAmount) {
            testValid = false;
            System.out.println("Member fines of " + fineAmount + " deducted by " + paymentAmount + "! -- FAIL");
        }
        else
            System.out.println("Member fine remains the same, invalid payment amount rejected! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testPayNegativeAmount() {
        float fineAmount = 5f;
        float paymentAmount = -5f;
        
        member.addFine(fineAmount);
        
        System.out.println("\nThis test demonstrates a case where the payment "
                + "\namount is negative. This should provoke an IllegalArgumentException"
                + "\nerror.");
        
        member.payFine(paymentAmount);
        
        boolean testValid = true;
        if(!(member.getFineAmount() == fineAmount)) {
            testValid = false;
            System.out.println("Member fines has been changed by negative value! -- FAIL");
        }
        else
            System.out.println("Member fine remains the same, invalid payment amount rejected! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testAddValidLoan() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-08-29"), Date.valueOf("2015-09-29"));
        
        System.out.println("\nThis test will perform the function addLoan(), and will"
                + "\nbe valid where the loan in the member class is identical to the"
                + "\nloan provided by the input variable.");
        
        member.addLoan(loan);
        
        loanList = member.getLoans();
        boolean testValid = false;
        
        if(!loanList.isEmpty()) {
            for(int i = 0; i < loanList.size(); i++) {
                if(loanList.get(i).equals(loan))
                    testValid = true;
            }
        }
        
        if(testValid)
            System.out.println("Member's loan list contains provided loan! -- PASS");
        else
            System.out.println("Member's loan list does not contain provided loan! -- FAIL");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testAddInvalidLoan() {
        
        System.out.println("\nThis test will perform the function addLoan() using "
                + "\na null loan. This test is passed if an IllegalArgumentException occurs"
                + "\nand the loan is not added to the member's loan list.");
        
        member.addLoan(null);
        
        loanList = member.getLoans();
        boolean testValid = true;

        if(!(loanList.isEmpty())) {
            testValid = false;
            System.out.println("Member's loan list contains loan when it should not have! -- FAIL");
        }
        else
            System.out.println("Member's loan list does not contain null loan! -- PASS");
        
        assertTrue(testValid);
    }
    
    // cannot test for loan by eligibility to borrow in this test class due to 
    // dependency on memberDAO for setting and obtaining member borrow state
    
    @Test
    public void returnLoanList() {
        IBook book = new Book("Author Name", "Book Title", "Number Here", 1);
        ILoan loan = new Loan(book, member, Date.valueOf("2015-08-29"), Date.valueOf("2015-09-29"));
        loan.commit(1);
        member.addLoan(loan);
        
        System.out.println("\nThis test demonstrates the function that returns"
                + "\nthe loan list. This will test a list that contains one loan,"
                + "\nand will return true if it returns the loan list with said"
                + "loan.");
        
        List<ILoan> loanList = member.getLoans();
        boolean testValid = true;
        
        if(!(loanList.get(0).equals(loan))) {
            testValid = false;
            System.out.println("Member's loan list does not contain the loan added to the loan list! -- FAIL");
        }
        else
            System.out.println("Member's loan list contains loan added! -- PASS");
        
        assertTrue(testValid);
    }

}
