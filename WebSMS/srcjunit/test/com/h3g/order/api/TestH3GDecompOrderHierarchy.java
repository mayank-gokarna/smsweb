/************************************************************************************
 * File Name    	: TestH3GDecompOrderHierarchy.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for TestH3GDecompOrderHierarchy.java
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
 * 
 *************************************************************************************/

package test.com.h3g.order.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.order.api.H3GDecompOrderHierarchy;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil" })
@PrepareForTest({ H3GDecompOrderHierarchy.class, H3GSterlingUtil.class })
public class TestH3GDecompOrderHierarchy extends TestCase {

	public H3GDecompOrderHierarchy decompOrderHierarchy;
	public YFSEnvironment env;

	/**
	 * Method to create Object of class to be tested and mock static for mocked
	 * classes/methods
	 * 
	 * @throws Exception
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * This test case is to fetch all related orders for customer order after
	 * decomposition
	 * 
	 * @throws Exception
	 */

	@Test
	public void testFetchAllOrders() throws Exception {

		Document expectedOutDoc = null;
		Document outResultDoc = null;
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = null;
		H3GDecompOrderHierarchy decompOrderHierarchy = new H3GDecompOrderHierarchy();

		expectedOutDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testFetchAllOrders_Expected.xml");

		Document outDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testFetchAllOrders_Output.xml");

		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("global/template/api/getOrderList_OrderHierarchy.xml"), eq("getOrderList"), any(Document.class))).thenReturn(outDoc);

		Document docInputXml = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testFetchAllOrders_Input.xml");

		Element eleInputOrder = docInputXml.getDocumentElement();

		outResultDoc = Whitebox.invokeMethod(decompOrderHierarchy, "fetchAllOrders", env, eleInputOrder);
		
		assertEquals(SCXmlUtil.getString(expectedOutDoc), SCXmlUtil.getString(outResultDoc));

	}
	
	/**
	 * This test case is to test buildDecompOrderHierarchy of H3GDecompOrderHierarchy
	 *  
	 * @throws Exception
	 */

	@Test
	public void testBuildDecompOrderHierarchy() throws Exception {
		Document expectedOutDoc = null;
		Document outResultDoc = null;
		YFSEnvironment env = null;
		H3GDecompOrderHierarchy decompOrderHierarchy = new H3GDecompOrderHierarchy();
		
		Document inputDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testBuildDecompOrderHierarchy_Input.xml");
		
		expectedOutDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testBuildDecompOrderHierarchy_Expected.xml");
		
		Document fetchAllOrderOutDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/order/api/TestH3GDecompOrderHierarchy/testBuildDecompOrderHierarchy_fetchAllOrders_Output.xml");
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		
		PowerMockito.when(H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class), eq("global/template/api/getOrderList_OrderHierarchy.xml"), eq("getOrderList"), any(Document.class))).thenReturn(
				fetchAllOrderOutDoc);

		outResultDoc = decompOrderHierarchy.buildDecompOrderHierarchy(env, inputDoc);

		assertEquals(SCXmlUtil.getString(expectedOutDoc), SCXmlUtil.getString(outResultDoc));

	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
