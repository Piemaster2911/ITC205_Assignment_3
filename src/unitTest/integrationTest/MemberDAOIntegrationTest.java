package unitTest.integrationTest;

import static org.junit.Assert.*;

import java.util.List;

import library.daos.MemberDAO;
import library.daos.MemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberDAOIntegrationTest {

    MemberDAO memberDAO;
    String firstName = "Bob";
    String lastName = "Smith";
    String contactPhone = "01234567";
    String emailAddress = "email@webmail.com";
    
    @Before
    public void setUp() {
        memberDAO = new MemberDAO(new MemberHelper());
    }
    
    @After
    public void refreshMocks() {
    }
    
    @Test
    public void testAddMember() {
        
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        
        System.out.println("\nThis test demonstrates adding a member to the memberDAO object.");
        
        boolean b = true;
        
        if(!(memberDAO.getMemberByID(1).getFirstName().equals(firstName))) 
            b = false;
        if(!(memberDAO.getMemberByID(1).getLastName().equals(lastName)))
            b = false;
        if(!(memberDAO.getMemberByID(1).getContactPhone().equals(contactPhone)))
            b = false;
        if(!(memberDAO.getMemberByID(1).getEmailAddress().equals(emailAddress)))
            b = false;
        
        if(b)
            System.out.println("The member object added to memberDAO matches! -- PASS");
        else
            System.out.println("The member object added to memberDAO does not match! -- FAIL");
        
        assertTrue(b);
    }

    @Test
    public void testGetMemberByID() {
        int idToSearch = 2;
        
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);

        System.out.println("\nThis test demonstrates a search function to receive a member object"
                + "\nfrom memberDAO with the idToSearch of " + idToSearch + ".");
        
        boolean b;
        
        IMember m = memberDAO.getMemberByID(idToSearch);
        
        if(m != null) {
            System.out.println("A member object is found with id " + idToSearch + "! -- PASS");
            b = true;
        }
        else {
            System.out.println("No member object is found with id " + idToSearch + "! -- FAIL");
            b = false;
        }
        
        assertTrue(b);
    }

    @Test
    public void testListMembers() {
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        
        System.out.println("\nThis test will demonstrate the function that gives a list of members.");
        
        boolean b;
        
        List<IMember> memberList = memberDAO.listMembers();
        
        if(memberList.isEmpty()) {
            System.out.println("The list returns and is empty! -- FAIL");
            b = false;
        }
        else {
            System.out.println("The list returns and is not empty! -- PASS");
            b = true;
        }
        
        assertTrue(b);
    }

    @Test
    public void testFindMembersByLastName() {
        String searchLastName = "Mail";
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, "Caddy", contactPhone, emailAddress);
        memberDAO.addMember(firstName, "Mail", contactPhone, emailAddress);
        
        System.out.println("\nThis test will demonstrate a search function to receive a member based"
                + "\non matching the last name string variable.");
        
        boolean b;
        
        List<IMember> memberList = memberDAO.findMembersByLastName(searchLastName);
        
        if(memberList.isEmpty()) {
            System.out.println("The list returns and is empty! -- FAIL");
            b = false;
        }
        else {
            System.out.println("The list returns and is not empty! -- PASS");
            b = true;
        }
        
        assertTrue(b);
    }

    @Test
    public void testFindMembersByEmailAddress() {
        String searchEmail = "email@webmail.com";
        
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, "bob@genericmail.com");
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember(firstName, lastName, contactPhone, "lightning@universemail.com");
        
        System.out.println("\nThis test will demonstrate the function that gives a list of members.");
        
        boolean b;
        
        List<IMember> memberList = memberDAO.findMembersByEmailAddress(searchEmail);
        
        if(memberList.isEmpty()) {
            System.out.println("The list returns and is empty! -- FAIL");
            b = false;
        }
        else {
            System.out.println("The list returns and is not empty! -- PASS");
            b = true;
        }
        
        assertTrue(b);
    }

    @Test
    public void testFindMembersByNames() {
        String searchFirstName = "Amelie";
        String searchLastName = "Ford";
        
        memberDAO.addMember(firstName, lastName, contactPhone, emailAddress);
        memberDAO.addMember("John", "McAndry", contactPhone, emailAddress);
        memberDAO.addMember(firstName, "Billiards", contactPhone, emailAddress);
        memberDAO.addMember("Amelie", "Ford", contactPhone, emailAddress);
        memberDAO.addMember("Newton", lastName, contactPhone, emailAddress);
        
        System.out.println("\nThis test will demonstrate the function that gives a list of members.");
        
        boolean b;
        
        List<IMember> memberList = memberDAO.findMembersByNames(searchFirstName, searchLastName);
        
        if(memberList.isEmpty()) {
            System.out.println("The list returns and is empty! -- FAIL");
            b = false;
        }
        else {
            System.out.println("The list returns and is not empty! -- PASS");
            b = true;
        }
        
        assertTrue(b);
    }

}
