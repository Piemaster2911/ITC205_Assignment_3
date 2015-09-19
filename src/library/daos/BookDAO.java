package library.daos;

import java.util.List;

import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookDAO implements IBookDAO {
    
    List<IBook> books;
    IBookHelper helper;
    
    public BookDAO(IBookHelper h) {
        if(h == null) {
            try {
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.err.print("Book helper is null!");
                e.printStackTrace();
            }
        }
        else {
            this.helper = h;
        }
    }

    @Override
    public IBook addBook(String author, String title, String callNo) {
        
        IBook book = this.helper.makeBook(author, title, callNo, books.size());
        books.add(book);
        return book;
    }

    @Override
    public IBook getBookByID(int id) {
        IBook book = null;
        for(int i = 0; i < books.size(); i++) {
            int a = books.get(i).getID();
            if(id == a) // if book is found to match id
                book = books.get(i);
            else {} // else, ignore
        }
        if(book != null) {
            // book is found!
            return book;
        }
        else {
            // book is not found!
            return null;
        }
    }

    @Override
    public List<IBook> listBooks() {
        return books;
    }

    @Override
    public List<IBook> findBooksByAuthor(String author) {
        List<IBook> book = null;
        for(int i = 0; i < books.size(); i++) {
            String a = books.get(i).getAuthor();
            if(a.equals(author)) // if book is found to match author name
                book.add(books.get(i));
            else {} // else, ignore
        }
        if(book.size() > 0) {
            // book list is not empty!
            return book;
        }
        else {
            // book list is empty!
            return null;
        }
    }

    @Override
    public List<IBook> findBooksByTitle(String title) {
        List<IBook> book = null;
        for(int i = 0; i < books.size(); i++) {
            String t = books.get(i).getTitle();
            if(t.equals(title)) // if book is found to match title name
                book.add(books.get(i));
            else {} // else, ignore
        }
        if(book.size() > 0) {
            // book list is not empty!
            return book;
        }
        else {
            // book list is empty!
            return null;
        }
    }

    @Override
    public List<IBook> findBooksByAuthorTitle(String author, String title) {
        List<IBook> book = null;
        for(int i = 0; i < books.size(); i++) {
            String a = books.get(i).getAuthor();
            if(a.equals(author)) { // if book is found to match author name
                String b = books.get(i).getTitle(); // check if title matches
                if(b.equals(title)) // if title matches
                    book.add(books.get(i)); // add to book list
                else {} // else, ignore
            }
            else {} // else, ignore
        }
        if(book.size() > 0) {
            // book list is not empty!
            return book;
        }
        else {
            // book list is empty!
            return null;
        }
    }

}
