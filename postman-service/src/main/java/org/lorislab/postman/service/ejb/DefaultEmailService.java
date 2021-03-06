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
package org.lorislab.postman.service.ejb;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.lorislab.jel.base.util.FileUtil;
import org.lorislab.postman.api.model.Email;
import org.lorislab.postman.api.model.EmailConfig;
import org.lorislab.postman.api.model.EmailTemplateResource;
import org.lorislab.postman.api.service.EmailService;
import org.lorislab.postman.impl.PostBox;
import org.lorislab.postman.util.EmailUtil;
import org.lorislab.postman.util.PostBoxUtil;
import org.lorislab.postman.util.ResourceUtil;
import org.lorislab.treasure.api.factory.PasswordServiceFactory;
import org.lorislab.treasure.api.service.PasswordService;

/**
 * The default email service implementation.
 *
 * @author Andrej Petras
 */
@Stateless
@Local(EmailService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DefaultEmailService implements EmailService {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(DefaultEmailService.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailTemplateResource loadMailTemplateResource(String template, String name, Locale locale) throws Exception {
        byte[] data = null;
        try {
            // load the file
            String path = ResourceUtil.getEmailResourcePath(template, locale, name);
            data = FileUtil.readFileAsByteArray(path, this.getClass().getClassLoader());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the template " + template + " resource " + name, ex);
        }
        // create template resource
        EmailTemplateResource result = new EmailTemplateResource();
        result.setName(name);
        result.setContent(data);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override        
    @Asynchronous
    public void sendEmailAsync(Email email, EmailConfig config) throws Exception {
        sendEmail(email, config);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override    
    public void sendEmail(Email email, EmailConfig config) throws Exception {

        // check email configuration
        if (config != null) {

            // create the session
            Session session = null;
            String jndi = config.getJndi();
            if (jndi != null && !jndi.isEmpty()) {
                try {
                    InitialContext ic = new InitialContext();
                    session = (Session) ic.lookup(config.getJndi());
                } catch (NamingException ex) {
                    throw new Exception("Error lookup session " + config.getJndi() + " for the email " + email.getGuid(), ex);
                }
            } else {
                PasswordService pswdService = PasswordServiceFactory.getService();
                char[] pswd = pswdService.getPassword(config.getPassword());
                session = PostBoxUtil.createSession(config, pswd);
            }
            
            // configure email
            EmailUtil.configureEmail(email, config);
            
            // load the subject and content
            String subject = ResourceUtil.getEmailSubject(email.getTemplate(), email.getLocale(), email.getParameters());
            String content = ResourceUtil.getEmailContent(email.getTemplate(), email.getLocale(), email.getParameters());
            
            // send email.         
            PostBox postBox = new PostBox(session);
            postBox.sendEmail(email, subject, content);

        } else {
            LOGGER.fine("The email service is disabled!");
        }

    }

}
