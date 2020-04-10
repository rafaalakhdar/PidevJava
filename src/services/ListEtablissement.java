/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import entities.Etablissement;
import utilitez.MyConnection;

/**
 *
 * @author Emir
 */
public class ListEtablissement {
     public ObservableList<Etablissement> ListEtab() {
        ObservableList<Etablissement> myList = FXCollections.observableArrayList();
        Connection cn = MyConnection.getInstance().getCnx();
        try {
            String requete = "SELECT * from etablissement" ;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Etablissement etab = new Etablissement();
                etab.setName(rs.getString("name"));
               
                etab.setLatitude(rs.getDouble("latitude"));
                etab.setLongitude(rs.getDouble("longitude"));

                
               
                myList.add(etab);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }
  

  
  
}
