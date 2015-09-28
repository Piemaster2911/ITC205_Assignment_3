package unitTest.MemberFamily;

import static org.junit.Assert.assertTrue;
import library.daos.MemberHelper;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.Test;

public class MemberHelperTest {

    @Test
    public void testMakeMember() {
        IMemberHelper memberHelper = new MemberHelper();
        
        String firstName = "Bob";
        String lastName = "Smith";
        String contactPhone = "01234567";
        String emailAddress = "email@webmail.com";
        int id = 1;
        
        System.out.println("\nThis test demonstrates the creation and initialization"
                + "\nof the MakeMember function.");
        
        IMember member = memberHelper.makeMember(firstName, lastName, contactPhone, emailAddress, id);
        
        boolean b = true;
        
        if(!(member.getFirstName().equals(firstName)))
            b = false;
        if(!(member.getLastName().equals(lastName)))
            b = false;
        if(!(member.getContactPhone().equals(contactPhone)))
            b = false;
        if(!(member.getEmailAddress().equals(emailAddress)))
            b = false;
        
        if(b)
            System.out.println("MemberHelper successfully created proper member "
                    + "\nobject from input variables! -- PASS");
        else
            System.out.println("MemberHelper did not successfully created proper "
                    + "\nmember object from input variables! -- FAIL");
        
        assertTrue(b);
    }

}
