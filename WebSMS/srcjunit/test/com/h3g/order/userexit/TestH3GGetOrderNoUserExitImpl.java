package test.com.h3g.order.userexit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.order.userexit.H3GGetOrderNoUserExitImpl;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GGetOrderNoUserExitImpl.class,H3GUtil.class})

public class TestH3GGetOrderNoUserExitImpl {
	
	private static final YFCLogCategory LOGGER = YFCLogCategory.instance(TestH3GGetOrderNoUserExitImpl.class.getName());
	Document docEnvironment;
	H3GGetOrderNoUserExitImpl orderNoUE = new H3GGetOrderNoUserExitImpl();
	YFSEnvironment environment ;
	YIFApi api;
	String strCustOrderNo= "CUST9999";
	String strOrderNo= "12345";
	String strBPOrderNo= "CUST9999.BP.100001";
	
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
	
	PowerMockito.mockStatic(H3GUtil.class);
	
	try {
		PowerMockito.when(
				H3GUtil.generateChildOrderNo(any(YFSEnvironment.class),eq(H3GConstants.VAL_BP_APPENDER),
				any(String.class))).thenReturn(strBPOrderNo);	

		
		} catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			LOGGER.debug(e);
			throw e;
		}
	
	
	}
	
	@Test
	public void testBPOrderNo() throws Exception, TransformerException,
		RemoteException, YFSException, YIFClientCreationException {
		
		Map<String,String> mUEMap = new ConcurrentHashMap<String,String>();
		
		mUEMap.put(H3GConstants.A_ORDER_NO, strOrderNo);
		mUEMap.put(H3GConstants.VAL_MAP_ARG_CUST_ORD_NO, strCustOrderNo);
		mUEMap.put(H3GConstants.A_ORDER_TYPE, H3GConstants.VAL_BUILD_PLAN);
				
		String strOutOrderNo = orderNoUE.getOrderNo(environment,mUEMap);
		//System.out.println("Order No ____________ "+strOutOrderNo);
		
		assertEquals(strOutOrderNo,strBPOrderNo);
		
	}
	
	@Test
	public void testOtherOrderNo() throws Exception, TransformerException,
		RemoteException, YFSException, YIFClientCreationException {
		
		Map<String,String> mUEMap = new ConcurrentHashMap<String,String>();
		
		mUEMap.put(H3GConstants.A_ORDER_NO,strOrderNo);
		mUEMap.put(H3GConstants.VAL_MAP_ARG_CUST_ORD_NO, strCustOrderNo);
		mUEMap.put(H3GConstants.A_ORDER_TYPE, "");
		
		String strOutOrderNo = orderNoUE.getOrderNo(environment,mUEMap);
		
		//System.out.println("Other Order No ____________ "+strOrderNo);
		
		assertEquals(strOrderNo,strOutOrderNo);
	}
	
	@After
	public void tearDown() {
		
		api=null;
		strCustOrderNo = null;
		strOrderNo = null;
		docEnvironment = null;
		environment = null;
	}
}
