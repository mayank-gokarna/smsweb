<%
// Licensed Materials - Property of IBM
// IBM Call Center for Commerce (5725-P82)
// (C) Copyright IBM Corp. 2013, 2016 All Rights Reserved.
// US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.ibm.isccs.core.SCCSConstants"%>
<%@page import="com.ibm.isccs.core.utils.SCCSSessionUtils"%>
<%@page import="com.sterlingcommerce.security.dv.SCEncoder"%>
<%@page import="com.sterlingcommerce.ui.web.framework.utils.SCUIContextHelper"%>
<%@page import="com.sterlingcommerce.ui.web.framework.context.SCUIContext"%>
<%@page import="com.sterlingcommerce.ui.web.framework.utils.SCUIJSONUtils"%>
<%@page import="com.sterlingcommerce.ui.web.framework.utils.SCUIUtils"%>
<%@page import="com.sterlingcommerce.baseutil.SCXmlUtil"%>
<%@page import="com.sterlingcommerce.baseutil.SCUtil"%>
<%@page import="com.sterlingcommerce.ui.web.framework.helpers.SCUIMashupHelper"%>
<%@page import="com.sterlingcommerce.ui.web.framework.helpers.SCUILocalizationHelper"%>
<%@page import="com.sterlingcommerce.ui.web.framework.extensibility.SCUIExtensibilityWorkbenchHelper"%>
<%@page import="com.ibm.isccs.core.utils.SCCSMobileUtils" %>
<%@page import="com.yantra.yfc.dom.YFCElement"%>
<%@page import="com.yantra.yfc.log.YFCLogCategory"%>
<%@page import="com.yantra.yfc.ui.backend.util.APIManager"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.IOException"%>
<%@page import="com.yantra.yfc.ui.backend.util.YFCConsoleUtil"%>
<%@ taglib uri="/WEB-INF/scui.tld" prefix="scuitag" %>
<%@ taglib uri="/WEB-INF/scuiimpl.tld" prefix="scuiimpltag" %>
<%@ taglib uri="/WEB-INF/isccs.tld" prefix="isccstag" %>

<%!

	YFCLogCategory logger = YFCLogCategory.instance(this.getClass().getName());
	public String getPaginationSizeList(SCUIContext uiContext){
	  	String pageSizefilePath = SCUIUtils.getExtendedPath(uiContext,"/isccs/common/pagination/PaginationSize.json");
	  	BufferedReader br = null;
	  	StringBuffer pageSizeList = new StringBuffer();
	     	try {
	     	    String s = null;
	     	    InputStream is = uiContext.getSession().getServletContext().getResourceAsStream(pageSizefilePath);
	     	    br = new BufferedReader(new InputStreamReader(is));
	     	    
	  	    while((s = br.readLine()) != null){
	  			pageSizeList.append(s);
	  	    }
	  	} catch (IOException e) {
	  	    logger.error(e);
	  	} finally{
	  	    try {
	  			br.close();
	  	    } catch (IOException e) {
	  			logger.error(e);
	  	    }
	  	}
	    return pageSizeList.toString();
	}
%>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.

	SCUIContext uiContext = SCUIContextHelper.getUIContext(request, response);
	String localeCode = uiContext.getUserPreferences().getLocale().getLocaleCode().toString().replace('_', '-').toLowerCase();
	String localeCharOrientation= uiContext.getUserPreferences().getLocaleCharacterOrientation().toString().replace('_', '-').toLowerCase();

	//Strip the -Ext at the end of the locale.
	//If the locale string contains a - then take the first 5 letters xx-xx
	//Else leave it as is to avoid  
	if (localeCode.length()>5 && localeCode.contains("-"))
		localeCode=localeCode.substring(0,5);
	
	YFCElement eUser = (YFCElement)SCCSSessionUtils.getObjFromSession(request.getSession(false), SCCSConstants.SESSION_CURRENT_USER);
	String sUserName = eUser.getAttribute("Username");
	
	Element getDocTypeDeterList = SCXmlUtil.createDocument("DocTypeDeter").getDocumentElement();
	Element eOutput = (Element) SCUIMashupHelper.invokeMashup("mashup_getDocTypeDeterminationList", getDocTypeDeterList, uiContext);
	
	Element getCommonCodeList = SCXmlUtil.createDocument("CommonCode").getDocumentElement();
	Element eCommonCodeList = (Element) SCUIMashupHelper.invokeMashup("mashup_getCommonCodeList", getCommonCodeList, uiContext);
	
	Element getOrganizationList = SCXmlUtil.createDocument("Organization").getDocumentElement();
	Element eOrganizationOutput = (Element) SCUIMashupHelper.invokeMashup("getOrganizationList_Container", getOrganizationList, uiContext);
	
	String strjson = SCUIJSONUtils.getJSONFromXML(eOrganizationOutput, uiContext);
	
	Element getRulesDetails = SCXmlUtil.createDocument("Rules").getDocumentElement();
	YFCElement eCurrentOrg = (YFCElement)SCCSSessionUtils.getObjFromSession(request.getSession(false), "CurrentOrganization");
	String sOrgCode = eCurrentOrg.getAttribute("OrganizationCode");
	getRulesDetails.setAttribute("CallingOrganizationCode", sOrgCode);
	getRulesDetails.setAttribute("RuleSetFieldName","WCC_SHOW_ITEM_IMAGES");
	Element eGetRulesDetails = (Element) SCUIMashupHelper.invokeMashup("dataprovider_getRuleDetails", getRulesDetails, uiContext);
	String strRuleJson = SCUIJSONUtils.getJSONFromXML(eGetRulesDetails, uiContext);
	
	
	Element msgPnlInput = SCXmlUtil.createDocument("UserUiState").getDocumentElement();
	Element relatedTaskInput = SCXmlUtil.createDocument("UserUiState").getDocumentElement();
	msgPnlInput.setAttribute("ComponentName","SHOW_MSG_PANEL");
	relatedTaskInput.setAttribute("ComponentName","RELATED_TASK_POSTN");
	Element msgPnlOutput = (Element) SCUIMashupHelper.invokeMashup("getUserUiStateListInContainer", msgPnlInput, uiContext);
	Element relatedTaskOutput = (Element) SCUIMashupHelper.invokeMashup("getUserUiStateListInContainer", relatedTaskInput, uiContext);
	String strMsgPnlJson = SCUIJSONUtils.getJSONFromXML(msgPnlOutput, uiContext);
	String strRelatedTaskJson = SCUIJSONUtils.getJSONFromXML(relatedTaskOutput, uiContext);
	
	
	boolean isRight2LeftLanguage = false;
	String bidiOrientation = "ltr";
	String isRTL = uiContext.getRequest().getParameter("isRTL");
	String enablePerformanceLogging = uiContext.getRequest().getParameter("performanceLog");
	String performanceThreshold = uiContext.getRequest().getParameter("performanceThreshold");
	if(SCUtil.equals(isRTL, "Y") || SCUtil.equals(localeCharOrientation, "right-to-left")){
		isRight2LeftLanguage = true;
		bidiOrientation = "rtl";
	}
	String documentDomain = APIManager.getInstance().getProperty("yfs.document.domain");
	
	String onlineHelpUrl = YFCConsoleUtil.getAPPKCOnlineHelpPath(request, "ccc");
%>

<html lang="<%=localeCode%>">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<title><%= SCUILocalizationHelper.getString(uiContext, "ISCCS_Product_Name")%></title>
		<script>
			<%
			if(!SCUtil.isVoid(documentDomain)){
			%>
			      document.domain = '<%=documentDomain%>';
			<%}
			%>
		</script>
		<link rel="SHORTCUT ICON" href="<%=request.getContextPath()%>/isccs/resources/css/icons/images/logo_window.ico"/>
		

		<scuitag:includeDojoJS/>
		<scuitag:includeDojoCSS isRtl="<%=new Boolean(isRight2LeftLanguage).toString()%>"/>
		<scuitag:inclDojoPlatformDependencies/>
		<scuiimpltag:inclDojoPlatformImplDependencies/>

		<script>
			appRequire([
		           "scbase/loader!dojo/parser",
		           "scbase/loader!dojo/_base/connect",
				   "scbase/loader!sc/plat/dojo/ext/mainExtn",
		           "scbase/loader!sc/plat/dojo/utils/Util",
		           "scbase/loader!sc/plat/dojo/utils/BundleUtils",
		           "scbase/loader!sc/plat/dojo/widgets/MDISCScreenContainer",
		           "scbase/loader!isccs/container/containerUtil",
		           "scbase/loader!isccs/container/AppHeader",
		           "scbase/loader!isccs/utils/ContextUtils",
		           "scbase/loader!isccs/utils/UIUtils",
		           "scbase/loader!sc/plat/dojo/utils/LogUtils",
				   "scbase/loader!dojo/ready",
				   "dojo/domReady!"],
		           function(
		        		   dParser,
		        		   dConnect,
						   scMainExtn,
		        		   scUtil,
		        		   scBundleUtils,
		        		   scMDISCScreenContainer,
		        		   isccsContainerUtil,
		        		   isccsAppHeader,
		        		   isccsContextUtils,
		        		   uiUtils,
		        		   scLogUtils,
						   dReady){
								
								dReady(100, function(){
								dParser.parse();
								
								scBundleUtils.registerGlobalBundles("isccs.resources.bundle","isccs_bundle");
								scBundleUtils.registerGlobalBundles("isccs.resources.bundle","isccs_generated_bundle");
								scBundleUtils.registerGlobalBundles("isccs.resources.bundle","isccs_be_bundle");
								scBundleUtils.registerGlobalBundles("isccs.resources.bundle","isccs_csr_message_bundle");
								scBundleUtils.registerGlobalBundles("isccs.resources.bundle","isccs_cust_message_bundle");
								
								<scuitag:includeExtensionBundles/>
								
								scUtil.setReloadConfirmationPopupMode("ALWAYS");
	                            isccsContextUtils.addToContext('bidiOrientation','<%=bidiOrientation%>');
	                            isccsContextUtils.addToContext('isMobile',false);
	                            isccsContextUtils.addToContext('DeviceType','<%=SCCSMobileUtils.getDeviceType(request)%>');
	                            isccsContainerUtil.setOnlineHelpUrl('<%=onlineHelpUrl%>');
	                            
								<%
								if(!SCUtil.isVoid(eOutput)){
									for (Element eDocTypeDeter : SCXmlUtil.getChildrenList(eOutput)){
										String docTypeName = SCEncoder.getEncoder().encodeForJavaScript(eDocTypeDeter.getAttribute("DocumentTypeName"));
										String docType = SCEncoder.getEncoder().encodeForJavaScript(eDocTypeDeter.getAttribute("DocumentType"));
										%>
										isccsContextUtils.addToContext('DOC_TYPE_<%=docTypeName%>','<%=docType%>');
										<%
									}
								}
								if(!SCUtil.isVoid(eCommonCodeList)){
									for(Element eCommonCode : SCXmlUtil.getChildrenList(eCommonCodeList)){
										String codeName = SCEncoder.getEncoder().encodeForJavaScript(eCommonCode.getAttribute("CodeName"));
										String codeValue = SCEncoder.getEncoder().encodeForJavaScript(eCommonCode.getAttribute("CodeValue"));
										%>
										isccsContextUtils.addToContext('<%=codeName%>','<%=codeValue%>');
										<%
									}									
								}
								
								
								if(!SCUtil.isVoid(msgPnlOutput)){
									for (Element resultMsgPnl : SCXmlUtil.getChildrenList(msgPnlOutput)){
										String definition = SCEncoder.getEncoder().encodeForJavaScript(resultMsgPnl.getAttribute("Definition"));
										%>
										isccsContextUtils.addToContext('SHOW_MSG_PANEL','<%=definition%>');
										<%
									}
								}
								
								String relatedtaskPos="";
								if(!SCUtil.isVoid(relatedTaskOutput)){
									String definition="";
									for (Element resultRelatedTask : SCXmlUtil.getChildrenList(relatedTaskOutput)){
										definition = SCEncoder.getEncoder().encodeForJavaScript(resultRelatedTask.getAttribute("Definition"));
										
										%>
										isccsContextUtils.addToContext('RELATED_TASK_POSTN','<%=definition%>');
										<%
									}
									
									if(definition.equalsIgnoreCase("LEFT")){
										relatedtaskPos="leftRelatedTask";
									} else {
										relatedtaskPos="rightRelatedTask";
									}
								}	
								
								%>
								
								isccsContextUtils.addToContext('OrganizationList',<%=strjson%>);
								isccsContextUtils.addToContext('ImagesRuleValue',<%=strRuleJson%>);

								<%
									if(!SCUtil.isVoid(enablePerformanceLogging) && enablePerformanceLogging.equals("Y")){
								 %>
								 	scLogUtils.enablePerformanceLogging();
								 <%}%>
								
								<%
									if(!SCUtil.isVoid(performanceThreshold)){
								 %>
								 	scLogUtils.setMinimumBenchmarkForLogging(<%=SCEncoder.getEncoder().encodeForJavaScript(performanceThreshold)%>);
								 <%}%>
								//scLogUtils.enableWidgetCountLogging();
								
								var contextPath = '<%=request.getContextPath()%>';
								isccsAppHeader.init('<%=SCEncoder.getEncoder().encodeForJavaScript(sUserName)%>');
								uiUtils.setAppHeader(isccsAppHeader);	
								isccsContainerUtil.init(contextPath);
								document.onhelp = new Function("return false;");
								window.onhelp = new Function("return false;");
								dConnect.connect(document, "onkeydown", this, function(eventObj) {
									if(eventObj.keyCode==112)
									{
										isccsContainerUtil.loadOnlineHelpWindow();
										eventObj.preventDefault();
                						eventObj.stopPropagation();
                						eventObj.keyCode = -1;
									}
								});
								dConnect.connect(window, "onhelp", this, function(eventObj) {
									if(eventObj.keyCode==112)
									{
										isccsContainerUtil.loadOnlineHelpWindow();
										eventObj.preventDefault();
                						eventObj.stopPropagation();
                						eventObj.keyCode = -1;
									}
								});	
								
								isccsContextUtils.setPaginationSizeList(<%=getPaginationSizeList(uiContext)%>);
								invokeAppReady();
					 });
		});
		
		
			
		</script>
		<!--  This is not used anywhere. We can uncomment it when required -->
		<!--  <script src="<%=request.getContextPath()%>/isccs/container/shortcut.js" ></script> -->
		<%if(SCCSMobileUtils.isPCDevice(request)){ %>
			<isccstag:includeFile filePath="/jsps/dojo/extensibility/workbench.jsp" inclusionReq="<%=String.valueOf(SCUIExtensibilityWorkbenchHelper.isExtensibilityWorkbenchMode())%>"/>
		<%}%>
		<%
			StringBuffer conditionalStyles = new StringBuffer(""); 
			if(SCUIUtils.isDevMode()){
				conditionalStyles = conditionalStyles.append(" devMode");
			}
			if(SCUIExtensibilityWorkbenchHelper.isExtensibilityWorkbenchMode()){
				conditionalStyles = conditionalStyles.append(" extnMode");
			}
		%>
	</head>

	<body dir="<%=bidiOrientation %>" class="oneui isccs <%=conditionalStyles%>">
		<script>
			<%
			if(!SCUtil.isVoid(documentDomain)){
			%>
			      document.domain = '<%=documentDomain%>';
			<%}
			%>
		</script>
		<!-- Include D3 js library-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/extn/scripts/d3.v2.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/extn/scripts/d3.v2.min.js"></script>
	<div class="contentwrapper">	
		<div class="fixed_layer">		
			<div id="Header" class="headerContainer" uId="TopContainer"></div>
		</div>
		
		<div aria-live="assertive" class="isccsCenter" >
			
			<div class="isccsContent isccsContainer <%=relatedtaskPos%>" data-dojo-type="dijit.layout.ContentPane" data-dojo-props="uId: 'isccsContainer'" >
				<div id="borderContainerCenter" class="isccsMDIContainer" data-dojo-type="sc.plat.dojo.widgets.MDISCScreenContainer" style="min-height:500px;"	data-dojo-props="uId:'CenterContainer', doLayout: false">
				</div>
				<div data-dojo-type="dijit.layout.ContentPane"></div> 
			</div>
		</div>

      <!--div class="push"></div-->
     </div>
     
     </div>
		<div id="right-social-content" class="scratch-pad">
				<%@ include file="/isccs/container/scratchpadEditor.jsp" %>
			</div>
		 <div class="right-bar-tools" style="display: block;" role="complementary" aria-label="<%= SCUILocalizationHelper.getString(uiContext,"Social_tools")%>">
			<ul role="toolbar" aria-label="Call Center Toolbar">
				<li> 
					<a id="isccsScratchPad" title="<%= SCUILocalizationHelper.getString(uiContext, "Scratch_Pad")%>" href="#" rel="print" dojoattrid="0" 
					 role="link" tabindex="0" onclick="isccs.container.containerUtil.handleScratchPad()">				 
				 		<img class="indicatorImage" src="../resources/css/icons/images/scratchpad.png" alt="<%= SCUILocalizationHelper.getString(uiContext, "Scratch_Pad")%>">				 
				 	</a>
				</li>
			</ul>
			
		 </div>
     </div>
    <!-- <div id="footer" role="complementary" aria-label="Footer Bar"> -->
			 <!--%@ include file="footer.jsp" %--> 
	<!-- </div>  -->
	
	<div id="extensibilityShellHolder" class="scExtensibilityShellHolder">
	</div>
	</body>
</html>
