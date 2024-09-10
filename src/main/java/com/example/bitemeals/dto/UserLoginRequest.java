package com.example.bitemeals.dto;

public class UserLoginRequest {

   private String email;
   private String password;

   public UserLoginRequest() {}

   public UserLoginRequest(String email, String password) {
       this.email = email;
       this.password = password;
   }

   public String getPassword() {
       return password;
   }

    public String getEmail() {
       return email;
    }
}
