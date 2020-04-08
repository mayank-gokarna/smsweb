/************************************************************************************
 * File Name    	: TestH3GCatalogFeed.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GCatalogFeed.java
 * 					  Private methods are covered with the public methods themselves.
 * 					  Hence, separate methods are not written for them.
 * 					  Method addOffer is tested with splitFile itself, hence, it is not covered separately.
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
package test.com.h3g.catalog.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.h3g.catalog.api.H3GCatalogFeed;
import com.h3g.catalog.util.H3GCatalogConstants;
import com.h3g.util.H3GSterlingUtil;
import com.h3g.util.H3GUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientCreationException;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfs.japi.YFSEnvironment;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "com.h3g.catalog.util.H3GCatalogUtil", "com.h3g.util.H3GSterlingUtil" })
@PowerMockIgnore({ "org.apache.log4j.*", "org.apache.log4j.xml.*" })

public class TestH3GCatalogFeed {

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
	 * This test case is used to test the createResource method of
	 * H3GCatalogFeed
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateResource() throws Exception {

		mapAttributePaths.put("CH_IS_MNP|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		mapAttributePaths.put("CH_MSISDN|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		mapAttributePaths.put("CH_IMSI|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_PORTSTATUS|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_ROUTINGLOCATION|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");

		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_createResource_Input.xml");

		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		mapParentGroups.put("CH_IMSI|MSISDN", eCHG);
		mapParentGroups.put("CH_PORTSTATUS|MSISDN", eCHG);
		mapParentGroups.put("CH_ROUTINGLOCATION|MSISDN", eCHG);
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);

		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_createResource_manageItemOutput.xml");

		Document docManageCategoryItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_createResource_getItemListOutput.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
				.thenReturn(docManageCategoryItem);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createResource(environment, inDoc);

	}

	/**
	 * This test case is used to test the createProdSpecs method of
	 * H3GCatalogFeed
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateProdSpecs() throws Exception {

		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_createProdSpecs_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_createProdSpecs_manageItem_Output.xml");

		Document docModifyCategoryItemValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_createProdSpecs_modifyCategoryItemValidationOutput.xml");
		
		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
				.thenReturn(docModifyCategoryItemValidation);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createProdSpecs(environment, inDoc);

	}

	/**
	 * This test case is used to test the createService (for RFS with
	 * no references) method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateRFSWithNoReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithNoReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithNoReferences_manageItem_Output.xml");
		Document docModifyCategoryItemValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithNoReferences_modifyCategoryItemValidationOutput.xml");
		
		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);
		
		PowerMockito
		.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
				eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
		.thenReturn(docModifyCategoryItemValidation);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createService(environment, inDoc);

	}
	
	/**
	 * This test case is used to test the createService (for RFS with
	 * references) method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateRFSWithReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithReferences_manageItem_Output.xml");
		Document docModifyCategoryItemValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateRFSWithReferences_modifyCategoryItemValidationOutput.xml");
		
		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);
		
		PowerMockito
		.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
				eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
		.thenReturn(docModifyCategoryItemValidation);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createService(environment, inDoc);

	}

	/**
	 * This test case is used to test the createService (for CFS with No
	 * references) method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateCFSWithNoReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithNoReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithNoReferences_manageItem_Output.xml");
		
		Document docModifyCategoryItemValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithNoReferences_modifyCategoryItemValidationOutput.xml");
		
		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);
		
		PowerMockito
		.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
				eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
		.thenReturn(docModifyCategoryItemValidation);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createService(environment, inDoc);

	}

	/**
	 * This test case is used to test the createService (for CFS with
	 * references) method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */
								
	@Test
	public void testCreateCFSWithReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithReferences_manageItem_Output.xml");
		
		Document docModifyCategoryItemValidation = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateCFSWithReferences_modifyCategoryItemValidationOutput.xml");
		
		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);
		
		PowerMockito
		.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
				eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
		.thenReturn(docModifyCategoryItemValidation);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createService(environment, inDoc);

	}

	/**
	 * This test case is used to test the createProductOffering method of
	 * H3GCatalogFeed for individual offers
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateProductOfferingWithNoReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithNoReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithNoReferences_manageItem_Output.xml");

		
		Document docModifyCategoryItemValidationOutput = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithNoReferences_modifyCategoryItemValidationOutput.xml");

		Document chgDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_charGroups.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();
		
		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
				.thenReturn(docModifyCategoryItemValidationOutput);

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);
		
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);

		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createProductOffering(environment, inDoc);

	}

	/**
	 * This test case is used to test the createProductOffering method of
	 * H3GCatalogFeed for composite offers
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateProductOfferingWithReferences() throws Exception {

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithReferences_Input.xml");

		Document docManageItem = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithReferences_manageItem_Output.xml");

		Document docModifyCategoryItemValidationOutput = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateProductOfferingWithReferences_modifyCategoryItemValidationOutput.xml");

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_MODIFY_CATEGORY_ITEM_VALIDATION), any(Document.class)))
				.thenReturn(docModifyCategoryItemValidationOutput);

		PowerMockito
				.when(H3GSterlingUtil.invokeService(any(YFSEnvironment.class),
						eq(H3GCatalogConstants.SERVICE_CREATE_MANAGE_ITEM_INPUT), any(Document.class)))
				.thenReturn(docManageItem);

		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createProductOffering(environment, inDoc);

	}

	/**
	 * This test case is used to test the createAttributePathsMapWithItemContext
	 * method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateAttributePathsMapWithItemContext() throws Exception {

		Map<String, String> mapAttrPaths = new ConcurrentHashMap<String, String>();

		Map<String, String> outMapAttrPaths = new ConcurrentHashMap<String, String>();

		mapAttrPaths.put("CH_IMSI|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttrPaths.put("CH_ROUTINGLOCATION|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttrPaths.put("CH_IS_MNP|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		mapAttrPaths.put("CH_MSISDN|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		
		mapAttrPaths.put("CH_PORTSTATUS|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		
		mapAttrPaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/api/testCatalogFeed_testCreateAttributePathsMapWithItemContext_Input.xml");

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.createAttributePathsMapWithItemContext(environment, inDoc);

		outMapAttrPaths = (ConcurrentHashMap<String, String>) environment
				.getTxnObject(H3GCatalogConstants.TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP);

		System.out.println("outMapAttrPaths->"+outMapAttrPaths.toString());
		
		assertEquals(mapAttrPaths, outMapAttrPaths);

	}

	/**
	 * This test case is used to test the splitFile method of H3GCatalogFeed
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSplitFile() throws Exception {

		Document inDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/catalog/api/testCatalogFeed_testSplitFile_Input.xml");

		H3GCatalogFeed catalogFeed = new H3GCatalogFeed();
		catalogFeed.splitFile(environment, inDoc);

	}


}
