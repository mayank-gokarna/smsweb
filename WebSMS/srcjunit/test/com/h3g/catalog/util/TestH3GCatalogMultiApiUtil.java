/************************************************************************************
 * File Name    	: TestH3GCatalogMultiApiUtil.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GCatalogMultiApiUtil.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Oct 31, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 
 * 
 *************************************************************************************/
package test.com.h3g.catalog.util;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.h3g.catalog.util.H3GCatalogConstants;
import com.h3g.catalog.util.H3GCatalogMultiApiUtil;
import com.h3g.catalog.util.H3GManageCategoryUtil;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import org.custommonkey.xmlunit.Diff;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.catalog.util.H3GCatalogUtil", "com.h3g.util.H3GSterlingUtil" })
@PowerMockIgnore({ "org.apache.log4j.*", "org.apache.log4j.xml.*" })

public class TestH3GCatalogMultiApiUtil {

	Document docEnvironment = null;
	YFSEnvironment environment;
	YIFApi api;
	Map<String, YFCElement> mapParentGroups = new ConcurrentHashMap<String, YFCElement>();
	Map<String, String> mapAttributePaths = new ConcurrentHashMap<String, String>();

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
			throw e;
		}
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		PowerMockito.mock(H3GUtil.class, Mockito.CALLS_REAL_METHODS);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test case is used to test the getGenericMultiApiXml method of H3GCatalogMultiApiUtil
	 * @throws Exception
	 */
	
	@Test
	public void testGetGenericMultiApiXml() throws Exception {
		
		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_Input.xml");

		Document outDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_Output.xml");
		
		Document chgDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_CHG.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();
		
		mapAttributePaths.put("CH_PORTSTATUS|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_PORTSTATUS|MSISDN", eCHG);
		
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);
		
		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_createManageItemInput.xml");
		Document docModifyCategoryItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_modifyCategoryItemInput.xml");
		Document docModifyCategoryItemValidationOutput = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testCatalogMultiApiUtil_getGenericMultiApiXml_modifyCategoryItemValidationOutput.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION),
				any(Document.class))).thenReturn(docModifyCategoryItemValidationOutput);
		
		PowerMockito.when(
				H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GCatalogConstants.SERVICE_CREATE_MODIFY_CATEGORY_ITEM_INPUT),
				any(Document.class))).thenReturn(docModifyCategoryItem);
		
		PowerMockito.when(
				H3GSterlingUtil.invokeService(any(YFSEnvironment.class),eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT),
				any(Document.class))).thenReturn(docManageItem);

		YFCElement eRoot = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCElement eTechnicalDetails = eRoot.getChildElement("technicalDetails");
		YFCElement eResourceSpecification = eTechnicalDetails.getChildElement("serviceSpecification");
		YFCDocument docMultiApi = H3GCatalogMultiApiUtil.getGenericMultiApiXml(environment, inDoc,
				eResourceSpecification);
		
		Diff diff = XMLUnit.compareXML(outDoc, docMultiApi.getDocument());
		assertTrue(diff.toString(), diff.identical());

	}

	/**
	 * This test case is used to test the addToMultiApi method of H3GCatalogMultiApiUtil
	 * @throws Exception
	 */
	@Test
	public void testAddToMultiApi() throws Exception {

		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/util/testCatalogMultiApiUtil_addToMultiApi_Input.xml");

		Document outDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/util/testCatalogMultiApiUtil_addToMultiApi_Output.xml");

		YFCDocument docMultiApi = YFCDocument.createDocument("MultiApi");
		
		docMultiApi = H3GCatalogMultiApiUtil.addToMultiApi(docMultiApi,
				YFCDocument.getDocumentFor(inDoc), "manageItem");

		Diff diff = XMLUnit.compareXML(outDoc, docMultiApi.getDocument());
		assertTrue(diff.toString(), diff.identical());

	}

}
