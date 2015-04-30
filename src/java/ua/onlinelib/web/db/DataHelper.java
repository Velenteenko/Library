/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.db;

import java.util.List;
import org.hibernate.Criteria;
//import javassist.bytecode.Opcode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import ua.onlinelib.web.ENTITY.Author;
import ua.onlinelib.web.ENTITY.Book;
import ua.onlinelib.web.ENTITY.Genre;
import ua.onlinelib.web.ENTITY.HibernateUtil;
import ua.onlinelib.web.beans.Pager;

/**
 *
 * @author velenteenko
 */
public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;
    private DetachedCriteria currentCriteria;
    private Pager currentPager;

    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    public static DataHelper getInstance() {
//        return dataHelper == null ? new DataHelper() : dataHelper;
        if (dataHelper == null) {
            dataHelper = new DataHelper();
        }
        return dataHelper;
    }

    public void runCurrentCriteria() {
        Criteria criteria = currentCriteria.addOrder(Order.asc("name")).getExecutableCriteria(getSession());
        List<Book> list = criteria.setFirstResult(currentPager.getFrom()).setMaxResults(currentPager.getTo()).list();
//        currentPager.setList(list);
        currentPager.setList(list);
    }
//    public List<Book> getAllBooks() {
//        return getSession().createCriteria(Book.class).list();
//    }

//    private <T> void setCriterias(String restrictionValue, T typeValue, Pager pager){
//        currentPager = pager;
//        if((typeValue!=null) && (restrictionValue.length()!=0)){
//            
//        }
//    }
    public void getAllBooks(Pager pager) {
        currentPager = pager;

        Criteria criteria = getSession().createCriteria(Book.class);
        int totalBooks = Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());

        currentPager.setTotalBooksCount(totalBooks);
        currentCriteria = DetachedCriteria.forClass(Book.class);
        // run
        runCurrentCriteria();
    }

    public List<Author> getAllAuthors() {
        return getSession().createCriteria(Author.class).list();
    }

    public List<Genre> getAllGenres() {
        return getSession().createCriteria(Genre.class).list();
    }

    public void getBookByGenre(Long genreId, Pager pager) {
        currentPager = pager;
        Criterion criterion = Restrictions.eq("genre.id", genreId);

        Criteria criteria = getSession().createCriteria(Book.class);
        Integer total = Integer.valueOf(criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult().toString());
        currentPager.setTotalBooksCount(total);

        currentCriteria = DetachedCriteria.forClass(Book.class);
        currentCriteria.add(criterion);

        runCurrentCriteria();
    }

    public void getBookByLetter(Character letter, Pager pager) {
        currentPager = pager;

        Criterion criterion = Restrictions.ilike("name", letter.toString(), MatchMode.START);

        Criteria criteria = getSession().createCriteria(Book.class);
        Integer total = Integer.valueOf(criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult().toString());
        currentPager.setTotalBooksCount(total);

        currentCriteria = DetachedCriteria.forClass(Book.class);
        currentCriteria.add(criterion);

        runCurrentCriteria();
    }

    public void getBooksByAuthor(String authorName, Pager pager) {
        currentPager = pager;

        Criterion criterion = Restrictions.ilike("author.fio", authorName, MatchMode.ANYWHERE);

        Criteria criteria = getSession().createCriteria(Book.class, "book").createAlias("book.author", "author");
        Integer total = Integer.valueOf(criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult().toString());
        currentPager.setTotalBooksCount(total);

        currentCriteria = DetachedCriteria.forClass(Book.class, "book").createAlias("book.author", "author");;
        currentCriteria.add(criterion);

        runCurrentCriteria();
    }

    public void getBooksByName(String bookName, Pager pager) {
        currentPager = pager;

        Criterion criterion = Restrictions.ilike("name", bookName, MatchMode.ANYWHERE);
        Criteria criteria = getSession().createCriteria(Book.class);
        Integer total = Integer.valueOf(criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult().toString());
        currentPager.setTotalBooksCount(total);

        currentCriteria = DetachedCriteria.forClass(Book.class);
        currentCriteria.add(criterion);

        runCurrentCriteria();
    }

    public byte[] getContent(Long id) {
        Criteria criteria = getSession().createCriteria(Book.class);
        criteria.setProjection(Property.forName("content"));
        criteria.add(Restrictions.eq("id", id));
        return (byte[]) criteria.uniqueResult();
    }

    public byte[] getImage(Long id) {
        Criteria criteria = getSession().createCriteria(Book.class);
        criteria.setProjection(Property.forName("image"));
        criteria.add(Restrictions.eq("id", id));
        return (byte[]) criteria.uniqueResult();
    }
//    public List<Book> getBookByGenre(long genreId) {
//        return getSession().createCriteria(Book.class).add(Restrictions.eq("genre.id", genreId)).list();
//    }

//    public List<Book> getBookByLetter(Character letter) {
//        return getBookList("name", letter.toString(), MatchMode.START);
//    }
//    public List<Book> getBookByName(String bookName) {
//        return getBookList("name", bookName, MatchMode.ANYWHERE);
//    }
//    public List<Book> getBookByAuthor(String authorName) {
//        return getBookList("author", authorName, MatchMode.ANYWHERE);
//    }
    public Author getAuthor(Long authorId) {
        return (Author) getSession().get(Author.class, authorId);
    }

//    public byte[] getContent(Long contentId) {
//        return (byte[]) getFieldValue("content", contentId);
//    }
//    public byte[] getImage(Long imageId) {
//        return (byte[]) getFieldValue("image", imageId);
//    }
    // private methods            
    private List<Book> getBookList(String field, String value, MatchMode matchMode) {
        return getSession().createCriteria(Book.class).add(Restrictions.ilike(field, value, matchMode)).list();
    }

    private Object getFieldValue(String field, Long id) {
        return getSession().createCriteria(Book.class).setProjection(Projections.property(field)).add(Restrictions.eq("id", id)).uniqueResult();
    }

}
