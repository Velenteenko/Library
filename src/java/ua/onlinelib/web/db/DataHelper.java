/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.db;

import java.util.List;
import javassist.bytecode.Opcode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ua.onlinelib.web.ENTITY.Author;
import ua.onlinelib.web.ENTITY.Book;
import ua.onlinelib.web.ENTITY.Genre;
import ua.onlinelib.web.ENTITY.HibernateUtil;

/**
 *
 * @author velenteenko
 */
public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;

    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public static DataHelper getInstance() {
        return dataHelper == null ? new DataHelper() : dataHelper;
    }

    public List<Book> getAllBooks() {
        return getSession().createCriteria(Book.class).list();
    }

    public List<Author> getAllAuthors() {
        return getSession().createCriteria(Author.class).list();
    }

    public List<Genre> getAllGenres() {
        return getSession().createCriteria(Genre.class).list();
    }

    public List<Book> getBookByGenre(long genreId) {
        return getSession().createCriteria(Book.class).add(Restrictions.eq("genre.id", genreId)).list();
    }

    public List<Book> getBookByLetter(Character letter) {
        return getBookList("name", letter.toString(), MatchMode.START);
    }

    public List<Book> getBookByName(String bookName) {
        return getBookList("name", bookName, MatchMode.ANYWHERE);
    }

    public List<Book> getBookByAuthor(String authorName) {
        return getBookList("author", authorName, MatchMode.ANYWHERE);
    }

    public Author getAuthor(Long authorId) {
        return (Author) getSession().get(Author.class, authorId);
    }

    public byte[] getContent(Long contentId) {
        return (byte[]) getFieldValue("content", contentId);
    }
    
    public byte[] getImage(Long imageId) {
        return (byte[]) getFieldValue("image", imageId);
    }
    

    // private methods            
    private List<Book> getBookList(String field, String value, MatchMode matchMode) {
        return getSession().createCriteria(Book.class).add(Restrictions.ilike(field, value, matchMode)).list();
    }

    private Object getFieldValue(String field, Long id) {
        return getSession().createCriteria(Book.class).setProjection(Projections.property(field)).add(Restrictions.eq("id", id)).uniqueResult();
    }

}
