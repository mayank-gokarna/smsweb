/************************************************************************************
 * File Name    	: TestH3GManageCategoryUtil.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GManageCategoryUtil.java
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

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.custommonkey.xmlunit.Diff;
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

import com.h3g.catalog.util.H3GManageCategoryUtil;
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

public class TestH3GManageCategoryUtil {

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
	 * This test case is used to test the createManageCategoryXml method of H3GManageCategoryUtil
	 * @throws Exception
	 */
	@Test
	public void testCreateManageCategoryXml() throws Exception {
		
		mapAttributePaths.put("CH_IS_MNP|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		mapAttributePaths.put("CH_MSISDN|MSISDN", "/ItemAttribute/MSISDN|MSISDN");
		mapAttributePaths.put("CH_IMSI|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_PORTSTATUS|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_ROUTINGLOCATION|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");
		mapAttributePaths.put("CH_ORIGIN|MSISDN", "/ItemAttribute/MSISDN/CHG_MNP|CHG_MNP");

		Document inDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testManageCategoryUtil_createManageCategoryXml_Input.xml");

		Document outDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testManageCategoryUtil_createManageCategoryXml_Output.xml");
		
		Document chgDoc = SCXmlUtil.createFromFileOrUrl(
				"resources/com/h3g/catalog/util/testManageCategoryUtil_createManageCategoryXml_CHG.xml");
		YFCElement eCHG = YFCDocument.getDocumentFor(chgDoc).getDocumentElement();
		
		mapParentGroups.put("CH_IMSI|MSISDN", eCHG);
		mapParentGroups.put("CH_PORTSTATUS|MSISDN", eCHG);
		mapParentGroups.put("CH_ROUTINGLOCATION|MSISDN", eCHG);
		mapParentGroups.put("CH_ORIGIN|MSISDN", eCHG);
		
		environment.setTxnObject("TRANSACTION_OBJECT_ATTRIBUTE_PATHS_MAP", mapAttributePaths);
		environment.setTxnObject("TRANSACTION_OBJECT_PARENT_GROUPS_MAP", mapParentGroups);
		
		YFCElement eRoot = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCElement eTechnicalDetails = eRoot.getChildElement("technicalDetails");
		YFCElement eResourceSpecification = eTechnicalDetails.getChildElement("resourceSpecification");
		YFCDocument docMultiApi = H3GManageCategoryUtil.createManageCategoryXml(inDoc, eResourceSpecification,null, environment);
		System.out.println("docMultiApi->"+docMultiApi.toString());
		Diff diff = XMLUnit.compareXML(outDoc, docMultiApi.getDocument());
		assertTrue(diff.toString(), diff.identical());

	}


}
