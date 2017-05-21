package manager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.RateEvent;
 
@ManagedBean
public class RatingView {
     
    private Integer rating3;    
     
    public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Obrigado pela avaliação!");
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println(((Integer) rateEvent.getRating()).intValue());
    }
     
    public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
 
    public Integer getRating3() {
        return rating3;
    }
 
    public void setRating3(Integer rating3) {
        this.rating3 = rating3;
    }
 
}
