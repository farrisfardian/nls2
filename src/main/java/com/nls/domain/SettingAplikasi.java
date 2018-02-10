/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author cak-ust
 */
@Entity
@Table(name = "setting_aplikasi")
public class SettingAplikasi {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 36)
    private String id;

    @Column(name = "interval_tracking_menit")
    private Integer intervalTrackingMenit;

    @Column(name = "interval_monitoring_menit")
    private Integer intervalMonitoringMenit;

    @Temporal(TemporalType.DATE)
    @Column(name = "tmt_berlaku")
    private Date tmtBerlaku;

    @Column(name = "emailaplikasi")
    private String emailAplikasi;

    @Column(name = "passwordemailaplikasi")
    private String passwordEmailAplikasi;

    @Column(name = "mailstoreprotocol")
    private String mailStoreProtocol;

    @Column(name = "mailimapshost")
    private String mailImapsHost;

    @Column(name = "mailimapsport")
    private String mailImapsPort;

    @Column(name = "mailimapstimeout")
    private String mailImapsTimeout;

    @Column(name = "mailsmtphost")
    private String mailSmtpHost;

    @Column(name = "mailsmtpport")
    private String mailSmtpPort;

    @Column(name = "mailsmtpauth")
    private String mailSmtpAuth;

    @Column(name = "mailsmtpsocketfactoryclass")
    private String mailSmtpSocketFactoryPort;

    @Column(name = "mailsmtpsocketfactoryport")
    private String mailSmtpSocketFactoryClass;

    @Column(name = "fcm_server_key")
    private String fcmServerKey;

    @Column(name = "fcm_legacy_server_key")
    private String fcmLegacyServerKey;

    @Column(name = "fcm_sender_id")
    private String fcmSenderId;

    @Column(name = "fcm_api_url")
    private String fcmApiUrl;

    @Column(name = "auto_batal_ijin_aktif")
    private Boolean autoBatalIjinAktif;

    /**
     * @return the tmtBerlaku
     */
    public Date getTmtBerlaku() {
        return tmtBerlaku;
    }

    /**
     * @param tmtBerlaku the tmtBerlaku to set
     */
    public void setTmtBerlaku(Date tmtBerlaku) {
        this.tmtBerlaku = tmtBerlaku;
    }

    /**
     * @return the intervalTrackingMenit
     */
    public Integer getIntervalTrackingMenit() {
        return intervalTrackingMenit;
    }

    /**
     * @param intervalTrackingMenit the intervalTrackingMenit to set
     */
    public void setIntervalTrackingMenit(Integer intervalTrackingMenit) {
        this.intervalTrackingMenit = intervalTrackingMenit;
    }

    /**
     * @return the intervalMonitoringMenit
     */
    public Integer getIntervalMonitoringMenit() {
        return intervalMonitoringMenit;
    }

    /**
     * @param intervalMonitoringMenit the intervalMonitoringMenit to set
     */
    public void setIntervalMonitoringMenit(Integer intervalMonitoringMenit) {
        this.intervalMonitoringMenit = intervalMonitoringMenit;
    }

    public String getEmailAplikasi() {
        return emailAplikasi;
    }

    public void setEmailAplikasi(String emailAplikasi) {
        this.emailAplikasi = emailAplikasi;
    }

    public String getPasswordEmailAplikasi() {
        return passwordEmailAplikasi;
    }

    public void setPasswordEmailAplikasi(String passwordEmailAplikasi) {
        this.passwordEmailAplikasi = passwordEmailAplikasi;
    }

    public String getMailStoreProtocol() {
        return mailStoreProtocol;
    }

    public void setMailStoreProtocol(String mailStoreProtocol) {
        this.mailStoreProtocol = mailStoreProtocol;
    }

    public String getMailImapsHost() {
        return mailImapsHost;
    }

    public void setMailImapsHost(String mailImapsHost) {
        this.mailImapsHost = mailImapsHost;
    }

    public String getMailImapsPort() {
        return mailImapsPort;
    }

    public void setMailImapsPort(String mailImapsPort) {
        this.mailImapsPort = mailImapsPort;
    }

    public String getMailImapsTimeout() {
        return mailImapsTimeout;
    }

    public void setMailImapsTimeout(String mailImapsTimeout) {
        this.mailImapsTimeout = mailImapsTimeout;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(String mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpSocketFactoryPort() {
        return mailSmtpSocketFactoryPort;
    }

    public void setMailSmtpSocketFactoryPort(String mailSmtpSocketFactoryPort) {
        this.mailSmtpSocketFactoryPort = mailSmtpSocketFactoryPort;
    }

    public String getMailSmtpSocketFactoryClass() {
        return mailSmtpSocketFactoryClass;
    }

    public void setMailSmtpSocketFactoryClass(String mailSmtpSocketFactoryClass) {
        this.mailSmtpSocketFactoryClass = mailSmtpSocketFactoryClass;
    }

    /**
     * @return the fcmServerKey
     */
    public String getFcmServerKey() {
        return fcmServerKey;
    }

    /**
     * @param fcmServerKey the fcmServerKey to set
     */
    public void setFcmServerKey(String fcmServerKey) {
        this.fcmServerKey = fcmServerKey;
    }

    /**
     * @return the fcmLegacyServerKey
     */
    public String getFcmLegacyServerKey() {
        return fcmLegacyServerKey;
    }

    /**
     * @param fcmLegacyServerKey the fcmLegacyServerKey to set
     */
    public void setFcmLegacyServerKey(String fcmLegacyServerKey) {
        this.fcmLegacyServerKey = fcmLegacyServerKey;
    }

    /**
     * @return the fcmSenderId
     */
    public String getFcmSenderId() {
        return fcmSenderId;
    }

    /**
     * @param fcmSenderId the fcmSenderId to set
     */
    public void setFcmSenderId(String fcmSenderId) {
        this.fcmSenderId = fcmSenderId;
    }

    /**
     * @return the fcmApiUrl
     */
    public String getFcmApiUrl() {
        return fcmApiUrl;
    }

    /**
     * @param fcmApiUrl the fcmApiUrl to set
     */
    public void setFcmApiUrl(String fcmApiUrl) {
        this.fcmApiUrl = fcmApiUrl;
    }

    /**
     * @return the autoBatalIjinAktif
     */
    public Boolean getAutoBatalIjinAktif() {
        return autoBatalIjinAktif;
    }

    /**
     * @param autoBatalIjinAktif the autoBatalIjinAktif to set
     */
    public void setAutoBatalIjinAktif(Boolean autoBatalIjinAktif) {
        this.autoBatalIjinAktif = autoBatalIjinAktif;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
