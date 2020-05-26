/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Rafaa
 */
public class User {
       private int id;
    private String username;
    private String username_canonical;
    private String email;
    private String email_canonical;
    private int enabled;
    private String salt;
    private String password;
    private Date last_login;
    private String confirmation_token;
    private Date password_requested_at;
    private String roles;
    private String sexe;
    private String pays;
    private String status;


    public User() {
    }

    public User(int id, String username, String email, String password, String sexe, String pays, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
    }

    public User(int id, String username, String username_canonical, String email, String email_canonical, int enabled, String salt, String password, Date last_login, String confirmation_token, Date password_requested_at, String roles, String sexe, String pays, String status) {
        this.id = id;
        this.username = username;
        this.username_canonical = username_canonical;
        this.email = email;
        this.email_canonical = email_canonical;
        this.enabled = enabled;
        this.salt = salt;
        this.password = password;
        this.last_login = last_login;
        this.confirmation_token = confirmation_token;
        this.password_requested_at = password_requested_at;
        this.roles = roles;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
    }

    public User(int id, String username, String sexe) {
        this.id = id;
        this.username = username;
        this.sexe = sexe;
    }

    public User(int id, String username, String email, int enabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
    }

    public User(String username, String email, int enabled, String password, String pays) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.pays = pays;
    }

    public User(String username, String email, String password, String sexe, String pays, String status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
    }

    public User(String username, String email, String sexe, String pays, String status) {
        this.username = username;
        this.email = email;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_canonical() {
        return username_canonical;
    }

    public void setUsername_canonical(String username_canonical) {
        this.username_canonical = username_canonical;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_canonical() {
        return email_canonical;
    }

    public void setEmail_canonical(String email_canonical) {
        this.email_canonical = email_canonical;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

  

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getConfirmation_token() {
        return confirmation_token;
    }

    public void setConfirmation_token(String confirmation_token) {
        this.confirmation_token = confirmation_token;
    }

    public Date getPassword_requested_at() {
        return password_requested_at;
    }

    public void setPassword_requested_at(Date password_requested_at) {
        this.password_requested_at = password_requested_at;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", username=" + username + ", username_canonical=" + username_canonical + ", email=" + email + ", email_canonical=" + email_canonical + ", enabled=" + enabled + ", salt=" + salt + ", password=" + password + ", last_login=" + last_login + ", confirmation_token=" + confirmation_token + ", password_requested_at=" + password_requested_at + ", roles=" + roles + ", sexe=" + sexe + ", pays=" + pays + ", status=" + status + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.username_canonical, other.username_canonical)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.email_canonical, other.email_canonical)) {
            return false;
        }
        if (!Objects.equals(this.salt, other.salt)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.confirmation_token, other.confirmation_token)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        if (!Objects.equals(this.sexe, other.sexe)) {
            return false;
        }
        if (!Objects.equals(this.pays, other.pays)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.last_login, other.last_login)) {
            return false;
        }
        if (!Objects.equals(this.password_requested_at, other.password_requested_at)) {
            return false;
        }
        return true;
    }

 
}
