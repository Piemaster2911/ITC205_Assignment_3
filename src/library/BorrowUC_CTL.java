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
	
	private ICardReader _reader;
	private IScanner _scanner; 
	private IPrinter _printer; 
	private IDisplay _display;
	//private String state;
	private int _scanCount = 0;
	private IBorrowUI _ui;
	private EBorrowState _state; 
	private IBookDAO _bookDAO;
	private IMemberDAO _memberDAO;
	private ILoanDAO _loanDAO;
	
	//private List<IBook> bookList;
	private List<ILoan> _loanList;
	private IMember _borrower;
	
	private JPanel _previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

	    reader.addListener(this);
	    scanner.addListener(this);
	    
		this._display = display;
		this._ui = new BorrowUC_UI(this);
		this._reader = reader;
		this._scanner = scanner;
		this._printer = printer;
		this._memberDAO = memberDAO;
		this._loanDAO = loanDAO;
		this._bookDAO = bookDAO;
		_state = EBorrowState.CREATED;
	}
	
	public void initialise() {
	    if(_state != EBorrowState.CREATED) {
	        throw new RuntimeException("Borrow Book already created!");
	    }
	    
		_previous = _display.getDisplay();
		_display.setDisplay((JPanel) _ui, "Borrow UI");
		//bookList = new ArrayList<IBook>();
		_loanList = new ArrayList<ILoan>();
		
		_reader.setEnabled(true);
		_scanner.setEnabled(false);
	//	state = EBorrowState.INITIALIZED;
	    setState(EBorrowState.INITIALIZED);
	}
	
	public void close() {
		_display.setDisplay(_previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
	    
	    if(_state != EBorrowState.INITIALIZED) {
	        _ui.displayErrorMessage("Borrow Book State not initialized!");
	    }
	    else if(_memberDAO.getMemberByID(memberID) == null) {
	        _ui.displayErrorMessage("Member does not exist!");
	        setState(EBorrowState.INITIALIZED);
	    }
	    else if(_reader == null) {
	        _ui.displayErrorMessage("Reader nor initialized or enabled!");
	    }
	    else {
    	    _borrower = _memberDAO.getMemberByID(memberID);
    	    _scanCount = _loanDAO.findLoansByBorrower(_borrower).size();
    
    	    boolean overdueLoans = _borrower.hasOverDueLoans();
    	    boolean atLoanLimit = _borrower.hasReachedLoanLimit();
    	    boolean hasFines = _borrower.hasFinesPayable();
    	    boolean overFineLimit = _borrower.hasReachedFineLimit();
    	    
    	    if(_borrower.getState().equals(EMemberState.BORROWING_ALLOWED)) {
    	        _reader.setEnabled(false);
    	        _scanner.setEnabled(true);
    	        setState(EBorrowState.SCANNING_BOOKS);
    	        
    	        String memberName = _borrower.getFirstName() + " " + _borrower.getLastName();
    	        _ui.displayMemberDetails(memberID, memberName, _borrower.getContactPhone());
    	        
    	        _ui.displayScannedBookDetails("");
    	        _ui.displayPendingLoan("");
    	        if(hasFines)
    	            _ui.displayOutstandingFineMessage(_borrower.getFineAmount());
    	        
    	        List<ILoan> loans = _borrower.getLoans();
    	        String loanDetails = buildLoanListDisplay(loans);
    	        _ui.displayExistingLoan(loanDetails);
    	        
    	        
    	    }
    	    else {
    	        _reader.setEnabled(false);
    	        _scanner.setEnabled(false);
    	        setState(EBorrowState.BORROWING_RESTRICTED);
    	        
                String memberName = _borrower.getFirstName() + " " + _borrower.getLastName();
                _ui.displayMemberDetails(memberID, memberName, _borrower.getContactPhone());
                
                _ui.displayScannedBookDetails("");
                _ui.displayPendingLoan(buildLoanListDisplay(_loanDAO.findLoansByBorrower(_borrower)));
                if(overFineLimit)
                    _ui.displayOverFineLimitMessage(_borrower.getFineAmount());
                else if(hasFines)
                    _ui.displayOutstandingFineMessage(_borrower.getFineAmount());
                
                if(atLoanLimit)
                    _ui.displayAtLoanLimitMessage();
                
                if(overdueLoans)
                    _ui.displayOverDueMessage();
    	    }
	    }
	}
	
	
	
	@Override
	public void bookScanned(int barcode) {
	    IBook book = _bookDAO.getBookByID(barcode);
	    if(book == null) { // if book does not exist
	        _scanner.setEnabled(true);
	        _reader.setEnabled(false);
	        _ui.displayErrorMessage("Book of this barcode id does not exist!");
	        setState(EBorrowState.SCANNING_BOOKS);
	    }
	    else {
	        EBookState bookState = book.getState();
	        
	        if(!(bookState.equals(EBookState.AVAILABLE))) {
	            _scanner.setEnabled(true);
	            _reader.setEnabled(false);
	            _ui.displayErrorMessage("Book is not available!");
	            setState(EBorrowState.SCANNING_BOOKS);
	        }
	        boolean loanExist = false;
	        
	        for(int i = 0; i < _loanList.size(); i++) {
	            if(!(_loanList.isEmpty())) {
	                if(_loanList.get(i).getBook().equals(book))
	                    loanExist = true;
	            }
	        }
	        
	        if(loanExist) {
	            _scanner.setEnabled(true);
                _reader.setEnabled(false);
                _ui.displayErrorMessage("Book is already scanned!");
                setState(EBorrowState.SCANNING_BOOKS);
	        }
	        else {
	            if(_scanCount <= IMember.LOAN_LIMIT) {
	                _scanCount++;
	                ILoan loan = _loanDAO.createLoan(_borrower, book);
	                _loanList.add(loan);
	                _ui.displayScannedBookDetails(book.toString());
	                _ui.displayPendingLoan(loan.toString());
	                _reader.setEnabled(false);
	                _scanner.setEnabled(true);
	            
	                setState(EBorrowState.SCANNING_BOOKS);
	            }
	            else {
	                scansCompleted();
	                
	            }
	        }
	    }
	}

	
	private void setState(EBorrowState state) {
	    this._state = state;
	    _ui.setState(state);
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
	    setState(EBorrowState.CONFIRMING_LOANS);
	    _reader.setEnabled(false);
	    _scanner.setEnabled(false);
        String loanDetails = buildLoanListDisplay(_loanList);
        _ui.displayConfirmingLoan(loanDetails);
	}

	@Override
	public void loansConfirmed() {
	    if(_loanList.isEmpty()) {
	        _ui.displayErrorMessage("Loan List is empty!");
	        setState(EBorrowState.CONFIRMING_LOANS);
	    }
	    else {
	        _scanner.setEnabled(false);
	        _reader.setEnabled(false);
	        for(int i = 0; i < _loanList.size(); i++) {
	            _loanDAO.commitLoan(_loanList.get(i));
	        }
	        _printer.print(buildLoanListDisplay(_loanList));
	        setState(EBorrowState.COMPLETED);
	        _display.setDisplay((JPanel) _ui, "Main Menu");
	    }
	}

	@Override
	public void loansRejected() {
	    _loanList = null; // empty it
	    _loanList = new ArrayList<ILoan>(); // then re-initialize it
	    _ui.displayMemberDetails(_borrower.getID(), _borrower.getFirstName() 
	            + " " + _borrower.getLastName(), _borrower.getContactPhone());
	    _ui.displayExistingLoan(buildLoanListDisplay(_loanDAO.findLoansByBorrower(_borrower)));
	    _scanCount = _loanDAO.findLoansByBorrower(_borrower).size();
	    _scanner.setEnabled(true);
	    _reader.setEnabled(false);
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
