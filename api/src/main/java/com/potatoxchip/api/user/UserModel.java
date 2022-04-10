package com.potatoxchip.api.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user")
public class UserModel {
    @Id
    private Long id;
    private String userName;
    private String handleName;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserModel{" + "id=" + id + ", userName='" + userName + '\'' + ", handleName='" + handleName + '\'' +
                ", email='" + email + '\'' + '}';
    }
}
