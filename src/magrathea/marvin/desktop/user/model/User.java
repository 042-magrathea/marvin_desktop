/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author boscalent
 */
public class User {

    private long id;
    private String nickname;
    private String password;  // only for user of the app
    private String email;
    private boolean administrator;

    //implemented just for user insertion
    private String name, phone, privateDes, publicDes,
            userRole, language, datePassword, memberSince;
    private boolean ads;
    
    public User() {
    }

    // Equals & HashCode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.nickname);
        hash = 59 * hash + Objects.hashCode(this.email);
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
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User{id=");
        builder.append(id);
        builder.append(", nickname=");
        builder.append(nickname);
        builder.append(", password=");
        builder.append(password);
        builder.append(", email=");
        builder.append(email);
        builder.append(", administrator=");
        builder.append(administrator);
        builder.append('}');
        return builder.toString();
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the ads
     */
    public boolean getAds() {
        return ads;
    }

    /**
     * @param ads the ads to set
     */
    public void setAds(boolean ads) {
        this.ads = ads;
    }

    /**
     * @return the privateDes
     */
    public String getPrivateDes() {
        return privateDes;
    }

    /**
     * @param privateDes the privateDes to set
     */
    public void setPrivateDes(String privateDes) {
        this.privateDes = privateDes;
    }

    /**
     * @return the publicDes
     */
    public String getPublicDes() {
        return publicDes;
    }

    /**
     * @param publicDes the publicDes to set
     */
    public void setPublicDes(String publicDes) {
        this.publicDes = publicDes;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the datePassword
     */
    public String getDatePassword() {
        return datePassword;
    }

    /**
     * @param datePassword the datePassword to set
     */
    public void setDatePassword(String datePassword) {
        this.datePassword = datePassword;
    }

    /**
     * @return the memberSince
     */
    public String getMemberSince() {
        return memberSince;
    }

    /**
     * @param memberSince the memberSince to set
     */
    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

}
