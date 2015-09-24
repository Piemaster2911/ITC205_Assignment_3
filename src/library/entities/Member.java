package library.entities;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember {
    
    EMemberState memberState;
    String firstName;
    String lastName;
    String contactPhone;
    String emailAddress;
    int id;
    float totalFines;
    List<ILoan> loans;
    
    public Member(String fn, String ln, String cp, String ea, int idn){
        if(fn == null || ln == null || cp == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("Cannot enter a member who has no first name, last name, or contact number!");
          //      e.printStackTrace();
            }
        }
        else {
            this.memberState = EMemberState.BORROWING_ALLOWED;
            this.firstName = fn;
            this.lastName = ln;
            this.contactPhone = cp;
            this.emailAddress = ea;
            this.id = idn;
            this.totalFines = 0.00f;
            this.loans = new ArrayList<ILoan>();
        }
        
    }

    @Override
    public boolean hasOverDueLoans() {
        boolean hasOverDueLoans = false;
       for(int i = 0; i < loans.size(); i++) {
           if(loans.get(i).isOverDue())
               hasOverDueLoans = true;
       }
       return hasOverDueLoans;
    }

    @Override
    public boolean hasReachedLoanLimit() {
        if(loans.size() >= IMember.LOAN_LIMIT)
            return true;
        else
            return false;
    }

    @Override
    public boolean hasFinesPayable() {
        if(totalFines > 0f){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean hasReachedFineLimit() {
        if(totalFines > IMember.FINE_LIMIT) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public float getFineAmount() {
        return totalFines;
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
            this.totalFines += fine;
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
        else if(payment > this.totalFines) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Payment must not exceed the cost of fine!");
             //   e.printStackTrace();
            }
        }
        else {
            this.totalFines -= payment;
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
        else if(this.memberState == EMemberState.BORROWING_DISALLOWED) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Borrower cannot be loaned because borrowing is disallowed for this member!");
             //   e.printStackTrace();
            }
        }
        else {
            this.loans.add(loan);
        }
    }

    @Override
    public List<ILoan> getLoans() {
        return loans;
    }

    @Override
    public void removeLoan(ILoan loan) {
        this.loans.remove(loan);
        
    }

    @Override
    public EMemberState getState() {
        return this.memberState;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getContactPhone() {
        return contactPhone;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public int getID() {
        return id;
    }

    
    
}
