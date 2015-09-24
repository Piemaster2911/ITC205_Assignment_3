package library.entities;

import java.util.Date;

import library.interfaces.entities.ELoanState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Loan implements ILoan {
    
    ELoanState borrowState;
    IBook book;
    IMember borrower;
    Date borrowDate;
    Date dueDate;
    int loanID;
    
    public ELoanState getState() {
        return this.borrowState;
    }
    
    public void setState(ELoanState e) {
        this.borrowState = e;
    }
    
    public Loan (IBook ib, IMember im, Date bd, Date dd, int li) {
        if(ib == null || im == null || bd == null || dd == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("Loan Book, Name, Borrow Date or Due Date is null!");
      //          e.printStackTrace();
            }
        }
        else if(dd.before(bd)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("Borrow Date cannot be set after Due Date!");
       //         e.printStackTrace();
            }
        }
        else if(li <= 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("Loan ID cannot be at or below 0!");
       //         e.printStackTrace();
            }
        }
        else {
            this.book = ib;
            this.borrower = im;
            this.borrowDate = bd;
            this.dueDate = dd;
            this.loanID = li;
            this.borrowState = ELoanState.PENDING;
        }
    }

    @Override
    public void commit(int id) {
        if(this.borrowState != ELoanState.PENDING) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("This loan is not valid - Loan State not pending!");
                e.printStackTrace();
            }
        }
        else {
            this.borrowState = ELoanState.CURRENT;
            this.loanID = id;
            this.book.borrow(this);
            this.borrower.addLoan(this);
        }
        
    }

    @Override
    public void complete() {
        if(this.borrowState != ELoanState.CURRENT || this.borrowState != ELoanState.OVERDUE) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("Loan state not current or overdue! Cannot complete!");
          //      e.printStackTrace();
            }
        }
        else {
            this.borrowState = ELoanState.COMPLETE;
        }
    }

    @Override
    public boolean isOverDue() {
       if(this.borrowState == ELoanState.OVERDUE) {
           return true;
       }
       else {
           return false;
       }
    }

    @Override
    public boolean checkOverDue(Date currentDate) {
        
        if(currentDate.after(this.dueDate)) {
            if(this.borrowState != ELoanState.OVERDUE)
                this.borrowState = ELoanState.OVERDUE;
            return true;
        }
        else if(this.borrowState != ELoanState.CURRENT || this.borrowState != ELoanState.OVERDUE) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("Loan state not current or overdue! Cannot complete!");
        //        e.printStackTrace();
            }
            return false;
        }
        else {
            return false;
        }
    }

    @Override
    public IMember getBorrower() {
        return borrower;
    }

    @Override
    public IBook getBook() {
        return book;
    }

    @Override
    public int getID() {
        return loanID;
    }

}
