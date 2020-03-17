/************************************************************************************
 * File Name    	: TestH3GServiceOrder.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GServiceOrder.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 12, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * UTR comments : UT001 - Junit test case written step by step for each method
 * 				  UT002 - Output to be validated will have to be mocked in the test case,
 * 						  so there is no point writing a test case for the same.
 * 						  The same is applicable for UT003, UT004, UT005, UT006
 *************************************************************************************/

package test.com.h3g.activation.api;

import static org.junit.Assert.*;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import com.h3g.activation.api.H3GServiceOrder;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GProvisioningUtil;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;
import static org.mockito.Matchers.any;

import org.w3c.dom.Document;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GServiceOrder.class, H3GSterlingUtil.class,
		H3GProvisioningUtil.class })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil",
		"com.h3g.util.H3GProvisioningUtil" })
public class TestH3GServiceOrder {

	H3GServiceOrder serviceOrder = null;
	H3GProvisioningUtil provUtil = null;
	Properties prop = null;

	@Before
	public void setUp() throws Exception {
		serviceOrder = new H3GServiceOrder();
		prop = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		serviceOrder = null;
	}

	/**
	 * Junit test case to validate the Interface XML for Service orders
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testPrepareInteraceXML() throws Exception {

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_prepareInterfaceXML_Input.xml");
		System.out.println("testPrepareInteraceXML method input "
				+ SCXmlUtil.getString(inputDoc));
		Document prepareInterfaceXMLDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_prepareInterfaceXML_Output.xml");
		System.out.println("testPrepareInteraceXML validation XML  "
				+ SCXmlUtil.getString(prepareInterfaceXMLDoc));

		Document outputDoc = serviceOrder.prepareInteraceXML(inputDoc);
		System.out.println("testPrepareInteraceXML method output "
				+ SCXmlUtil.getString(outputDoc));
		assertEquals(SCXmlUtil.getString(prepareInterfaceXMLDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate the logic to get all linked Child Resource
	 * orders and update ResourceDependency
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetChildResources() throws Exception {
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDocInterfaceData = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getChildResources_Input.xml");
		System.out.println("testGetChildResources method input "
				+ SCXmlUtil.getString(inputDocInterfaceData));
		Document resourceDepnOutputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getChildResources_Output.xml");
		System.out.println("testGetChildResources method validation XML "
				+ SCXmlUtil.getString(resourceDepnOutputDoc));

		YFCElement eleInput = YFCDocument.getDocumentFor(inputDocInterfaceData)
				.getDocumentElement();
		YFCElement eleOrder = eleInput.getElementsByTagName(
				H3GConstants.E_ORDER).item(0);

		// Create Document with root element as ResourceDependancy
		YFCDocument resourceDepnDoc = YFCDocument
				.createDocument(H3GConstants.E_RESOURCE_DEPENDENCY);

		// Mock getOrderList output
		Document outputDocGetOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getOrderList_Output.xml");

		System.out.println("testGetChildResources: mock : getOrderList "
				+ SCXmlUtil.getString(outputDocGetOrderList));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class),
						any(Document.class))).thenReturn(outputDocGetOrderList);

		Document outputDoc = serviceOrder.getChildResources(null, eleOrder,
				resourceDepnDoc);

		System.out.println("testGetChildResources method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(resourceDepnOutputDoc),
				SCXmlUtil.getString(outputDoc));
	}

	/**
	 * Junit test case to validate changeOrder API Input XML to update Service
	 * orders
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetChangeOrdInpToUpdateServiceOrder() throws Exception {

		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getChangeOrdInpToUpdateServiceOrder_Input.xml");
		System.out
				.println("testGetChangeOrdInpToUpdateServiceOrder method input "
						+ SCXmlUtil.getString(inputDoc));
		Document changeOrderDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getChangeOrdInpToUpdateServiceOrder_Output.xml");
		System.out
				.println("testGetChangeOrdInpToUpdateServiceOrder method validation XML "
						+ SCXmlUtil.getString(changeOrderDoc));

		// Mock generateCharID method to generate new characteristics
		String newCharID = "NEW20170914001008858100000010";

		PowerMockito.when(
				H3GProvisioningUtil.generateCharID(any(YFSEnvironment.class)))
				.thenReturn(newCharID);

		Document outputDoc = serviceOrder.getChangeOrdInpToUpdateServiceOrder(
				inputDoc.getDocumentElement(), null);
		System.out
				.println("testGetChangeOrdInpToUpdateServiceOrder method output "
						+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(changeOrderDoc),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * Junit test case to validate changeOrderStatus API Input XML to update
	 * Service orders to Completed status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToCompleted() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForChangeOrderStatusToCompleted_Output.xml");
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
	public void testGetInputForMultiApi() throws Exception {

		Document inputDoc1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApi_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApi_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApi_Output.xml");
		System.out.println("testGetInputForMultiApi method validation XML "
				+ SCXmlUtil.getString(multiAPIDoc));

		Document outputDoc = H3GProvisioningUtil.getInputForMultiApi(inputDoc1,
				inputDoc2);
		System.out.println("testGetInputForMultiApi method output "
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
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForCreateException_Input.xml");
		Document createExcepDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForCreateException_Output.xml");
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
	 * Service orders to Suspended status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToSuspended() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForChangeOrderStatusToSuspended_Output.xml");
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
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApiException_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApiException_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GServiceOrder/testServiceOrder_getInputForMultiApiException_Output.xml");
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
