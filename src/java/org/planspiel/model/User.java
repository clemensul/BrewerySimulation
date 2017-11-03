/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.model;

public class User {

    private Company company;
    private String cookie;
    private Boolean admin;

    public User(String cname, String cookie, Boolean admin) {
        this.cookie = cookie;
        this.company = new Company(cname);
        this.admin = admin;
    }

    public Company getCompany() {
        return company;
    }
    
    public String getCookie() {
        return cookie;
    }
    
    public Boolean getAdmin(){
        return admin;
    }
}
