package test.com.h3g.statusnotification.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;

import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GXMLUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;
import com.h3g.statusnotification.api.H3GCustomerOrderNotification;
import com.h3g.statusnotification.api.H3GProductNotification;
import com.h3g.statusnotification.api.H3GResourceNotification;
import com.h3g.statusnotification.api.H3GServiceNotification;
import com.h3g.statusnotification.api.H3GStatusNotificationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TestH3GCustomerOrderNotification.class, H3GSterlingUtil.class, H3GXMLUtil.class,
		H3GCustomerOrderNotification.class, H3GProductNotification.class, H3GResourceNotification.class,
		H3GServiceNotification.class, H3GStatusNotificationUtil.class })
@SuppressStaticInitializationFor("com.h3g.util.H3GSterlingUtil")
public class TestH3GCustomerOrderNotification {

	H3GCustomerOrderNotification customerOrderNotification = null;
	H3GResourceNotification resourceNotification = null;
	H3GServiceNotification serviceNotification = null;
	H3GProductNotification productNotification = null;
	H3GStatusNotificationUtil statusNtificationUtil = null;
	YFSEnvironment env = null;

	@Before
	public void setUp() throws Exception {

		customerOrderNotification = new H3GCustomerOrderNotification();
		resourceNotification = new H3GResourceNotification();
		serviceNotification = new H3GServiceNotification();
		productNotification = new H3GProductNotification();
		statusNtificationUtil = new H3GStatusNotificationUtil();
		PowerMockito.mockStatic(H3GSterlingUtil.class);

	}

	@After
	public void tearDown() throws Exception {
		customerOrderNotification = null;
		resourceNotification = null;
		serviceNotification = null;
		productNotification = null;
	}

	// UT001

	/**
	 * This method is used to test sendCustomerOrderNotification of
	 * H3GCustomerOrderNotification.java Takes
	 * Expected_InputDoc_ForCustomerOrderAccepted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendCustomerOrderNotificationForCustomerOrderAccepted() throws Exception {

		Document Expected_InputDoc_ForCustomerOrderAccepted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForCustomerOrderAccepted.xml");

		Document Expected_OutputDoc_ForCustomerOrderAccepted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForCustomerOrderAccepted.xml");

		Document Actual_OutputDoc_ForCustomerOrderAccepted = customerOrderNotification
				.sendCustomerOrderNotification(env, Expected_InputDoc_ForCustomerOrderAccepted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForCustomerOrderAccepted),
				SCXmlUtil.getString(Actual_OutputDoc_ForCustomerOrderAccepted));

	}

	// UT001 - B

	/**
	 * This method is used to test sendCustomerOrderNotification of
	 * H3GCustomerOrderNotification.java Takes
	 * Expected_InputDoc_ForCustomerOrderRejected as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendCustomerOrderNotificationForCustomerOrderRejected() throws Exception {

		Document Expected_InputDoc_ForCustomerOrderRejected = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForCustomerOrderRejected.xml");

		Document Expected_OutputDoc_ForCustomerOrderRejected = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForCustomerOrderRejected.xml");

		Document Actual_OutputDoc_ForCustomerOrderRejected = customerOrderNotification
				.sendCustomerOrderNotification(env, Expected_InputDoc_ForCustomerOrderRejected);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForCustomerOrderRejected),
				SCXmlUtil.getString(Actual_OutputDoc_ForCustomerOrderRejected));

	}

	/**
	 * This method is used to test sendResourceNotification of
	 * H3GResourceNotification.java Takes
	 * Expected_InputDoc_ForResourceOrderSuspended as input
	 * 
	 * @throws Exception
	 */

	// UT003

	@Test
	public void testSendResourceNotificationForResourceOrderSuspended() throws Exception {

		Document Expected_getOrderListOutputForCustomerDoc_ForResourceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForResourceOrderSuspended.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForResourceOrderSuspended);

		Document Expected_InputDoc_ForResourceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForResourceOrderSuspended.xml");

		Document Expected_OutputDoc_ForResourceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForResourceOrderSuspended.xml");

		Document Actual_OutputDoc_ForResourceOrderSuspended = resourceNotification.sendResourceNotification(env,
				Expected_InputDoc_ForResourceOrderSuspended);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForResourceOrderSuspended),
				SCXmlUtil.getString(Actual_OutputDoc_ForResourceOrderSuspended));

	}

	// UT004
	/**
	 * This method is used to test sendResourceNotification of
	 * H3GResourceNotification.java Takes
	 * Expected_InputDoc_ForResourceOrderCompleted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendResourceNotificationForResourceOrderCompleted() throws Exception {

		Document Expected_getOrderListOutputForSiblingOrdersDoc_ForResourceOrderCompleted = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForSiblingOrdersDoc_ForResourceOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForSiblingOrdersDoc_ForResourceOrderCompleted);

		Document Expected_getOrderListOutputForCustomerDoc_ForResourceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForResourceOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForResourceOrderCompleted);

		Document Expected_InputDoc_ForResourceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForResourceOrderCompleted.xml");

		Document Expected_OutputDoc_ForResourceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForResourceOrderCompleted.xml");

		Document Actual_OutputDoc_ForResourceOrderCompleted = resourceNotification.sendResourceNotification(env,
				Expected_InputDoc_ForResourceOrderCompleted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForResourceOrderCompleted),
				SCXmlUtil.getString(Actual_OutputDoc_ForResourceOrderCompleted));

	}

	// UT004

	/**
	 * This method is used to test sendResourceNotification of
	 * H3GResourceNotification.java Takes
	 * Expected_InputDoc_ForShipResourceOrderCompleted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendResourceNotificationForShipResourceOrderCompleted() throws Exception {

		Document Expected_getOrderListOutputForSiblingOrdersDoc_ForShipResourceOrderCompleted = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForSiblingOrdersDoc_ForShipResourceOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForSiblingOrdersDoc_ForShipResourceOrderCompleted);

		Document Expected_getOrderListOutputForCustomerDoc_ForShipResourceOrderCompleted = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForShipResourceOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForShipResourceOrderCompleted);

		Document Expected_InputDoc_ForShipResourceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForShipResourceOrderCompleted.xml");

		Document Expected_OutputDoc_ForShipResourceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForShipResourceOrderCompleted.xml");

		Document Actual_OutputDoc_ForShipResourceOrderSuspended = resourceNotification.sendResourceNotification(env,
				Expected_InputDoc_ForShipResourceOrderCompleted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForShipResourceOrderCompleted),
				SCXmlUtil.getString(Actual_OutputDoc_ForShipResourceOrderSuspended));

	}

	// UT005

	/**
	 * This method is used to test sendProductNotification of
	 * H3GProductNotification.java Takes
	 * Expected_InputDoc_ForProductOrderSuspended as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendNotificationForProductOrderSuspended() throws Exception {

		Document Expected_getOrderListOutputForCustomerDoc_ForProductOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForProductOrderSuspended.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForProductOrderSuspended);

		Document Expected_InputDoc_ForProductOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForProductOrderSuspended.xml");

		Document Expected_OutputDoc_ForProductOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForProductOrderSuspended.xml");

		Document Actual_OutputDoc_ForProductOrderSuspended = productNotification.sendProductNotification(env,
				Expected_InputDoc_ForProductOrderSuspended);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForProductOrderSuspended),
				SCXmlUtil.getString(Actual_OutputDoc_ForProductOrderSuspended));

	}

	// UT006

	/**
	 * This method is used to test sendProductNotification of
	 * H3GProductNotification.java Takes
	 * Expected_InputDoc_ForProductOrderCompleted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendNotificationForProductOrderCompleted() throws Exception {

		Document Expected_getOrderListOutputForSiblingOrdersDoc_ForProductOrderCompleted = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForSiblingOrdersDoc_ForProductOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForSiblingOrdersDoc_ForProductOrderCompleted);

		Document Expected_getOrderListOutputForCustomerDoc_ForProductOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForProductOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForProductOrderCompleted);

		Document Expected_InputDoc_ForProductOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForProductOrderCompleted.xml");

		Document Expected_OutputDoc_ForProductOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForProductOrderCompleted.xml");

		Document Actual_OutputDoc_ForProductOrderCompleted = productNotification.sendProductNotification(env,
				Expected_InputDoc_ForProductOrderCompleted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForProductOrderCompleted),
				SCXmlUtil.getString(Actual_OutputDoc_ForProductOrderCompleted));

	}

	// UT007

	/**
	 * This method is used to test sendServiceNotification of
	 * H3GServiceNotification.java Takes
	 * Expected_InputDoc_ForServiceOrderSuspended as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendNotificationForServiceOrderSuspended() throws Exception {

		Document Expected_getOrderListOutputForCustomerDoc_ForServiceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForProductOrderSuspended.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForServiceOrderSuspended);

		Document Expected_InputDoc_ForServiceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForServiceOrderSuspended.xml");

		Document Expected_OutputDoc_ForServiceOrderSuspended = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForServiceOrderSuspended.xml");

		Document Actual_OutputDoc_ForServiceOrderSuspended = serviceNotification.sendServiceNotification(env,
				Expected_InputDoc_ForServiceOrderSuspended);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForServiceOrderSuspended),
				SCXmlUtil.getString(Actual_OutputDoc_ForServiceOrderSuspended));

	}

	// UT008

	/**
	 * This method is used to test sendServiceNotification of
	 * H3GServiceNotification.java Takes
	 * Expected_InputDoc_ForServiceOrderCompleted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendNotificationForServiceOrderCompleted() throws Exception {

		Document Expected_getOrderListOutputForCustomerDoc_ForServiceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_getOrderListOutputForCustomerDoc_ForServiceOrderCompleted.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), any(String.class), any(String.class),
						any(Document.class)))
				.thenReturn(Expected_getOrderListOutputForCustomerDoc_ForServiceOrderCompleted);

		Document Expected_InputDoc_ForServiceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForServiceOrderCompleted.xml");

		Document Expected_OutputDoc_ForServiceOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForServiceOrderCompleted.xml");

		Document Actual_OutputDoc_ForServiceOrderCompleted = serviceNotification.sendServiceNotification(env,
				Expected_InputDoc_ForServiceOrderCompleted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForServiceOrderCompleted),
				SCXmlUtil.getString(Actual_OutputDoc_ForServiceOrderCompleted));

	}

	// UT010

	/**
	 * This method is used to test sendCustomerOrderNotification of
	 * H3GCustomerOrderNotification.java Takes
	 * Expected_InputDoc_ForServiceOrderCompleted as input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testSendNotificationForCustomerOrderCompleted() throws Exception {

		Document Expected_InputDoc_ForCustomerOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForCustomerOrderCompleted.xml");

		Document Expected_OutputDoc_ForCustomerOrderCompleted = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForCustomerOrderCompleted.xml");

		Document Actual_OutputDoc_ForCustomerOrderCompleted = customerOrderNotification
				.sendCustomerOrderNotification(env, Expected_InputDoc_ForCustomerOrderCompleted);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForCustomerOrderCompleted),
				SCXmlUtil.getString(Actual_OutputDoc_ForCustomerOrderCompleted));

	}

	// To test input to getOrderListAPI

	/**
	 * This method is used to test prepareGetOrderListInputForCustomerOrder of
	 * H3GStatusNotificationUtil.java Takes OrderNo as input
	 * 
	 * @throws Exception
	 */

	/*@Test

	public void testgetOrderListInputOfCustomerOrder() throws Exception {

		String strOrderNo = "SG_002";
		System.out.println("testgetOrderListInputOfCustomerOrder method input " + strOrderNo);
		Document Expected_OutputDoc_Of_CustomerOrderGetOrderListInput = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_Of_CustomerOrderGetOrderListInput.xml");

		Document Actual_OutputDoc_Of_CustomerOrderGetOrderListInput = H3GStatusNotificationUtil
				.prepareGetOrderListInputForCustomerOrder(strOrderNo);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_Of_CustomerOrderGetOrderListInput),
				SCXmlUtil.getString(Actual_OutputDoc_Of_CustomerOrderGetOrderListInput));

	}*/

	// To test input to getOrderListAPI For Sibling Orders

	/**
	 * This method is used to test prepareGetOrderListInputForSiblingOrders of
	 * H3GResourceOrderNotification.java Takes
	 * Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder as
	 * input
	 * 
	 * @throws Exception
	 */

	/*@Test

	public void testgetOrderListInputOfSiblingOrdersForResourceOrder() throws Exception {

		Document Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder.xml");

		Document Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder.xml");

		Document Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder = resourceNotification
				.prepareGetOrderListInputForSiblingOrders(
						Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder),
				SCXmlUtil.getString(Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder));

	}*/

	/**
	 * This method is used to test prepareGetOrderListInputForSiblingOrders of
	 * H3GResourceOrderNotification.java Takes
	 * Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder
	 * as input
	 * 
	 * @throws Exception
	 */

	/*public void testgetOrderListInputOfSiblingOrdersForShipResourceOrder() throws Exception {

		Document Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder.xml");

		Document Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder = SCXmlUtil
				.createFromFileOrUrl(
						"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForCustomerOrderCompleted.xml");

		Document Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder = resourceNotification
				.prepareGetOrderListInputForSiblingOrders(
						Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder),
				SCXmlUtil.getString(Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder));

	}*/

	/**
	 * This method is used to test prepareGetOrderListInputForSiblingOrders of
	 * H3GResourceOrderNotification.java Takes
	 * Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForProductOrder as
	 * input
	 * 
	 * @throws Exception
	 */

	/*public void testgetOrderListInputOfSiblingOrdersForProductOrder() throws Exception {

		Document Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForProductOrder = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForResourceOrder.xml");

		Document Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForProductOrder = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/statusnotification/api/TestH3GCustomerOrderNotification/Expected_OutputDoc_ForCustomerOrderCompleted.xml");

		Document Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder = resourceNotification
				.prepareGetOrderListInputForSiblingOrders(
						Expected_InputDoc_ForGetOrderListInputOfSiblingOrdersForProductOrder);

		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForGetOrderListInputOfSiblingOrdersForProductOrder),
				SCXmlUtil.getString(Actual_OutputDoc_ForGetOrderListInputOfSiblingOrdersForShipResourceOrder));

	}*/

}
