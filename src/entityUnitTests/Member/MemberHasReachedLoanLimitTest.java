package entityUnitTests.Member;

import static org.junit.Assert.*;

import java.sql.Date;

import library.entities.Book;
import library.entities.Loan;
import library.entities.Member;
import library.interfaces.entities.IMember;

import org.junit.Test;

public class MemberHasReachedLoanLimitTest {
    
    Member member = new Member("Bob", "Smith", "012345678", "email@webmail.com", 1);
    
    Book book1 = new Book("Alice King", "Ten Thousand Pies Under the Kitchen", "12341234", 1);
    Book book2 = new Book("Joe Macaroni", "How to Cook using Tomatoes", "01230123", 2);
    Book book3 = new Book("Marsh Mallow", "The Great Fall of the Marshmallow Man", "99999999", 3);
    Book book4 = new Book("Dean Willy", "Geography of the World: 1999 version", "54542121", 4);
    Book book5 = new Book("Xyzzys Nyalent", "How NOT to walk on the Mountain of Madness", "42424242", 5);
    
    @Test
    public void testMemberHasNotReachedLoanLimit() {
        Member testMember = member;
        
        testMember.addLoan(new Loan(book1, testMember, Date.valueOf("2015-09-20"), 
                Date.valueOf("2015-10-04"), 1));
        testMember.addLoan(new Loan(book2, testMember, Date.valueOf("2015-09-20"),
                Date.valueOf("2015-10-04"), 2));
        
        System.out.println("This test will check if member has not reached the "
                + "loan limit.");
        
        boolean loanLimit = testMember.hasReachedLoanLimit();
        
        if(loanLimit)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has reached loan limit!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has not reached loan limit!");
        
        assertFalse(loanLimit);
        
    }
    
    @Test
    public void testMemberHasReachedLoanLimit() {
        Member testMember = member;
        
        testMember.addLoan(new Loan(book1, testMember, Date.valueOf("2015-09-20"), 
                Date.valueOf("2015-10-04"), 1));
        testMember.addLoan(new Loan(book2, testMember, Date.valueOf("2015-09-20"),
                Date.valueOf("2015-10-04"), 2));
        testMember.addLoan(new Loan(book3, testMember, Date.valueOf("2015-09-20"),
                Date.valueOf("2015-10-04"), 3));
        testMember.addLoan(new Loan(book4, testMember, Date.valueOf("2015-09-20"),
                Date.valueOf("2015-10-04"), 4));
        testMember.addLoan(new Loan(book5, testMember, Date.valueOf("2015-09-20"),
                Date.valueOf("2015-10-04"), 5));
        
        System.out.println("This test will check if member has reached the "
                + "loan limit.");
        
        boolean loanLimit = testMember.hasReachedLoanLimit();
        
        if(loanLimit)
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has reached loan limit!");
        else
            System.out.println("Member " + testMember.getFirstName() + " " 
                    + testMember.getLastName() + " has not reached loan limit!");
        
        assertTrue(loanLimit);
    }

}
