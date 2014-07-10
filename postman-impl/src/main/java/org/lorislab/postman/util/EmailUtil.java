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

import org.lorislab.postman.api.model.Email;
import org.lorislab.postman.api.model.EmailConfig;

/**
 * The email utility.
 * 
 * @author Andrej Petras
 */
public final class EmailUtil {
    
    /**
     * The default constructor.
     */
    private EmailUtil() {
        // empty constructor
    }
    
    /**
     * Updates the email base on the email configuration.
     *
     * @param email the email.
     * @param config the email configuration.
     */
    public static void configureEmail(final Email email, final EmailConfig config) {
        // set email locale
        email.setLocale(config.getLocale());
        // set email from
        email.setFrom(config.getFrom());
        // sets the attributes
        email.setContentType(config.getContentType());
        email.setContentCharset(config.getContentCharset());
        email.setTransferEncoding(config.getTransferEncoding());
        // add special parameter
        email.getParameters().put(EmailConfig.class.getSimpleName(), config);
        email.getParameters().put(Email.class.getSimpleName(), email);
    }    
}
