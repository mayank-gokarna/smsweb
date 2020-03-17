/************************************************************************************
 * File Name    	: TestH3GBuildPlanProcessor.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GBuildPlanProcessor.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 28, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 *************************************************************************************/

package test.com.h3g.buildplan;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.HashMap;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.buildplan.H3GBuildPlanProcessor;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.h3g.util.H3GXMLUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GBuildPlanProcessor.class, H3GSterlingUtil.class,H3GUtil.class })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil","com.h3g.util.H3GUtil","com.h3g.util.H3GXMLUtil" })
public class TestH3GBuildPlanProcessor {
	private static final YFCLogCategory LOGGER = YFCLogCategory.instance(TestH3GBuildPlanProcessor.class.getName());
	
	H3GBuildPlanProcessor buildPlanProcsr = new H3GBuildPlanProcessor();
	Properties properties = new Properties();
	Document docEnvironment;
	YFSEnvironment environment ;
	YIFApi api;

	@Before
	public void setUp() throws Exception {
		
		
		docEnvironment = SCXmlUtil.createDocument("YFSEnvironment");
		final Element elem = docEnvironment.getDocumentElement();
		elem.setAttribute("userId", "DataUpdater");
		elem.setAttribute("progId", "DataUpdater");
		
		
		try {
			api = YIFClientFactory.getInstance().getApi("HTTP", null);
			environment = api.createEnvironment(docEnvironment);
			
		} catch (YIFClientCreationException e) {
			// Expected exception caused by creating the environment outside the Sterling framework
			LOGGER.debug(e);
		}
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mock(H3GXMLUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	@After
	public void tearDown() throws Exception {
		buildPlanProcsr = null;
	}

	/**
	 * Getter for properties
	 * 
	 * @return Properties object containing key/value pairs
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Setter called by the framework for the properties set during
	 * 
	 * @param prop
	 *            for setting key/value pairs
	 * @throws Exception
	 *             if there is problem setting the properties
	 */
	public void setProperties(Properties prop) throws Exception {
		this.properties = prop;

	}

	/**
	 * Junit test case to validate build plan request xml
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testPrepareBuildplanRequest() throws Exception {

		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_prepareBuildplanRequest_Input.xml");
		Document buildPlanRequest = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_prepareBuildplanRequest_Output.xml");
		
		// Mock output of service H3GGetOrderProcessingData
		Document extnOrderProcessingOutDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_prepareBuildplanRequest_OrderProcessingOut.xml");
		PowerMockito.when(
				H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						any(String.class), any(Document.class))).thenReturn(
				extnOrderProcessingOutDoc);

		// Mock output of API getOrderList
		Document outputDocGetOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_prepareBuildplanRequest_getOrderList_Output.xml");

		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class),
						any(Document.class))).thenReturn(outputDocGetOrderList);

		Document outputDoc = buildPlanProcsr.prepareBuildplanRequest(null,
				inputDoc);

		
		assertEquals(SCXmlUtil.getString(buildPlanRequest),
				SCXmlUtil.getString(outputDoc));

	}
	
	/**
	 * Junit test case to validate build plan response xml
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testGetBuildPlanOrder() throws Exception {

		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_Input.xml");
		Document buildPlanResponse = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_Output.xml");
		
		// Mock output of service H3GGetOrderProcessingData
		Document extnOrderProcessingOutDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_OrderProcessingOut.xml");
		
		// Mock webservice output to get Build Plan response
		Document webserviceResponse = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_WebserviceResponseOutput.xml");
		
		// Mock the output of service to create Build Plan order
		Document createBPOutput = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_CreateBPOrderOutput.xml");
		PowerMockito.when(
				H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						any(String.class),any(Document.class))).thenReturn(
				extnOrderProcessingOutDoc, webserviceResponse, createBPOutput);

		// Mock output of API getOrderList
		Document outputDocGetOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_prepareBuildplanRequest_getOrderList_Output.xml");
		
		// Mock output of API getOrderList
		Document docOrderListOutput = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testBuildPlan_getBuildPlanOrder_getOrderListBP_Output.xml");

		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class),
						any(Document.class))).thenReturn(outputDocGetOrderList, docOrderListOutput);

		Document outputDoc = buildPlanProcsr.getBuildPlanOrder(null, inputDoc);

		
		assertEquals(SCXmlUtil.getString(buildPlanResponse),
				SCXmlUtil.getString(outputDoc));

	}
	/**
	 * This junit id to test ammended build plan created for modification scenario
	 * @throws Exception
	 */
	@Test
	public void testAmmendedBuildPlan() throws Exception{
		
		YFCDocument docBPCReq2 = YFCDocument.createDocument("bpcReq");
		YFCElement bpcReq2Ele = docBPCReq2.getDocumentElement();
		
		String strOrderNo = "ST003_20171310";
		
		Document docgetOrderListOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testAmmendedBuildPlan_getOrderListOutput.xml");
		
		Document docParentBPResponse = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testAmmendedBuildPlan_getParentBPResponseOutput.xml");
		
		Document docChildOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testAmmendedBuildPlan_getChildOrdersList.xml");
		Document docAmmendedBuilPlan = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanProcessor/testAmmendedBuildPlan_getAmmendedBuildPlan.xml");
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.TEMPLATE_AMENDED_BP_ORDER_LIST),
					eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docgetOrderListOut);
			
			PowerMockito.when(
					H3GUtil.getProcessedOrderData(any(YFSEnvironment.class),
					any(String.class), 
					eq(H3GConstants.VAL_BUILD_PLAN))).thenReturn(docParentBPResponse);
			
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.TEMPLATE_ENRICHED_RESP_ORDER_LIST),
					eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docChildOrderList);
			
			PowerMockito.when(
					H3GUtil.getEnrichedAmendedResponse(any(YFSEnvironment.class),any(String.class),
					any(Document.class), 
					any(HashMap.class))).thenReturn(docAmmendedBuilPlan);
			
			
								
			} catch (YFSException e) {
				final YFCException yfcEx = new YFCException(e);			
				throw yfcEx;
			
			} catch (YIFClientCreationException e) {
				final YFCException yfcEx = new YFCException(e);			
				throw yfcEx;
			
			} catch (Exception e) {
				LOGGER.debug(e);
				throw e;
			}
		PowerMockito.mock(H3GUtil.class, Mockito.CALLS_REAL_METHODS);
		buildPlanProcsr.addAmmendedBuildPlan(environment, strOrderNo, bpcReq2Ele);
		
		
		
		
		
	}

}
