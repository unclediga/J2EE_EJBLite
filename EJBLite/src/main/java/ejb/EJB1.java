package ejb;

import javax.ejb.Stateless;

@Stateless
public class EJB1 {
    public String getHello(){
        return "Hello from bean!";
    }
}
