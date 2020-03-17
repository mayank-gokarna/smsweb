/************************************************************************************
 * File Name    	: TestH3GBuildPlanResponseHandler.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GBuildPlanResponseHandler.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Oct 25, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 *************************************************************************************/

package test.com.h3g.buildplan;

import static org.junit.Assert.*;

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

import com.h3g.buildplan.H3GBuildPlanProcessor;
import com.h3g.buildplan.H3GBuildPlanResponseHandler;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GOrderStatus;
import com.h3g.util.H3GProvisioningUtil;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import org.w3c.dom.Document;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GBuildPlanResponseHandler.class, H3GUtil.class, H3GSterlingUtil.class })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GUtil",
		"com.h3g.util.H3GSterlingUtil" })

public class TestH3GBuildPlanResponseHandler {
	
	H3GBuildPlanResponseHandler buildPlanRespHandlr = null;
	Properties properties = null;

	@Before
	public void setUp() throws Exception {
		buildPlanRespHandlr = new H3GBuildPlanResponseHandler();
		properties = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		buildPlanRespHandlr = null;
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
	 * UT001: Customer order having composite offer which is comprised of
	 * 2 individual offers with CustomerConfiguration attribute. Validate 
	 * build plan order createOrder Output xml
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testProcessBuildPlanResponse_UT001() throws Exception {

		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT001_Input.xml");
		System.out.println("testProcessBuildPlanResponse_UT001 method input "
				+ SCXmlUtil.getString(inputDoc));
		Document buildPlanCreateOrderXML = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT001_Output.xml");
		System.out.println("testProcessBuildPlanResponse_UT001 validation XML  "
				+ SCXmlUtil.getString(buildPlanCreateOrderXML));
		
		// Mock output of getOrderListCustOrderNo
		Document getOrderListCustOrd = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT001_getOrderListCustOrdr.xml");
		System.out.println("testProcessBuildPlanResponse_UT001: getOrderList API output"
				+ SCXmlUtil.getString(getOrderListCustOrd));
		PowerMockito.when(
				H3GUtil.getOrderListCustOrderNo(any(YFSEnvironment.class),
					any(String.class))).thenReturn(
							getOrderListCustOrd);
		
		// Mock output of API changeOrder
		Document changeOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT001_changeOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT001: changeOrderOut API output"
				+ SCXmlUtil.getString(changeOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class), any(Document.class))).thenReturn(
						changeOrderOut);
		
		// Mock output of API createOrder
		Document createOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT001_createOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT001: createOrderOut API output"
				+ SCXmlUtil.getString(createOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq(H3GConstants.API_CREATE_ORDER), 
				any(Document.class))).thenReturn(createOrderOut);
		
		Document outputDoc = buildPlanRespHandlr.processBuildPlanResponse(null,
				inputDoc);

		System.out.println("testProcessBuildPlanResponse_UT001 output  "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(buildPlanCreateOrderXML),
				SCXmlUtil.getString(outputDoc));

	}
	
	/**
	 * UT002: Customer order having composite offer which is comprised of
	 * 2 individual offers without CustomerConfiguration attribute 
	 * Validate build plan order createOrder Output xml 
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testProcessBuildPlanResponse_UT002() throws Exception {

		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT002_Input.xml");
		System.out.println("testProcessBuildPlanResponse_UT002 method input "
				+ SCXmlUtil.getString(inputDoc));
		Document buildPlanCreateOrderXML = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT002_Output.xml");
		System.out.println("testProcessBuildPlanResponse_UT002 validation XML  "
				+ SCXmlUtil.getString(buildPlanCreateOrderXML));
		
		// Mock output of getOrderListCustOrderNo
		Document getOrderListCustOrd = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT002_getOrderListCustOrdr.xml");
		System.out.println("testProcessBuildPlanResponse_UT002: getOrderList API output"
				+ SCXmlUtil.getString(getOrderListCustOrd));
		PowerMockito.when(
				H3GUtil.getOrderListCustOrderNo(any(YFSEnvironment.class),
					any(String.class))).thenReturn(
							getOrderListCustOrd);
		
		// Mock output of API changeOrder
		Document changeOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT002_changeOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT002: changeOrderOut API output"
				+ SCXmlUtil.getString(changeOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class), any(Document.class))).thenReturn(
						changeOrderOut);
		
		// Mock output of API createOrder
		Document createOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT002_createOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT002: createOrderOut API output"
				+ SCXmlUtil.getString(createOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq(H3GConstants.API_CREATE_ORDER), 
				any(Document.class))).thenReturn(createOrderOut);
		
		Document outputDoc = buildPlanRespHandlr.processBuildPlanResponse(null,
				inputDoc);

		System.out.println("testProcessBuildPlanResponse_UT002 output  "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(buildPlanCreateOrderXML),
				SCXmlUtil.getString(outputDoc));

	}
	
	/**
	 * UT003: Customer order with single offer without 
	 * customer configuration. Validate build plan order 
	 * createOrder Output xml 
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testProcessBuildPlanResponse_UT003() throws Exception {

		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT003_Input.xml");
		System.out.println("testProcessBuildPlanResponse_UT003 method input "
				+ SCXmlUtil.getString(inputDoc));
		Document buildPlanCreateOrderXML = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT003_Output.xml");
		System.out.println("testProcessBuildPlanResponse_UT003 validation XML  "
				+ SCXmlUtil.getString(buildPlanCreateOrderXML));
		
		// Mock output of getOrderListCustOrderNo
		Document getOrderListCustOrd = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT003_getOrderListCustOrdr.xml");
		System.out.println("testProcessBuildPlanResponse_UT003: getOrderList API output"
				+ SCXmlUtil.getString(getOrderListCustOrd));
		PowerMockito.when(
				H3GUtil.getOrderListCustOrderNo(any(YFSEnvironment.class),
					any(String.class))).thenReturn(
							getOrderListCustOrd);
		
		// Mock output of API changeOrder
		Document changeOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT003_changeOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT003: changeOrderOut API output"
				+ SCXmlUtil.getString(changeOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class), any(Document.class))).thenReturn(
						changeOrderOut);
		
		// Mock output of API createOrder
		Document createOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT003_createOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT003: createOrderOut API output"
				+ SCXmlUtil.getString(createOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq(H3GConstants.API_CREATE_ORDER), 
				any(Document.class))).thenReturn(createOrderOut);
		
		Document outputDoc = buildPlanRespHandlr.processBuildPlanResponse(null,
				inputDoc);

		System.out.println("testProcessBuildPlanResponse output  "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(buildPlanCreateOrderXML),
				SCXmlUtil.getString(outputDoc));

	}
	
	/**
	 * UT004: Customer order with single offer with 
	 * customer configuration. Validate build plan order 
	 * createOrder Output xml 
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testProcessBuildPlanResponse_UT004() throws Exception {

		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT004_Input.xml");
		System.out.println("testProcessBuildPlanResponse_UT004 method input "
				+ SCXmlUtil.getString(inputDoc));
		Document buildPlanCreateOrderXML = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT004_Output.xml");
		System.out.println("testProcessBuildPlanResponse_UT004 validation XML  "
				+ SCXmlUtil.getString(buildPlanCreateOrderXML));
		
		// Mock output of getOrderListCustOrderNo
		Document getOrderListCustOrd = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT004_getOrderListCustOrdr.xml");
		System.out.println("testProcessBuildPlanResponse_UT004: getOrderList API output"
				+ SCXmlUtil.getString(getOrderListCustOrd));
		PowerMockito.when(
				H3GUtil.getOrderListCustOrderNo(any(YFSEnvironment.class),
					any(String.class))).thenReturn(
							getOrderListCustOrd);
		
		// Mock output of API changeOrder
		Document changeOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT004_changeOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT004: changeOrderOut API output"
				+ SCXmlUtil.getString(changeOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class), any(Document.class))).thenReturn(
						changeOrderOut);
		
		// Mock output of API createOrder
		Document createOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT004_createOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT004: createOrderOut API output"
				+ SCXmlUtil.getString(createOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq(H3GConstants.API_CREATE_ORDER), 
				any(Document.class))).thenReturn(createOrderOut);
		
		Document outputDoc = buildPlanRespHandlr.processBuildPlanResponse(null,
				inputDoc);

		System.out.println("testProcessBuildPlanResponse_UT004 output  "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(buildPlanCreateOrderXML),
				SCXmlUtil.getString(outputDoc));

	}

	/**
	 * UT006: Manipulate BuildPlan response to create dependency of orders 
	 * from different build plan. Validate build plan order 
	 * createOrder Output xml
	 * 
	 * @param
	 * @return
	 */

	@Test
	public void testProcessBuildPlanResponse_UT006() throws Exception {

		PowerMockito.mockStatic(H3GUtil.class);
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		Document inputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT006_Input.xml");
		System.out.println("testProcessBuildPlanResponse_UT006 method input "
				+ SCXmlUtil.getString(inputDoc));
		Document buildPlanCreateOrderXML = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT006_Output.xml");
		System.out.println("testProcessBuildPlanResponse_UT006 validation XML  "
				+ SCXmlUtil.getString(buildPlanCreateOrderXML));
		
		// Mock output of getOrderListCustOrderNo
		Document getOrderListCustOrd = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT006_getOrderListCustOrdr.xml");
		System.out.println("testProcessBuildPlanResponse_UT006: getOrderList API output"
				+ SCXmlUtil.getString(getOrderListCustOrd));
		PowerMockito.when(
				H3GUtil.getOrderListCustOrderNo(any(YFSEnvironment.class),
					any(String.class))).thenReturn(
							getOrderListCustOrd);
		
		// Mock output of API changeOrder
		Document changeOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT006_changeOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT006: changeOrderOut API output"
				+ SCXmlUtil.getString(changeOrderOut));
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class), any(Document.class))).thenReturn(
						changeOrderOut);
		
		// Mock output of API createOrder
		Document createOrderOut = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/buildPlan/TestH3GBuildPlanResponseHandler/testprocessBuildPlanResponse_UT006_createOrderOut.xml");
		System.out.println("testProcessBuildPlanResponse_UT006: createOrderOut API output"
				+ SCXmlUtil.getString(createOrderOut));
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq(H3GConstants.API_CREATE_ORDER), 
				any(Document.class))).thenReturn(createOrderOut);
		
		Document outputDoc = buildPlanRespHandlr.processBuildPlanResponse(null,
				inputDoc);

		System.out.println("testProcessBuildPlanResponse_UT006 output  "
				+ SCXmlUtil.getString(outputDoc));

		assertEquals(SCXmlUtil.getString(buildPlanCreateOrderXML),
				SCXmlUtil.getString(outputDoc));

	}

}
