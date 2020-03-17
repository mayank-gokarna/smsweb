/************************************************************************************
 * File Name    	: TestH3GOrderDetails.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GOrderDetails.java
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
 *************************************************************************************/

package test.com.h3g.activation.api;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import com.h3g.activation.api.H3GOrderDetails;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;
import org.w3c.dom.Document;



@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GOrderDetails.class, H3GUtil.class,
		 })
@SuppressStaticInitializationFor({ "com.h3g.util.H3GUtil" })

public class TestH3GOrderDetails {
	
	H3GOrderDetails orderDetail = null;
	Properties prop = null;

	@Before
	public void setUp() throws Exception {
		orderDetail = new H3GOrderDetails();
		prop = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		orderDetail = null;
	}
	
	/**
	 * Junit test case to validate the logic for
	 * getting order details.
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testGetOrderDetails() throws Exception {
		
		PowerMockito.mockStatic(H3GUtil.class);

		Document orderDetailsInputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GOrderDetails/testOrderDetails_getOrderDetails_Input.xml");
		System.out
				.println("testGetOrderDetails method Input XML "
						+ SCXmlUtil.getString(orderDetailsInputDoc));
		
		Document orderDetailsOutputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GOrderDetails/testOrderDetails_getOrderDetails_Output.xml");
		System.out
				.println("testGetOrderDetails method Validation XML "
						+ SCXmlUtil.getString(orderDetailsOutputDoc));
		
		//Mock getOrderList output
		Document outputDocGetOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/api/TestH3GOrderDetails/testOrderDetails_getOrderList_Output.xml");

		System.out.println("testGetOrderDetails: mock : getOrderList "
				+ SCXmlUtil.getString(outputDocGetOrderList));
		PowerMockito.when(
				H3GUtil.callGetOrderList(any(YFSEnvironment.class),
						any(Document.class), any(String.class))).thenReturn(outputDocGetOrderList);
		System.out.println("testGetOrderDetails method Output XML "
				+ SCXmlUtil.getString(outputDocGetOrderList));
		
		Document outputDoc = orderDetail.getOrderDetails(null, orderDetailsInputDoc);

		assertEquals(SCXmlUtil.getString(outputDoc),
				SCXmlUtil.getString(orderDetailsOutputDoc));

	}
}
