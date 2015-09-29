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
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
        exception.expect(IllegalArgumentException.class);
        member = new Member(null, lastName, contactPhone, emailAddress, id);
        
        boolean testValid = true;
        if(member.getFirstName() != null) {
            testValid = false;
            System.out.println("Member's first name is not null!");
        }
        else {
            System.out.println("Member's first name is null!");
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
    public void testHasReachedLoanLimit() {
        
    }

}
