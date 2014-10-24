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

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The resource utility.
 *
 * @author Andrej Petras
 */
public final class ResourceUtil {

    /**
     * The content file prefix.
     */
    private static final String TEMPLATE_RESOURCE = "resource";
    /**
     * The content file prefix.
     */
    private static final String TEMPLATE_CONTENT = "content";
    /**
     * The subject file prefix.
     */
    private static final String TEMPLATE_SUBJECT = "subject";
    /**
     * The bundle file name
     */
    private static final String BUNDLE = "mailtemplate";

    /**
     * The template directory.
     */
    private static final String TEMPLATE_DIR = System.getProperty(ResourceUtil.class.getName(), null);
    /**
     * The map of templates.
     */
    private static final Map<String, Template> TEMPLATES = new HashMap<>();

    /**
     * The path separator.
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     * The default constructor.
     */
    private ResourceUtil() {
        // empty constructor
    }

    /**
     * Gets the path of the resource of the email.
     *
     * @param template the template.
     * @param locale the locale.
     * @param resource the resource.
     * @exception Exception if the method fails.
     * @return the path to the email resource.
     */
    public static final String getEmailResourcePath(String template, Locale locale, String resource) throws Exception {
        String tmp = getResourcePath(template, TEMPLATE_RESOURCE, locale);
        StringBuilder sb = new StringBuilder();
        sb.append(tmp);
        sb.append(PATH_SEPARATOR);
        sb.append(resource);
        String result = sb.toString();

        if (TEMPLATE_DIR != null) {
            File file = new File(TEMPLATE_DIR, result);
            result = file.getAbsolutePath();
        }

        return result;
    }

    /**
     * Gets the mail content.
     *
     * @param template the mail template.
     * @param locale the mail locale.
     * @param parameters the mail parameters.
     * @return the mail content.
     * @throws Exception if the method fails.
     */
    public static String getEmailContent(String template, Locale locale, Map<String, Object> parameters) throws Exception {
        return getResource(TEMPLATE_CONTENT, template, locale, parameters);
    }

    /**
     * Gets the mail subject.
     *
     * @param template the mail template.
     * @param locale the mail locale.
     * @param parameters the mail parameters.
     * @return the mail subject.
     * @throws Exception if the method fails.
     */
    public static String getEmailSubject(String template, Locale locale, Map<String, Object> parameters) throws Exception {
        return getResource(TEMPLATE_SUBJECT, template, locale, parameters);
    }

    /**
     * Gets the email content.
     *
     * @param name the name of the content.
     * @param template the template.
     * @param parameters the list of parameters.
     * @return the email content.
     * @throws Exception if the method fails.
     */
    private static String getResource(String name, String template, Locale locale, Map<String, Object> parameters) throws Exception {

        String key = getResourcePath(template, name, locale);


        Template compiled = TEMPLATES.get(key);
        // if not exist create compiled template
        if (compiled == null) {
            // load from external directory
            if (TEMPLATE_DIR != null) {                
                compiled = Mustache.compiler().compile(new FileReader(new File(TEMPLATE_DIR, key)));
            } else {
                // load from class path
                try (InputStreamReader stream = new InputStreamReader( EmailUtil.class.getResourceAsStream(key))) {
                    compiled = Mustache.compiler().compile(stream);
                }
            }
            // add to the template cache only if not use the directory.
            if (compiled != null && TEMPLATE_DIR == null) {
                TEMPLATES.put(key, compiled);
            }
        }
        // create the result
        String result = null; 
        if (compiled != null) {
            result = compiled.execute(parameters);
        }
        return result;
    }

    /**
     * Gets the resource path.
     *
     * @param template the template.
     * @param name the name of the resource.
     * @param locale the locale.
     * @return the corresponding path.
     * @throws Exception if the method fails.
     */
    private static String getResourcePath(String template, String name, Locale locale) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE, locale);
        // create ID
        StringBuilder sb = new StringBuilder();
        sb.append(template).append('.').append(name);
        String key = rb.getString(sb.toString());
        if (key == null) {
            throw new Exception("Missing mail template key: " + key);
        }
        return key;
    }
}
