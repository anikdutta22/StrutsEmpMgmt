/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.services;

import com.exavalu.models.District;
import com.exavalu.utils.JDBCConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RISHAV DUTTA
 */
public class DistrictService {
     public static ArrayList getAllDistrictAccordingToState(int stateId ){
        ArrayList districtList = new ArrayList();
        try {

            Connection con = JDBCConnectionManager.getConnection();

            String sql = "SELECT districtId, districtName from district where stateId =?";
            
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, stateId);


            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                District district = new District();
                
                district.setDistrictId(rs.getInt("districtId"));
                district.setDistrictName(rs.getString("districtName"));
                
                districtList.add(district);
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return districtList;
    }
}
