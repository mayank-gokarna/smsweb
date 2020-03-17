package test.com.h3g.activation.api;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import static org.mockito.Matchers.any;

import com.h3g.activation.api.H3GFulfilmentUpdate;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({H3GSterlingUtil.class, H3GFulfilmentUpdate.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})


public class TestH3GFulfilmentUpdate {


	@Test
	public void testProcessResponseForShipmentAllocatedStatus() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		
		H3GFulfilmentUpdate objFulfilmentUpdateApi = new H3GFulfilmentUpdate();
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_Input.xml");
		Document inDoc_getShipmentDetails = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_getShipmentDetails_Input.xml");
		Document outDoc_getShipmentDetails = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_getShipmentDetails_Output.xml");
		Document inDoc_changeShipmentStatus = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_inDoc_changeShipmentStatus.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class), any(Document.class))).thenReturn(outDoc_getShipmentDetails);
		
		objFulfilmentUpdateApi.processResponse(env, inDoc);
		
	}
	
	
	@Test
	public void testinvokeChangeShipmentStatusAllocatedStatus() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		
		H3GFulfilmentUpdate objFulfilmentUpdateApi = new H3GFulfilmentUpdate();
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_Input.xml");
		Document inDoc_changeShipmentStatus = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_inDoc_changeShipmentStatus.xml");
		
		
		Document actualDoc = objFulfilmentUpdateApi.prepareInputForChangeShipmentStatus(inDoc, H3GConstants.VAL_SALES_DOC_TYPE);
		
		assertEquals(SCXmlUtil.getString(inDoc_changeShipmentStatus), SCXmlUtil.getString(actualDoc));
	}
	
	
	@Test
	public void testCreateExceptionForBackorderedStatus() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		
		H3GFulfilmentUpdate objFulfilmentUpdateApi = new H3GFulfilmentUpdate();
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_Input.xml");
		Document inDoc_createException = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_inDoc_createException.xml");
		
		
		Document actualDoc = objFulfilmentUpdateApi.prepareInputForCreateException(inDoc.getDocumentElement());
		
		assertEquals(SCXmlUtil.getString(inDoc_createException), SCXmlUtil.getString(actualDoc));
	}
	
	
	@Test
	public void testPrepareInputForChangeOrderForShippedStatus() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GFulfilmentUpdate objFulfilmentUpdateApi = new H3GFulfilmentUpdate();
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_Input.xml");
		Document inDoc_chaneOrder = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_changeOrder_Input.xml");
		Document outDoc_getShipmentDetails = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_getShipmentDetails_Output.xml");
		
		Document actualDoc = objFulfilmentUpdateApi.prepareInputForChangeOrder(env, outDoc_getShipmentDetails.getDocumentElement(), inDoc.getDocumentElement());
		
		assertEquals(SCXmlUtil.getString(inDoc_chaneOrder), SCXmlUtil.getString(actualDoc));
	}
	
	
	@Test
	public void testPrepareInputForMultiApi() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GFulfilmentUpdate objFulfilmentUpdateApi = new H3GFulfilmentUpdate();
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_Input.xml");
		Document inDoc_multiApi = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_multiApi_Input.xml");
		Document outDoc_getShipmentDetails = SCXmlUtil.createFromFileOrUrl
				("resources/com/h3g/activation/api/TestH3GFulfilmentUpdate/testProcessResponseForShipmentAllocatedStatus_getShipmentDetails_Output.xml");
		
		Document changeOrderInput = objFulfilmentUpdateApi.prepareInputForChangeOrder(env, outDoc_getShipmentDetails.getDocumentElement(), inDoc.getDocumentElement());
		Document actualDoc = objFulfilmentUpdateApi.prepareInputForMultiApi(changeOrderInput, inDoc,"1400");
		
		
		assertEquals(SCXmlUtil.getString(inDoc_multiApi), SCXmlUtil.getString(actualDoc));
	}
}
