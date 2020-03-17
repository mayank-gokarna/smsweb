/************************************************************************************
 * File Name    	: TestH3GReturnOrder.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GReturnOrder.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 18, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * UTR comments : UT001 - Return order request Interface XML is prepared with XSL transformation.
 * 						  So Junit test case cannot be written for the same.
 * 				  UT002 - Output to be validated will have to be mocked in the test case,
 * 						  so there is no point writing Junit for the same.
 * 						  Instead Junit testcases have been written for each method to validate 
 * 						  the same. The same is applicable for UT003, UT004, UT005, UT006, UT007
 * 						  
 *************************************************************************************/

package test.com.h3g.activation.api;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.h3g.activation.api.H3GReturnOrder;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GProvisioningUtil;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfc.core.YFCObject;
import com.yantra.yfs.japi.YFSEnvironment;
import static org.mockito.Matchers.any;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GReturnOrder.class, H3GSterlingUtil.class,
		H3GProvisioningUtil.class })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil",
		"com.h3g.util.H3GProvisioningUtil" })
public class TestH3GReturnOrder {

	H3GReturnOrder returnOrder = null;
	H3GProvisioningUtil provUtil = null;
	Properties prop = null;

	@Before
	public void setUp() throws Exception {
		returnOrder = new H3GReturnOrder();
		prop = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		returnOrder = null;
	}

	/**
	 * Junit test case to validate changeOrder API Input XML to update Return
	 * orders
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testGetChangeOrdInpToUpdateReturnOrder() throws Exception {

		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getChangeOrdInpToUpdateReturnOrder_Input.xml");
		System.out
				.println("testGetChangeOrdInpToUpdateReturnOrder method input "
						+ SCXmlUtil.getString(inputDoc));
		Document changeOrderDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getChangeOrdInpToUpdateReturnOrder_Output.xml");
		System.out
				.println("testGetChangeOrdInpToUpdateReturnOrder method validation XML "
						+ SCXmlUtil.getString(changeOrderDoc));

		// Mock generateCharID method to generate new characteristics
		String newCharID = "NEW20170914001008858100000010";
		PowerMockito.when(
				H3GProvisioningUtil.generateCharID(any(YFSEnvironment.class)))
				.thenReturn(newCharID);
		Document outputDoc = returnOrder.getChangeOrdInpToUpdateReturnOrder(
				inputDoc.getDocumentElement(), null);
		System.out
				.println("testGetChangeOrdInpToUpdateReturnOrder method output "
						+ SCXmlUtil.getString(outputDoc));
		assertEquals(SCXmlUtil.getString(changeOrderDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate splitLine API Input XML to update Return
	 * orders
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testGetSplitLineInpToUpdateReturnOrder() throws Exception {

		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getSplitLineInpToUpdateReturnOrder_Input.xml");
		System.out
				.println("testGetSplitLineInpToUpdateReturnOrder method input "
						+ SCXmlUtil.getString(inputDoc));
		Document splitLineDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getSplitLineInpToUpdateReturnOrder_Output.xml");
		System.out
				.println("testGetSplitLineInpToUpdateReturnOrder method validation XML "
						+ SCXmlUtil.getString(splitLineDoc));

		// Mock generateCharID method to generate new characteristics
		String newCharID = "NEW20170914001008858100000010";
		PowerMockito.when(
				H3GProvisioningUtil.generateCharID(any(YFSEnvironment.class)))
				.thenReturn(newCharID);
		// START: Logic to group orderLines with same OrderLineKey
		ArrayList<Element> arrOrderLines = SCXmlUtil.getElements(
				inputDoc.getDocumentElement(), H3GConstants.XPATH_ORDER_LINE);
		// Map to group orderLines with same OrderLineKey
		Map<String, ArrayList<Element>> mapOrderLinesLineKey = new ConcurrentHashMap<String, ArrayList<Element>>();
		ArrayList<Element> arrOrdrLinesWithSameLineKey = null;
		if (!YFCObject.isVoid(arrOrderLines)) {
			for (Element eleOrderLine : arrOrderLines) {
				String strOrderLineKey = eleOrderLine
						.getAttribute(H3GConstants.A_ORDER_LINE_KEY);
				if (mapOrderLinesLineKey.containsKey(strOrderLineKey)) {
					// Add OrderLine to existing OrderLinekey
					mapOrderLinesLineKey.get(strOrderLineKey).add(eleOrderLine);
					// Set splitLineRequired attribute to true if
					// OrderLineKey
					// is repeated having different disposition code
					// in the input

				} else {
					// Create a new List if OrderLineKey is not already
					// present
					// in the map
					arrOrdrLinesWithSameLineKey = new ArrayList<Element>();
					arrOrdrLinesWithSameLineKey.add(eleOrderLine);
					mapOrderLinesLineKey.put(strOrderLineKey,
							arrOrdrLinesWithSameLineKey);
				}
			}
		}

		// END: Logic to group orderLines with same OrderLineKey

		Document outputDoc = returnOrder.getSplitLineInpToUpdateReturnOrder(
				inputDoc.getDocumentElement(), mapOrderLinesLineKey, null);
		System.out
				.println("testGetSplitLineInpToUpdateReturnOrder method output "
						+ SCXmlUtil.getString(outputDoc));
		assertEquals(SCXmlUtil.getString(splitLineDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate changeOrderStatus API Input XML to update
	 * Return orders to Completed status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToCompleted() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForChangeOrderStatusToCompleted_Output.xml");
		System.out
				.println("testGetInputForChangeOrderStatusToCompleted method validation XML "
						+ SCXmlUtil.getString(changeOrderStatusDoc));

		Document outputDoc = H3GProvisioningUtil.getInpurForChangeOrderStatus(
				"20170418134030223195",
				H3GConstants.SERVICE_ORDER_STATUS_TO_COMPLETED);
		System.out.println("testGetInputForChangeOrderStatus method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(changeOrderStatusDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate multiAPI Input XML for change Order and
	 * changeOrderStatus to Completed
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testGetInputForMultiApiChangeOrder() throws Exception {

		Document inputDoc1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiChangeOrder_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiChangeOrder_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiChangeOrder_Output.xml");
		System.out
				.println("testGetInputForMultiApiChangeOrder method validation XML "
						+ SCXmlUtil.getString(multiAPIDoc));

		Document outputDoc = H3GProvisioningUtil.getInputForMultiApi(inputDoc1,
				inputDoc2);
		System.out.println("testGetInputForMultiApiChangeOrder method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(multiAPIDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate multiAPI Input XML for splitLine and
	 * changeOrderStatus to Completed
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testGetInputForMultiApiSplitLine() throws Exception {

		Document inputDoc1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiSplitLine_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiSplitLine_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiSplitLine_Output.xml");
		System.out
				.println("testGetInputForMultiApiSplitLine method validation XML "
						+ SCXmlUtil.getString(multiAPIDoc));

		Document outputDoc = H3GProvisioningUtil.getInputForMultiApi(inputDoc1,
				inputDoc2);
		System.out.println("testGetInputForMultiApiSplitLine method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(multiAPIDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate Input XML for createException API
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForCreateException() throws Exception {

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForCreateException_Input.xml");
		Document createExcepDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForCreateException_Output.xml");
		System.out
				.println("testGetInputForCreateException method validation XML "
						+ SCXmlUtil.getString(createExcepDoc));

		Document outputDoc = H3GProvisioningUtil
				.getInputForCreateException(inputDoc.getDocumentElement(), false);
		System.out.println("testGetInputForCreateException method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(createExcepDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate changeOrderStatus API Input XML to update
	 * Return orders to Suspended status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToSuspended() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForChangeOrderStatusToSuspended_Output.xml");
		System.out
				.println("testGetInputForChangeOrderStatusToSuspended method validation XML "
						+ SCXmlUtil.getString(changeOrderStatusDoc));

		Document outputDoc = H3GProvisioningUtil.getInpurForChangeOrderStatus(
				"20170418134030223195",
				H3GConstants.SERVICE_ORDER_STATUS_TO_SUSPENDED);
		System.out
				.println("testGetInputForChangeOrderStatusToSuspended method output "
						+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(changeOrderStatusDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate multiAPI Input XML for createException API
	 * and changeOrderStatus to Suspended
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForMultiApiException() throws Exception {

		Document inputDoc1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiException_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiException_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GReturnOrder/testReturnOrder_getInputForMultiApiException_Output.xml");
		System.out
				.println("testGetInputForMultiApiException method validation XML "
						+ SCXmlUtil.getString(multiAPIDoc));

		Document outputDoc = H3GProvisioningUtil.getInputForMultiApi(inputDoc1,
				inputDoc2);
		System.out.println("testGetInputForMultiApiException method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(multiAPIDoc),
				SCXmlUtil.getString(outputDoc));

	}
}
