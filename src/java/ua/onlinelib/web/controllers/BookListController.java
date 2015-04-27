package ua.onlinelib.web.controllers;

import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import ua.onlinelib.web.ENTITY.Book;
//import ua.onlinelib.web.beans.Book;
import ua.onlinelib.web.db.DataHelper;
//import ua.onlinelib.web.db.Database;
import ua.onlinelib.web.enums.SearchType;

@ManagedBean(eager = true)
@SessionScoped
public class BookListController implements Serializable {

    private List<Book> currentBookList; // текущий список книг для отображения
    private Long selectedAuthorId;// текущий автор книги из списка при редактировании книги
    private ArrayList<Integer> pageNumbers = new ArrayList<Integer>(); // кол-во страниц для постраничности
    // критерии поиска
    private char selectedLetter; // выбранная буква алфавита, по умолчанию не выбрана ни одна буква
    private SearchType selectedSearchType = SearchType.TITLE;// хранит выбранный тип поиска, по-умолчанию - по названию
    private long selectedGenreId; // выбранный жанр
    private String currentSearchString; // хранит поисковую строку
    // для постраничности----
    private boolean pageSelected;
    private int booksCountOnPage = 2;// кол-во отображаемых книг на 1 странице
    private long selectedPageNumber = 1; // выбранный номер страницы в постраничной навигации
    private long totalBooksCount; // общее кол-во книг (не на текущей странице, а всего), нажно для постраничности
    //-------
    private boolean editModeView;// отображение режима редактирования

    public BookListController() {
        fillBooksAll();
    }

    private void submitValues(Character selectedLetter, long selectedPageNumber, long selectedGenreId, boolean requestFromPager) {
        this.selectedLetter = selectedLetter;
        this.selectedPageNumber = selectedPageNumber;
        this.selectedGenreId = selectedGenreId;
        this.pageSelected = requestFromPager;

    }

    //<editor-fold defaultstate="collapsed" desc="запросы в базу">
    private void fillBooksAll() {
        currentBookList = DataHelper.getInstance().getAllBooks();
    }

    public String fillBooksByGenre() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        selectedGenreId = Long.valueOf(params.get("genre_id"));

        submitValues(' ', 1, selectedGenreId, false);
        currentBookList = DataHelper.getInstance().getBookByGenre(selectedGenreId);

        return "books";
    }
    
    public String fillBooksByLetter() {
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedLetter = params.get("letter").charAt(0);
        
        submitValues(selectedLetter, 1, -1, false);
        
        
       currentBookList = DataHelper.getInstance().getBookByLetter(selectedLetter);
        
        return "books";
    }
    

    public String fillBooksBySearch() {

        submitValues(' ', 1, -1, false);

        if (currentSearchString.trim().length() == 0) {
            fillBooksAll();
            return "books";
        }

        if (selectedSearchType == SearchType.AUTHOR) {
            currentBookList = DataHelper.getInstance().getBookByAuthor(currentSearchString);
        } else if (selectedSearchType == SearchType.TITLE) {
            currentBookList = DataHelper.getInstance().getBookByName(currentSearchString);
        }


        return "books";
    }

    public void updateBooks() {
       
        cancelEditMode();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="режим редактирования">
    public void showEdit() {
        editModeView = true;
    }

    public void cancelEditMode() {
        editModeView = false;
        for (Book book : currentBookList) {
            book.setEdit(false);
        }
    }
    //</editor-fold>

    public Character[] getRussianLetters() {
        Character[] letters = new Character[]{'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',};
        return letters;
    }

    public void searchStringChanged(ValueChangeEvent e) {
        currentSearchString = e.getNewValue().toString();
    }

    public void searchTypeChanged(ValueChangeEvent e) {
        selectedSearchType = (SearchType) e.getNewValue();
    }

    //<editor-fold defaultstate="collapsed" desc="постраничность">
    public void changeBooksCountOnPage(ValueChangeEvent e) {
//        imitateLoading();
//        cancelEditMode();
//        pageSelected = false;
//        booksCountOnPage = Integer.valueOf(e.getNewValue().toString()).intValue();
//        selectedPageNumber = 1;
//        fillBooksBySQL(currentSqlNoLimit);
    }

    public void selectPage() {
//        cancelEditMode();
//        imitateLoading();
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        selectedPageNumber = Integer.valueOf(params.get("page_number"));
//        pageSelected = true;
//        fillBooksBySQL(currentSqlNoLimit);
    }

    private void fillPageNumbers(long totalBooksCount, int booksCountOnPage) {

        int pageCount = 0;

        if (totalBooksCount % booksCountOnPage == 0) {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) : 0;
        } else {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) + 1 : 0;
        }

        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="гетеры сетеры">
    public boolean isEditMode() {
        return editModeView;
    }

    public ArrayList<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(ArrayList<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public String getSearchString() {
        return currentSearchString;
    }

    public void setSearchString(String searchString) {
        this.currentSearchString = searchString;
    }

    public SearchType getSearchType() {
        return selectedSearchType;
    }

    public void setSearchType(SearchType searchType) {
        this.selectedSearchType = searchType;
    }

    public List<Book> getCurrentBookList() {
        return currentBookList;
    }

    public void setTotalBooksCount(long booksCount) {
        this.totalBooksCount = booksCount;
    }

    public long getTotalBooksCount() {
        return totalBooksCount;
    }

    public long getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(int selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public int getBooksOnPage() {
        return booksCountOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksCountOnPage = booksOnPage;
    }

    public void setSelectedPageNumber(long selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public Long getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Long selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }
    
    
    //</editor-fold>

  
}
