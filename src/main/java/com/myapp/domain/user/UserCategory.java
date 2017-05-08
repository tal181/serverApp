package com.myapp.domain.user;

import com.myapp.domain.category.Category;

/**
 * Created by Tal on 16/04/2017.
 */
public class UserCategory extends Category {


    private String loginName;


    public UserCategory() {
        super("", 0);
    }

    public UserCategory(String loginName, String category, double value) {
        super(category, value);
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}

