package com.happymoney.webcrawler.controller.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Credentials")
public class UserDto {
    @ApiModelProperty(example="qa@happymoney.com")
    private String user;
    @ApiModelProperty(example="givemeoffer$123")
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
