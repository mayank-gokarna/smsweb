package test.com.h3g.activation.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.h3g.activation.api.H3GResourceOrder;
import com.h3g.util.H3GProvisioningUtil;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil", "com.h3g.util.H3GProvisioningUtil" })
/**
 * Test Cases not implemented due to limited scope for writing JUnit : RPUT006
 * 
 * @author SwapnilShingane
 *
 */
public class TestH3GResourceOrder extends TestCase {

	/**
	 * Test Case of missing order header key : RPUT003
	 */
	H3GResourceOrder rso = null;

	@Override
	protected void setUp() throws Exception {

	}

	/**
	 * RPUT003 : Test case to check null order header key in the response
	 * 
	 * @throws FactoryConfigurationError
	 * @throws Exception
	 */

	@Test
	public void testMissingOrderKey() throws FactoryConfigurationError, Exception {
		// mock methods

		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testMissingOrderKey_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);
		rso = new H3GResourceOrder();
		try {
			rso.processResponse(null, docInput);
		} catch (YFSException yfs) {
			assertEquals(yfs.getErrorCode(), "YFS10364");
		}
	}

	/**
	 * RPUT005 : Test case to check null order header key
	 * 
	 * @throws FactoryConfigurationError
	 * @throws Exception
	 */

	@Test
	public void testMissingOrderKeyInError() throws FactoryConfigurationError, Exception {
		// mock methods

		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testMissingOrderKeyInError_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);
		rso = new H3GResourceOrder();
		try {
			rso.processResponse(null, docInput);
		} catch (YFSException yfs) {
			assertEquals(yfs.getErrorCode(), "YFS10364");
		}

	}

	/**
	 * RPUT002 - Success response.
	 * 
	 * @throws FactoryConfigurationError
	 * @throws Exception
	 */
	@Test
	public void testSuccessResponse() throws FactoryConfigurationError, Exception {
		// mock methods
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		PowerMockito.when(H3GProvisioningUtil.generateCharID(any(YFSEnvironment.class))).thenReturn("12345678980");

		// method input
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testSuccessResponse_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);
		// getOrderList outpu
		InputStream is1 = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testSuccessResponse_getOrderList_Output.xml");
		Document getOrderListOutput = SCXmlUtil.getDocumentBuilder().parse(is1);

		// getOrderLineList multiapi output
		InputStream is2 = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testSuccessResponse_getOrderLineListMultiApi_Output.xml");
		Document getOrderLineListMultiApi = SCXmlUtil.getDocumentBuilder().parse(is2);

		// final multiApi call output
		InputStream is3 = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testSuccessResponse_multiApi_Output.xml");
		Document multiApiOutput = SCXmlUtil.getDocumentBuilder().parse(is3);

		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq("global/template/api/H3GResourceOrder_getOrderList.xml"), eq("getOrderList"), any(Document.class)))
				.thenReturn(getOrderListOutput);
		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				eq("global/template/api/H3GResourceOrder_getOrderList.xml"), eq("getOrderList"), any(Document.class)))
				.thenReturn(getOrderListOutput);
		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("multiApi"), any(Document.class)))
				.thenReturn(getOrderLineListMultiApi).thenReturn(multiApiOutput);

		rso = new H3GResourceOrder();
		try {
			rso.processResponse(null, docInput);
			assertFalse(false);
		} catch (Exception e) {

			e.printStackTrace();
			assertFalse(true);
		}

	}

	/**
	 * RPUT004 - Error Response with proper error code and message
	 * 
	 * @throws FactoryConfigurationError
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testErrorResponse()
			throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError {
		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		// method input
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testErrorResponse_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);
		rso = new H3GResourceOrder();
		try {
			rso.processResponse(null, docInput);
			assertFalse(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertFalse(true);
		}
	}

	/**
	 * Invalid API Input
	 * 
	 * @throws FactoryConfigurationError
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testInvalidInput()
			throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError {
		// method input
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testInvalidInput_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);
		rso = new H3GResourceOrder();
		try {
			rso.processResponse(null, docInput);
			assertFalse(false);
		} catch (YFSException yfsException) {
			assertEquals(yfsException.getErrorCode(), "YCP0042");

		} catch (Exception e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}

	/**
	 * test case for method getInputToUpdateResourceOrder
	 * 
	 * @throws FactoryConfigurationError
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testGetInputToUpdateResourceOrder()
			throws SAXException, IOException, ParserConfigurationException, FactoryConfigurationError {
		// method input
		InputStream is = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testGetInputToUpdateResourceOrder_Input.xml");
		Document docInput = SCXmlUtil.getDocumentBuilder().parse(is);

		// Expected Output
		InputStream isExpectedOutput = H3GResourceOrder.class.getResourceAsStream(
				"/resources/com/h3g/activation/api/TestH3GResourceOrder/testGetInputToUpdateResourceOrder_Expected_Output.xml");
		Document docExpectedOutput = SCXmlUtil.getDocumentBuilder().parse(isExpectedOutput);
		final Map<String, Map<String, String>> productPropertiesToUpdate = new ConcurrentHashMap<String, Map<String, String>>();
		PowerMockito.mockStatic(H3GProvisioningUtil.class);
		PowerMockito.when(H3GProvisioningUtil.generateCharID(any(YFSEnvironment.class))).thenReturn("12345678980");
		rso = new H3GResourceOrder();
		Document docOutput = rso.getInputToUpdateResourceOrder(docInput.getDocumentElement(), productPropertiesToUpdate,
				null);
		assertEquals(SCXmlUtil.getString(docExpectedOutput), SCXmlUtil.getString(docOutput));
		
	}

	@Override
	protected void tearDown() throws Exception {
		rso = null;
	}
}
