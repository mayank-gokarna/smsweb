package test.com.h3g.statemachine.agent;

import static org.mockito.Matchers.any;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;

import com.h3g.statemachine.agent.H3GScheduledActivation;
import com.h3g.util.H3GSterlingUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfs.japi.YFSEnvironment;


@RunWith(PowerMockRunner.class)
@PrepareForTest({H3GSterlingUtil.class, H3GScheduledActivation.class})
@SuppressStaticInitializationFor({"com.h3g.util.H3GSterlingUtil"})
@PowerMockIgnore({"org.apache.log4j.*"})

public class TestH3GScheduledActivation {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteTask() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GScheduledActivation objScheduledActivation = new H3GScheduledActivation();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTask_InputDoc.xml");
		Document getOrderListOutputForDecomposedOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTask_getOrderList_DecomposedOrder.xml");
		Document getOrderListOutputForBuildPlanOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTask_getOrderList_BuildPlanOrder.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class), any(Document.class))).thenReturn(getOrderListOutputForDecomposedOrder,getOrderListOutputForBuildPlanOrder);
		
		
		Document actual = objScheduledActivation.executeTask(env, inDoc);
		
		Assert.assertEquals(SCXmlUtil.getString(inDoc), SCXmlUtil.getString(actual));
	}
	
	
	@Test
	public void testExecuteTaskForCancelledBuildPlan() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GScheduledActivation objScheduledActivation = new H3GScheduledActivation();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForCancelledBuildPlan_InputDoc.xml");
		Document getOrderListOutputForDecomposedOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForCancelledBuildPlan_getOrderList_DecomposedOrder.xml");
		Document getOrderListOutputForBuildPlanOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForCancelledBuildPlan_getOrderList_BuildPlanOrder.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class), any(Document.class))).thenReturn(getOrderListOutputForDecomposedOrder,getOrderListOutputForBuildPlanOrder);
		
		Document actual = objScheduledActivation.executeTask(env, inDoc);
		
		Assert.assertEquals(SCXmlUtil.getString(inDoc), SCXmlUtil.getString(actual));
	}
	
	
	@Test
	public void testExecuteTaskForBuildPlanOnHold() throws Exception {
		
		PowerMockito.mockStatic(H3GSterlingUtil.class);
		YFSEnvironment env = PowerMockito.mock(YFSEnvironment.class);
		
		H3GScheduledActivation objScheduledActivation = new H3GScheduledActivation();
		Document inDoc = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForBuildPlanOnHold_InputDoc.xml");
		Document getOrderListOutputForDecomposedOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForBuildPlanOnHold_getOrderList_DecomposedOrder.xml");
		Document getOrderListOutputForBuildPlanOrder = SCXmlUtil.createFromFileOrUrl("resources/com/h3g/statemachine/agent/testExecuteTaskForBuildPlanOnHold_getOrderList_BuildPlanOrder.xml");
		
		PowerMockito.when(
				H3GSterlingUtil.invokeAPI(any(YFSEnvironment.class),
						any(String.class), any(String.class), any(Document.class))).thenReturn(getOrderListOutputForDecomposedOrder,getOrderListOutputForBuildPlanOrder);
		
		Document actual = objScheduledActivation.executeTask(env, inDoc);
		
		Assert.assertEquals(SCXmlUtil.getString(inDoc), SCXmlUtil.getString(actual));
	}
}
