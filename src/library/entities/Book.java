package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook {

    ILoan loan;
    String author;
    String title;
    String callNumber;
    int bookID;
    EBookState bookState;
    
    public Book (String an, String tn, String cn, int bi) {
        if(an == null || tn == null || cn == null ) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Book author name, title name, or contact number is absent! This is not valid!");
     //           e.printStackTrace();
            }
        }
        else if(bi <= 0) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.println("Book ID is not valid! Value must be above 0!");
      //          e.printStackTrace();
            }
        }
        else {
            this.author = an;
            this.title = tn;
            this.callNumber = cn;
            this.bookID = bi;
            this.bookState = EBookState.AVAILABLE;
        }
    }

    @Override
    public void borrow(ILoan loan) {
        if(this.bookState != EBookState.AVAILABLE) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is not available!");
      //          e.printStackTrace();
            }
        }
        else {
            this.loan = loan;
        }
    }

    @Override
    public ILoan getLoan() {
        if(this.bookState != EBookState.ON_LOAN) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is not on loan!");
      //          e.printStackTrace();
                return null;
            }
        }
        else {
            return loan;
        }    
    }

    @Override
    public void returnBook(boolean damaged) {
        if(this.bookState != EBookState.ON_LOAN) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is not on loan!");
      //          e.printStackTrace();
            }
        }
        else {
            this.loan.complete();
            if(damaged) {
                this.bookState = EBookState.DAMAGED;
            }
            else {
                this.bookState = EBookState.AVAILABLE;
            }
        }
        
    }

    @Override
    public void lose() {
        if(this.bookState != EBookState.ON_LOAN) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is not available!");
      //          e.printStackTrace();
            }
        }
        else {
            this.bookState = EBookState.LOST;
        }
    }

    @Override
    public void repair() {
        if(this.bookState != EBookState.DAMAGED) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is not damaged!");
       //         e.printStackTrace();
            }
        }
        else {
            this.bookState = EBookState.AVAILABLE;
        }
    }

    @Override
    public void dispose() {
        if(this.bookState == EBookState.ON_LOAN) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book cannot be disposed! Book is currently on loan!");
       //         e.printStackTrace();
            }
        }
        else if(this.bookState == EBookState.DISPOSED) {
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                System.err.println("The book is already disposed!");
        //        e.printStackTrace();
            }
        }
        else {
            this.bookState = EBookState.DISPOSED;
        }
    }

    @Override
    public EBookState getState() {
        return this.bookState;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getCallNumber() {
        return this.callNumber;
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return bookID;
    }
}
