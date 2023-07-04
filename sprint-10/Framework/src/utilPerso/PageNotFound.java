/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilPerso;

/**
 *
 * @author andriniaina
 */
public class PageNotFound extends Exception{
    private final String Message = "Page not found";
    
    @Override
    public String getMessage(){
        return Message;
    }
}

