package com.cbox.business.system.user.userlogin.bean;



public class LoginReqVO {

    /** 登陆账号 */
    private String accountId;

    /** 登陆密码 */
    private String password;

    /** 新密码 */
    private String newpassword;

    private String veryCode;

    public String getVeryCode() {
        return veryCode;
    }

    public void setVeryCode(String veryCode) {
        this.veryCode = veryCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
