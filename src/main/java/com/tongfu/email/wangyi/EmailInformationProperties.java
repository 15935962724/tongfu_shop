package com.tongfu.email.wangyi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "emailinformation")
public class EmailInformationProperties {


    private String MyEmailSMTPHost;
    //
    private String serviceSendEmailAccount;
    //
    private String serviceSendEmailPassword;
    //
    private String devTestProSystem;

    private String IpAddress;

    public String getMyEmailSMTPHost() {
        return MyEmailSMTPHost;
    }

    public void setMyEmailSMTPHost(String myEmailSMTPHost) {
        MyEmailSMTPHost = myEmailSMTPHost;
    }

    public String getServiceSendEmailAccount() {
        return serviceSendEmailAccount;
    }

    public void setServiceSendEmailAccount(String serviceSendEmailAccount) {
        this.serviceSendEmailAccount = serviceSendEmailAccount;
    }

    public String getServiceSendEmailPassword() {
        return serviceSendEmailPassword;
    }

    public void setServiceSendEmailPassword(String serviceSendEmailPassword) {
        this.serviceSendEmailPassword = serviceSendEmailPassword;
    }

    public String getDevTestProSystem() {
        return devTestProSystem;
    }

    public void setDevTestProSystem(String devTestProSystem) {
        this.devTestProSystem = devTestProSystem;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }
}