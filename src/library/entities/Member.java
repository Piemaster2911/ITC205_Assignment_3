package library.entities;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember {
    
    private EMemberState _memberState;
    private String _firstName;
    private String _lastName;
    private String _contactPhone;
    private String _emailAddress;
    private int _id;
    private float _totalFines;
    private List<ILoan> _loans;
    
    public Member(String fn, String ln, String cp, String ea, int idn){
        if(fn == null || ln == null || cp == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Cannot enter a member who has no first name, last name, or contact number!");
          //      e.printStackTrace();
            }
        }
        else {
            this._memberState = EMemberState.BORROWING_ALLOWED;
            this._firstName = fn;
            this._lastName = ln;
            this._contactPhone = cp;
            this._emailAddress = ea;
            this._id = idn;
            this._totalFines = 0.00f;
            this._loans = new ArrayList<ILoan>();
        }
        
    }

    @Override
    public boolean hasOverDueLoans() {
        boolean hasOverDueLoans = false;
       for(int i = 0; i < _loans.size(); i++) {
           if(_loans.get(i).isOverDue())
               hasOverDueLoans = true;
       }
       return hasOverDueLoans;
    }

    @Override
    public boolean hasReachedLoanLimit() {
        if(_loans.size() >= IMember.LOAN_LIMIT)
            return true;
        else
            return false;
    }

    @Override
    public boolean hasFinesPayable() {
        if(_totalFines > 0f){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean hasReachedFineLimit() {
        if(_totalFines > IMember.FINE_LIMIT) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public float getFineAmount() {
        return _totalFines;
    }

    @Override
    public void addFine(float fine) {
        if(fine < 0) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Fines must not be of negative value!");
            //    e.printStackTrace();
            }
        }
        else {
            this._totalFines += fine;
        }
    }

    @Override
    public void payFine(float payment) {
        if(payment < 0) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Fine Payments must not be of negative value!");
            //    e.printStackTrace();
            }
        }
        else if(payment > this._totalFines) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Payment must not exceed the cost of fine!");
             //   e.printStackTrace();
            }
        }
        else {
            this._totalFines -= payment;
        }
    }

    @Override
    public void addLoan(ILoan loan) {
        if(loan == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Loan added is null! Cannot proceed!");
             //   e.printStackTrace();
            }
        }
        else if(this._memberState == EMemberState.BORROWING_DISALLOWED) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Borrower cannot be loaned because borrowing is disallowed for this member!");
             //   e.printStackTrace();
            }
        }
        else {
            this._loans.add(loan);
        }
    }

    @Override
    public List<ILoan> getLoans() {
        return _loans;
    }

    @Override
    public void removeLoan(ILoan loan) {
        this._loans.remove(loan);
        
    }

    @Override
    public EMemberState getState() {
        return this._memberState;
    }

    @Override
    public String getFirstName() {
        return _firstName;
    }

    @Override
    public String getLastName() {
        return _lastName;
    }

    @Override
    public String getContactPhone() {
        return _contactPhone;
    }

    @Override
    public String getEmailAddress() {
        return _emailAddress;
    }

    @Override
    public int getID() {
        return _id;
    }

    
    
}
