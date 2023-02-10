/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.models;

import com.exavalu.services.CountryService;
import com.exavalu.services.DistrictService;
import com.exavalu.services.EmployeeService;
import com.exavalu.services.LoginService;
import com.exavalu.services.StateService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import org.apache.struts2.dispatcher.ApplicationMap;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author ANIK DUTTA
 */
public class Users extends ActionSupport implements ApplicationAware, SessionAware, Serializable {

    private SessionMap<String, Object> sessionMap = (SessionMap) ActionContext.getContext().getSession();

    private ApplicationMap map = (ApplicationMap) ActionContext.getContext().getApplication();

    @Override
    public void setApplication(Map<String, Object> application) {
        map = (ApplicationMap) application;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        sessionMap = (SessionMap) session;
    }

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int countryId;
    private int stateId;
    private int districtId;

    public String doLogin() throws Exception {
        sessionMap.clear();
        String result = "FAILURE";

        boolean success = LoginService.getInstance().doLogin(this);
        System.out.println(success);
        if (success) {
            System.out.println("returning Success from doLogin method");
            sessionMap.put("LoggedIn", this);

            Users user = LoginService.getInstance().getUser(this.email);
            sessionMap.put("USER", user);

            ArrayList empList = EmployeeService.getInstance().getAllEmployees();
            sessionMap.put("EmpList", empList);
            result = "SUCCESS";
        } else {
            String errorMsg = "Either Email Address or Password is Wrong";
            sessionMap.put("ErrorMsg", errorMsg);
            System.out.println("returning Failure from doLogin method");
        }
        return result;
    }

    public String doLogout() throws Exception {
        sessionMap.clear();
        return "SUCCESS";
    }

    public String doSignUp(Users user) throws Exception {
        sessionMap.clear();
        String result = "FAILURE";

        boolean success = LoginService.getInstance().doSignUp(user);

        if (success) {
            System.out.println("returning Success from doSignUp method");
            String successMsg = "Now Login with your Email Id and PassWord";
            sessionMap.put("SuccessMsg", successMsg);
            result = "SUCCESS";
        } else {
            String errorMsg = "Email Id Already Register";
            sessionMap.put("ErrorMsg1", errorMsg);
            System.out.println("returning Failure from doSignUp method");
        }

        return result;
    }
    
    public String doPreSignUp() throws Exception {
        String result = "FAILURE";
        ArrayList countryList = CountryService.getAllCountry();
        ArrayList stateList =null;
        ArrayList districtList =null;
        
        
        sessionMap.put("CountryList", countryList);
        
        System.err.println("CountryId: "+this.countryId);
        System.err.println("CountryId: "+this.stateId);
        System.err.println("CountryId: "+this.districtId);
        
        if(this.countryId!=0){
            stateList = StateService.getAllStateAccordingToCountry(this.countryId);
            sessionMap.put("StateList", stateList);
            sessionMap.put("User", this);
        }
         if(this.countryId!=0 && this.stateId!=0){
            districtList = DistrictService.getAllDistrictAccordingToState(this.stateId);
            sessionMap.put("DistrictList", districtList);
            sessionMap.put("User", this);
        }
             
       if(this.firstName!=null && this.lastName!=null && this.email!=null && this.password!=null && this.countryId!=0 && this.districtId!=0 && this.stateId!=0){       
           result = this.doSignUp(this);
           
       }  
       
        return result;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
