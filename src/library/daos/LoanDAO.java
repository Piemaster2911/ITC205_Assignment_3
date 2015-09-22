package library.daos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanDAO implements ILoanDAO {

    ILoanHelper helper;
    Date borrowDate = new Date();
    Date dueDate = new Date();
    List<ILoan> loans = new ArrayList<ILoan>();
    
    public LoanDAO(ILoanHelper h) {
        if(h == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.print("Helper is null and does not exist!");
                e.printStackTrace();
            }
        }
        else {
            this.helper = h;
        }
    }
    
    @Override
    public ILoan createLoan(IMember borrower, IBook book) {
        if(borrower == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.print("No borrower detected!");
                e.printStackTrace();
            }
            return null;
        }
        else if(book == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.print("No book detected!");
                e.printStackTrace();
            }
            return null;
        }
        else {
            dueDate.setTime(borrowDate.getTime() + (86000 * 14));
            ILoan loan = this.helper.makeLoan(book, borrower, borrowDate, dueDate);
            return loan;
        }
    }

    @Override
    public void commitLoan(ILoan loan) {
        int r = (int) Math.random();
        loan.commit(r);
        loans.add(loan);
    }

    @Override
    public ILoan getLoanByID(int id) {
        ILoan loan = null;
        for(int i = 0; i < loans.size(); i++) {
            int a = loans.get(i).getID();
            if(id == a)
                loan = loans.get(i);
        }
        if(loan != null)
            return loan;
        else
            return null;
    }

    @Override
    public ILoan getLoanByBook(IBook book) {
        ILoan loan = null;
        for(int i = 0; i < loans.size(); i++) {
            IBook b = loans.get(i).getBook();
            if(book.equals(b))
                loan = loans.get(i);
        }
        if(loan != null)
            return loan;
        else
            return null;
    }

    @Override
    public List<ILoan> listLoans() {
        return loans;
    }

    @Override
    public List<ILoan> findLoansByBorrower(IMember borrower) {
        List<ILoan> loanList = new ArrayList<ILoan>();
        for(int i = 0; i < loans.size(); i++) {
            IMember m = loans.get(i).getBorrower();
            if(borrower.equals(m))
                loanList.add(loans.get(i));
        }
        if(loanList.isEmpty()) {
            return null;
        }
        else {
            return loanList;
        }
    }

    @Override
    public List<ILoan> findLoansByBookTitle(String title) {
        List<ILoan> loanList = new ArrayList<ILoan>();
        for(int i = 0; i < loans.size(); i++) {
            String s = loans.get(i).getBook().getTitle();
            if(title.equals(s))
                loanList.add(loans.get(i));
        }
        if(loanList.isEmpty()) {
            return null;
        }
        else {
            return loanList;
        }
    }

    @Override
    public void updateOverDueStatus(Date currentDate) {
        Iterator<ILoan> iterator = loans.iterator();
        while(iterator.hasNext()) {
            iterator.next().checkOverDue(currentDate);
        }
    }

    @Override
    public List<ILoan> findOverDueLoans() {
        List<ILoan> loanList = new ArrayList<ILoan>();
        for(int i = 0; i < loans.size(); i++) {
            if(loans.get(i).isOverDue()) {
                loanList.add(loans.get(i));
            }
        }
        if(loanList.isEmpty())
            return null;
        else
            return loanList;
    }

}
