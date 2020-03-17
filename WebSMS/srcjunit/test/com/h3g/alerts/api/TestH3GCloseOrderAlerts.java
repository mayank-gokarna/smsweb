/************************************************************************************
 * File Name    	: TestH3GCloseOrderAlerts.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for TestH3GCloseOrderAlerts.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 26, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/

package test.com.h3g.alerts.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.rmi.RemoteException;

import javax.xml.transform.TransformerException;

import org.custommonkey.xmlunit.Diff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.alerts.api.H3GCloseOrderAlerts;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GCloseOrderAlerts.class,H3GSterlingUtil.class})
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil"})
public class TestH3GCloseOrderAlerts {

	private static final YFCLogCategory LOGGER = YFCLogCategory.instance(TestH3GCloseOrderAlerts.class.getName());
	
	Document docInXml ;
	Document docOutXml ;
	Document docEnvironment;
	H3GCloseOrderAlerts closeOrderAlerts = new H3GCloseOrderAlerts();
	YFSEnvironment environment ;
	YIFApi api;
	
	@Before
	public void setUp() throws Exception{

	docInXml = SCXmlUtil
			.createFromFileOrUrl("resources/com/h3g/alerts/api/testCloseAlerts_Input.xml");
	docOutXml = SCXmlUtil
			.createFromFileOrUrl("resources/com/h3g/alerts/api/testCloseAlerts_Output.xml");
	
	//System.out.println("testCloseAlerts_Input "+ SCXmlUtil.getString(docInXml));
	//System.out.println("testCloseAlerts_Output "+ SCXmlUtil.getString(docOutXml));
	
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
	try {
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
				any(String.class),eq("getExceptionList"), 
				any(Document.class))).thenReturn(docOutXml);	

		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						eq("multiApi"),
						any(Document.class))).thenReturn(docOutXml);
				
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
	
	}
	@Test
	public void testCloseAlerts() throws Exception, TransformerException,
		RemoteException, YFSException, YIFClientCreationException {
		docOutXml = closeOrderAlerts.closeAlerts(environment, docInXml);
		//System.out.println("outputDoc _________ "+ SCXmlUtil.getString(docOutXml));
		final Diff diffInXml = new Diff(docOutXml, docInXml);
		
		assertTrue("Expected XML and Output XML are similar" + diffInXml, diffInXml.similar());
	}
	
	@After
	public void tearDown() {
		
		api=null;
		docInXml = null;
		docOutXml=null;
		docEnvironment = null;
		environment = null;
	}

}
