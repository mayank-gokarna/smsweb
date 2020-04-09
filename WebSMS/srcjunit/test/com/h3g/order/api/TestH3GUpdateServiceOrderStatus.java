/************************************************************************************
 * File Name    	: TestH3GUpdateServiceOrderStatus.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GUpdateServiceOrderStatus.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Oct 30, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/

package test.com.h3g.order.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.order.api.H3GUpdateServiceOrderStatus;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil" })
@PrepareForTest({ H3GUpdateServiceOrderStatus.class, H3GSterlingUtil.class })
public class TestH3GUpdateServiceOrderStatus extends TestCase {

	private YIFApi api;

	private DocumentBuilder docBuilder;

	private YFSEnvironment env;

	public TestH3GUpdateServiceOrderStatus(String name) {
		super(name);
	}

	/**
	 * Method to create Object of class to be tested and mock static for mocked
	 * classes/methods
	 * 
	 * @throws Exception
	 */

	@Override
	protected void setUp() throws Exception {

		api = YIFClientFactory.getInstance().getApi("HTTP", null);

		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		docBuilder = fac.newDocumentBuilder();
		Document environmentDoc = docBuilder.newDocument();
		Element envElement = environmentDoc.createElement("YFSEnvironment");
		envElement.setAttribute("userId", "admin");
		envElement.setAttribute("progId", "password");
		environmentDoc.appendChild(envElement);
		env = api.createEnvironment(environmentDoc);

	}

	@Test
	public void testRetry() throws Exception {
		Document expectedOutDoc = null;
		Document outResultDoc = null;
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mock(YFSEnvironment.class);

		H3GUpdateServiceOrderStatus updateServiceOrderStatus = new H3GUpdateServiceOrderStatus();

		expectedOutDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_retry_Expected.xml");

		Document outDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_retry_Output.xml");

		PowerMockito.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class), eq("H3GForwardActivationRequestToBPM"), any(Document.class))).thenReturn(outDoc);
		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("changeOrder"), any(Document.class))).thenReturn(outDoc);

		Document docInputXml = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_retry_Input.xml");

		outResultDoc = updateServiceOrderStatus.changeServiceOrderStatus(env, docInputXml);

		assertEquals(SCXmlUtil.getString(expectedOutDoc), SCXmlUtil.getString(outResultDoc));

	}

	@Test
	public void testMarkAsComplete() throws Exception {
		Document expectedOutDoc = null;
		Document outResultDoc = null;
		PowerMockito.mockStatic(H3GSterlingUtil.class);

		H3GUpdateServiceOrderStatus updateServiceOrderStatus = new H3GUpdateServiceOrderStatus();

		expectedOutDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_markAsComplete_Expected.xml");

		Document outDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_markAsComplete_Output.xml");

		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("changeOrderStatus"), any(Document.class))).thenReturn(outDoc);
		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("changeOrder"), any(Document.class))).thenReturn(outDoc);

		Document docInputXml = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GUpdateServiceOrderStatus/testChangeServiceOrderStatus_markAsComplete_Input.xml");

		outResultDoc = updateServiceOrderStatus.changeServiceOrderStatus(env, docInputXml);

		assertEquals(SCXmlUtil.getString(expectedOutDoc), SCXmlUtil.getString(outResultDoc));

	}

	@Override
	public void tearDown() throws Exception {
		api = null;
		env = null;
	}
}
