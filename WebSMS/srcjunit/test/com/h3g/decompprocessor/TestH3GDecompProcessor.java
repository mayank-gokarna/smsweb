/************************************************************************************
 * File Name    	: TestH3GDecompProcessor.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for TestH3GDecompProcessor.java
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

package test.com.h3g.decompprocessor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import org.custommonkey.xmlunit.Diff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.h3g.decomp.decompprocessor.H3GDecompProcessor;
import com.h3g.decomp.decompprocessor.H3GPrepareProdSpecifications;
import com.h3g.util.H3GConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.h3g.util.H3GXMLUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.sterlingcommerce.tools.datavalidator.XmlUtils;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ H3GDecompProcessor.class,H3GSterlingUtil.class,H3GUtil.class,H3GXMLUtil.class,YFSSystem.class, H3GPrepareProdSpecifications.class})
@SuppressStaticInitializationFor({ "com.h3g.util.H3GSterlingUtil","com.h3g.util.H3GUtil","com.h3g.util.H3GXMLUtil"})
public class TestH3GDecompProcessor {

	
	Document docInputXml ;
	Document docEnvironment;
	Document docOutputXml;
	Document docOrderList;
	Document getProcessedDataList;
	Document docPrepareCustomerOrderElement;
	Document docProdSpecifications;
	Document docDecomWebServiceRes;
	Document docPersistDecompRespSyncService;
	Document docOutputOFGetOrderListXml;
	Document docGetDocumentForOrderElementXml;
	Document docInputOfCustomerOrderXml;
	Document docProdSpecsXml;
	Document docCustomerConfigurationXml;
	Document docDecomWebServiceReq;
	Document docOutputOfMergeCustOrderProdSpecs;
	Document docOutputOfProdSpecifications;
	String strOrderHeaderKey;
	Element orderEleExtn;
		
	H3GDecompProcessor decompProcessor = new H3GDecompProcessor();
	YFSEnvironment environment ;
	YIFApi api;
	
	@Before
	public void setUp() throws Exception{
	
	PowerMockito.mockStatic(H3GSterlingUtil.class);
	PowerMockito.mockStatic(H3GUtil.class);
	PowerMockito.mockStatic(H3GXMLUtil.class);
	PowerMockito.mock(H3GUtil.class, Mockito.CALLS_REAL_METHODS);
		
	}
	
	
	@Test
	public void testBuildAndInvokeDecomp() throws Exception{
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_BuildAndInvokeDecomp.xml");
		docOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetOrderListDecom.xml");
		docGetDocumentForOrderElementXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetDocumentForOrderElement.xml");
		getProcessedDataList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_GetProcessedDataList.xml");
		docPrepareCustomerOrderElement = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderElement.xml");
		docProdSpecifications = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ProdSpecifications.xml");
		docDecomWebServiceRes = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_DecomWebService.xml");
		docOutputOFGetOrderListXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetOrderList.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_BuildAndInvokeDecomp.xml");
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.PATH_ORDER_LIST_DECOM_TEMPLATE),
					eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOrderList);
			PowerMockito.when(
					H3GXMLUtil.getDocumentForElement(any(Element.class))).thenReturn(docGetDocumentForOrderElementXml);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_GET_PROCESSED_DATA_LIST), 
					any(Document.class))).thenReturn(getProcessedDataList);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CUSTOMER_ORDER), 
					any(Document.class))).thenReturn(docPrepareCustomerOrderElement);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_PROD_SPEC), 
					any(Document.class))).thenReturn(docProdSpecifications);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CALL_DECOMPOSITION_WEBSERVICE), 
					any(Document.class))).thenReturn(docDecomWebServiceRes);
			PowerMockito.when(
					H3GUtil.generateChildOrderNo(any(YFSEnvironment.class),any(String.class), 
					any(String.class))).thenReturn("DEV20171022_001_01.PO.001");
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOutputOFGetOrderListXml);
			
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		
		Document buildAndInvokeDecompRes = decompProcessor.buildAndInvokeDecomp(environment,docInputXml); 
		
		diffOutXml = new Diff(SCXmlUtil.getString(buildAndInvokeDecompRes), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testPrepareDecompResponseDoc() throws Exception{
		final Diff diffOutXml;
		
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_PrepareDecompResponseDoc.xml");
		docOutputOFGetOrderListXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutPutDoc_Of_GetOrderListBillToID.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareDecompResponseDoc.xml");
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.PATH_BILL_TO_ID_TEMPLATE),
					eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOutputOFGetOrderListXml);
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		Document buildAndInvokeDecompRes = decompProcessor.prepareDecompResponseDoc(environment,docInputXml);
		diffOutXml = new Diff(SCXmlUtil.getString(buildAndInvokeDecompRes), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testlinkChildOrders() throws Exception{
		
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_LinkChildOrders.xml");
		docOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetOrderListForCustomerOrder.xml");
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOrderList);
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		decompProcessor.linkChildOrders(environment,docInputXml);		
	}
	
	@Test
	public void testMergeCustOrderProdSpecs() throws Exception{
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_BuildAndInvokeDecomp.xml");
		docPrepareCustomerOrderElement = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderElement.xml");
		docProdSpecifications = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ProdSpecifications.xml");
		strOrderHeaderKey = "20171022122340771475";
		docDecomWebServiceReq = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_DecomposeRequest.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_MergeCustOrderProdSpecs.xml");
		
		String strCustomerConfigurationData = SCXmlUtil.getXpathAttribute(docInputXml.getDocumentElement(),
				H3GConstants.XPATH_DATASTORE_CUSTOMER_CONF);
		docCustomerConfigurationXml = YFCDocument.createDocument(H3GConstants.E_CUSTOMER_CONFIG).getDocument();
		if (null != strCustomerConfigurationData && !"".equalsIgnoreCase(strCustomerConfigurationData)) {
				Document docCustomerConfiguration = XmlUtils.createFromString(strCustomerConfigurationData);
				H3GXMLUtil.copyElement(docCustomerConfigurationXml, docCustomerConfiguration.getDocumentElement(),
					docCustomerConfigurationXml.getDocumentElement());
		}
		
		Element orderListRootEle = docInputXml.getDocumentElement();
		orderEleExtn = (Element) orderListRootEle.getElementsByTagName(H3GConstants.E_EXTN)
				.item(H3GConstants.VAL_ZERO);
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOrderList);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CREATE_DECOMP_INPUT), 
					any(Document.class))).thenReturn(docDecomWebServiceReq);
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		docOutputOfMergeCustOrderProdSpecs = decompProcessor.mergeCustOrderProdSpecs(environment,docPrepareCustomerOrderElement,docProdSpecifications,docCustomerConfigurationXml,strOrderHeaderKey,orderEleExtn);		
		diffOutXml = new Diff(SCXmlUtil.getString(docOutputOfMergeCustOrderProdSpecs), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	
	@Test
	public void testPrepareProdSpecs() throws Exception{
		final Diff diffOutXml;
		
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_PrepareProdSpecs.xml");
		docProdSpecifications = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ServiceProdSpec.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareProdSpecs.xml");
		
		H3GPrepareProdSpecifications prepareProdSpecifications = new H3GPrepareProdSpecifications(){
			@Override protected String getTextContentValue(){
				return "CHV580518";
			}
		};
		
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_PROD_SPECS_ELEMENT), 
					any(Document.class))).thenReturn(docProdSpecifications);
			PowerMockito.whenNew(H3GPrepareProdSpecifications.class).withNoArguments().thenReturn(prepareProdSpecifications);
			PowerMockito.when(H3GXMLUtil.createDocument(H3GConstants.E_ITEM)).thenReturn(SCXmlUtil.createDocument(H3GConstants.E_ITEM));	
			
		} catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		
		docOutputOfProdSpecifications = decompProcessor.prepareProdSpecs(environment, docInputXml);
		diffOutXml = new Diff(SCXmlUtil.getString(docOutputOfProdSpecifications), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testPrepareCustomerOrderDoc() throws Exception{
		final Diff diffOutXml;
		
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_PrepareCustomerOrderDocument.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderDocument.xml");		
		docPrepareCustomerOrderElement = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderDocument.xml");
		try {
			PowerMockito.when(H3GXMLUtil.xmlAcceptableToSpecialChar(any(Document.class))).thenReturn(docPrepareCustomerOrderElement);
		} catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		}catch (Exception e) {
			
			throw e;
		}
		
		docOutputOfProdSpecifications = H3GDecompProcessor.prepareCustomerOrderDoc(environment, docInputXml);
		diffOutXml = new Diff(SCXmlUtil.getString(docOutputOfProdSpecifications), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	/**
	 * This method will call buildRussianDollForItem recursively based on item details, hence Junit is not possible for this method  
	 * @throws Exception
	 */
	@Test
	public void testCreateItemHierarchy() throws Exception{
		
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_CreateItemHierarchy.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_CreateItemHierarchy.xml");
				
		diffOutXml = new Diff(SCXmlUtil.getString(docOutputXml), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testBuildAndInvokeDecompForModifiedOrder() throws Exception{
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_BuildAndInvokeDecomp_Modify.xml");
		docOrderList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetOrderListDecom_Modify.xml");
		docGetDocumentForOrderElementXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetDocumentForOrderElement_Modify.xml");
		getProcessedDataList = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetProcessedDataList_Modify.xml");
		docPrepareCustomerOrderElement = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderElement_Modify.xml");
		docProdSpecifications = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ProdSpecifications_Modify.xml");
		docDecomWebServiceRes = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_DecomWebService_Modify.xml");
		docOutputOFGetOrderListXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_GetOrderList_Modify.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_BuildAndInvokeDecomp_Modify.xml");
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.PATH_ORDER_LIST_DECOM_TEMPLATE),
					eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOrderList);
			PowerMockito.when(
					H3GXMLUtil.getDocumentForElement(any(Element.class))).thenReturn(docGetDocumentForOrderElementXml);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_GET_PROCESSED_DATA_LIST), 
					any(Document.class))).thenReturn(getProcessedDataList);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CUSTOMER_ORDER), 
					any(Document.class))).thenReturn(docPrepareCustomerOrderElement);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_PROD_SPEC), 
					any(Document.class))).thenReturn(docProdSpecifications);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CALL_DECOMPOSITION_WEBSERVICE), 
					any(Document.class))).thenReturn(docDecomWebServiceRes);
			PowerMockito.when(
					H3GUtil.generateChildOrderNo(any(YFSEnvironment.class),any(String.class), 
					any(String.class))).thenReturn("ST2017112253_001_01.PO.001");
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOutputOFGetOrderListXml);
			
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		
		Document buildAndInvokeDecompRes = decompProcessor.buildAndInvokeDecomp(environment,docInputXml);
		diffOutXml = new Diff(SCXmlUtil.getString(buildAndInvokeDecompRes), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@Test
	public void testMergeCustOrderProdSpecsModification() throws Exception{
		final Diff diffOutXml;
		docInputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/InputDoc_Of_BuildAndInvokeDecomp_Modify.xml");
		docPrepareCustomerOrderElement = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_PrepareCustomerOrderElement_Modify.xml");
		docProdSpecifications = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_ProdSpecifications_Modify.xml");
		strOrderHeaderKey = "20171022122340771475";
		docDecomWebServiceReq = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_DecomposeRequest_Modify.xml");
		docOutputXml = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/decompprocessor/OutputDoc_Of_MergeCustOrderProdSpecs_Modify.xml");
		
		String strCustomerConfigurationData = SCXmlUtil.getXpathAttribute(docInputXml.getDocumentElement(),
				H3GConstants.XPATH_DATASTORE_CUSTOMER_CONF);
		docCustomerConfigurationXml = YFCDocument.createDocument(H3GConstants.E_CUSTOMER_CONFIG).getDocument();
		if (null != strCustomerConfigurationData && !"".equalsIgnoreCase(strCustomerConfigurationData)) {
				Document docCustomerConfiguration = XmlUtils.createFromString(strCustomerConfigurationData);
				H3GXMLUtil.copyElement(docCustomerConfigurationXml, docCustomerConfiguration.getDocumentElement(),
					docCustomerConfigurationXml.getDocumentElement());
		}
		
		Element orderListRootEle = docInputXml.getDocumentElement();
		orderEleExtn = (Element) orderListRootEle.getElementsByTagName(H3GConstants.E_EXTN)
				.item(H3GConstants.VAL_ZERO);
		
		try {
			PowerMockito.when(
					H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),eq(H3GConstants.API_GET_ORDER_LIST), 
					any(Document.class))).thenReturn(docOrderList);
			PowerMockito.when(
					H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GConstants.SERVICE_CREATE_DECOMP_INPUT), 
					any(Document.class))).thenReturn(docDecomWebServiceReq);
		}catch (YFSException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (YIFClientCreationException e) {
			final YFCException yfcEx = new YFCException(e);			
			throw yfcEx;
		
		} catch (Exception e) {
			
			throw e;
		}
		docOutputOfMergeCustOrderProdSpecs = decompProcessor.mergeCustOrderProdSpecs(environment,docPrepareCustomerOrderElement,docProdSpecifications,docCustomerConfigurationXml,strOrderHeaderKey,orderEleExtn);
		diffOutXml = new Diff(SCXmlUtil.getString(docOutputOfMergeCustOrderProdSpecs), SCXmlUtil.getString(docOutputXml));
		assertTrue("Expected XML and Output XML are similar" + diffOutXml, diffOutXml.similar());
	}
	
	@After
	public void tearDown() {
		docInputXml = null;
		docEnvironment = null;
		docOutputXml = null;
		docOrderList = null;
		getProcessedDataList = null;
		docPrepareCustomerOrderElement = null;
		docProdSpecifications = null;
		docDecomWebServiceRes = null;
		docPersistDecompRespSyncService = null;
		docOutputOFGetOrderListXml = null;
	}

}
