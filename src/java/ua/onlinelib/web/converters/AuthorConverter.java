/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.converters;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import ua.onlinelib.web.ENTITY.Author;
import ua.onlinelib.web.db.DataHelper;
import ua.onlinelib.web.enums.SearchType;


/**
 *
 * @author velenteenko
 */

@ManagedBean(eager = false)
@ApplicationScoped

public class AuthorConverter implements Serializable, Converter{
    
    private List<SelectItem> selectedItems = new ArrayList<SelectItem>();
    private Map<Long, Author> map;
    private List<Author> list;

    public AuthorConverter() {
    map = new HashMap<Long, Author>();
    list = DataHelper.getInstance().getAllAuthors();
    
        for (Author author : list) {
            map.put(author.getId(), author);
            selectedItems.add(new SelectItem(author, author.getFio()));
        }
    }
    
    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
       return ((Author)value).getId().toString();
    }

    public List<SelectItem> getSelectedItems() {
        return selectedItems;
    }

    public List<Author> getAuthorList() {
        return list;
    }
    
}
