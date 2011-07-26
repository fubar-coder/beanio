/*
 * Copyright 2011 Kevin Seim
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.beanio.config;

import static org.junit.Assert.*;

import java.io.*;

import org.beanio.*;
import org.beanio.parser.ParserTest;
import org.beanio.util.IOUtil;
import org.junit.Test;

/**
 * JUnit test cases for invalid mapping configurations.
 * @author Kevin Seim
 * @since 1.0
 */
public class XmlMappingConfigurationTest extends ParserTest {

    @Test
    public void testInvalidGroupXmlType() throws IOException {
        loadInvalidMappingFile("invalidStreamXmlType.xml", "Invalid xmlType 'text'");
    }
    
    @Test
    public void testInvalidBeanXmlType() throws IOException {
        loadInvalidMappingFile("invalidBeanXmlType.xml", "Invalid xmlType 'attribute'");
    }
    
    @Test
    public void testInvalidFieldXmlType() throws IOException {
        loadInvalidMappingFile("invalidFieldXmlType.xml", "Invalid xmlType 'invalid'");
    }
    
    @Test
    public void testPrefixWithNoNamespace() throws IOException {
        loadInvalidMappingFile("prefixWithNoNamespace.xml", "Missing namespace for configured XML prefix");
    }
    
    @Test
    public void testFieldNamespaceForText() throws IOException {
        loadInvalidMappingFile("fieldNamespaceForText.xml", "XML namespace is not applicable for xmlType 'text'");
    }
    
    @Test
    public void testNillableAttributeField() throws IOException {
        loadInvalidMappingFile("nillableAttributeField.xml", "xmlType 'attribute' is not nillable");
    }
    
    @Test
    public void testCollectionAttributeField() throws IOException {
        loadInvalidMappingFile("collectionAttributeField.xml", "Collection type bean/field must have xmlType 'element'");
    }

    @Test
    public void testInvalidStreamMode() throws IOException {
        loadInvalidMappingFile("invalidStreamMode.xml", "Invalid mode 'xxx'");
    }
    
    @Test
    public void testInvalidBeanClass() throws IOException {
        loadInvalidMappingFile("invalidBeanClass.xml", "Class must be concrete unless stream mode is set to 'write'");
    }
    
    @Test
    public void testNoWriteableMethod() throws IOException {
        loadInvalidMappingFile("noWriteableMethod.xml", "No writeable method for property 'name' in class 'org.beanio.config.ConcreteBean'");
    }

    @Test
    public void testNoReadableMethod() throws IOException {
        loadInvalidMappingFile("noReadableMethod.xml", "No readable method for property 'value' in class 'org.beanio.config.InterfaceBean'");
    }
    
    @Test
    public void testNoBeanProperty() throws IOException {
        loadInvalidMappingFile("noBeanProperty.xml", "No such property 'birthDate' in class 'org.beanio.config.ConcreteBean'");
    }
    
    @Test
    public void testInvalidSetter() throws IOException {
        loadInvalidMappingFile("invalidSetter.xml", "Method not found: setAge");
    }
    
    /**
     * 
     * @param name
     * @param errorMessage
     * @throws IOException
     */
    private void loadInvalidMappingFile(String name, String errorMessage) throws IOException {
        StreamFactory factory = StreamFactory.newInstance();
        InputStream in = getClass().getResourceAsStream(name);
        try {
            factory.load(in);
            fail("Expected BeanIOConfigurationException: " + errorMessage);
        }
        catch (BeanIOConfigurationException ex) {
            Throwable t = ex;
            while (t != null) {
                if (t.getCause() == null)
                    break;
                else
                    t = t.getCause();
            }
            assertEquals(errorMessage, t.getMessage());
        }
        finally {
            IOUtil.closeQuietly(in);
        }
    }
}
