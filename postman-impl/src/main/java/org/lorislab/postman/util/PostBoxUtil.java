/*
 * Copyright 2014 Andrej Petras.
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
package org.lorislab.postman.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import org.lorislab.postman.api.model.EmailConfig;

/**
 * The post box utility.
 *
 * @author Andrej Petras
 */
public final class PostBoxUtil {

    /**
     * The default constructor
     */
    private PostBoxUtil() {
        // empty constructor
    }

    /**
     * Creates the session.
     *
     * @param config the email configuration.
     * @param password the password.
     * @return the new session.
     */
    public static Session createSession(final EmailConfig config, final char[] password) {
        Session result = null;
        if (result == null) {
            Properties properties = new Properties();
            if (config.isSsl()) {
                properties.put("mail.transport.protocol", "smtps");
            } else {
                properties.put("mail.transport.protocol", "smtp");
            }

            properties.put("mail.smtp.ssl.enable", config.isSsl());
            properties.put("mail.smtp.port", config.getPort());
            properties.setProperty("mail.smtp.host", config.getHost());

            Authenticator authenticator = null;

            if (config.getUser() != null) {
                properties.put("mail.smtp.auth", "true");
                authenticator = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getUser(), new String(password));
                    }
                };
            }
            result = Session.getInstance(properties, authenticator);
        }
        return result;
    }

    /**
     * Creates the list of addresses.
     *
     * @param addresses the set of addresses.
     * @return the list of addresses.
     * @throws Exception if the method fails.
     */
    public static Address[] createAddresses(Set<String> addresses) throws Exception {
        Address[] result = null;
        if (addresses != null && !addresses.isEmpty()) {
            List<Address> items = new ArrayList<>();
            for (String address : addresses) {
                Address item = createAddress(address);
                items.add(item);
            }

            if (!items.isEmpty()) {
                result = items.toArray(new Address[items.size()]);
            }
        }
        return result;
    }

    /**
     * Creates the email address object.
     *
     * @param address the address as a string.
     * @return the email address object.
     * @throws Exception if the method fails.
     */
    public static Address createAddress(String address) throws Exception {
        Address result = null;
        if (address != null) {
            result = new InternetAddress(address);
        }
        return result;
    }

}
