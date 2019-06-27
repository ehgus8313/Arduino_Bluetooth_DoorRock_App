package com.example.k201444001;


public class UserObject {

    private String btname;
    private String btpassword;
    private String password;
    private boolean loginOption;

    public UserObject(String btname, String btpassword, String password, boolean loginOption) {
        this.btname = btname;
        this.btpassword = btpassword;
        this.password = password;
        this.loginOption = loginOption;
    }

    public String getBTname() {
        return btname;
    }

    public String getBtpassword() {
        return btpassword;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoginOption() {
        return loginOption;
    }
}
