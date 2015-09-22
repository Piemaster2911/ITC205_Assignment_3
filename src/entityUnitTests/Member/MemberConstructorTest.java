package entityUnitTests.Member;

import static org.junit.Assert.*;
import library.entities.Member;

import org.junit.Test;

/**
 * Testing constructor for Member class - this compares the member class created
 * in the class variables and compare them to the message versions. This is done 
 * on a per variable basis.
 * 
 * Test is true if all variable values match
 * 
 * Note that ID has to be parsed back to int so as to better represent the type of
 * variable used in the class.
 */
public class MemberConstructorTest {
    
    String firstName = "Bob";
    String lastName = "Smith";
    String contactPhone = "012345678";
    String emailAddress = "email@webmail.com";
    Integer id = 1; // had to use Integer instead of int to allow casting to string
    MessageUtil messageFirstName = new MessageUtil(firstName);
    MessageUtil messageLastName = new MessageUtil(lastName);
    MessageUtil messageContactPhone = new MessageUtil(contactPhone);
    MessageUtil messageEmailAddress = new MessageUtil(emailAddress);
    MessageUtil messageID = new MessageUtil(id.toString());

    /**
     * A constructor for a test member class, just in case
     */
    Member member = new Member(firstName, lastName, contactPhone, emailAddress, id);
    
    
    @Test
    public void testMemberConstructorFirstName() {
        System.out.println("Test is true when firstName variable match equivalent "
                + "variable's value used in constructing Member class");
        System.out.print("var values: " + member.getFirstName() + ", ");
        assertEquals(member.getFirstName(), messageFirstName.printMessage());
    }
    
    @Test
    public void testMemberConstructorLastName() {
        System.out.println("Test is true when lastName variable match equivalent "
                + "variable's value used in constructing Member class");
        System.out.print("var values: " + member.getLastName() + ", ");
        assertEquals(member.getLastName(), messageLastName.printMessage());
    }
    
    @Test
    public void testMemberConstructorContactPhone() {
        System.out.println("Test is true when contactPhone variable match equivalent "
                + "variable's value used in constructing Member class");
        System.out.print("var values: " + member.getContactPhone() + ", ");
        assertEquals(member.getContactPhone(), messageContactPhone.printMessage());
    }
    
    @Test
    public void testMemberConstructorEmailAddress() {
        System.out.println("Test is true when emailAddress variable match equivalent "
                + "variable's value used in constructing Member class");
        System.out.print("var values: " + member.getEmailAddress() + ", ");
        assertEquals(member.getEmailAddress(), messageEmailAddress.printMessage());
    }
    
    @Test
    public void testMemberConstructorID() {
        System.out.println("Test is true when id variable match equivalent "
                + "variable's value used in constructing Member class");
        System.out.print("var values: " + member.getID() + ", ");
        assertEquals(member.getID(),Integer.parseInt(messageID.printMessage()));
    }
    
    
    
}
