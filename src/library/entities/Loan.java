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
    
    public Loan (IBook ib, IMember im, Date bd, Date dd, int li) {
        if(ib == null || im == null || bd == null || dd == null) {
            try {
                System.err.print("Loan Book, Name, Borrow Date or Due Date is null!");
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(dd.before(bd)) {
            try {
                System.err.print("Borrow Date cannot be set after Due Date!");
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(loanID <= 0) {
            try {
                System.err.print("Loan ID cannot be at or below 0!");
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
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
        // TODO Auto-generated method stub
        if(this.borrowState != ELoanState.PENDING) {
            try {
                System.err.print("This loan is not valid - Loan State not pending!");
                throw new RuntimeException();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        else {
            this.borrowState = ELoanState.CURRENT;
            this.book.borrow(this);
            this.borrower.addLoan(this);
        }
        
    }

    @Override
    public void complete() {
        // TODO Auto-generated method stub
        if(this.borrowState != ELoanState.CURRENT || this.borrowState != ELoanState.OVERDUE) {
            try {
                System.err.print("Loan state not current or overdue! Cannot complete!");
                throw new RuntimeException();
            } catch (RuntimeException e) {
                e.printStackTrace();
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
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public IMember getBorrower() {
        // TODO Auto-generated method stub
        return borrower;
    }

    @Override
    public IBook getBook() {
        // TODO Auto-generated method stub
        return book;
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return loanID;
    }

}
