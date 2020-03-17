/************************************************************************************
 * File Name    	: TestH3GProductOrder.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GProductOrder.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 20, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * UTR comments : UT001/UT002 - Product order request Interface XML is prepared with XSL transformation.
 * 						  		So Junit test case cannot be written for the same.
 * 				  UT003 - 		Output to be validated will have to be mocked in the test case,
 * 						 		so there is no point writing Junit for the same.
 * 						  		Instead Junit testcases have been written for each method to validate 
 * 						  		the same. The same is applicable for UT004, UT005, UT006
 * 						  
 *************************************************************************************/

package test.com.h3g.activation.api;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import com.h3g.activation.api.H3GProductOrder;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GProvisioningUtil;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GProductOrder.class, H3GSterlingUtil.class,
		H3GProvisioningUtil.class })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil",
		"com.h3g.util.H3GProvisioningUtil" })

public class TestH3GProductOrder {
	
	H3GProductOrder productOrder = null;
	H3GProvisioningUtil provUtil = null;
	Properties prop = null;

	@Before
	public void setUp() throws Exception {
		productOrder = new H3GProductOrder();
		prop = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		productOrder = null;
	}
	
	/**
	 * Junit test case to validate changeOrderStatus API Input XML to update
	 * Product orders to Completed status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToCompleted() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForChangeOrderStatusToCompleted_Output.xml");
		System.out
				.println("testGetInputForChangeOrderStatusToCompleted method validation XML "
						+ SCXmlUtil.getString(changeOrderStatusDoc));

		Document outputDoc = H3GProvisioningUtil.getInpurForChangeOrderStatus(
				"20170418134030223196",
				H3GConstants.SERVICE_ORDER_STATUS_TO_COMPLETED);
		System.out.println("testGetInputForChangeOrderStatusToCompleted method output "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(changeOrderStatusDoc),
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
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForCreateException_Input.xml");
		Document createExcepDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForCreateException_Output.xml");
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
	 * Product orders to Suspended status
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetInputForChangeOrderStatusToSuspended() throws Exception {

		Document changeOrderStatusDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForChangeOrderStatusToSuspended_Output.xml");
		System.out
				.println("testGetInputForChangeOrderStatusToSuspended method validation XML "
						+ SCXmlUtil.getString(changeOrderStatusDoc));

		Document outputDoc = H3GProvisioningUtil.getInpurForChangeOrderStatus(
				"20170418134030223196",
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
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForMultiApiException_Input1.xml");
		Document inputDoc2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForMultiApiException_Input2.xml");
		Document multiAPIDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GProductOrder/testProductOrder_getInputForMultiApiException_Output.xml");
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
