
package ua.onlinelib.web.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


@ManagedBean
@SessionScoped
public class LocaleChanger implements Serializable {
    private final Locale ENGLISH = new Locale("en");
    private final Locale RUSSIAN = new Locale("ru");
    private final Locale UKRAINIAN = new Locale("uk");
    
    private Locale locale = RUSSIAN;
    
    public Locale getLocale(){
        return (locale);
    }
    
    private void updateViewLocale(){
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    public void setEnglish(ActionEvent ev){
        locale=ENGLISH;
        updateViewLocale();
    }
    
    public void setRussian(ActionEvent ev){
        locale=RUSSIAN;
        updateViewLocale();
    }
    
    public void setUkrainian(ActionEvent ev){
        locale=UKRAINIAN;
        updateViewLocale();
    }
}
