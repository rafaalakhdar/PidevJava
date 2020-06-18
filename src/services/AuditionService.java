// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.sql.ResultSet;
import java.sql.Statement;
import entities.User;
import entities.Categorie;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import entities.SendMail;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitez.Copy;
import java.io.File;
import java.util.UUID;
import entities.Audition;
import utilitez.MyConnection;
import java.sql.Connection;

public class AuditionService
{
    Connection cnx;
    
    public AuditionService() {
        this.cnx = MyConnection.getInstance().getCnx();
    }
    
    public void ajouterAudition(final Audition p) throws IOException {
        try {
            final String req = "INSERT INTO audition(nom,description,image,video,checked,qualified,categorieFk,userFk) VALUES (?,?,?,?,?,?,?,?)";
            final PreparedStatement pst = this.cnx.prepareStatement(req);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            String image = p.getImage();
            final UUID u = UUID.randomUUID();
            final String old = image;
            final String extension = image.substring(image.lastIndexOf("."));
            image = image.substring(image.lastIndexOf("\\") + 1, image.lastIndexOf("."));
            image = image + u.toString() + extension;
            pst.setString(3, image);
            String video = p.getVideo();
            final UUID uv = UUID.randomUUID();
            final String oldv = video;
            final String extensionv = video.substring(video.lastIndexOf("."));
            video = video.substring(video.lastIndexOf("\\") + 1, video.lastIndexOf("."));
            video = video + u.toString() + extensionv;
            pst.setString(4, video);
            pst.setBoolean(5, p.getChecked());
            pst.setBoolean(6, p.getQualified());
            pst.setInt(7, p.getCategorieFk().getId());
            pst.setInt(8, p.getUserFk().getId());
            pst.executeUpdate();
            final File source = new File(old);
            final File dest = new File("C:\\wamp64\\www\\PIDEV\\web\\uploads\\files\\" + image);
            Copy.copyFileUsingStream(source, dest);
            final File sourcev = new File(oldv);
            final File destv = new File("C:\\wamp64\\www\\PIDEV\\web\\uploads\\files\\" + video);
            Copy.copyFileUsingStream(sourcev, destv);
            System.out.println(pst);
        }
        catch (SQLException ex) {
            Logger.getLogger(AuditionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void supprimerAudition(final Audition c) throws SQLException {
        try {
            final String req = "delete from audition where id=?";
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
    
    public void sendmail() {
        final String host = "smtp.gmail.com";
        final String port = "587";
        final String mailFrom = "jassem.laajili@esprit.tn";
        final String password = "182JMT2123";
        final String mailTo = "housssembnsr@gmail.com";
        final String subject = "DEMANDE DE PARTICIPATION \u00c0 UN \u00c9V\u00c9NEMENT ";
        final String message = "Madame, Monsieur, Directeur du Jardin d'enfant ELITE\n\nC\u2019est avec un immense plaisir que mon enfant peut participer \u00e0 cette \u00e9vennement.\n\nmerci et cordialement.";
        final SendMail mailer = new SendMail();
        try {
            mailer.SendMail(host, port, mailFrom, password, mailTo, subject, message);
            System.out.println("Email sent.");
        }
        catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
    }
    
    public void modifierAudition(final Audition e) throws SQLException {
        final String sql = "UPDATE audition SET nom=?, description=?, image=?, video=?, checked=?, qualified=?, categorieFk=? WHERE id=?";
        final PreparedStatement statement = this.cnx.prepareStatement(sql);
        statement.setString(1, e.getNom());
        statement.setString(2, e.getDescription());
        statement.setString(3, e.getImage());
        statement.setString(4, e.getVideo());
        statement.setBoolean(5, e.getChecked());
        statement.setBoolean(6, e.getQualified());
        statement.setInt(7, e.getCategorieFk().getId());
        statement.setInt(8, e.getId());
        final int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing product was updated successfully!");
        }
    }
    
    public List<Audition> afficherTout() {
        final List<Audition> myList = new ArrayList<Audition>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT * FROM audition order by id desc";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Audition per = new Audition();
                per.setId(rs.getInt(1));
                per.setNom(rs.getString(2));
                per.setDescription(rs.getString(3));
                per.setDate(rs.getDate(4));
                per.setImage(rs.getString(5));
                per.setVideo(rs.getString(6));
                per.setChecked(rs.getBoolean(7));
                per.setQualified(rs.getBoolean(8));
                per.setCategorieFk(new Categorie(rs.getInt(9)));
                per.setUserFk(new User(rs.getInt(10)));
                myList.add(per);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
    
    public List<Audition> afficherUnchecked() {
        final List<Audition> myList = new ArrayList<Audition>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT * FROM audition WHERE checked = 0 order by id desc";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Audition per = new Audition();
                per.setId(rs.getInt(1));
                per.setNom(rs.getString(2));
                per.setDescription(rs.getString(3));
                per.setDate(rs.getDate(4));
                per.setImage(rs.getString(5));
                per.setVideo(rs.getString(6));
                per.setChecked(rs.getBoolean(7));
                per.setQualified(rs.getBoolean(8));
                per.setCategorieFk(new Categorie(rs.getInt(9)));
                per.setUserFk(new User(rs.getInt(10)));
                myList.add(per);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
    
    public List<Audition> afficherUnqualified() {
        final List<Audition> myList = new ArrayList<Audition>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT * FROM audition WHERE qualified = 0 order by id desc";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Audition per = new Audition();
                per.setId(rs.getInt(1));
                per.setNom(rs.getString(2));
                per.setDescription(rs.getString(3));
                per.setDate(rs.getDate(4));
                per.setImage(rs.getString(5));
                per.setVideo(rs.getString(6));
                per.setChecked(rs.getBoolean(7));
                per.setQualified(rs.getBoolean(8));
                per.setCategorieFk(new Categorie(rs.getInt(9)));
                per.setUserFk(new User(rs.getInt(10)));
                myList.add(per);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
    
    public List<Audition> afficherUncheckedUnqualified() {
        final List<Audition> myList = new ArrayList<Audition>();
        try {
            final Statement st = this.cnx.createStatement();
            final String requete = "SELECT * FROM audition WHERE checked = 0 AND qualified = 0 order by id desc";
            final ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                final Audition per = new Audition();
                per.setId(rs.getInt(1));
                per.setNom(rs.getString(2));
                per.setDescription(rs.getString(3));
                per.setDate(rs.getDate(4));
                per.setImage(rs.getString(5));
                per.setVideo(rs.getString(6));
                per.setChecked(rs.getBoolean(7));
                per.setQualified(rs.getBoolean(8));
                per.setCategorieFk(new Categorie(rs.getInt(9)));
                per.setUserFk(new User(rs.getInt(10)));
                myList.add(per);
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return myList;
    }
    
    public int countAudition(final Categorie cat) {
        try {
            final String sql = "SELECT COUNT(*) as total FROM audition WHERE categorieFk = " + cat.getId();
            final PreparedStatement statement = this.cnx.prepareStatement(sql);
            final ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("total");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    
    public int countAllAudition() {
        try {
            final String sql = "SELECT COUNT(*) as total FROM audition ";
            final PreparedStatement statement = this.cnx.prepareStatement(sql);
            final ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("total");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
    public int countAllUncheckedAudition() {
        try {
            final String sql = "SELECT COUNT(*) as total FROM audition Where checked = 0";
            final PreparedStatement statement = this.cnx.prepareStatement(sql);
            final ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("total");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
