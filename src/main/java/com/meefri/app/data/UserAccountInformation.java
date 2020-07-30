package com.meefri.app.data;

public class UserAccountInformation {

    private boolean isVerified = false;
    private boolean isBlocked = false;
    private String verificationCode = "";

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public UserAccountInformation(String verificationCode){
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() { return verificationCode; }

    public void setVerificationCode(String code){ this.verificationCode = code;  }

    public void verifyAccount(String code){
        if(code.equals(verificationCode)) isVerified = true;
    }


}
