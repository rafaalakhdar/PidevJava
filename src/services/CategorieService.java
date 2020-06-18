// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.sql.Statement;
import entities.User;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import entities.keyValuePair;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import entities.Categorie;
import utilitez.MyConnection;
import java.sql.Connection;

public class CategorieService
{
    Connection cnx;
    
    public CategorieService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }
    
    public void ajouterCategorie(final Categorie c) {
        try {
            final String requete = "INSERT INTO categorie(nom,userId) VALUES (?,?)";
            final PreparedStatement pst = this.cnx.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setInt(2, c.getUserId().getId());
            pst.executeUpdate();
            System.out.println(pst);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<keyValuePair> getUserNomId() {
        final List<keyValuePair> pair = new ArrayList<keyValuePair>();
        final String req = " SELECT id, nom FROM user ";
        try {
            final PreparedStatement preparedStatement = this.cnx.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final keyValuePair p = new keyValuePair(resultSet.getInt("id"), resultSet.getString("nom"));
                pair.add(p);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pair;
    }
    
    public List<keyValuePair> getCategorieNomId() {
        final List<keyValuePair> pair = new ArrayList<keyValuePair>();
        final String req = " SELECT id, nom FROM categorie ";
        try {
            final PreparedStatement preparedStatement = this.cnx.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final keyValuePair p = new keyValuePair(resultSet.getInt("id"), resultSet.getString("nom"));
                pair.add(p);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pair;
    }
    
    public List<Categorie> afficherCategorie() {
        final List<Categorie> myList = new ArrayList<Categorie>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT * FROM categorie order by id desc";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Categorie per = new Categorie();
                per.setId(rs.getInt("id"));
                per.setNom(rs.getString(2));
                per.setDate(rs.getDate(3));
                per.setUserId(new User(rs.getInt(4)));
                myList.add(per);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
    
    public void supprimerCategorie(final Categorie c) throws SQLException {
        try {
            final String req = "delete from categorie where id=?";
            final PreparedStatement pre = this.cnx.prepareStatement(req);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
            final int rowsDeleted = pre.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Audition supprim\u00e9e!");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void modifierCategorie(final Categorie e, final int id) throws SQLException {
        final String sql = "UPDATE categorie SET nom=?, userId=? WHERE id=" + id;
        final PreparedStatement statement = this.cnx.prepareStatement(sql);
        statement.setString(1, e.getNom());
        statement.setInt(2, e.getUserId().getId());
        final int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing product was updated successfully!");
        }
    }
    
    public List<String> getCategorieNames() {
        final List<String> myList = new ArrayList<String>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT nom FROM categorie";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                myList.add(rs.getString(1));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
}
