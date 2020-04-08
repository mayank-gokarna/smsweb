/************************************************************************************
 * File Name    	: TestH3GPrepareProdSpecifications.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for TestH3GPrepareProdSpecifications.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Oct 23, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/

package test.com.h3g.decompprocessor;

import static org.junit.Assert.*;
import java.util.Random;
import org.custommonkey.xmlunit.Diff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import com.h3g.decomp.decompprocessor.H3GPrepareProdSpecifications;
import com.sterlingcommerce.baseutil.SCXmlUtil;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GPrepareProdSpecifications.class , Random.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})

public class TestH3GPrepareProdSpecifications {
	
	Document docInputXml;
	Document docOutputXml;
	Document docResult;
	Random rand = new Random();
	
	
	@Test
	public void testPrepareCustomerOrderDocument() throws Exception{
		
		docInputXml = SCXmlUtil
		.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_ProdSpecsElementDoc.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ProdSpecsElementDoc.xml");
		H3GPrepareProdSpecifications prepareProdSpecifications = new H3GPrepareProdSpecifications(){
			@Override protected String getTextContentValue(){
				return "CHV184900";
			}
		};
				
		docResult = prepareProdSpecifications.prepareProdSpecifications(docInputXml);
		final Diff diffOutXml = new Diff(SCXmlUtil.getString(docResult), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
}
