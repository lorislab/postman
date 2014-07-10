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
package org.lorislab.postman.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.lorislab.postman.api.model.Email;
import org.lorislab.postman.api.model.EmailAttachment;
import org.lorislab.postman.util.PostBoxUtil;

/**
 * The post box.
 *
 * @author Andrej Petras
 */
public class PostBox {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(PostBox.class.getName());

    /**
     * The email object constant.
     */
    private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    /**
     * The email object constant.
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * The session.
     */
    private final Session session;

    /**
     * Creates the post box.
     *
     * @param session the mail session.
     */
    public PostBox(Session session) {
        this.session = session;
    }
   
    /**
     * Sends the email.
     *
     * @param email the email.
     * @param subject the email subject.
     * @param content the email content.
     * @throws Exception if the method fails.
     */
    public void sendEmail(final Email email, final String subject, final String content) throws Exception {
        // check email
        if (email == null) {
            throw new Exception("The email object is null!");
        }

        // check the session
        if (session == null) {
            throw new Exception("No active session found!");
        }

        // send email
        sendEmail(email, session, subject, content);
    }

    /**
     * Sends the email.
     *
     * @param email the email.
     * @param session the email session.
     * @param subject the email subject.
     * @param content the email content.
     * @throws Exception if the method fails.
     */
    private void sendEmail(final Email email, final Session session, final String subject, final String content) throws Exception {
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(PostBoxUtil.createAddress(email.getFrom()));
            message.setRecipients(Message.RecipientType.TO, PostBoxUtil.createAddresses(email.getTo()));

            message.setSubject(subject);
            message.setSentDate(new Date());

            // BCC addresses
            if (!email.getBcc().isEmpty()) {
                message.setRecipients(Message.RecipientType.BCC, PostBoxUtil.createAddresses(email.getBcc()));
            }

            // CC addresses
            if (!email.getCc().isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, PostBoxUtil.createAddresses(email.getCc()));
            }

            // Email content
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(content, email.getContentCharset());

            // Email header
            messagePart.setHeader(CONTENT_TRANSFER_ENCODING, email.getTransferEncoding());
            messagePart.setHeader(CONTENT_TYPE, email.getContentType());

            // create message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);

            List<EmailAttachment> attachments = email.getAttachments();
            if (attachments != null && !attachments.isEmpty()) {
                for (EmailAttachment attachment : attachments) {

                    MimeBodyPart attachmentPart = new MimeBodyPart();

                    byte[] fileContent = attachment.getContent();
                    attachmentPart.setContent(fileContent, attachment.getContentType());

                    attachmentPart.setFileName(attachment.getName());
                    multipart.addBodyPart(attachmentPart);
                }
            }
            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error by sending the email", ex);
            throw new Exception("Error send the email " + email.getGuid(), ex);
        }
    }
}
