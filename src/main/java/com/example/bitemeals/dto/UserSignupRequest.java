package com.example.bitemeals.dto;

import lombok.NonNull;

public class UserSignupRequest {
    private String userName;
    private String email;
    private String password;

   // Constructors
    public UserSignupRequest() {}

    public UserSignupRequest(String userName,String email, String password) {
       this.userName = userName;
       this.email = email;
       this.password = password;
   }

   // Getters and Setters
   public String getUserName() {
       return userName;
   }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
