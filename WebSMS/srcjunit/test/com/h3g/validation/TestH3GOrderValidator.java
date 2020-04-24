package test.com.h3g.validation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;

import com.h3g.decomp.decompprocessor.H3GDecompProcessor;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.h3g.util.H3GXMLUtil;
import com.h3g.validation.H3GOrderValidator;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;
import static org.mockito.Matchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TestH3GOrderValidator.class, H3GSterlingUtil.class, H3GXMLUtil.class, H3GDecompProcessor.class,
		H3GUtil.class })
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil","com.h3g.util.H3GUtil"})
public class TestH3GOrderValidator {

	H3GOrderValidator orderValidator = null;

	YFSEnvironment env = null;

	@Before
	public void setUp() throws Exception {

		orderValidator = new H3GOrderValidator();

		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mockStatic(H3GDecompProcessor.class);
		PowerMockito.mockStatic(H3GUtil.class);

	}

	@After
	public void tearDown() throws Exception {
		orderValidator = null;

	}
	// UT001

	@Test
	public void testprocessOrderValidation() throws Exception {

		Document Expected_InputDoc_ForProcessOrderValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/validation/TestH3GOrderValidator/Expected_InputDoc_ForProcessOrderValidation.xml");
		
		Document Expected_OutputDoc_ForProcessOrderValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/validation/TestH3GOrderValidator/Expected_OutputDoc_ForProcessOrderValidation.xml");
		
		Document Expected_OutputDoc_ForPrepareCustomerOrderDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/validation/TestH3GOrderValidator/Expected_OutputDoc_ForPrepareCustomerOrderDoc.xml");
		
		PowerMockito
				.when(H3GUtil.getProcessedOrderData(any(YFSEnvironment.class), any(String.class), any(String.class)))
				.thenReturn(null);

		
		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class), eq(H3GConstants.SERVICE_CALL_ORDER_VALIDATION_WEBSERVICE), any(Document.class)))
				.thenReturn(Expected_Output_For_OrderValidation_WebserviceCall);
		

		Document Actual_OutputDoc_ForProcessOrderValidation = orderValidator.processOrderValidation(env,
				Expected_InputDoc_ForProcessOrderValidation);
		
		assertEquals(SCXmlUtil.getString(Expected_OutputDoc_ForProcessOrderValidation),
				SCXmlUtil.getString(Actual_OutputDoc_ForProcessOrderValidation));
	}
	

}
