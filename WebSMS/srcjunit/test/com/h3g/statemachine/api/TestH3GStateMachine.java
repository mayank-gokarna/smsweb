package test.com.h3g.statemachine.api;

import static org.mockito.Matchers.any;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.h3g.statemachine.api.H3GStateMachine;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GXMLUtil;
import com.sterlingcommerce.baseutil.SCDateTimeUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;


@RunWith(PowerMockRunner.class)
@PrepareForTest({H3GSterlingUtil.class, H3GStateMachine.class, H3GXMLUtil.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil","com.h3g.util.H3GXMLUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})


public class TestH3GStateMachine {
	
	Map<String, String> statusMap = new ConcurrentHashMap<String, String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		statusMap.put("DEV_ST004.PO.100006588", "1100.080");
		statusMap.put("DEV_ST004.PO.100006589", "1100.080");
		statusMap.put("DEV_ST004.RO.100006590", "3700.010");
		statusMap.put("DEV_ST004.RO.100006591", "1100.080");
		statusMap.put("DEV_ST004.SO.100006592", "1100.080");
		statusMap.put("DEV_ST004.SO.100006593", "1100.080");
		statusMap.put("DEV_ST004.SO.100006594", "1100.080");
		statusMap.put("DEV_ST004.SO.100006595", "1100.080");
		statusMap.put("DEV_ST004.SO.100006596", "1100.080");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessBuildPlanOrder() throws Exception {

		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testProcessBuildPlanOrder_InputDoc.xml");
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testProcessBuildPlanOrder_childOrderList.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class), any(Document.class))).thenReturn(docChildOrderList);
		
		Document actualDoc = objStateMachine.processBuildPlanOrder(env, inDoc);
		
		Assert.assertEquals(SCXmlUtil.getString(inDoc), SCXmlUtil.getString(actualDoc));
	}
	
	/**
	 * This test case tests if the status map is getting generated with the statuses of right set of orders.
	 * @throws Exception
	 */
	@Test
	public void testGetStatusMap() throws Exception {
		
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testGetStatusMap_childOrderList.xml");
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		
		
		final Map<String, String> actualStatusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		Assert.assertEquals(statusMap.toString(), actualStatusMap.toString());
		
	}
	
	@Ignore
	public void testSubmitReqForProvisioningOrSchedulingForPastReqShipDate() throws Exception{
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		Map<String, ArrayList<String>> ordrNoHdrKeyMap = PowerMockito.mock(ConcurrentHashMap.class); 
				//new ConcurrentHashMap<String, ArrayList<String>>();
		ArrayList<String> hdrKeyReqShpDateList = new ArrayList<String>();
		
		Document docToBPM = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testsubmitReqForActivation_docToBPM.xml");
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testProcessBuildPlanOrder_childOrderList.xml");
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		String strChildOrderNo = "DEV_ST004.RO.100006590";
		Document yfsenv = SCXmlUtil.createDocument("YFSEnvironment");
		
		PowerMockito.when(H3GSterlingUtil.createEnvironment(any(YFSEnvironment.class),any(String.class), any(String.class))).thenReturn(env);
		
		//run this method to set the class level variable of ordrNoHdrKeyMap.
		final Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean actualIsRecordInsertedInTaskQ = objStateMachine.submitReqForProvisioningOrScheduling(env, strChildOrderNo);
		
		Assert.assertFalse(actualIsRecordInsertedInTaskQ);
		
	}
	
	
	@Test
	public void testSubmitReqForProvisioningOrSchedulingForFutureReqShipDate() throws Exception{
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		Map<String, ArrayList<String>> ordrNoHdrKeyMap = PowerMockito.mock(ConcurrentHashMap.class); 
				//new ConcurrentHashMap<String, ArrayList<String>>();
		ArrayList<String> hdrKeyReqShpDateList = new ArrayList<String>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date currentDate = new Date();
		Date futureReqShipDate = SCDateTimeUtil.addHours(currentDate, 2);
		String strFutureReqShipDate = formatter.format(futureReqShipDate);
				
		
		Document docToBPM = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testsubmitReqForActivation_docToBPM.xml");
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testProcessBuildPlanOrder_childOrderList.xml");
		
		NodeList ndlstOrder = H3GXMLUtil.getXpathNodeList(docChildOrderList, "/OrderList/Order[@OrderNo='DEV_ST004.RO.100006590']");
		Element eleOrder = (Element)ndlstOrder.item(0);
		eleOrder.setAttribute(H3GConstants.A_REQ_SHIP_DATE, strFutureReqShipDate); 
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		String strChildOrderNo = "DEV_ST004.RO.100006590";
		Document yfsenv = SCXmlUtil.createDocument("YFSEnvironment");
		
		PowerMockito.when(H3GSterlingUtil.createEnvironment(env,any(String.class), any(String.class))).thenReturn(env);
		
		//run this method to set the class level variable of ordrNoHdrKeyMap.
		final Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean actualIsRecordInsertedInTaskQ = objStateMachine.submitReqForProvisioningOrScheduling(env, strChildOrderNo);
		
		Assert.assertTrue(actualIsRecordInsertedInTaskQ);
		
	}
	
	@Test
	public void testCheckBuildPlanOrderHoldTrue() throws Exception{
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testCheckBuildPlanOrderHoldTrue_InputDoc.xml");
		
		boolean actualHoldFlag = objStateMachine.checkBuildPlanOrderHold(inDoc.getDocumentElement());
		Assert.assertTrue(actualHoldFlag);
	}
	
	@Test
	public void testCheckBuildPlanOrderHoldFalse() throws Exception{
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testCheckBuildPlanOrderHoldFalse_InputDoc.xml");
		
		boolean actualHoldFlag = objStateMachine.checkBuildPlanOrderHold(inDoc.getDocumentElement());
		Assert.assertFalse(actualHoldFlag);
		
	}
	
	@Test
	public void testEvaluateCustomerOrderCompleted() throws Exception{
		
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		String customerOrderNo = "DEV_ST004";
		String enterpriseCode = "H3G_UK";
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateCustomerOrderCompleted_childOrderList.xml");
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean areAllChildOrdersCompleted = objStateMachine.evaluateCustomerOrder(statusMap, customerOrderNo, enterpriseCode, env);
		
		Assert.assertTrue(areAllChildOrdersCompleted);
	}
	
	
	@Test
	public void testEvaluateCustomerOrderNotCompleted() throws Exception{
		
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		String customerOrderNo = "DEV_ST004";
		String enterpriseCode = "H3G_UK";
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateCustomerOrderNotCompleted_childOrderList.xml");
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean areAllChildOrdersCompleted = objStateMachine.evaluateCustomerOrder(statusMap, customerOrderNo, enterpriseCode, env);
		
		Assert.assertFalse(areAllChildOrdersCompleted);
	}
	
	
	@Test
	public void testEvaluateBuildJobForActivationTrue() throws Exception{
		
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateBuildJobForActivationTrue_childOrderList.xml");
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateBuildJobForActivationTrue_buildPlanOrder.xml");
		Element eleOrderList = inDoc.getDocumentElement();
		Element eleBuildPlanOrder = (Element) eleOrderList.getElementsByTagName(H3GConstants.E_ORDER).item(0);
		NodeList ndlstEleOrderLine = eleBuildPlanOrder.getElementsByTagName(H3GConstants.E_ORDER_LINE);
		Element eleOrderLine = (Element) ndlstEleOrderLine.item(1);
		NodeList ndlstOLLinks = eleOrderLine.getElementsByTagName(H3GConstants.E_EXTN_ORDER_LINE_LINKS);
		
		int listLength = ndlstOLLinks.getLength();
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean readyForActivation = objStateMachine.evaluateBuildJobForActivation(statusMap, ndlstOLLinks, listLength);
		
		Assert.assertTrue(readyForActivation);
	}
	
	
	@Test
	public void testEvaluateBuildJobForActivationFalse() throws Exception{
		
		Document docChildOrderList = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateBuildJobForActivationFalse_childOrderList.xml");
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/api/testEvaluateBuildJobForActivationFalse_buildPlanOrder.xml");
		Element eleOrderList = inDoc.getDocumentElement();
		Element eleBuildPlanOrder = (Element) eleOrderList.getElementsByTagName(H3GConstants.E_ORDER).item(0);
		NodeList ndlstEleOrderLine = eleBuildPlanOrder.getElementsByTagName(H3GConstants.E_ORDER_LINE);
		Element eleOrderLine = (Element) ndlstEleOrderLine.item(1);
		NodeList ndlstOLLinks = eleOrderLine.getElementsByTagName(H3GConstants.E_EXTN_ORDER_LINE_LINKS);
		
		int listLength = ndlstOLLinks.getLength();
		
		H3GStateMachine objStateMachine = new H3GStateMachine();
		Map<String, String> statusMap = objStateMachine.getStatusMap(docChildOrderList);
		
		boolean readyForActivation = objStateMachine.evaluateBuildJobForActivation(statusMap, ndlstOLLinks, listLength);
		
		Assert.assertFalse(readyForActivation);
	}
	
}