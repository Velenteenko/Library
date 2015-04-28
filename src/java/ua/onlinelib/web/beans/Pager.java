/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author velenteenko
 */
public class Pager<T> {
    private int selectedPageNumber = 1;
    private int booksCountOnPage = 5;
    private int totalBooksCount;
    private List<Integer> pageNumberOnPage = new ArrayList<Integer>();
    
    private List<T> list;
    
    
    public int getFrom(){
        return selectedPageNumber * booksCountOnPage - booksCountOnPage;
    }
    
    public int getTo(){
        return booksCountOnPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalBooksCount() {
        return totalBooksCount;
    }

    public void setTotalBooksCount(int totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public int getBooksCountOnPage() {
        return booksCountOnPage;
    }

    public void setBooksCountOnPage(int booksCountOnPage) {
        this.booksCountOnPage = booksCountOnPage;
    }

    public List<Integer> getPageNumberOnPage() {
        
        int pageCount = 0;
        if(totalBooksCount % booksCountOnPage == 0){
            pageCount = booksCountOnPage > 0 ? (totalBooksCount / booksCountOnPage) : 0;
        } else{
            pageCount = booksCountOnPage > 0 ? (totalBooksCount / booksCountOnPage) + 1 : 0;
        }
        pageNumberOnPage.clear();
        
        for (int i = 1; i <=pageCount; i++) {
            pageNumberOnPage.add(i);
        }
  
        return pageNumberOnPage;
    }
    
    
    
}
