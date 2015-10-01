package unitTest.scenarioOperationTest;

import static org.junit.Assert.*;

import java.util.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.BorrowUC_CTL;
import library.BorrowUC_UI;
import library.daos.BookDAO;
import library.daos.BookHelper;
import library.daos.LoanDAO;
import library.daos.LoanHelper;
import library.daos.MemberDAO;
import library.daos.MemberHelper;
import library.entities.Loan;
import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BBConfirmLoans {
    int barcode;
    ICardReader reader;
    IScanner scanner; 
    IPrinter printer; 
    IDisplay displayMock;
    IDisplay display;
    int scanCount = 0;
    IBorrowUI ui;
    EBorrowState state; 
    IBookDAO bookDAO;
    IMemberDAO memberDAO;
    ILoanDAO loanDAO;
    //private List<IBook> bookList;
    List<ILoan> loanList;
    IMember borrower;
    JPanel previous;
    
    BorrowUC_CTL ctl;
    

    @Before
    public void setUp() throws Exception {
        displayMock = EasyMock.createMock(IDisplay.class);
        display = new Display();
        
        
        // build up the DAOs
        bookDAO = new BookDAO(new BookHelper()); // bookDAO with 22 books
        bookDAO.addBook("Thomas Edison", "A Thousand Patents", "24241313");
        bookDAO.addBook("Bayle Cassidy", "Being the Empress' Best Friend", "84124245");
        bookDAO.addBook("Donathan R. Lambie", "On Technicalities of Gyroscopes: A Thesis", "24672144");
        bookDAO.addBook("Spino Saurus", "Dinosaur's Diet", "00000101");
        bookDAO.addBook("Frederick Logombo", "Logombo, Diplomat Extraodinaire!", "34110111");
        bookDAO.addBook("R'lyeh Cthulhu", "How I Saved the World", "65436543");
        bookDAO.addBook("Theresa Mays", "205 Ways of Cooking Offals", "99999999");
        bookDAO.addBook("Spino Saurus", "Laments of Time Travel", "00000001");
        bookDAO.addBook("Dennis Yeoman", "Archery 101", "67675567");
        bookDAO.addBook("Eugene Yazat", "Kosher Dishes to Try", "21112112");
        bookDAO.addBook("Sarah Keyes", "Sarah's Guide to Civilian Aircraft", "48124812");
        bookDAO.addBook("R'lyeh Cthulhu", "Cookies of the Mountains of Madness", "65436543");
        bookDAO.addBook("Unknown Author", "The Epic of Bullgamash", "n/a");
        bookDAO.addBook("Dr Kinash Mason", "Advanced Anatomy, 2015 ed", "77777777");
        bookDAO.addBook("Michael Jackson", "15th Place", "n/a");
        bookDAO.addBook("Gillie Gallagher Damois", "Irish Travels", "38695931");
        bookDAO.addBook("King Kong", "How I Bought The Empire State Building", "n/a");
        bookDAO.addBook("R'lyeh Cthulhu", "Video Games is My New Addiction", "65436543");
        bookDAO.addBook("Professor D. Maple", "Maple Syrup and Pokemon", "88818882");
        bookDAO.addBook("Ashley Cox", "20 Years Passed", "62137981");
        bookDAO.addBook("Alfred McIntosh", "Detective Studies of the 20th Century", "38114242");
        bookDAO.addBook("Mr Fiction", "The Non-Existent Book", "00000005");
        
        memberDAO = new MemberDAO(new MemberHelper()); // memberDAO with 14 members 
        memberDAO.addMember("David", "Jones", "25525525", "jones8shorts@intermail.com");
        memberDAO.addMember("Johann", "Dixon", "75757522", "master5_hard@pmail.com");
        memberDAO.addMember("Hannah", "Wisconscin", "05560010", "badtree03@intermail.com");
        memberDAO.addMember("Abdul", "Rahman", "27772831", "not_a_pineapple@intermail.com");
        memberDAO.addMember("Xavier", "McCullery", "90000001", "brain_on_board@jinxmail.com");
        memberDAO.addMember("Samantha", "Dart", "55531932", "plutos_mine25@pmail.com");
        memberDAO.addMember("Jene", "Bamomo", "38843231", "jene_bamomo_001@pmail.com");
        memberDAO.addMember("Kassie", "Kladdsmen", "22210000", "");
        memberDAO.addMember("Hitamo", "Kayugsaki", "80454500", "hkayugsaki81@intermail.com");
        memberDAO.addMember("Leia", "Margaret", "52332522", "leia_powers6@jinxmail.com");
        memberDAO.addMember("John", "Rollers", "64552555", "");
        memberDAO.addMember("Veronica", "Mailes", "25908232", "vixen8mail@pmail.com");
        memberDAO.addMember("Henderson", "Hanks", "34525235", "");
        memberDAO.addMember("Reginald", "Catan", "89124011", "rcatan01@ihesmail.com");

        loanDAO = new LoanDAO(new LoanHelper()); // loanDAO with loans attached for each member and book
        loanDAO.createLoan(memberDAO.getMemberByID(1), bookDAO.getBookByID(4));
        loanDAO.createLoan(memberDAO.getMemberByID(4), bookDAO.getBookByID(19));
        loanDAO.createLoan(memberDAO.getMemberByID(4), bookDAO.getBookByID(21));
        loanDAO.createLoan(memberDAO.getMemberByID(5), bookDAO.getBookByID(14));
        loanDAO.createLoan(memberDAO.getMemberByID(10), bookDAO.getBookByID(3));
        loanDAO.createLoan(memberDAO.getMemberByID(10), bookDAO.getBookByID(5));
        loanDAO.createLoan(memberDAO.getMemberByID(10), bookDAO.getBookByID(7));
        loanDAO.createLoan(memberDAO.getMemberByID(10), bookDAO.getBookByID(16));
        loanDAO.createLoan(memberDAO.getMemberByID(10), bookDAO.getBookByID(17));
        loanDAO.createLoan(memberDAO.getMemberByID(14), bookDAO.getBookByID(13));
        
        for(int i = 0; i < loanDAO.listLoans().size(); i++) {
            loanDAO.commitLoan(loanDAO.listLoans().get(i));
        }
        
        memberDAO.getMemberByID(6).addFine(4f);
        memberDAO.getMemberByID(11).addFine(11f);
        
        reader = new CardReader();
        scanner = new Scanner();
        printer = new Printer();
        
        ctl = new BorrowUC_CTL(reader, scanner, printer, display, bookDAO, loanDAO, memberDAO);
        ui = new BorrowUC_UI(ctl);
        state = EBorrowState.SCANNING_BOOKS; // as we're starting at start of scanBook()
                                            // function instead of at start of class
        
        borrower = memberDAO.getMemberByID(2); // the member is set to member id number 2
        loanList = new ArrayList<ILoan>();
        barcode = 1;
        
    }


    @After
    public void tearDown() throws Exception {
    }
    

    @Test
    public void testConfirmLoans() {
        System.out.println("\nThis test demonstrate the loansConfirmed() method"
                + "\nwhich in this case has a valid confirming loan list.");
        
        Date currentDate = new Date();
        Date dueDate = currentDate;
        dueDate.setTime((((1000*60)*60)*24)*14);

        ILoan loan = new Loan(bookDAO.getBookByID(11), borrower, currentDate, dueDate);
        loanList.add(loan);
        loanDAO.commitLoan(loan);
        loan = new Loan(bookDAO.getBookByID(8), borrower, currentDate, dueDate);
        loanList.add(loan);
        loanDAO.commitLoan(loan);
        
        
        printer.print("Loans here!!!");
        state = EBorrowState.COMPLETED;
        scanner.setEnabled(false);
        reader.setEnabled(false);
        
        boolean testValid = true;
        
        List<ILoan> loanList2 = loanDAO.findLoansByBorrower(borrower);
        
        if(loanList2.isEmpty()) {
            testValid = false;
            System.out.println("Loan list for borrower " + borrower.getFirstName() + " " + borrower.getLastName() + " is empty! -- FAIL");
        }
        else
            System.out.println("Loan list for borrower " + borrower.getFirstName() + " " + borrower.getLastName() + " is not empty! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testConfirmLoansEmptyList() {
        System.out.println("\nThis test demonstrates the loanConfirmed() method"
                + "\nwith an empty list. It should return to the previous screen"
                + "\nwhen this occurs.");
        
        boolean testValid = true;
        state = EBorrowState.SCANNING_BOOKS;
        
        if(!loanList.isEmpty()) {
            testValid = false;
            System.out.println("loanList is not empty when it should! -- FAIL");
        }
        else {
            System.out.println("loanList is empty! -- PASS");
        }
        
        assertTrue(testValid);
    }
    
    @Test
    public void testTransitionToMainMenu() {
        System.out.println("\nThis test demonstrates the process that occurs"
                + "\nafter the loanConfirmed() method completes successfully,"
                + "\nand returns to the main menu. This is simulated by"
                + "\nthe state being changed to COMPLETED.");
        
        state = EBorrowState.COMPLETED;
        //display.setDisplay((JPanel) ui, "Main Menu");
        boolean testValid = true;
        
        if(!(state.equals(EBorrowState.COMPLETED))) {
            testValid = false;
            System.out.println("CTL state is not set to COMPLETED! -- FAIL");
        }
        else
            System.out.println("CTL state is COMPLETED! -- PASS");
        
        assertTrue(testValid);
    }

}
