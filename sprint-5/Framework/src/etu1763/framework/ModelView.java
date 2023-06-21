/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1763.framework;

/**
 *
 * @author andriniaina
 */
public class ModelView {
    String url;
    
    public ModelView() {
    }

    public ModelView(String view) {
        this.url = view;
    }

    public String getView() {
        return url;
    }

    public void setView(String view) {
        this.url = view;
    }
}
