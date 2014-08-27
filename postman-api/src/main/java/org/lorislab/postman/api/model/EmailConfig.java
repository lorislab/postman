/*
 * Copyright 2014 lorislab.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.postman.api.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * The email configuration.
 *
 * @author Andrej Petras
 */
public class EmailConfig implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 660924217079412123L;

    /**
     * The JNDI lookup path.
     */
    private String jndi = null;

    /**
     * The SMTP host.
     */
    private String host = null;

    /**
     * The server port.
     */
    private int port = 25;

    /**
     * The user.
     */
    private String user = null;

    /**
     * The server password.
     */
    private String password = null;

    /**
     * The SSL flag.
     */
    private boolean ssl = false;

    /**
     * The server.
     */
    private String server = "http://localhost:8080/test";
    /**
     * The server resource URL.
     */
    private String url = "http://localhost:8080/test/public";
    /**
     * Disabled or enabled the send email functionality.
     */
    private boolean enabled = false;
    /**
     * The default email locale.
     */
    private Locale locale = Locale.ENGLISH;
    /**
     * The from email.
     */
    private String from = "root@localhost";
    /**
     * The content type.
     */
    private String contentType = "text/html;charset=\"UTF-8\"";
    /**
     * The content char-set.
     */
    private String contentCharset = "UTF-8";
    /**
     * The transfer encoding.
     */
    private String transferEncoding = "quoted-printable";

    /**
     * Gets the SSL flag.
     *
     * @return the SSL flag.
     */
    public boolean isSsl() {
        return ssl;
    }

    /**
     * Sets the SSL flag.
     *
     * @param ssl the SSL flag.
     */
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    /**
     * Gets the port.
     *
     * @return the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port.
     *
     * @param port the port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the JNDI path.
     *
     * @return the JNDI path.
     */
    public String getJndi() {
        return jndi;
    }

    /**
     * Sets the JNDI path.
     *
     * @param jndi the JNDI path.
     */
    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    /**
     * Gets the SMTP host.
     *
     * @return the SMTP host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the SMTP host.
     *
     * @param host the SMTP host.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user.
     *
     * @return the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the server.
     *
     * @return the server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the server.
     *
     * @param server the server.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets the URL.
     *
     * @return the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL.
     *
     * @param url the URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the enabled.
     *
     * @return the enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the locale.
     *
     * @return the locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the locale.
     *
     * @param locale the locale to set.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the from.
     *
     * @return the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from the from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets the content type.
     *
     * @return the contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the contentType to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the content char-set.
     *
     * @return the contentCharset.
     */
    public String getContentCharset() {
        return contentCharset;
    }

    /**
     * Sets the content char-set.
     *
     * @param contentCharset the contentCharset to set
     */
    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    /**
     * Gets the transfer encoding.
     *
     * @return the transferEncoding.
     */
    public String getTransferEncoding() {
        return transferEncoding;
    }

    /**
     * Sets the transfer encoding.
     *
     * @param transferEncoding the transferEncoding to set.
     */
    public void setTransferEncoding(String transferEncoding) {
        this.transferEncoding = transferEncoding;
    }

}
