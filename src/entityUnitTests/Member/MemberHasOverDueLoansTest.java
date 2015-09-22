package entityUnitTests.Member;

import static org.junit.Assert.*;

import java.sql.Date;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.entities.ILoan;

import org.junit.Test;

public class MemberHasOverDueLoansTest {
    
    Member member = new Member("Bob", "Smith", "012345678", "email@webmail.com", 1);
    
    Book book1 = new Book("Alice King", "Ten Thousand Pies Under the Kitchen", "12341234", 1);

    /**
     * This test will check for a member who does not have an overdue loan, and will be true if
     * the test points to that this member does not have overdue loans.
     */
    @Test
    public void testMemberDoesNotHaveOverDueLoans() {
        Member testMember = member;
        ILoan loan1 = new Loan(book1, testMember, Date.valueOf("2015-09-19"), Date.valueOf("2015-10-03"), 1);
        
        testMember.addLoan(loan1);
        boolean overdue = false;
        
        testMember.getLoans().get(0).commit(1);
        testMember.getLoans().get(0).checkOverDue(Date.valueOf("2015-09-22"));
        if(testMember.getLoans().get(0).isOverDue())
            overdue = true;
        
        System.out.println("This test will demonstrate if this member does not have overdue loans. "
                + "\nThis test is true if this member has no overdue loans of books borrowed.");
        
        if(overdue)
            System.out.println("Member " + testMember.getFirstName() + " " + testMember.getLastName() + 
                    " has overdue books!");
        
        else
            System.out.println("Member " + testMember.getFirstName() + " " + testMember.getLastName() + 
                    " does not have overdue books!");
        
        // now check if test member has any over due loans
        assertFalse(testMember.hasOverDueLoans());
        
    }
    
    /**
     * This test will check for a member who does have overdue loans.
     * The test is successful only if the member has any overdue loans.
     */
    @Test
    public void testMemberHasOverDueBooks() {
        Member testMember = member;
        ILoan loan1 = new Loan(book1, testMember, Date.valueOf("2015-09-19"), Date.valueOf("2015-10-03"), 1);
        
        testMember.addLoan(loan1);
        
        boolean overdue = false;
        
        testMember.getLoans().get(0).commit(1);
        testMember.getLoans().get(0).checkOverDue(Date.valueOf("2018-10-22"));
        if(testMember.getLoans().get(0).isOverDue())
            overdue = true;
        
        System.out.println("This test will demonstrate if this member have any overdue loans. "
                + "\nThis test is true if this member has overdue loans of books borrowed.");
        
        if(overdue)
            System.out.println("Member " + testMember.getFirstName() + " " + testMember.getLastName() + 
                    " has overdue books!");
        
        else
            System.out.println("Member " + testMember.getFirstName() + " " + testMember.getLastName() + 
                    " does not have overdue books!");
        
        // now check if test member has any over due loans
        assertTrue(testMember.hasOverDueLoans());
    }

}
