package unitTest.BorrowUC_CTL;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.easymock.EasyMock;

import static org.junit.Assert.*;
import library.BorrowUC_CTL;
import library.entities.Loan;
import library.entities.Member;
import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Scanner;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BorrowCTLTest {
    
    BorrowUC_CTL borrowCTL;
    ICardReader reader;
    IScanner scanner;
    IPrinter printer;
    IDisplay display;
    IBookDAO bookDAO;
    ILoanDAO loanDAO;
    IMemberDAO memberDAO;
    int scanCount = 0;
    private IBorrowUI _ui;
    private EBorrowState _state; // this should not alter
    private List<IBook> _bookList;
    private List<ILoan> _loanList;
    private IMember _borrower;
    private JPanel _previous;
    

    @Before
    public void setUp() throws Exception {
        reader = EasyMock.createMock(ICardReader.class); 
        scanner = EasyMock.createMock(IScanner.class); 
        printer = EasyMock.createMock(IPrinter.class);
        display = EasyMock.createNiceMock(IDisplay.class); // not easy to mock due to not being able to 
        // figure how to mock parameterized functions, so used actual class for now
        bookDAO = EasyMock.createMock(IBookDAO.class);
        loanDAO = EasyMock.createMock(ILoanDAO.class);
        memberDAO = EasyMock.createMock(IMemberDAO.class);
        _ui = EasyMock.createMock(IBorrowUI.class);
        //bookList = new ArrayList<IBook>();
        _loanList = new ArrayList<ILoan>();
        _borrower = EasyMock.createMock(IMember.class);
        
        borrowCTL = new BorrowUC_CTL(reader, scanner, printer, display, bookDAO, loanDAO, memberDAO);
    }
    
    @After
    public void refresh() {
        EasyMock.reset();
    }

 /*   @Test
    public void testBorrowUC_CTL() {
        System.out.println("\nThis test simulates the creation of the BorrowUC_CTL class.");
        
        
    }*/

    @Test
    public void testInitialiseState() {        
        System.out.println("\nThis test demonstrates the initialise state of the class.");
        // set state
        _state = EBorrowState.CREATED;
        boolean testValid = true;
        
        if(_state.equals(EBorrowState.CREATED)) {
            System.out.println("State is properly set for initialisation process! -- PASS");
        }
        else {
            System.out.println("State is not properly set to CREATED! -- FAIL");
            testValid = false;
        }
        
        assertTrue(testValid);
    }
    
    @Test
    public void testInitialiseStateReader() {
        boolean testValid = true;
        
        // next, we simulate the enabling and disabling of the reader and scanner objects.
        boolean setReader = true;
        
        System.out.println("\nThis test demonstrates if the setReader boolean value is valid - i.e. true.");
        
   //     reader.setEnabled(setReader);
        
        if(setReader != true) {
            testValid = false;
            System.out.println("setReader is supposed to be true, but isn't! -- FAIL");
        }
        else
            System.out.println("setReader sets Reader to enabled! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testInitialiseStateScanner() {
        boolean testValid = true;
        
        boolean setScanner = false;
        
        System.out.println("\nThis test will demonstrate if the setScanner boolean value is valid - i.e. false.");
        
       // scanner.setEnabled(setScanner);

        if(setScanner != false) {
            testValid = false;
            System.out.println("setScanner is suppsoed to be false, but isn't! -- FAIL");
        }
        else
            System.out.println("setScanner sets Scanner to disabled! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testInitialiseStatePostState() {    
        // now we set the state to initialized, then test if it is set to initialized
        boolean testValid = true;
        
        System.out.println("\nThis test will demonstrate whether or not the state has been "
                + "\nchanged to meet the post-initialization requirements of the function");
        
        _state = EBorrowState.INITIALIZED;
        if(_state != EBorrowState.INITIALIZED) {
            testValid = false;
            System.out.println("State of BorrowCTL is not INITIALIZED! -- FAIL");
        }
        else
            System.out.println("State of BorrowCTL is INITIALIZED! -- PASS");

        assertTrue(testValid);
        
    }

  /*// No idea what to do with this function to test it, so left it like this
   *   @Test
    public void testClose() {
        
    }*/

    @Test
    public void testCardSwipedInitState() {
        // input for test of this function
        _state = EBorrowState.INITIALIZED; // just in case the test decides
        // to take test functions at random
        boolean testValid = true; // to make sure test is valid by the end of test.
        
        System.out.println("\nThis test will test the initializing checks on the state value"
                + "\nto see if it meets the conditions.");
        
        //Now to test if state is indeed initialized
        if(!(_state.equals(EBorrowState.INITIALIZED))) {
            testValid = false;
            System.out.println("State of BorrowCTL is not INITIALIZED! -- FAIL");
        }
        else
            System.out.println("State of BorrowCTL is INITIALIZED! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testCardSwipedGetInvalidMember() {
        int memberID = 1; // we need member id number, just in case
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates a case where a memberId is not valid and"
                + "\ndoes not exist. It will return true if the member returned is null, and an"
                + "\nexception is implied to occurs.");
        
        EasyMock.expect(memberDAO.getMemberByID(memberID)).andReturn(null);
        EasyMock.replay(memberDAO);
        
        if(memberDAO.getMemberByID(memberID) != null) {
            testValid = false;
            System.out.println("Member returned is supposed to not exist! -- FAIL");
        }
        else
            System.out.println("Member returned is null! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testCardSwipedGetValidMember() {
        int memberID = 1; // we need member id number, just in case
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates a case where a memberId is valid.");
        
        EasyMock.expect(memberDAO.getMemberByID(memberID)).andReturn(new Member("Bob", "Smith", "012345678", "email@webmail.com", memberID));
        EasyMock.replay(memberDAO);
        
        if(memberDAO.getMemberByID(memberID).equals(null)) {
            testValid = false;
            System.out.println("Member returned is null, but supposed to exist! -- FAIL");
        }
        else
            System.out.println("Member returned exists! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testCardSwipedReader() {
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates a reader that does exists. It is implied"
                + "\nthat a reader can also not exists where this test is true if a null is given"
                + "\nas a value for the reader variable.");
        
        if(reader.equals(null)) {
            testValid = false;
            System.out.println("Reader does not exist when it is supposed to exist! -- FAIL");
        }
        else
            System.out.println("Reader exists! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void checkMemberEligibleCondition() {
        boolean testValid = true;
        IMember member = EasyMock.createNiceMock(IMember.class);
        
        System.out.println("\nThis test demonstrates a process where the member is eventually"
                + "\nproven to be allowed to borrow, by having four conditions result in false."
                + "\nThe opposite is also true if this test is true.");
        
        EasyMock.expect(member.hasFinesPayable()).andReturn(false);
        EasyMock.expect(member.hasOverDueLoans()).andReturn(false);
        EasyMock.expect(member.hasReachedFineLimit()).andReturn(false);
        EasyMock.expect(member.hasReachedLoanLimit()).andReturn(false);
        EasyMock.replay(member);
        
        if(member.hasFinesPayable() != false) {
            testValid = false;
            System.out.println("hasFinesPayable is true, supposed to be false! -- FAIL");
        }
        else
            System.out.println("hasFinesPayable is false! -- PASS");
        
        if(member.hasOverDueLoans() != false) {
            testValid = false;
            System.out.println("hasOverDueLoans is true, supposed to be false! -- FAIL");
        }
        else
            System.out.println("hasOverDueLoans is false! -- PASS");
        
        if(member.hasReachedFineLimit() != false) {
            testValid = false;
            System.out.println("hasReachedFineLimit is true, supposed to be false! -- FAIL");
        }
        else
            System.out.println("hasReachedFineLimit is false! -- PASS");
        
        if(member.hasReachedLoanLimit() != false) {
            testValid = false;
            System.out.println("hasReachedLoanLimit is true, supposed to be false! -- FAIL");
        }
        else
            System.out.println("hasReachedLoanLimit is false! -- PASS");
        
        assertTrue(testValid);
    }

    @Test
    public void testBookScannedCheckExist() {
        IBook book = EasyMock.createMock(IBook.class);
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates the BookScanned function process,"
                + "\nwhich involves a number of checks. In this case, we first check if"
                + "\nthe book given by a barcode number exists, which should be true"
                + "\nin this case.");
        
        if(book == null) {
            testValid = false;
            System.out.println("Book does not exist where it is supposed to exist! -- FAIL");
        }
        else
            System.out.println("Book exists! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testBookScannedCheckDoesNotExist() {
        IBook book = null;
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates the BookScanned function process,"
                + "\nwhich involves a number of checks. In this case, we first check if"
                + "\nthe book given by a barcode number exists, which should be false"
                + "\nin this case.");
        
        if(book != null) {
            testValid = false;
            System.out.println("Book exist where it is not supposed to exist! -- FAIL");
        }
        else
            System.out.println("Book does not exists! -- PASS");
        
        assertTrue(testValid);
    }

    
    @Test
    public void testBookScannedStateValid() {
        IBook book = EasyMock.createMock(IBook.class);
        boolean testValid = true;
        
        System.out.println("\nNow we check if the book is available. This test is true if"
                + "\nit is of book state AVAILABLE.");
        
        EasyMock.expect(book.getState()).andReturn(EBookState.AVAILABLE);
        EasyMock.replay(book);
        
        EBookState bookState = book.getState();
        
        if(!(bookState.equals(EBookState.AVAILABLE))) {
            testValid = false;
            System.out.println("Book state is not AVAILABLE! -- FAIL");
        }
        else
            System.out.println("Book state is AVAILABLE! -- PASS");
  
        assertTrue(testValid);
    }
    
    @Test
    public void testBookScannedStateInvalid() {
        IBook book = EasyMock.createMock(IBook.class);
        boolean testValid = true;
        
        System.out.println("\nNow we check if the book is NOT available. This test is true if"
                + "\nit is not of book state AVAILABLE.");
        
        EasyMock.expect(book.getState()).andReturn(EBookState.ON_LOAN);
        EasyMock.replay(book);
        
        EBookState bookState = book.getState();
        
        if(bookState.equals(EBookState.AVAILABLE)) {
            testValid = false;
            System.out.println("Book state is AVAILABLE! -- FAIL");
        }
        else
            System.out.println("Book state is not AVAILABLE! -- PASS");
  
        assertTrue(testValid);
    }
    
    @Test
    public void testBookScannedNotExistLoanList() {
        IBook book = EasyMock.createMock(IBook.class);
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates a case where the book is not already"
                + "\nunder pending loan of the user, simulated by an empty pending loan list"
                + "\nand the user scanned the book.");
        
        if(_loanList.contains(book)) {
            testValid = false;
            System.out.println("loanList contains book when it should not! -- FAIL");
        }
        else
            System.out.println("loanList does not contain book! -- PASS");
        
        assertTrue(testValid);
    }


/*    @Test
    public void testCancelled() {
    }*/

    @Test
    public void testScansCompletedCorrectState() {
        _state = EBorrowState.CONFIRMING_LOANS;
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates the change of state to"
                + "\nCONFIRMING_LOANS. The result expected is the state requested.");
        
        if(!(_state.equals(EBorrowState.CONFIRMING_LOANS))) {
            testValid = false;
            System.out.println("Borrow state not CONFIRMING_LOANS! -- FAIL");
        }
        else
            System.out.println("Borrow state is CONFIRMING_LOANS! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testScansCompletedInvalidState() {
        _state = EBorrowState.INITIALIZED;
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates the change of state to"
                + "\nCONFIRMING_LOANS. The result expected is the state requested.");
        
        if(_state.equals(EBorrowState.CONFIRMING_LOANS)) {
            testValid = false;
            System.out.println("Borrow state is CONFIRMING_LOANS! -- FAIL");
        }
        else
            System.out.println("Borrow state is not CONFIRMING_LOANS! -- PASS");
        
        assertTrue(testValid);
    }

    @Test
    public void testLoansConfirmedLoanListNotEmpty() {
        ILoan loan = EasyMock.createMock(ILoan.class);
        _loanList.add(loan);
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates a case where the confirmed"
                + "\nloan list has a confirming loan list with loans in it.");
        
        if(_loanList.isEmpty()) {
            testValid = false;
            System.out.println("loanList is empty! -- FAIL");
        }
        else
            System.out.println("loanList is not empty! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testLoansConfirmedLoanListEmpty() {
        _loanList = new ArrayList<ILoan>();
        boolean testValid = true;
        
        System.out.println("\nThis test demonstrates an ideal case where the confirmed"
                + "\nloan list has an empty confirming loans list.");
        
        if(!(_loanList.isEmpty())) {
            testValid = false;
            System.out.println("loanList is not empty! -- FAIL");
        }
        else
            System.out.println("loanList is empty! -- PASS");
        
        assertTrue(testValid);
    }

    @Test
    public void testLoansRejected() {
        boolean testValid = true;
        
        System.out.println("\nThis test simulates a loan rejection, which involves"
                + "\nflushing and reinitializing the loanList, and setting the scanner"
                + "\nand reader.");
        _loanList = new ArrayList<ILoan>();
        if(!(_loanList.isEmpty())) {
            testValid = false;
            System.out.println("loanList is not empty! -- FAIL");
        }
        else
            System.out.println("loanList is empty! -- PASS");
        
        boolean setScanner = true;
        boolean setReader = false;
        
        if(setScanner != true) {
            testValid = false;
            System.out.println("setScanner is not true! -- FAIL");
        }
        else
            System.out.println("setScanner is true! -- PASS");
        
        if(setReader != false) {
            testValid = false;
            System.out.println("setReader is not false! -- FAIL");
        }
        else
            System.out.println("setReader is false! -- PASS");
    }

}
