/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.Country;
import com.exavalu.utils.JDBCConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Anik Dutta
 */
public class CountryService {
    
    public static ArrayList getAllCountry(){
        ArrayList countryList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "Select * from country";

            PreparedStatement preparedStatement = con.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Country country = new Country();
                
                country.setCountryId(rs.getInt("countryId"));
                country.setCountryName(rs.getString("countryName"));
                
                countryList.add(country);
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countryList;
    }
}
