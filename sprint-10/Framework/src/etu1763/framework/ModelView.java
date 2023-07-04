/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1763.framework;

import java.util.HashMap;

/**
 *
 * @author andriniaina
 */
public class ModelView {
    String url;
    private HashMap<String,Object> data;
    
    public ModelView() {
         data= new HashMap<String,Object>();
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
    /**
     * @return the data
     */
    public HashMap<String,Object> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(HashMap<String,Object> data) {
        this.data = data;
    }

    /**
     * @return the addItems
     */

    /**
     * @param addItems the addItems to set
     */
    public void AddItems(String key,Object liste) {
        data.put(key, liste);
    }
}
