/************************************************************************************
 * File Name    	: TestH3GCheckTargetOSS.java
 *
 * Organization     : IBM
 *
 * Description      : This class is a junit test class for H3GCheckTargetOSS.java
 * 					  
 * 
 * Modification Log  :
 * -------------------------------------------------------------------------
 * Ver #      Date             Author                Remarks
 * ------------------------------------------------------------------------
 * 0.1      Sep 27, 2017        IBM        Initial Version
 * 
 * -------------------------------------------------------------------------
 * 						  
 *************************************************************************************/

package test.com.h3g.activation.condition;

import static org.junit.Assert.*;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.h3g.activation.condition.H3GCheckTargetOSS;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import org.w3c.dom.Document;

@RunWith(PowerMockRunner.class)
@PrepareForTest(H3GCheckTargetOSS.class)

public class TestH3GCheckTargetOSS {
	
	H3GCheckTargetOSS checkTarOSS = null;
	Properties prop = null;

	@Before
	public void setUp() throws Exception {
		checkTarOSS = new H3GCheckTargetOSS();
		prop = new Properties();
	}

	@After
	public void tearDown() throws Exception {
		checkTarOSS = null;
	}
	
	/**
	 * Junit test case to validate if the dynamic condition returns false
	 * when ExtnTargetOSS attribute has a value.
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testEvaluateConditionForTargetOSS() throws Exception {
		
		Document targetOSSInputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/condition/TestH3GCheckTargetOSS/testCheckTargetOSS_evaluateConditionForTargetOSS_Input.xml");
		System.out
				.println("testEvaluateConditionForTargetOSS method Input XML "
						+ SCXmlUtil.getString(targetOSSInputDoc));
		
		Boolean targetOSSIsNull = checkTarOSS.evaluateCondition(null, null, null, targetOSSInputDoc);
						
		System.out.println("testEvaluateConditionForTargetOSS method output boolean result "
				+ targetOSSIsNull);
		
		// ExtnTargetOSS is not null, so boolean value returned is false
		assertFalse(targetOSSIsNull);

	}
	
	/**
	 * Junit test case to validate if the dynamic condition returns true
	 * when ExtnTargetOSS is null
	 * 
	 * @param
	 * @return
	 */
	@Test
	public void testEvaluateConditionForBlankTargetOSS() throws Exception {
		
		Document targetOSSInputDoc = SCXmlUtil
				.createFromFileOrUrl("resources/com/h3g/activation/condition/TestH3GCheckTargetOSS/testCheckTargetOSS_evaluateConditionForBlankTargetOSS_Input.xml");
		System.out
				.println("testEvaluateConditionForBlankTargetOSS method Input XML "
						+ SCXmlUtil.getString(targetOSSInputDoc));
		
		Boolean targetOSSIsNull = checkTarOSS.evaluateCondition(null, null, null, targetOSSInputDoc);
						
		System.out.println("testEvaluateConditionForBlankTargetOSS method output boolean result "
				+ targetOSSIsNull);
		
		// ExtnTargetOSS is null, so boolean value returned is true
		assertTrue(targetOSSIsNull);

	}

}
