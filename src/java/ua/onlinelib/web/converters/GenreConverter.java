package ua.onlinelib.web.converters;

import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import ua.onlinelib.web.ENTITY.Genre;
import ua.onlinelib.web.db.DataHelper;
//import ua.onlinelib.web.beans.Genre;
//import ua.onlinelib.web.db.Database;

@ManagedBean(eager = false)
@ApplicationScoped
public class GenreConverter implements Serializable, Converter {

    private List<Genre> genreList;
    private Map<Long, Genre> genreMap;
    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
//    private List<SelectItem> ArrayList;
//    private List<SelectItem> SelectItem;

    public GenreConverter() {
//        fillAllGenres();
        genreMap = new HashMap<Long, Genre>();
        genreList = DataHelper.getInstance().getAllGenres();

        for (Genre genre : genreList) {
            genreMap.put(genre.getId(), genre);
            selectItems.add(new SelectItem(genre, genre.getName()));
        }
    }
    
//    private List<Genre> fillAllGenres() {
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection conn = null;
//        
//        genreList = new ArrayList<Genre>();
//        
//        try {
//            conn = Database.getConnection();
//
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery("select * from library.genre order by name");
//            while (rs.next()) {
//                Genre genre = new Genre();
//                genre.setName(rs.getString("name"));
//                genre.setId(rs.getLong("id"));
//                genreList.add(genre);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(GenreConvertor.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(GenreConvertor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return genreList;
//    }

    public List<Genre> getGenreList() {
//        if (!genreList.isEmpty()) {
        return genreList;
//        } else {
//            return getGenres();
//        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return genreMap.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Genre) value).getId().toString();
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
}
