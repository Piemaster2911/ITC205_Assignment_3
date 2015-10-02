package unitTest.operationTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
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
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
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

public class BBScanBook {
    int barcode;
    ICardReader reader;
    IScanner scanner; 
    IPrinter printer; 
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
        display = EasyMock.createMock(IDisplay.class);
        
        
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
            loanDAO.listLoans().get(i).getBook().borrow(loanDAO.listLoans().get(i));
            loanDAO.listLoans().get(i).getBorrower().addLoan(loanDAO.getLoanByID(i));
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
    public void testScanBookIncorrectBarcode() {
        System.out.println("\nThis test will demonstrate a situation where a barcode"
                + "\nscanned in for a book does not exist.");
        
        barcode = 99; // give barcode scanned a ludicrous value
        
        IBook book = bookDAO.getBookByID(barcode);
        boolean testValid = true;
        
        if(book != null) {
            testValid = false;
            System.out.println("Book with scancode " + barcode + " exists, even though it should not! -- FAIL");
        }
        else
            System.out.println("Book with scancode " + barcode + " does not exist! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testScanBookCorrectBarcode() {
        System.out.println("\nThis test will demonstrate a situation where a barcode"
                + "\nscanned in for a book does exist, and is available.");
        
        barcode = 2; // give barcode scanned a ludicrous value
        
        IBook book = bookDAO.getBookByID(barcode);
        boolean testValid = true;
        
        if(book == null) {
            testValid = false;
            System.out.println("Book with scancode " + barcode + " does not exists, even though it should! -- FAIL");
        }
        else if(book.getState() != EBookState.AVAILABLE) {
            testValid = false;
            System.out.println("Book with scancode " + barcode + " exists, but is NOT_AVAILABLE! -- FAIL");
        }
        else
            System.out.println("Book with scancode " + barcode + " exist and is AVAILABLE! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testScanBookAlreadyOnPendingList() {
        System.out.println("\nThis test will demonstrate a situation where a barcode"
                + "\nscanned in for a book is already scanned onto the pending loan list.");
        
        barcode = 2;
        Date currentDate = new Date();
        Date dueDate = currentDate;
        dueDate.setTime((((1000*60)*60)*24)*14);
        IBook book = bookDAO.getBookByID(barcode);
        ILoan loan = new Loan(book, borrower, currentDate, dueDate);
        loanList.add(loan);
        
        boolean testValid = false;
        
        if(!loanList.isEmpty()) {
            for(int i = 0; i < loanList.size(); i++) {
                if(loanList.get(i).equals(loan)) {
                    testValid = true;
                }
            }
        }
        
        if(testValid)
            System.out.println("Loan already exists in loanList! -- PASS");
        else
            System.out.println("Loan does not exist in loanList! -- FAIL");
        
        assertTrue(testValid);
    }
    
    @SuppressWarnings("unused")
    @Test
    public void testScanBookNotAvailable() {
        System.out.println("\nThis test will demonstrate a situation where barcode"
                + "\nscanned in for book that is already on loan and is not available.");
        
        barcode = 2;
        Date currentDate = new Date();
        Date dueDate = currentDate;
        dueDate.setTime((((1000*60)*60)*24)*14);
        
        IBook book = bookDAO.getBookByID(barcode);
        // let's assume that this book is on loan
        ILoan loan = new Loan(book, borrower, currentDate, dueDate);
        book.borrow(loan);
        boolean testValid = true;
        
        if(book == null) {
            testValid = false;
            System.out.println("Book with scancode " + barcode + " does not exists, even though it should! -- FAIL");
        }
        else if(book.getState() == EBookState.AVAILABLE) {
            testValid = false;
            System.out.println("Book with scancode " + barcode + " exists, but is AVAILABLE! -- FAIL");
        }
        else
            System.out.println("Book with scancode " + barcode + " exist and is NOT_AVAILABLE! -- PASS");
        
        assertTrue(testValid);
        
    }
    
    @Test
    public void testScanBookMemberBelowBorrowLimit() {
        // using default member, who is always eligible
        System.out.println("\nThis test demonstrates the case where the member is"
                + "\nbelow the borrowing limit.");
        
        boolean testValid = true;
        ILoan loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(2));
        bookDAO.getBookByID(2).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(8));
        bookDAO.getBookByID(8).borrow(loan);
        loanList.add(loan);
        
        if(loanList.size() >= IMember.LOAN_LIMIT) {
            testValid = false;
            System.out.println("Member at or exceeded loan limit! -- FAIL");
        }
        else
            System.out.println("Member not at or exceeded loan limit! -- PASS");
        
        assertTrue(testValid);
    }
    
    @Test
    public void testScanBookMemberAboveLimit() {
        System.out.println("\nThis test demonstrates a hypothetical case where the"
                + "\nmember manages to borrow more books than the loan limit.");
        
        boolean testValid = true;
        ILoan loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(2));
        bookDAO.getBookByID(2).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(8));
        bookDAO.getBookByID(8).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(9));
        bookDAO.getBookByID(9).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(10));
        bookDAO.getBookByID(10).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(11));
        bookDAO.getBookByID(11).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(12));
        bookDAO.getBookByID(12).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(18));
        bookDAO.getBookByID(18).borrow(loan);
        loanList.add(loan);
        loan = loanDAO.createLoan(borrower, bookDAO.getBookByID(20));
        bookDAO.getBookByID(20).borrow(loan);
        loanList.add(loan);
        
        if(loanList.size() < IMember.LOAN_LIMIT) {
            testValid = false;
            System.out.println("Member not at or exceeded loan limits!");
        }
        else
            System.out.println("Member at or exceeded loan limits!");
        
        assertTrue(testValid);
    }
    
}
