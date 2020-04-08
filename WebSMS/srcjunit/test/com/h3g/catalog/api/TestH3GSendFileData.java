/************************************************************************************
 * File Name    	: TestH3GSendFileData.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GSendFileData.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Dec 05, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/
package test.com.h3g.catalog.api;

import static org.junit.Assert.assertEquals;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.catalog.api.H3GSendFileData;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.catalog.util.H3GCatalogUtil", "com.h3g.util.H3GSterlingUtil" })
@PowerMockIgnore({ "org.apache.log4j.*", "org.apache.log4j.xml.*" })

public class TestH3GSendFileData {

	Document docEnvironment = null;
	YFSEnvironment environment;
	YIFApi api;

	@Before
	public void setUp() throws Exception {

		XMLUnit.setIgnoreWhitespace(true);

		docEnvironment = SCXmlUtil.createDocument("YFSEnvironment");
		final Element elem = docEnvironment.getDocumentElement();
		elem.setAttribute("userId", "DataUpdater");
		elem.setAttribute("progId", "DataUpdater");

		try {
			api = YIFClientFactory.getInstance().getApi("HTTP", null);
			environment = api.createEnvironment(docEnvironment);

		} catch (YIFClientCreationException e) {
			// Expected exception caused by creating the environment outside the
			// Sterling framework
			throw e;
		}

		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mock(H3GUtil.class, Mockito.CALLS_REAL_METHODS);
		PowerMockito.mock(H3GSterlingUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test case is used to test the prepareFileInput method of
	 * H3GSendFileData with file name in input
	 * 
	 * @throws Exception
	 */

	@Test
	public void testPrepareFileInputWithFileName() throws Exception {
		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithFileName_Input.xml");
		Document outDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithFileName_Output.xml");
		
		H3GSendFileData sendFileData = new H3GSendFileData();
		Document outSendFileData = sendFileData.prepareFileInput(environment, inDoc);
		assertEquals(SCXmlUtil.getString(outDoc), SCXmlUtil.getString(outSendFileData));
		
	}
	
	/**
	 * This test case is used to test the prepareFileInput method of
	 * H3GSendFileData without file name in input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPrepareFileInputWithoutFileName() throws Exception {
		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithoutFileName_Input.xml");
		Document outDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithoutFileName_Output.xml");
		
		H3GSendFileData sendFileData = new H3GSendFileData();
		Document outSendFileData = sendFileData.prepareFileInput(environment, inDoc);
		assertEquals(SCXmlUtil.getString(outDoc), SCXmlUtil.getString(outSendFileData));
		
	}
	
	/**
	 * This test case is used to test the prepareFileInput method of
	 * H3GSendFileData without AckType in input (Success scenario)
	 * 
	 * @throws Exception
	 */

	@Test
	public void testPrepareFileInputWithoutAckType() throws Exception {
		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithoutAckType_Input.xml");
		Document outDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testSendFileData_testPrepareFileInputWithoutAckType_Output.xml");
		
		H3GSendFileData sendFileData = new H3GSendFileData();
		Document outSendFileData = sendFileData.prepareFileInput(environment, inDoc);
		assertEquals(SCXmlUtil.getString(outDoc), SCXmlUtil.getString(outSendFileData));
		
	}


}
