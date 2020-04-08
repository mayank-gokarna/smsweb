/************************************************************************************
 * File Name    	: TestH3GCatalogUtil.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GCatalogUtil.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Oct 31, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/
package test.com.h3g.catalog.util;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;

import com.h3g.catalog.util.H3GCatalogConstants;
import com.h3g.catalog.util.H3GCatalogUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfc.core.YFCIterable;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GCatalogUtil.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})


public class TestH3GCatalogUtil {
	
	/**
	 * This test case is used to test the getCatalogId method of H3GCatalogUtil
	 * @throws Exception
	 */
	@Test
	public void testGetCatalogId() throws Exception{

	String input = "PARAMETER:CH_DEFAULTPDNCONTEXTID:3UKi1_New";
	String output = "CH_DEFAULTPDNCONTEXTID";
	
	String result = H3GCatalogUtil.getCatalogId(input, ":");
	assertEquals(output, result);
	}
	
	/**
	 * This test case is used to test the getSpecificationType method of H3GCatalogUtil
	 * @throws Exception
	 */
	@Test
	public void testGetSpecificationType() throws Exception{
		String input = "ns2:ProductOfferingRelationshipSpecification";
		String output = "ProductOfferingRelationshipSpecification";
		
		String result = H3GCatalogUtil.getSpecificationType(input, ":");
		assertEquals(output, result);
	}
	
	/**
	 * This test case is used to test the getStringBeforeSeparator method of H3GCatalogUtil
	 * @throws Exception
	 */
	@Test
	public void testGetStringBeforeSeparator() throws Exception{
		
		String input = "TEST_CHG_EIR_COLOUR|CHG_EIR_COLOUR";
		String output = "TEST_CHG_EIR_COLOUR";

		String result = H3GCatalogUtil.getStringBeforeSeparator(input, "|");
		assertEquals(output, result);
	}
	
	/**
	 * This test case is used to test the getSpecificationsMap method of H3GCatalogUtil
	 * @throws Exception
	 */
	@Test
	public void testGetSpecificationsMap() throws Exception{
		
		Map<String, YFCElement> mapSpecifications = new ConcurrentHashMap<String, YFCElement>();
		Map<String, YFCElement> resultMap = new ConcurrentHashMap<String, YFCElement>();
		
		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/util/testCatalogUtil_getSpecificationsMap_Input.xml");
		
		
		YFCElement eRoot = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCNodeList<YFCElement> eSpecifications = eRoot.getElementsByTagName("characteristicSpecification");
		
		for (YFCElement eSpecification : eSpecifications) {
			mapSpecifications.put(eSpecification.getAttribute(H3GCatalogConstants.A_CATALOG_ID),
					eSpecification);
		}
		
		
		resultMap = H3GCatalogUtil.getSpecificationsMap(eSpecifications, resultMap);
		
		assertEquals(mapSpecifications, resultMap);
		
	}
	
	/**
	 * This test case is used to test the getSpecificationsMap method of H3GCatalogUtil
	 * @throws Exception
	 */
	@Test
	public void testGetSpecificationsMapIterable() throws Exception{
		
		Map<String, YFCElement> mapSpecifications = new ConcurrentHashMap<String, YFCElement>();
		Map<String, YFCElement> resultMap = new ConcurrentHashMap<String, YFCElement>();
		
		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/util/testCatalogUtil_getSpecificationsMap_Input.xml");
		
		
		YFCElement eRoot = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCIterable<YFCElement> eSpecifications = eRoot.getChildren("characteristicSpecification");
		
		for (YFCElement eSpecification : eSpecifications) {
			mapSpecifications.put(eSpecification.getAttribute(H3GCatalogConstants.A_CATALOG_ID),
					eSpecification);
		}
		
		
		resultMap = H3GCatalogUtil.getSpecificationsMap(eSpecifications, resultMap);
		
		assertEquals(mapSpecifications, resultMap);
		
	}
	
	
}
