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
package org.lorislab.postman.api.factory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.lorislab.postman.api.model.Email;

/**
 * The email factory.
 *
 * @author Andrej Petras
 */
public final class EmailFactory {

    /**
     * The default constructor.
     */
    private EmailFactory() {
        // empty constructor
    }

    /**
     * Creates the email.
     *
     * @param locale the email locale.
     * @param to the email to address.
     * @param template the email template.
     * @param values the list of template values.
     * @return the created email.
     */
    public static Email createEmail(Locale locale, String template, String to, Object... values) {
        Email result = new Email();
        result.getTo().add(to);
        // set email locale
        result.setLocale(locale);
        // sets the attributes
        result.setTemplate(template);
        // add parameters
        result.setParameters(createTemplateValues(values));
        return result;
    }

    /**
     * Creates the template values.
     *
     * @param values the object.
     * @return the map of object for the template generator.
     */
    private static Map<String, Object> createTemplateValues(Object... values) {
        Map<String, Object> result = new HashMap<>();
        if (values != null) {
            for (Object item : values) {
                result.put(item.getClass().getSimpleName(), item);
            }
        }
        return result;
    }
}
