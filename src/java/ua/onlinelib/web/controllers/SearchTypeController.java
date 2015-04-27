/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ua.onlinelib.web.enums.SearchType;


@ManagedBean(eager = true)
@RequestScoped
public class SearchTypeController implements Serializable{
    private Map<String, SearchType> searchList = new HashMap<String, SearchType>();

    public SearchTypeController() {
        ResourceBundle bundle = ResourceBundle.getBundle("ua.onlinelib.web.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("author_name_s"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_name"), SearchType.TITLE);
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
        
}
