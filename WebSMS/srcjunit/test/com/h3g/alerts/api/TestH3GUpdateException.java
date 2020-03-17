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

import com.h3g.alerts.api.H3GUpdateException;
import com.h3g.util.H3GConstants;
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
@PrepareForTest({ H3GUpdateException.class,H3GSterlingUtil.class})
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil"})
public class TestH3GUpdateException {

	private static final YFCLogCategory LOGGER = YFCLogCategory.instance(TestH3GUpdateException.class.getName());
	
	Document docInXml1 ;
	Document docInXml2 ;
	Document docOutXml1 ;
	Document docOutXml2 ;
	Document docResult1 ;
	Document docResult2 ;
	Document docEnvironment;
	H3GUpdateException updateException = new H3GUpdateException();
	YFSEnvironment environment ;
	YIFApi api;
	
	@Before
	public void setUp() throws Exception{

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
		
	}
	@Test
	public void testResolveExceptions() throws Exception, TransformerException,
		RemoteException, YFSException, YIFClientCreationException {
		docInXml1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/alerts/api/testUpdateException_ResolveExceptionInput.xml");
		//System.out.println("testUpdateException_ResolveExceptionInput "+ SCXmlUtil.getString(docInXml1));
		
		docOutXml1 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/alerts/api/testUpdateException_ResolveExceptionOutput.xml");
		//System.out.println("testUpdateException_ResolveExceptionOutput "+ SCXmlUtil.getString(docOutXml1));
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
					eq(H3GConstants.API_RESOLVE_EXCEPTION), 
					any(Document.class))).thenReturn(docOutXml1);	

								
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
		
		docResult1 = updateException.processUpdateRequest(environment, docInXml1);
		//System.out.println(" testResolveExceptions outputDoc _________ "+ SCXmlUtil.getString(docResult1));
		final Diff diffInXml = new Diff(docResult1, docOutXml1);
		
		assertTrue("Expected XML and Output XML are similar" + diffInXml, diffInXml.similar());
	}
	
	@Test
	public void testUpdateExceptions() throws Exception, TransformerException,
		RemoteException, YFSException, YIFClientCreationException {
		
		docInXml2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/alerts/api/testUpdateException_ChangeExceptionInput.xml");
		docOutXml2 = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/alerts/api/testUpdateException_ChangeExceptionOutput.xml");
		
		//System.out.println("testUpdateException_ChangeExceptionInput "+ SCXmlUtil.getString(docInXml2));
		
		//System.out.println("testUpdateException_ChangeExceptionOutput "+ SCXmlUtil.getString(docOutXml2));
		
		try {
			
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
							eq(H3GConstants.API_CHANGE_EXCEPTIONS),
							any(Document.class))).thenReturn(docOutXml2);
					
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
		
		docResult2 = updateException.processUpdateRequest(environment, docInXml2);
		//System.out.println(" testUpdateExceptions outputDoc _________ "+ SCXmlUtil.getString(docResult2));
		final Diff diffInXml = new Diff(docResult2, docOutXml2);
		
		assertTrue("Expected XML and Output XML are similar" + diffInXml, diffInXml.similar());
	}
	
	
	@After
	public void tearDown() {
		
		api=null;
		docInXml1 = null;
		docOutXml2=null;
		docInXml2 = null;
		docOutXml1=null;
		docResult1 = null;
		docResult2 = null;
		docEnvironment = null;
		environment = null;
	}

}
