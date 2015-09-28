package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.panels.borrow.ScanningPanel;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;
import library.panels.borrow.ScanningPanel;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	
	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

	    reader.addListener(this);
	    scanner.addListener(this);
	    
		this.display = display;
		this.ui = new BorrowUC_UI(this);
		this.reader = reader;
		this.scanner = scanner;
		this.printer = printer;
		this.memberDAO = memberDAO;
		this.loanDAO = loanDAO;
		this.bookDAO = bookDAO;
		state = EBorrowState.CREATED;
	}
	
	public void initialise() {
	    if(state != EBorrowState.CREATED) {
	        throw new RuntimeException("Borrow Book already created!");
	    }
	    
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");
		bookList = new ArrayList<IBook>();
		loanList = new ArrayList<ILoan>();
		
		reader.setEnabled(true);
		scanner.setEnabled(false);
	//	state = EBorrowState.INITIALIZED;
	    setState(EBorrowState.INITIALIZED);
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
	    
	    if(state != EBorrowState.INITIALIZED) {
	        ui.displayErrorMessage("Borrow Book State not initialized!");
	    }
	    else if(memberDAO.getMemberByID(memberID) == null) {
	        ui.displayErrorMessage("Member does not exist!");
	        setState(EBorrowState.INITIALIZED);
	    }
	    else if(reader == null) {
	        ui.displayErrorMessage("Reader nor initialized or enabled!");
	    }
	    else {
    	    borrower = memberDAO.getMemberByID(memberID);
    	    scanCount = loanDAO.findLoansByBorrower(borrower).size();
    
    	    boolean overdueLoans = borrower.hasOverDueLoans();
    	    boolean atLoanLimit = borrower.hasReachedLoanLimit();
    	    boolean hasFines = borrower.hasFinesPayable();
    	    boolean overFineLimit = borrower.hasReachedFineLimit();
    	    
    	    if(borrower.getState().equals(EMemberState.BORROWING_ALLOWED)) {
    	        reader.setEnabled(false);
    	        scanner.setEnabled(true);
    	        setState(EBorrowState.SCANNING_BOOKS);
    	        
    	        String memberName = borrower.getFirstName() + " " + borrower.getLastName();
    	        ui.displayMemberDetails(memberID, memberName, borrower.getContactPhone());
    	        
    	        ui.displayScannedBookDetails("");
    	        ui.displayPendingLoan("");
    	        if(hasFines)
    	            ui.displayOutstandingFineMessage(borrower.getFineAmount());
    	        
    	        List<ILoan> loans = borrower.getLoans();
    	        String loanDetails = buildLoanListDisplay(loans);
    	        ui.displayExistingLoan(loanDetails);
    	        
    	        
    	    }
    	    else {
    	        reader.setEnabled(false);
    	        scanner.setEnabled(false);
    	        setState(EBorrowState.BORROWING_RESTRICTED);
    	        
                String memberName = borrower.getFirstName() + " " + borrower.getLastName();
                ui.displayMemberDetails(memberID, memberName, borrower.getContactPhone());
                
                ui.displayScannedBookDetails("");
                ui.displayPendingLoan(buildLoanListDisplay(loanDAO.findLoansByBorrower(borrower)));
                if(overFineLimit)
                    ui.displayOverFineLimitMessage(borrower.getFineAmount());
                else if(hasFines)
                    ui.displayOutstandingFineMessage(borrower.getFineAmount());
                
                if(atLoanLimit)
                    ui.displayAtLoanLimitMessage();
                
                if(overdueLoans)
                    ui.displayOverDueMessage();
    	    }
	    }
	}
	
	
	
	@Override
	public void bookScanned(int barcode) {
	    IBook book = bookDAO.getBookByID(barcode);
	    if(book == null) { // if book does not exist
	        scanner.setEnabled(true);
	        reader.setEnabled(false);
	        ui.displayErrorMessage("Book of this barcode id does not exist!");
	        setState(EBorrowState.SCANNING_BOOKS);
	    }
	    else {
	        EBookState bookState = book.getState();
	        
	        if(!(bookState.equals(EBookState.AVAILABLE))) {
	            scanner.setEnabled(true);
	            reader.setEnabled(false);
	            ui.displayErrorMessage("Book is not available!");
	            setState(EBorrowState.SCANNING_BOOKS);
	        }
	        boolean loanExist = false;
	        
	        for(int i = 0; i < loanList.size(); i++) {
	            if(!(loanList.isEmpty())) {
	                if(loanList.get(i).getBook().equals(book))
	                    loanExist = true;
	            }
	        }
	        
	        if(loanExist) {
	            scanner.setEnabled(true);
                reader.setEnabled(false);
                ui.displayErrorMessage("Book is already scanned!");
                setState(EBorrowState.SCANNING_BOOKS);
	        }
	        else {
	            if(scanCount <= IMember.LOAN_LIMIT) {
	                scanCount++;
	                ILoan loan = loanDAO.createLoan(borrower, book);
	                loanList.add(loan);
	                ui.displayScannedBookDetails(book.toString());
	                ui.displayPendingLoan(loan.toString());
	                reader.setEnabled(false);
	                scanner.setEnabled(true);
	            
	                setState(EBorrowState.SCANNING_BOOKS);
	            }
	            else {
	                scansCompleted();
	                
	            }
	        }
	    }
	}

	
	private void setState(EBorrowState state) {
	    this.state = state;
	    ui.setState(state);
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
	    setState(EBorrowState.CONFIRMING_LOANS);
	    reader.setEnabled(false);
	    scanner.setEnabled(false);
        String loanDetails = buildLoanListDisplay(loanList);
        ui.displayConfirmingLoan(loanDetails);
	}

	@Override
	public void loansConfirmed() {
	    if(loanList.isEmpty()) {
	        ui.displayErrorMessage("Loan List is empty!");
	        setState(EBorrowState.CONFIRMING_LOANS);
	    }
	    else {
	        scanner.setEnabled(false);
	        reader.setEnabled(false);
	        for(int i = 0; i < loanList.size(); i++) {
	            loanDAO.commitLoan(loanList.get(i));
	        }
	        printer.print(buildLoanListDisplay(loanList));
	        setState(EBorrowState.COMPLETED);
	        display.setDisplay((JPanel) ui, "Main Menu");
	    }
	}

	@Override
	public void loansRejected() {
	    loanList = null; // empty it
	    loanList = new ArrayList<ILoan>(); // then re-initialize it
	    ui.displayMemberDetails(borrower.getID(), borrower.getFirstName() 
	            + " " + borrower.getLastName(), borrower.getContactPhone());
	    ui.displayExistingLoan(buildLoanListDisplay(loanDAO.findLoansByBorrower(borrower)));
	    scanCount = loanDAO.findLoansByBorrower(borrower).size();
	    scanner.setEnabled(true);
	    reader.setEnabled(false);
	    setState(EBorrowState.SCANNING_BOOKS);
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
