// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import utilitez.MyConnection;
import javafx.collections.FXCollections;
import entities.Etablissement;
import javafx.collections.ObservableList;

public class ListEtablissement
{
    public ObservableList<Etablissement> ListEtab() {
        final ObservableList<Etablissement> myList = FXCollections.observableArrayList();
        final Connection cn = MyConnection.getInstance().getCnx();
        try {
            final String requete = "SELECT * from etablissement";
            final Statement st = cn.createStatement();
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Etablissement etab = new Etablissement();
                etab.setName(rs.getString("name"));
                etab.setLatitude(rs.getDouble("latitude"));
                etab.setLongitude(rs.getDouble("longitude"));
               myList.add(etab);
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }
}
