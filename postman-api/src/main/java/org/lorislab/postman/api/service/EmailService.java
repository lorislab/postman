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

package org.lorislab.postman.api.service;

import java.util.Locale;
import org.lorislab.postman.api.model.Email;
import org.lorislab.postman.api.model.EmailConfig;
import org.lorislab.postman.api.model.EmailTemplateResource;

/**
 * The email service.
 * 
 * @author Andrej Petras
 */
public interface EmailService {
    
    /**
     * Load the mail template resource.
     *
     * @param template the template name.
     * @param name the resource name.
     * @param locale the locale.
     * @return the mail template resource.
     * 
     * @throws java.lang.Exception if the method fails.
     */
    public EmailTemplateResource loadMailTemplateResource(String template, String name, Locale locale) throws Exception;  
    
    /**
     * Sends the email.
     *
     * @param email the email object.
     * @param config the email configuration.
     * 
     * @throws java.lang.Exception if the method fails.
     */
    public void sendEmail(Email email, EmailConfig config) throws Exception;

}
