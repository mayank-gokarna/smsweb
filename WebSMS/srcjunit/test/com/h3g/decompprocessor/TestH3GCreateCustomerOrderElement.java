/************************************************************************************
 * File Name    	: TestH3GCreateCustomerOrderElement.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GCreateCustomerOrderElement.java
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

import org.custommonkey.xmlunit.Diff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.h3g.decomp.decompprocessor.H3GCreateCustomerOrderElement;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.yfs.japi.YFSEnvironment;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GCreateCustomerOrderElement.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})

public class TestH3GCreateCustomerOrderElement {
	
	
	Document docEnvironment;
	Document docInputXml;
	Document docOutputXml;
	Document docResult;
	YFSEnvironment environment ;
	YIFApi api;
	H3GCreateCustomerOrderElement createCustomerOrderElement = new H3GCreateCustomerOrderElement();
	
	
	
	@Before
	public void setUp() throws Exception{
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		
		PowerMockito.mock(H3GUtil.class, Mockito.CALLS_REAL_METHODS);
		
	}
	
	@Test
	public void testPrepareCustomerOrderDocument() throws Exception{
		
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_PrepareCustomerOrderDocument.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderDocument.xml");
		
		docResult = createCustomerOrderElement.prepareCustomerOrderDocument(environment,docInputXml); 
		final Diff diffOutXml = new Diff(SCXmlUtil.getString(docResult), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testCreateEntity() throws Exception{
		
		Document docInputBusinessEntityEleXml;
		
		docInputBusinessEntityEleXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_CreateEntity_BusinessEntity.xml");
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_CreateEntity_InputOrderEle.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_CreateEntity.xml");
		
		Element createEntityResultele = H3GCreateCustomerOrderElement.createEntity(docInputBusinessEntityEleXml.getDocumentElement(),docInputXml.getDocumentElement(),H3GConstants.VAL_CUSTOMER_ORDER, false, false); 
		final Diff diffOutXml = new Diff(SCXmlUtil.getString(createEntityResultele), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testIterateParentGroup() throws Exception{
		
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_IterateParentGroup_ProdSpecificEle.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_IterateParentGroup.xml");
		
		NodeList createEntityResulteleList = H3GCreateCustomerOrderElement.iterateParentGroup(docInputXml.getDocumentElement(),"CHG_OTASD"); 
		
		if(null != createEntityResulteleList.item(0)){
			diffOutXml = new Diff(SCXmlUtil.getString((Element)createEntityResulteleList.item(0).getParentNode()), SCXmlUtil.getString(docOutputXml));
			assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
		}else{
			assertTrue("Expected XML and Output XML are not similar", false);
		}
	}
}
