/*
 * Licensed Materials - Property of IBM
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013, 2016 All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
scDefine([
	"scbase/loader!dojo/_base/lang",
	"scbase/loader!dojox/timing",
	 "scbase/loader!dojo/date",        
	"scbase/loader!dojo/date/locale",
	"scbase/loader!dojo/_base/array",
	"scbase/loader!dojo/currency",
	"scbase/loader!dijit/form/Button",
	"scbase/loader!dojox/html/entities",
	"scbase/loader!isccs",
	"scbase/loader!sc/plat/dojo/utils/BaseUtils",
	"scbase/loader!sc/plat/dojo/utils/ScreenUtils",
	"scbase/loader!sc/plat/dojo/utils/GridxUtils",
	"scbase/loader!sc/plat/dojo/utils/EventUtils",
	"scbase/loader!sc/plat/dojo/utils/BundleUtils",
	"scbase/loader!sc/plat/dojo/utils/ControllerUtils",
	"scbase/loader!sc/plat/dojo/info/ApplicationInfo",
	"scbase/loader!sc/plat/dojo/utils/ModelUtils",
	"scbase/loader!sc/plat/dojo/utils/WidgetUtils",
	"scbase/loader!sc/plat/dojo/utils/EditorUtils",
	"scbase/loader!isccs/utils/UIUtils",
	"scbase/loader!isccs/utils/ModelUtils",
	"scbase/loader!isccs/utils/WidgetUtils",
	"scbase/loader!isccs/utils/ContextUtils",
	"scbase/loader!isccs/utils/BaseTemplateUtils",
	"scbase/loader!isccs/utils/OrderUtils",
	"scbase/loader!isccs/utils/DeliveryUtils",
	"scbase/loader!sc/plat/dojo/Userprefs",
	"scbase/loader!sc/plat/dojo/utils/WizardUtils",
	"scbase/loader!sc/plat/dojo/utils/PaginationUtils"
          ],
          function(dLang,
		dTimer,
		dDate,
		dLocale,
		dArray,
		dCurrency,
		dButton,
		dHtmlEntities,
		isccs,
		scBaseUtils,
		scScreenUtils,
		scGridUtils,
		scEventUtils,
		scBundleUtils,
		scControllerUtils,
		scApplicationInfo,
		scModelUtils,
		scWidgetUtils,
		scEditorUtils,
		isccsUIUtils,
		isccsModelUtils,
		isccsWidgetUtils,
		isccsContextUtils,
		isccsBaseTemplateUtils,
		isccsOrderUtils,
		isccsDeliveryUtils,
		scUserprefs,
		scWizardUtils,
		scPaginationUtils)
			
				{
	
				var orderLineUtils = dLang.getObject("extn.utils.OrderLineUtils", true,
						isccs);
				
				orderLineUtils.OLST_cellClick = function(event, bEvent, ctrl, args){
					var cellJson = scBaseUtils.getAttributeValue("cellJson",false,args);
					var screen = scEventUtils.getScreenFromEventArguments(args);
					//setting additional data  for link image fields
					var cellJsonData = scBaseUtils.getAttributeValue("cellJsonData",false,args);
					var itemData = scBaseUtils.getAttributeValue("item",false,args);
					if (!scBaseUtils.isVoid(itemData) && scBaseUtils.isVoid(cellJsonData))
						scBaseUtils.setAttributeValue("cellJsonData", itemData, args);
					var uniqueRowId = scBaseUtils.getAttributeValue("uniqueRowId",false,args);
					var rowIndex = scBaseUtils.getAttributeValue("rowIndex",false,args);
					if (!scBaseUtils.isVoid(rowIndex) && scBaseUtils.isVoid(uniqueRowId))
						scBaseUtils.setAttributeValue("uniqueRowId", rowIndex, args);
					//
					if (!(scBaseUtils.isVoid(cellJson))){
						var item = scBaseUtils.getAttributeValue("item",false,args);
						if (scBaseUtils.equals("OriginalOrderNo", scBaseUtils.getAttributeValue("colField",false,cellJson))){
							var eDerivedOrder = scModelUtils.createNewModelObjectWithRootKey("Order");
							scModelUtils.addModelToModelPath("Order",scModelUtils.getModelObjectFromPath("DerivedFromOrder", item), eDerivedOrder);
							isccsOrderUtils.openOrder(screen, eDerivedOrder);
						} else if (scBaseUtils.equals("ExpectedOn", scBaseUtils.getAttributeValue("colField",false,cellJson))){
							if(!args.linkUId){
								var eOrder = scModelUtils.createNewModelObjectWithRootKey("Order");
								scModelUtils.addModelToModelPath("Order", item, eOrder);
								isccsUIUtils.openWizardInEditor("isccs.shipment.wizards.shipmentTracking.ShipmentTrackingWizard", eOrder, "isccs.editors.OrderEditor", screen);
							}else{
								if(scBaseUtils.equals("ExpectedOn", args.linkUId)){
									orderLineUtils.OLST_openExpectedOn(event, bEvent, ctrl, args);
								} else if(scBaseUtils.equals("TrackingNo", args.linkUId)){
									orderLineUtils.OLST_openTrackingNo(event, bEvent, ctrl, args);
								} else if(scBaseUtils.equals("BundleUnitPtice", args.linkUId)){
									screen.openBundlePopup(event, bEvent, ctrl, args);
								}
							}
						} else if (scBaseUtils.equals("TrackingNo", scBaseUtils.getAttributeValue("colField", false, cellJson))){
							orderLineUtils.openTrackingNo(screen, item);
						} else if (scBaseUtils.equals("DeleteOrderLine", scBaseUtils.getAttributeValue("colField", false, cellJson)) || 
							scBaseUtils.equals("Delete", scBaseUtils.getAttributeValue("colField", false, cellJson)) ){
							screen.OLL_handleCancel(event, bEvent, ctrl, args);
						} else if(scBaseUtils.equals("UnitPrice", scBaseUtils.getAttributeValue("colField", false, cellJson))){
							//scEventUtils.fireEventInsideScreen(screen,args.grid.uId,null,args);
							screen.openBundlePopup(event, bEvent, ctrl, args);
						} else if(scBaseUtils.equals("InvoiceDisplayDescription", scBaseUtils.getAttributeValue("colField", false, cellJson))){
							screen.openInvoiceList(event, bEvent, ctrl, args);
						} else if(scBaseUtils.equals("MultiPriceMatchReason", scBaseUtils.getAttributeValue("colField", false, cellJson))){
							screen.askToOverride(event, bEvent, ctrl, args);
						} else {
							screen.LST_DoubleClickHandler(event, bEvent, ctrl, args);
						}
					} else {
						screen.LST_DoubleClickHandler(event, bEvent, ctrl, args);
					}
				};
				
				orderLineUtils.OLST_openTrackingNo = function(event, bEvent, ctrl, args) {
					var cellJson = scBaseUtils.getAttributeValue("cellJsonData",false,args);
					if (cellJson._dataItem) {
						cellJson = cellJson._dataItem;
					}
					if(!cellJson){
						cellJson = scBaseUtils.getAttributeValue("item",false,args);
					}
					if(cellJson) {
						var screen = scEventUtils.getScreenFromEventArguments(args);
						orderLineUtils.openTrackingNo(screen, cellJson);
					}
				};
				
				orderLineUtils.OLST_openExpectedOn = function(event, bEvent, ctrl, args) {
					var cellJson = scBaseUtils.getAttributeValue("cellJsonData",false,args);
					if (cellJson._dataItem) {
						cellJson = cellJson._dataItem;
					}
					if(!cellJson){
						cellJson = scBaseUtils.getAttributeValue("item",false,args);
					}
					if(cellJson) {
						var screen = scEventUtils.getScreenFromEventArguments(args);
						var eOrder = scModelUtils.createNewModelObjectWithRootKey("Order");
						scModelUtils.addModelToModelPath("Order", cellJson, eOrder);
						isccsUIUtils.openWizardInEditor("isccs.shipment.wizards.shipmentTracking.ShipmentTrackingWizard", eOrder, "isccs.editors.OrderEditor", screen);
					}
				};
				
				orderLineUtils.openTrackingNo = function(screen, item) {
					if (item.TrackingInfoList && item.TrackingInfoList.TrackingInfo){
						var length = item.TrackingInfoList.TrackingInfo.length;
						if (length == 1){
							var options = {"destination":"window"};
							isccsUIUtils.openURL(item.TrackingInfoList.TrackingInfo[0].TrackingUrl, options);
						} else if (length > 1) {
							var eOrder = scModelUtils.createNewModelObjectWithRootKey("Order");
							scModelUtils.addModelToModelPath("Order", item, eOrder);
							isccsUIUtils.openWizardInEditor("isccs.shipment.wizards.shipmentTracking.ShipmentTrackingWizard", eOrder, "isccs.editors.OrderEditor", screen);
						}
					}
				};
				orderLineUtils.openBundlePopup = function(orderLineKey, title,bundlePopupInput){
					var model = null;
					var editor = scEditorUtils.getCurrentEditor();
					var screen = scEditorUtils.getScreenInstance(editor);
					var wiz = isccsUIUtils.getCurrentWizardInstance(editor);
					// When opened from returns, exchange order detials are to be used to open bundle popup.
					var currentExchange = isccsUIUtils.getWizardModel(wiz,"currentExchange");
					if(!scBaseUtils.isVoid(bundlePopupInput)){
						model = bundlePopupInput;
					}
					else if(scBaseUtils.isVoid(currentExchange)){
						model = scScreenUtils.getInitialInputData(screen);
					} 
					else{						
						model = currentExchange;
					}					
					var orderHeaderKey = scModelUtils.getStringValueFromPath("Order.OrderHeaderKey",model);
					if (!orderHeaderKey) {
						orderHeaderKey = scModelUtils.getStringValueFromPath("OrderLine.OrderHeaderKey",model);
					}
					var orderModel = scModelUtils.createNewModelObjectWithRootKey("Order");
					scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",orderHeaderKey,orderModel);
					scModelUtils.setStringValueAtModelPath("Order.OrderLineKey",orderLineKey,orderModel);
					
					var popupParams = null; //Bean
					popupParams = scBaseUtils.getNewBeanInstance();
					scBaseUtils.setAttributeValue("screenInput", orderModel, popupParams);

					isccsUIUtils.openSimplePopup("isccs.order.details.BundlePopup", title, screen, popupParams, {});
				};
				
				orderLineUtils.isShipTogetherBundle = function(orderLineJSON){
					var sBundleFulfillmentMode = scModelUtils.getStringValueFromPath("ItemDetails.PrimaryInformation.BundleFulfillmentMode", orderLineJSON);
					return (scModelUtils.getBooleanValueFromPath("IsBundleParent", orderLineJSON, false) && sBundleFulfillmentMode && scBaseUtils.equals(sBundleFulfillmentMode, "00"));
				};
				 
				orderLineUtils.getDeliveryMethod = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){					
					if (orderLineUtils.isShipTogetherBundle(gridRowJSON)){
						return scBundleUtils.getString("ShipIndBundleFulfillment");
					} 
					else{
						unformattedValue = this.handleDeliveryMethod(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue);
						return unformattedValue;
					} 
					return unformattedValue;
				};
				
				orderLineUtils.handleDeliveryMethod = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){
					var sLineType = scModelUtils.getStringValueFromPath("LineType", gridRowJSON);
					if(isccsDeliveryUtils.isServiceLine(gridRowJSON)){
						return scBundleUtils.getString("PS_No_FulfillmentMethod");
					} else if (sLineType && scBaseUtils.equals(sLineType, "Credit")){
						 return scBundleUtils.getString("CustomerCanKeep");
					}
					return unformattedValue;
				}
								
				orderLineUtils.getDeliveryMethodForAddItems = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){
					//using seperate deliverymethod dynamic binding func for add items as we need to show delivery method for ship indp bundle item in add items.
					unformattedValue = this.handleDeliveryMethod(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue);
					return unformattedValue;
				};
				
				orderLineUtils.getImageStyleforFulfillment = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};
					if(gridRowRecord.DeliveryMethod === "SHP"){
						styleInfo.imageStyleClass = "indicatorImage";
					}else{
						styleInfo.imageStyleClass = "indicatorImage";
					}
					
					return styleInfo;
				};
				
				orderLineUtils.handleError = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){
					// valiatin error are to be shown only for new lines.For existing lines, clear error element.
					var draftOrderFlag = scModelUtils.getStringValueFromPath("Order.DraftOrderFlag",gridRowJSON);
					if(draftOrderFlag && draftOrderFlag === 'N'){
						if(!isccsOrderUtils.isNewLine(gridReference.ownerScreen,gridRowJSON,draftOrderFlag)){
							return "";
						}
					}
					return unformattedValue;
				};
					
                orderLineUtils.getFormattedDate = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue, modifiedValue){
                    var userDate = scBaseUtils.getDate();
					var parentScreen = gridReference.ownerScreen;
					var orderLineListModel = scScreenUtils.getModel(parentScreen,"getFulfillmentSummaryDetails_output");
					if(orderLineListModel && orderLineListModel.IsLargeOrder && orderLineListModel.IsLargeOrder=="Y"){
						 var notAvailable = scBundleUtils.getString("NotAvailable");
                         return returnValue;
					}
                    var serverDate = null;
                    var isBundleParent = gridRowJSON.IsBundleParent;
                    var fulfillmentMode = gridRowJSON.ItemDetails.PrimaryInformation.BundleFulfillmentMode;
                    if (!(isBundleParent && isBundleParent === "Y" && fulfillmentMode && (fulfillmentMode === "00" || fulfillmentMode === "02"))) {
                        serverDate = scBaseUtils.getAttributeValue("EarliestShipDate", false, gridRowJSON);
                       var formattedServerDate = scBaseUtils.formatDateToUserFormat(serverDate);
                        var formattedUserDate = dLocale.format(userDate, {
                            selector: 'date'
                        });
                        if (scBaseUtils.equals(formattedUserDate, formattedServerDate)) {
                            //var bundleUtils = sc.plat.dojo.utils.BundleUtils;
                            var returnValue = scBundleUtils.getString("Today");
                            return returnValue;
                        }
                        else {
                            return formattedServerDate;
                        }
                    }
                    else {
                        return null;
                    }
                };
				
				orderLineUtils.getFormattedReqShipDateForSummary = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){
						
						var emptyString = scBundleUtils.getString("blank");
						//handle PS/DS
						if(gridRowJSON.ItemGroupCode == "PS" || gridRowJSON.DeliveryMethod == "DEL"){
							if(gridRowJSON.PromisedApptStartDate && gridRowJSON.PromisedApptEndDate){
								var screenInstance = gridReference.ownerScreen;
								var formattedDate = scBaseUtils.formatDateToUserFormat(gridRowJSON.PromisedApptStartDate);
								var input = [];
								scBaseUtils.appendToArray(input, formattedDate);
								scBaseUtils.appendToArray(input, isccsDeliveryUtils.formatTimeToUserFormat(gridRowJSON.PromisedApptStartDate,screenInstance));
								scBaseUtils.appendToArray(input, isccsDeliveryUtils.formatTimeToUserFormat(gridRowJSON.PromisedApptEndDate,screenInstance));
								return scScreenUtils.getFormattedString(screenInstance, "FormattedAppointment", input);
							}
							else{
								return emptyString;
							}
						}
					
						var gridRowJson = scGridUtils.getItemFromRowIndexUsingUId(gridReference.ownerScreen,gridReference.uId,rowIndex);
						var dateModel = scBaseUtils.getNewModelInstance();
						var userDate = scBaseUtils.getDate();
						var serverDateString = "";
						var formattedUserDate = dLocale.format(userDate, {
							selector: 'date'
						});
						var rowJson = scGridUtils.getRowJsonFromRowIndex(gridReference.ownerScreen,gridReference.uId,rowIndex);
						var serverDate = null;
						var serverDateArray = scModelUtils.getModelListFromPath("ReqShipDate",gridRowJson);
						if(serverDateArray){
							serverDateString = scBaseUtils.getArrayBeanItemByIndex(serverDateArray,0);
							if(serverDateString){
								serverDate = scModelUtils.getDateValueFromPath("ReqShipDate", gridRowJson);
							}else{
								serverDate = scModelUtils.getDateValueFromPath("ExpectedStartDate", rowJson);
							}
						}
						if ((!isccsOrderUtils.isShipIndOrDeliverTogParent(gridRowJSON))) {
							if(!serverDate)
								return emptyString;
							var formattedServerDate = dLocale.format(serverDate, {selector: 'date'});
						if (scBaseUtils.equals(formattedUserDate, formattedServerDate)) {
							var returnValue = scBundleUtils.getString("Today");
							return returnValue;
						}else if(!scBaseUtils.equals(formattedUserDate, formattedServerDate)) {
							return formattedServerDate;
						}else {
							return emptyString;
						}
					}else{
						return emptyString;
					}
						
				};
				
				orderLineUtils.getFormattedReqShipDate = function(gridReference,rowIndex,columnIndex,gridRowJSON,unformattedValue){
						var gridRowJson = scGridUtils.getItemFromRowIndexUsingUId(gridReference.ownerScreen,gridReference.uId,rowIndex);
						var earliestShipDate = scModelUtils.getDateValueFromPath("EarliestShipDate", gridRowJson);
						// Extra Code -->
						
						var dateModel = scBaseUtils.getNewModelInstance();
						scModelUtils.addStringValueToModelObject("MaxDate","2999-12-31",dateModel);
						var maxDate = scModelUtils.getDateValueFromPath("MaxDate", dateModel);
						scWidgetUtils.setAllowableDateTimeRangeUsingUid(gridReference.ownerScreen,"OLST_listGrid_tblColPickupDate",earliestShipDate,maxDate);
						var userDate = scBaseUtils.getDate();
						var emptyString = "";
						var serverDateString = "";
						var formattedUserDate = dLocale.format(userDate, {
							selector: 'date'
						});
						var serverDateArray = scModelUtils.getModelListFromPath("ReqShipDate",gridRowJson);
						if(serverDateArray){
							serverDateString = scBaseUtils.getArrayBeanItemByIndex(serverDateArray,0);
						}
						
						var serverDate = null;
						if ((!isccsOrderUtils.isShipIndOrDeliverTogParent(gridRowJSON))) {
							serverDate = scModelUtils.getDateValueFromPath("ReqShipDate", gridRowJson);
							var formattedServerDate = dLocale.format(serverDate, {selector: 'date'});
						if (scBaseUtils.equals(formattedUserDate, formattedServerDate)) {
							var returnValue = scBundleUtils.getString("Today");
							return returnValue;
						}
						else if(serverDateString && !scBaseUtils.equals(formattedUserDate, formattedServerDate)) {
							return formattedServerDate;
						}else {
							return emptyString;
						}
					}else{
						return emptyString;
					}
					
						
				};
				
				orderLineUtils.getLineUnitPrice = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue){
					var unitprice = null;
					unitprice = scBaseUtils.getAttributeValue("LineOverallTotals.DisplayUnitPrice",false,gridRowJSON);		
					if ((scBaseUtils.isVoid(unitprice))){
						unitprice = unformattedValue;
					}
					unitprice = isccsOrderUtils.setPriceForLinePriceInformation(gridRowJSON, unitprice);
					return unitprice;
				};
				
				orderLineUtils.getBundleLineUnitPriceForLink = function(dataItem, gridRowRecord, gridReference){
					var isBundleParent = null;
					var gridRowJSON = gridRowRecord;
					var unitprice = "";
					isBundleParent = scBaseUtils.getAttributeValue("IsBundleParent",false,gridRowJSON);
					var orderLineKey = scBaseUtils.getAttributeValue("OrderLineKey",false,gridRowJSON);
					if(scBaseUtils.isVoid(orderLineKey)){
						orderLineKey = scBaseUtils.getAttributeValue("DerivedFrom.OrderLineKey",false,gridRowJSON);
					}
					if (!(scBaseUtils.isVoid(orderLineKey)) && !(scBaseUtils.isVoid(isBundleParent)) && (isBundleParent == "Y")){
						unitprice = orderLineUtils.getLineUnitPriceMultiLine(dataItem, gridRowRecord, gridReference);
					}
					return unitprice;
				};
				
				orderLineUtils.getBundleLineUnitPriceForField = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
					var isBundleParent = null;
					var gridRowJSON = gridRowRecord;
					var unitprice = "";
					var orderLineKey = scBaseUtils.getAttributeValue("OrderLineKey",false,gridRowJSON);
					var transactionalLineId = scBaseUtils.getAttributeValue("TransactionalLineId",false,gridRowJSON);
					var derivedOrderLineKey = scModelUtils.getStringValueFromPath("DerivedFrom.OrderLineKey", gridRowJSON);
					isBundleParent = scBaseUtils.getAttributeValue("IsBundleParent",false,gridRowJSON);
					if ((scBaseUtils.isVoid(isBundleParent) || (isBundleParent == "N")) && (!scBaseUtils.isVoid(orderLineKey) || !scBaseUtils.isVoid(transactionalLineId) || !scBaseUtils.isVoid(derivedOrderLineKey))){
						unitprice = orderLineUtils.getLineUnitPriceMultiLine(dataItem, gridRowRecord, gridReference,updatedGridRecord);
					}else if(!(scBaseUtils.isVoid(isBundleParent)) && (isBundleParent == "Y") && (scBaseUtils.isVoid(orderLineKey)) && !(scBaseUtils.isVoid(transactionalLineId))){
						unitprice = orderLineUtils.getLineUnitPriceMultiLine(dataItem, gridRowRecord, gridReference,updatedGridRecord);
					}else{
					
					}

					return unitprice;
				};
				
				orderLineUtils.getBundleLineUnitPriceForLineSummary = function(unformattedValue,widget,screen,namespace,orderline){
					var isLinePriceForInformationOnly = scBaseUtils.getAttributeValue("LinePriceInfo.IsLinePriceForInformationOnly",false,orderline.OrderLine);
					var unitprice = "";
					//If isLinePriceForInformationOnly is empty or N return price otherwise not considered, return included.
					if (!(scBaseUtils.isVoid(isLinePriceForInformationOnly)) && (isLinePriceForInformationOnly == "Y")){
						unitprice = scBundleUtils.getString("Included");
						return unitprice;
					}
					else {
						unitprice = scBaseUtils.getAttributeValue("ComputedPrice.UnitPrice",false,orderline.OrderLine);		
						if ((scBaseUtils.isVoid(unitprice))){
							unitprice = unformattedValue;
						}
						var countryCode = scBaseUtils.getAttributeValue("Order.PriceInfo.Currency", false, orderline.OrderLine);
						if(countryCode){
							unitprice = dCurrency.format(unitprice,{currency:countryCode});
						}
						return unitprice;
					}
					return unitprice;
				};
				
				orderLineUtils.getBundleLineUnitPrice = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue){
					var isLinePriceForInformationOnly = scBaseUtils.getAttributeValue("LinePriceInfo.IsLinePriceForInformationOnly",false,gridRowJSON);
					var unitprice = "";
					//If isLinePriceForInformationOnly is empty or N return price otherwise not considered, return included.
					if (!(scBaseUtils.isVoid(isLinePriceForInformationOnly)) && (isLinePriceForInformationOnly == "Y")){
						unitprice = scBundleUtils.getString("Included");
						return unitprice;
					}
					else {
						unitprice = scBaseUtils.getAttributeValue("LineOverallTotals.UnitPrice",false,gridRowJSON);		
						if ((scBaseUtils.isVoid(unitprice))){
							unitprice = unformattedValue;
						}
						var countryCode = scBaseUtils.getAttributeValue("Order.PriceInfo.Currency", false, gridRowJSON);
						if(countryCode){
							unitprice = dCurrency.format(unitprice,{currency:countryCode});
						}
						return unitprice;
					}
					return unitprice;
				};
				
				orderLineUtils.getDerivedOrderNo = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue){
					if (unformattedValue){
						return unformattedValue;
					}
					return "";
				};
							
				orderLineUtils.getLineTotal = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue){
					var linetotal = null;
					linetotal = scBaseUtils.getAttributeValue("LineOverallTotals.DisplayLineTotalWithoutTaxes",false,gridRowJSON);
					var screen = gridReference.ownerScreen;
		            if (scBaseUtils.isVoid(scModelUtils.getStringValueFromPath("OrderLineKey", gridRowJSON))) {
		                unformattedValue = scScreenUtils.getString(screen, "unsavedLineTotal");
		                return unformattedValue;
		            } 
		            else if (!(scBaseUtils.isVoid(linetotal))){	
						/*if(isNaN(linetotal)){
							return linetotal;
						}
						else {*/
							linetotal = isccsOrderUtils.setPriceForLinePriceInformation(gridRowJSON, linetotal);
							return linetotal;
						//}			
					}
					else
					{
						unformattedValue = isccsOrderUtils.setPriceForLinePriceInformation(gridRowJSON, unformattedValue);
						return unformattedValue;
					}
				};
				
				orderLineUtils.getCurrencyDisplay = function(gridReference, rowIndex, columnIndex, gridRowJSON, currencyValue){
					return isccsOrderUtils.setPriceForLinePriceInformation(gridRowJSON, currencyValue);
				};
				
				orderLineUtils.getLineUnitPriceMultiLine = function (dataItem, gridRowRecord, gridReference,updatedGridRecord){
					var gridRowJSON = gridRowRecord;
					var unitprice = null;
					unitprice = scBaseUtils.getAttributeValue("LineOverallTotals.DisplayUnitPrice",false,gridRowJSON);
					if(!scBaseUtils.isVoid(updatedGridRecord) && !scBaseUtils.isVoid(updatedGridRecord.overridenUnitPrice) && 
							!scBaseUtils.isVoid(updatedGridRecord.overridenUnitPrice[0])){
						unitprice = updatedGridRecord.overridenUnitPrice[0];
					}
					unitprice = isccsOrderUtils.setPriceForLinePriceInformation(gridRowJSON, unitprice);
					if (unitprice != scBundleUtils.getString("Included")) {
						//Format currency since not supported on multiline cells.  Only do if not "Included".
						var screenInstance = gridReference.ownerScreen;
						var orderNamespace = "getCompleteOrderLineList_output";
						if (!(scBaseUtils.isVoid(screenInstance.lstOutputNamespace))) {
							orderNamespace = screenInstance.lstOutputNamespace;
						}
						var currencyDtlsFromOrder = scScreenUtils.getModel(screenInstance,orderNamespace);
						var countryCode = scBaseUtils.getAttributeValue("Order.PriceInfo.Currency", false, gridRowJSON);
						if (!countryCode) {
							countryCode =  scBaseUtils.getAttributeValue("OrderLineList.Order.PriceInfo.Currency",false,currencyDtlsFromOrder);
						}
						if(!countryCode){
							countryCode =  scBaseUtils.getAttributeValue("Page.Output.OrderLineList.Order.PriceInfo.Currency",false,currencyDtlsFromOrder);
						}
						if(!currencyDtlsFromOrder || !countryCode){
							orderNamespace = "getCompleteOrderDetails_output";
							if (!(scBaseUtils.isVoid(screenInstance.lstOrderNamespace))) {
								orderNamespace = screenInstance.lstOrderNamespace;
							}
							currencyDtlsFromOrder = scScreenUtils.getModel(screenInstance,orderNamespace);
							if(currencyDtlsFromOrder)
								countryCode = scBaseUtils.getAttributeValue("Order.PriceInfo.Currency",false,currencyDtlsFromOrder);
						}
											
						unitprice = dCurrency.format(unitprice,{currency:countryCode});
					}
					
					return unitprice;
				};
				
				orderLineUtils.getDeliveryStartDate = function(gridReference,rowIndex,colIndex,modelObj,unformattedVal,dataValue) {
					var inputArray = scBaseUtils.getNewArrayInstance();
					var carrierServiceCode = modelObj.CarrierServiceCode;
					var length = modelObj.CarrierServiceList.CarrierService.length;
					var carrierService = modelObj.CarrierServiceList.CarrierService;
					for(var i = 0;i<length;i++){
						if(scBaseUtils.stringEquals(carrierServiceCode,carrierService[i].CarrierServiceCode)){
							var formattedStartDate = scBaseUtils.formatDateToUserFormat(carrierService[i].DeliveryStartDate);
							scBaseUtils.appendToArray(inputArray,formattedStartDate);
							break;
						}
					}
					return dataValue;
				};
				
				orderLineUtils.getDeliveryEndDate = function(gridReference,rowIndex,colIndex,modelObj,unformattedVal,dataValue) {
					var inputArray = scBaseUtils.getNewArrayInstance();
					var carrierServiceCode = modelObj.CarrierServiceCode;
					var length = modelObj.CarrierServiceList.CarrierService.length;
					var carrierService = modelObj.CarrierServiceList.CarrierService;
					for(var i = 0;i<length;i++){
						if(scBaseUtils.stringEquals(carrierServiceCode,carrierService[i].CarrierServiceCode)){
							var formattedEndDate = scBaseUtils.formatDateToUserFormat(carrierService[i].DeliveryEndDate);
							scBaseUtils.appendToArray(inputArray,formattedEndDate);
							break;
						}
					}
					return dataValue;
				};
				
				orderLineUtils.getFormattedDateRangeAppeasement = function(bindingData,gridRowRecord,gridReference){
							var modelObj = gridRowRecord;
					var serverStartDate = scBaseUtils.getAttributeValue("ExpectedStartDate",false,modelObj);
					if (serverStartDate) {
						var formattedServerStartDate = scBaseUtils.formatDateToUserFormat(serverStartDate);
						var serverEndDate = scBaseUtils.getAttributeValue("ExpectedEndDate",false,modelObj);
						var formattedServerEndDate = scBaseUtils.formatDateToUserFormat(serverEndDate);
						var inputArray = scBaseUtils.getNewArrayInstance();
						var descField = null;
						if (scBaseUtils.equals(formattedServerEndDate, formattedServerStartDate)) {
							scBaseUtils.appendToArray(inputArray,formattedServerEndDate);
							descField = scBundleUtils.getFormattedString("ExpectedDateValue",inputArray);
						} else {
							scBaseUtils.appendToArray(inputArray,formattedServerStartDate);
							scBaseUtils.appendToArray(inputArray,formattedServerEndDate);
							descField = scBundleUtils.getFormattedString("ExpectedDateRangeValue",inputArray);
						}
						return descField;
					}
					return "";
                };
				
				orderLineUtils.getFormattedDateRange = function(bindingData,gridRowRecord,gridReference){
					var modelObj = gridRowRecord;
					var screenInstance = gridReference.ownerScreen;
					var ruleDetailsFromScreen = scScreenUtils.getModel(screenInstance,"getRuleDetails_output");
					var ruleValue = ruleDetailsFromScreen.Rules.RuleSetValue;
					var isBundleParent = gridRowRecord.IsBundleParent;
                    var fulfillmentMode = gridRowRecord.ItemDetails.PrimaryInformation.BundleFulfillmentMode;
                    var deliveryMethod = gridRowRecord.DeliveryMethod;
                    if((isccsDeliveryUtils.isServiceLine(gridRowRecord) || deliveryMethod == "DEL") && gridRowRecord.FormattedStartDate && gridRowRecord.FormattedEndDate){
                    	var formattedDate = scBaseUtils.formatDateToUserFormat(gridRowRecord.PromisedApptStartDate);
        				var input = [];
        				scBaseUtils.appendToArray(input, formattedDate);
        				scBaseUtils.appendToArray(input, isccsDeliveryUtils.formatTimeToUserFormat(gridRowRecord.FormattedStartDate,screenInstance));
        				scBaseUtils.appendToArray(input, isccsDeliveryUtils.formatTimeToUserFormat(gridRowRecord.FormattedEndDate,screenInstance));
        				return scScreenUtils.getFormattedString(screenInstance, "FormattedAppointment", input);
                    } else if (!(isBundleParent && isBundleParent === "Y" && fulfillmentMode && (fulfillmentMode === "00" || fulfillmentMode === "02"))) {
						var serverStartDate = scBaseUtils.getAttributeValue("ExpectedStartDate", false, modelObj);
						if (serverStartDate) {
							var formattedServerStartDate = scBaseUtils.formatDateToUserFormat(serverStartDate);
							var serverEndDate = scBaseUtils.getAttributeValue("ExpectedEndDate", false, modelObj);
							var formattedServerEndDate = scBaseUtils.formatDateToUserFormat(serverEndDate);
							var inputArray = scBaseUtils.getNewArrayInstance();
							var descField = null;
							if (scBaseUtils.equals(formattedServerEndDate, formattedServerStartDate)) {
								scBaseUtils.appendToArray(inputArray, formattedServerEndDate);
								descField = scBundleUtils.getFormattedString("ExpectedDateValue", inputArray);
							}
							else {
								scBaseUtils.appendToArray(inputArray, formattedServerStartDate);
								scBaseUtils.appendToArray(inputArray, formattedServerEndDate);
								descField = scBundleUtils.getFormattedString("ExpectedDateRangeValue", inputArray);
							}
							return descField;
						}
						else{
							return "";
						}
					}else{
							return "";
						}
				};
				
				orderLineUtils.getStringForSeeComponents = function(bindingData,gridRowRecord,gridReference){
					var modelObj = gridRowRecord;
					var screenInstance = gridReference.ownerScreen;
					var ruleDetailsFromScreen = scScreenUtils.getModel(screenInstance,"getRuleDetails_output");
					var ruleValue = ruleDetailsFromScreen.Rules.RuleSetValue;
					var isBundleParent = gridRowRecord.IsBundleParent;
                    var fulfillmentMode = gridRowRecord.ItemDetails.PrimaryInformation.BundleFulfillmentMode;
                    if (!(isBundleParent && isBundleParent === "Y" && fulfillmentMode && (fulfillmentMode === "00" || fulfillmentMode === "02"))) {
						return "";
					}
					else {
						if (ruleValue === "N") {
							return scScreenUtils.getString(screenInstance, "SeeComponents");
						}
						else {
							return "";
						}
					}
				};
				
				orderLineUtils.getTrackingNo = function(bindingData,gridRowRecord,gridReference) {
					var screen = gridReference.ownerScreen;
					var modelObj = gridRowRecord;
					if (modelObj.TrackingInfoList && modelObj.TrackingInfoList.TrackingInfo){
						var length = modelObj.TrackingInfoList.TrackingInfo.length;
						if (length > 1){
							return scScreenUtils.getString(screen, "Multiple");
						} else if (length < 1) {
							return "";
						} else {
							return modelObj.TrackingInfoList.TrackingInfo[0].TrackingNo;
						}
					}
					return "";
				};
				
				orderLineUtils.getStringForOrderNo = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord){
						return gridRowRecord.OrderNo;
					}
					return "";
				};
				
				orderLineUtils.getStringForDisplayUserID = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord){
						return gridRowRecord.DisplayUserID;
					}
					return "";
				};
				
				orderLineUtils.getStringForPullCart = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord){
						return scBundleUtils.getString("Pull");
					}
					return "";
				};
				
				orderLineUtils.getImageStyleforBundleParentItem = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};
					var isBundleParent = gridRowRecord.IsBundleParent;
					if(isBundleParent==="Y"){
						styleInfo.imageStyleClass = "icon-bundle indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageforBundleParentItem = function(dataItem, gridRowRecord, gridReference){
					var isBundleParent = gridRowRecord.IsBundleParent;
					if(isBundleParent==="Y"){
						return isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/bundleParent.png");
					}
					return "";
				};
				
				orderLineUtils.getImageStyleforBundleComponentItem = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};	
					if((gridRowRecord.BundleParentLine && gridRowRecord.BundleParentLine.OrderLineKey) ||
							gridRowRecord.ComponentItemID){
						styleInfo.imageStyleClass = "icon-bundleComponent indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforIsPickupAllowed = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};	
					var isPickupAllowedAtEnterprise=isccsContextUtils.isPickUpAllowedAtEnterprise();
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsPickupAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsPickupAllowed === "Y" &&isPickupAllowedAtEnterprise==="Y"){
						styleInfo.imageStyleClass = "icon-pickupallowed indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforIsShippingAllowed = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};	
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsShippingAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsShippingAllowed === "Y"){
						styleInfo.imageStyleClass = "icon-shippingallowed indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforIsDeliveryAllowed = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};	
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsDeliveryAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsDeliveryAllowed === "Y"){
						styleInfo.imageStyleClass = "icon-deliveryallowed indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				
				
				
				
				orderLineUtils.getToolTipDetailsForPickupRecipient = function(dataItem,gridRowRecord,gridReference,updatedGridRowRecord){
					var firstNameColList = scModelUtils.getModelListFromPath("FirstName",updatedGridRowRecord);
					var lastNameColList = scModelUtils.getModelListFromPath("LastName",updatedGridRowRecord);
					var phoneNoColList = scModelUtils.getModelListFromPath("PhoneNo",updatedGridRowRecord);
					var firstName = firstNameColList[0];
					var lastName = lastNameColList[0];
					var phoneNo = phoneNoColList[0];
					var returnValue = null;
					if(firstName){
						var argsList = [];
						argsList.push(firstName);
						argsList.push(lastName);
						argsList.push(phoneNo);
						returnValue = scScreenUtils.getFormattedString(gridReference.ownerScreen,"Cust_Name_Tooltip",argsList);
					}else{
						returnValue = scScreenUtils.getString(gridReference.ownerScreen,"CustomerPickItem");
					}
					return returnValue;
				};
				
				orderLineUtils.getAltforGiftOptions = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
                    var data = null;
					if(updatedGridRecord.GiftFlag && updatedGridRecord.GiftFlag[0]){
                        var giftFlag = updatedGridRecord.GiftFlag[0];
                        if (scBaseUtils.equals("Y", giftFlag)) {
							if (updatedGridRecord.GiftWrap && updatedGridRecord.GiftWrap[0] && scBaseUtils.equals("Y", updatedGridRecord.GiftWrap[0])) {
								return scBundleUtils.getString("GiftWrap");
							}
							else {
								return scBundleUtils.getString("GiftItem");
							}
						}
						}else{
                        	if (gridRowRecord.GiftFlag && scModelUtils.getBooleanValueFromPath("GiftFlag", gridRowRecord, false)) {
								if (gridRowRecord.GiftWrap && scModelUtils.getBooleanValueFromPath("GiftWrap", gridRowRecord, false)) {
									return scBundleUtils.getString("GiftWrap");
								}else {
									return scBundleUtils.getString("GiftItem");
								}
						}
						}

                    return "";
                };
                
                orderLineUtils.getAltForLoginId = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
                    var loginIDAlt = null;
                    loginIDAlt = scScreenUtils.getString(gridReference.ownerScreen, "alt_LoginId");
                    return loginIDAlt;
                },
                
                orderLineUtils.getAltForPullCart = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
                    var loginIDAlt = null;
                    loginIDAlt = scScreenUtils.getString(gridReference.ownerScreen, "alt_Pull");
                    return loginIDAlt;
                },
				
				orderLineUtils.getImageStyleforGiftOptions = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
                    var styleInfo = {};
                    var data = null;
                    if (!updatedGridRecord.GiftFlag) {
                        if (scModelUtils.getBooleanValueFromPath("GiftFlag", gridRowRecord, false)) {
							if (scModelUtils.getBooleanValueFromPath("GiftWrap", gridRowRecord, false)) {
								styleInfo.imageStyleClass = "icon-giftWrap indicatorImage";
							}else {
								styleInfo.imageStyleClass = "icon-giftItem indicatorImage";
							}
						}
                        else {
                            styleInfo.imageStyleClass = "hideImage";
                        }
                    }
                    else {
                        var giftFlag = updatedGridRecord.GiftFlag[0];
                        if (scBaseUtils.equals("Y", giftFlag)) {
                        	if (updatedGridRecord.GiftWrap && updatedGridRecord.GiftWrap[0] && scBaseUtils.equals("Y", updatedGridRecord.GiftWrap[0])) {
									styleInfo.imageStyleClass = "icon-giftWrap indicatorImage";
							}else{
									styleInfo.imageStyleClass = "icon-giftItem indicatorImage";
							}
						}
                        else {
                            styleInfo.imageStyleClass = "hideImage";
                        }
                    }
                    return styleInfo;
                };
				
				
				
                orderLineUtils.getImageStyleforPickupRecipient = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};
					/* if ((gridRowRecord.MarkForKey && gridRowRecord.MarkForKey[0]) ||(gridRowRecord.FirstName && gridRowRecord.FirstName[0]) ) {
					 	styleInfo.imageStyleClass = "icon-pickuprecipient";
					 }else if(gridRowRecord._dataItem.MarkForKey){
					 	styleInfo.imageStyleClass = "icon-pickuprecipient";
					 }
					 else{
					 	styleInfo.imageStyleClass ="";
					 }*/
					if (gridRowRecord.DeliveryMethod==="PICK"){
						styleInfo.imageStyleClass = "floatRight";
					}
					 return styleInfo;
				};
				
				orderLineUtils.getReturnReasonDesc = function(dataItem, gridRowRecord, gridReference, updatedGridRecord){
					if (updatedGridRecord && updatedGridRecord.ReasonCodeDesc && updatedGridRecord.ReasonCodeDesc[0]){
						if (updatedGridRecord.ReasonCodeDesc[0] === scBundleUtils.getString("Mandatory")){
							return scBundleUtils.getString("ReturnReasonMandatory");
						}
						return updatedGridRecord.ReasonCodeDesc[0];
					}
					return "";
				};
				
				orderLineUtils.getImageStyleMandatoryReasonCode = function(dataItem, gridRowRecord, gridReference, updatedGridRecord){
					var styleInfo = {};
					styleInfo.imageStyleClass = "indicatorImage";
					return styleInfo;
				};
				
				orderLineUtils.getImageMandatoryReasonCode = function(dataItem, gridRowRecord, gridReference, updatedGridRecord){
					if (updatedGridRecord && updatedGridRecord.ReasonCodeDesc && updatedGridRecord.ReasonCodeDesc[0] && updatedGridRecord.ReasonCodeDesc[0] === scBundleUtils.getString("Mandatory")){
						return isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/icon-error.png");
					}else{
						return isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/blank.gif");
					}
				};
								
				orderLineUtils.getImageStyleForMergeLines = function(dataItem, gridRowRecord, gridReference){
					var styleInfo = {};
					 if ((gridRowRecord && gridRowRecord.NoOfInteractions!="1") && (gridRowRecord.IsBundleParent==="Y" && gridRowRecord.ItemDetails.PrimaryInformation.BundleFulfillmentMode==="01")) {
					 	styleInfo.imageStyleClass = "indicatorImage floatRight";
					 }
					 else{
					 	styleInfo.imageStyleClass ="";
					 }
					 return styleInfo;
				};
				
				orderLineUtils.getImageStyleforRelatedItems = function(dataItem,gridRowRecord,gridReference,updatedGridRecord){
					
					var styleInfo = {};
					var data = updatedGridRecord;
					if(data && (data.ParentOrderLineKey && !scBaseUtils.isVoid(data.ParentOrderLineKey[0])) || (data.ParentTransactionLineID && !scBaseUtils.isVoid(data.ParentTransactionLineID[0]))
							|| (gridRowRecord && gridRowRecord.ParentOrderLineRelationships && gridRowRecord.ParentOrderLineRelationships.OrderLineRelationship && gridRowRecord.ParentOrderLineRelationships.OrderLineRelationship.ParentOrderLineKey)){
						styleInfo.imageStyleClass = "icon-relatedItem indicatorImage";
					}else{
						styleInfo.imageStyleClass = "hideImage";	
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforStopDelivery = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getNumberValueFromPath("StopDeliveryRequestDetails.TotalNumberOfRecords", data) > 0){
						styleInfo.imageStyleClass = "icon-StopDelivery indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforOpenBox = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("IsOpenBox", data, false)){
						styleInfo.imageStyleClass = "icon-openbox indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforReship = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getNumberValueFromPath("ReshippedQty", data) > 0){
						styleInfo.imageStyleClass = "icon-Reshipped indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforReturn = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("HasReturnLines", data, false)){
						styleInfo.imageStyleClass = "icon-Return indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
								
				orderLineUtils.getImageStyleforPriceMatched = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("IsPriceMatched", data, false)){
						styleInfo.imageStyleClass = "icon-PriceMatch indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforPriceOverridden = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("LinePriceInfo.IsPriceLocked", data, false)){
						styleInfo.imageStyleClass = "icon-PriceOverride indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforLineNotes = function (dataItem, gridRowRecord, gridReference) {
					var styleInfo = {};
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("HasNotes", data, false) ||
							(gridRowRecord.HasNotes && scBaseUtils.equals(gridRowRecord.HasNotes[0],"Y"))){						
						styleInfo.imageStyleClass = "icon-notes indicatorImage";
					} else {
						styleInfo.imageStyleClass = "hideImage";
					}
					return styleInfo;
				};			

				orderLineUtils.getImageStyleforLineValidationErrors = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					var styleInfo = {};
					var data = updatedGridRecord;
					var draftOrderFlag = scModelUtils.getStringValueFromPath("Order.DraftOrderFlag",gridRowRecord);
					styleInfo.imageStyleClass = "";
					if(draftOrderFlag && draftOrderFlag === "N"){
						// vaidation error icon to be shown only for new lines in add lines flow.
						if(isccsOrderUtils.isNewLine(gridReference.ownerScreen,gridRowRecord,draftOrderFlag) && 
								!scBaseUtils.isVoid(data.Errors) && !scBaseUtils.isVoid(data.Errors[0])){							
							styleInfo.imageStyleClass = "indicatorImage";
							var rowIndex = scGridUtils.getRowIdFromRowJson(gridReference.ownerScreen,"OLST_listGrid",updatedGridRecord)
							scGridUtils.applyCssToRow(gridReference.ownerScreen,"OLST_listGrid",rowIndex,"gridErrorRow");
						}
					}
					else if(!scBaseUtils.isVoid(data.Errors) && !scBaseUtils.isVoid(data.Errors[0])){
						styleInfo.imageStyleClass = "indicatorImage";
						var rowIndex = scGridUtils.getRowIdFromRowJson(gridReference.ownerScreen,"OLST_listGrid",updatedGridRecord)
						scGridUtils.applyCssToRow(gridReference.ownerScreen,"OLST_listGrid",rowIndex,"gridErrorRow");
					}else{
						styleInfo.imageStyleClass = "";
					}
					return styleInfo;
				};
				
				orderLineUtils.getImageStyleforReturnViolations = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					var styleInfo = {};										
					if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
						if (orderLineUtils.isReturnPolicyOverridden(gridRowRecord, updatedGridRecord)){
							styleInfo.imageStyleClass = "icon-ReturnPolicyOverride indicatorImage";
						} else {
							styleInfo.imageStyleClass = "icon-NotReturnable indicatorImage";
						}
						
					}else{
						styleInfo.imageStyleClass = "";
					}
					return styleInfo;
				};
				
				orderLineUtils.getCellImageForReturnViolations = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					var imageUrl = "";										
					if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
						if (orderLineUtils.isReturnPolicyOverridden(gridRowRecord, updatedGridRecord)){
							imageUrl = isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/returnPolicyOverridden.png");
						} else {
							imageUrl = isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/icon-not-returnable.png");
						}
						
					}else{
						imageUrl = isccsUIUtils.getFullURLForImage("isccs/resources/css/icons/images/blank.gif");
					}
					return imageUrl;
				};
				
				orderLineUtils.getAltforReturnViolations = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
						if (orderLineUtils.isReturnPolicyOverridden(gridRowRecord, updatedGridRecord)){
			    			return scBundleUtils.getString("ReturnPolicyOverridden");
			    		} else {
			    			return scBundleUtils.getString("ValidationErrors");
			    		}
					}
					return "";
				};

				orderLineUtils.getAltforHoldExceptions = function (dataValue,rowIndex,cellObj,gridRowRecord) {
					if (!scBaseUtils.isVoid(scModelUtils.getStringValueFromPath("ExceptionKey", gridRowRecord, true))) {
						return scBundleUtils.getString("alt_inboxKey");
					} else {
						return "";
					}
				};
				
				orderLineUtils.getTextStyleforReturnViolations = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					var styleInfo = {};										
					if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
						if (orderLineUtils.isReturnPolicyOverridden(gridRowRecord, updatedGridRecord)){
							styleInfo.textStyle = "";
						} else {
							styleInfo.textStyle = "highLightRed imageAlignedText";
						}
					}else{
						styleInfo.textStyle = "";
					}
					return styleInfo;
				};
				
				orderLineUtils.getReturnReasonStyle = function (dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					var styleInfo = {};
					if (updatedGridRecord && updatedGridRecord.ReasonCodeDesc && updatedGridRecord.ReasonCodeDesc[0]){
						if (updatedGridRecord.ReasonCodeDesc[0] === scBundleUtils.getString("Mandatory")){
							styleInfo.textStyle = "highLightRed imageAlignedText";
						} else {
							styleInfo.textStyle = "";
						}
					}else{
						styleInfo.textStyle = "";
					}
					return styleInfo;
				};
				
				
				orderLineUtils.getReturnPolicyViolationMessage = function(dataItem, gridRowRecord, gridReference,updatedGridRecord) {
			    	if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
			    		if (orderLineUtils.isReturnPolicyOverridden(gridRowRecord, updatedGridRecord)){
			    			return ""; //scBundleUtils.getString("ReturnPolicyOverridden");
			    		} else {
			    			var list = scModelUtils.getModelListFromPath("ReturnPolicyViolations.ReturnPolicyViolation", gridRowRecord);
				    		if(!scBaseUtils.isVoid(list)){
				    			if (list.length > 1){
					    			return scBundleUtils.getString("MultipleReturnViolations");
					    		} else if (list.length == 1){
					    			return list[0].MessageCodeDesc;
					    		}
				    		}
			    		}
			    	}
			    	return "";
			    };
			    
			    orderLineUtils.isReturnPolicyOverridden = function (gridRowRecord, updatedGridRecord){
			    	if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
			    		if (gridRowRecord.DerivedFromOrderLine && gridRowRecord.DerivedFromOrderLine.OrderLineKey){
			    			return true;
			    		} else if (updatedGridRecord.ReasonCode && updatedGridRecord.ReasonCode[0]){
			    			return true;
			    		} else if (updatedGridRecord.ReturnOrderQuantity && updatedGridRecord.ReturnOrderQuantity[0] && updatedGridRecord.ReturnOrderQuantity[0] > 0){
			    			return true;
			    		}
			    	}
			    	return false;
			    };
				
			
                orderLineUtils.getAltforPickupRecipient = function(dataItem, gridRowRecord, gridReference){
					if (gridRowRecord.DeliveryMethod==="PICK"){
						return scBundleUtils.getString("Pickup_Product_At_Store");
					}
					 return "";
				};
				
				orderLineUtils.getAltForMergeLines = function(dataItem, gridRowRecord, gridReference){
					 if ((gridRowRecord && gridRowRecord.NoOfInteractions!="1")) {
						 return scBundleUtils.getString("Merged_Lines");
					 }
					 return "";
				};
				
				orderLineUtils.getAltforRelatedItems = function(dataItem,gridRowRecord,gridReference, updatedGridRecord){
					var data = updatedGridRecord;
					if(data && (data.ParentOrderLineKey && !scBaseUtils.isVoid(data.ParentOrderLineKey[0])) || (data.ParentTransactionLineID && !scBaseUtils.isVoid(data.ParentTransactionLineID[0]))
							|| (gridRowRecord && gridRowRecord.ParentOrderLineRelationships && gridRowRecord.ParentOrderLineRelationships.OrderLineRelationship && gridRowRecord.ParentOrderLineRelationships.OrderLineRelationship.ParentOrderLineKey)){
						return scBundleUtils.getString("RelatedLine");
					}
					return ""; 
				};
								
				orderLineUtils.getAltForOrderLineUpdate = function(dataItem,gridRowRecord,gridReference){					
					return scBundleUtils.getString("OrderLineUpdated");					
				};
				
				orderLineUtils.getAltForNewLineIndicator = function(dataItem,gridRowRecord,gridReference){					
					if(gridRowRecord && gridReference){
						var draftOrderFlag = scModelUtils.getStringValueFromPath("Order.DraftOrderFlag",gridRowRecord);
						if(isccsOrderUtils.isNewLine(gridReference.ownerScreen,gridRowRecord,draftOrderFlag)){
							return scBundleUtils.getString("NewLine");
						}
					}
					return "";
				};
				
				orderLineUtils.getAltforStopDelivery = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getNumberValueFromPath("StopDeliveryRequestDetails.TotalNumberOfRecords", data) > 0){
						return scBundleUtils.getString("StopDeliveryRequest");
					} 
					return "";
				};
				
				orderLineUtils.getAltforOpenBox = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("IsOpenBox", data, false)){
						return scBundleUtils.getString("OpenBox");
					}
					return "";
				};
				
				orderLineUtils.getAltforReship = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getNumberValueFromPath("ReshippedQty", data) > 0){
						return scBundleUtils.getString("ReshippedLine");
					}
					return "";
				};
				
				
				orderLineUtils.getAltforReturn = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("HasReturnLines", data, false)){
						return scBundleUtils.getString("Returned_Line");
					} 
					return "";
				};
								
				orderLineUtils.getAltforPriceMatched = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("IsPriceMatched", data, false)){
						return scBundleUtils.getString("Price_Matched_Line");
					} 
					return "";
				};
				
				orderLineUtils.getAltforPriceOverridden = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("LinePriceInfo.IsPriceLocked", data, false)){
						return scBundleUtils.getString("Price_Overridden");
					} 
					return "";
				};
				
				orderLineUtils.getAltforLineNotes = function (dataItem, gridRowRecord, gridReference) {
					var data = gridRowRecord;
					if (scModelUtils.getBooleanValueFromPath("HasNotes", data, false) ||
							(gridRowRecord.HasNotes && scBaseUtils.equals(gridRowRecord.HasNotes[0],"Y"))){						
						return scBundleUtils.getString("Has_Line_Notes");
					} 
					return "";
				};				
				
				orderLineUtils.getAltforLineValidationErrors = function (dataItem, gridRowRecord, gridReference) {
					if(scModelUtils.getBooleanValueFromPath("ReturnPolicyViolations.HasViolations", gridRowRecord, false)){
						return scBundleUtils.getString("ProductCannotBeReturned");
					}
					if(scModelUtils.getModelListFromPath("Errors.Error",gridRowRecord) != null){
						return scBundleUtils.getString("LineValidationErrors");
					}
						
					return "";
				};
					
					
				orderLineUtils.getAltforDeliveryDetail = function(dataItem, gridRowRecord, gridReference){
					var deliveryMethod = gridRowRecord.DeliveryMethod;
					if(deliveryMethod==="SHP"){
						return scBundleUtils.getString("Shipping");
					}else{
						return scBundleUtils.getString("Pickup");
					}
					return "";
				};			
				orderLineUtils.getAltforBundleParentItem = function(dataItem, gridRowRecord, gridReference){
					var isBundleParent = gridRowRecord.IsBundleParent;
					if(isBundleParent==="Y"){
						return scBundleUtils.getString("BundleParent");
					}
					return "";
				};
				
				orderLineUtils.getAltforBundleComponentItem = function(dataItem, gridRowRecord, gridReference){
					if((gridRowRecord.BundleParentLine && gridRowRecord.BundleParentLine.OrderLineKey) || gridRowRecord.ComponentItemID){
						return scBundleUtils.getString("BundleComponent");
					}
					return "";
				};
				
				orderLineUtils.getAltforIsPickupAllowed = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsPickupAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsPickupAllowed === "Y"){
						return scBundleUtils.getString("pickup_allowed");
					}
					return "";
				};
				
				orderLineUtils.getAltforIsShippingAllowed = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsShippingAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsShippingAllowed === "Y"){
						return scBundleUtils.getString("ShippingAllowed");
					}
					return "";
				};
				
				orderLineUtils.getAltforIsDeliveryAllowed = function(dataItem, gridRowRecord, gridReference){
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation && gridRowRecord.ItemDetails.PrimaryInformation.IsDeliveryAllowed && gridRowRecord.ItemDetails.PrimaryInformation.IsDeliveryAllowed === "Y"){
						return scBundleUtils.getString("DeliveryAllowed");
					}
					return "";
				};
				
				orderLineUtils.getAltForDeleteOrderLine = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
					return scBundleUtils.getString("DeleteOrderLine");
				};

				orderLineUtils.getAltForDeleteReturnLine = function(dataItem, gridRowRecord, gridReference,updatedGridRecord){
					return scBundleUtils.getString("DeleteReturnLine");
				};

				orderLineUtils.getAltForOriginalOrderNo = function(){
					return scBundleUtils.getString("Alt_OriginalOrderNo");
				};
				
				orderLineUtils.getEditorInputForOrderLine = function(order, screen){
					var model = scScreenUtils.getInitialInputData(screen);
					if(order.Order.OrderLineKey){
						var editorInput = {Order:{}};
						model.Order.OrderLineKey = order.Order.OrderLineKey;
						editorInput = orderLineUtils.updateOrderAttributes(editorInput, model);
						return editorInput;
					}					
					return order;
				};
				
				orderLineUtils.getOrderLineModification = function(orderLine, modificationType){
					var eModification = null;
					var modAllowed = "Y";
					eModification = scModelUtils.getModelListFromPath("Modifications.Modification", orderLine);
					var length = scBaseUtils.getAttributeCount(eModification);
					for (var index = 0; index < length; index++) {
						var modification = scModelUtils.getModelFromList(eModification, index);
						if (scBaseUtils.equals(scModelUtils.getStringValueFromPath("ModificationType", modification), modificationType)) {
							modAllowed = scModelUtils.getStringValueFromPath("ModificationAllowed", modification);
						}
					}
					return modAllowed;
				};
				
				orderLineUtils.updateOrderAttributes = function(modelToUpdate,order){
					modelToUpdate.Order.EnterpriseCode = order.Order.EnterpriseCode;
					modelToUpdate.Order.DocumentType = order.Order.DocumentType;
					modelToUpdate.Order.OrderHeaderKey = order.Order.OrderHeaderKey;
					modelToUpdate.Order.OrderNo = order.Order.OrderNo;
					modelToUpdate.Order.EntryType = order.Order.EntryType;
					modelToUpdate.Order.SellerOrganizationCode = order.Order.SellerOrganizationCode;
					if(order.Order.OrderLineKey)
						modelToUpdate.Order.OrderLineKey = order.Order.OrderLineKey;
					
					if(order.Order.CustomerFirstName)
						modelToUpdate.Order.CustomerFirstName = order.Order.CustomerFirstName;

					if(order.Order.CustomerLastName)
						modelToUpdate.Order.CustomerLastName = order.Order.CustomerLastName;

					if(order.Order.CustomerType)
						modelToUpdate.Order.CustomerType = order.Order.CustomerType;
					
					if(order.Order.BuyerUserId)
						modelToUpdate.Order.BuyerUserId = order.Order.BuyerUserId;
					
					if(order.Order.DraftOrderFlag)
						modelToUpdate.Order.DraftOrderFlag = order.Order.DraftOrderFlag;

					if(order.Order.CustomerContactID)
						modelToUpdate.Order.CustomerContactID = order.Order.CustomerContactID;

					if(order.Order.BillToID)
						modelToUpdate.Order.BillToID = order.Order.BillToID;

					if(order.Order.BuyerOrganizationCode)
						modelToUpdate.Order.BuyerOrganizationCode = order.Order.BuyerOrganizationCode;
					
					if(order.Order.isHistory)
						modelToUpdate.Order.isHistory = order.Order.isHistory;
						
					if(order.Order.OrderName)
						modelToUpdate.Order.OrderName = order.Order.OrderName;					
					
					if(order.Order.PriceInfo && order.Order.PriceInfo.Currency) {
						modelToUpdate.Order.PriceInfo = {};
						modelToUpdate.Order.PriceInfo.Currency = order.Order.PriceInfo.Currency;
					}
					
					if(order.Order.CustomerPONo){
						modelToUpdate.Order.CustomerPONo = order.Order.CustomerPONo;
					}
					
					if(order.Order.TaxExemptFlag){
						modelToUpdate.Order.TaxExemptFlag = order.Order.TaxExemptFlag;
					}
					
					if(order.Order.TaxExemptionCertificate){
						modelToUpdate.Order.TaxExemptionCertificate = order.Order.TaxExemptionCertificate;
					}
					if(order.Order.Storefront){
						modelToUpdate.Order.Storefront = order.Order.Storefront;
					}

					return modelToUpdate;
				};
				orderLineUtils.getAltForUnitPrice = function(){
					return scBundleUtils.getString("ComponentUnitPriceAlt");
				};
				orderLineUtils.getAltForExpectedOn = function(){
					return scBundleUtils.getString("LineExpectedOnAlt");
				};
				orderLineUtils.getAltForPrimeLineNo = function(){
					return scBundleUtils.getString("alt_primelineno");
				};
				orderLineUtils.getAltForOrderNo = function(){
					return scBundleUtils.getString("alt_OrderNo");
				};
				orderLineUtils.getAltForCompetitorName = function(){
					return scBundleUtils.getString("alt_CompetitorName");
				};
				orderLineUtils.getAltForWebAddress = function(){
					return scBundleUtils.getString("alt_WebAddress");
				};
				orderLineUtils.getAltForManagePriceMatch = function(){
					return scBundleUtils.getString("alt_ManagePriceMatch");
				};
				orderLineUtils.getAltForInvoiceLines = function(){
					return scBundleUtils.getString("alt_InvoiceLines");
				};
				orderLineUtils.getAltForOrderNoLink = function(dataValue,rowIndex,cellObj,gridRowRecord){
					if(scBaseUtils.equals("Y", scModelUtils.getStringValueFromPath("DraftOrderFlag", gridRowRecord, false))){
						return scBundleUtils.getString("alt_draftOrderNo");
					}else{
						return scBundleUtils.getString("alt_OrderNo");
					}
				};
				
				orderLineUtils.getOrderLineItemImageLocation = function(dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation){
						return gridRowRecord.ItemDetails.PrimaryInformation.ImageLocation;
					}
					else if(gridRowRecord.Item && gridRowRecord.Item.PrimaryInformation){
						return gridRowRecord.Item.PrimaryInformation.ImageLocation;
					}
				};
				
				orderLineUtils.getOrderLineItemImageId = function(dataItem, gridRowRecord, gridReference,updatedGridRecord) {
					if(gridRowRecord.ItemDetails && gridRowRecord.ItemDetails.PrimaryInformation){
						return gridRowRecord.ItemDetails.PrimaryInformation.ImageID;
					}
					else if(gridRowRecord.Item && gridRowRecord.Item.PrimaryInformation){
						return gridRowRecord.Item.PrimaryInformation.ImageID;
					}					
				};

				orderLineUtils.getAltForAvailability = function (dataItem, gridRowRecord, gridReference) {
					if (scBaseUtils.equals(false, scModelUtils.getBooleanValueFromPath("Availability.IsAvailable", gridRowRecord, false))) {
						return scBundleUtils.getString("Alt_variation_unavailable");
					} else {
						return scBundleUtils.getString("Alt_variation_available");
					}
				};

				orderLineUtils.getAltForPaymentMethod = function(dataItem,gridRowRecord,gridReference){
					var data = gridRowRecord;
					var paymentTypeGroup = scModelUtils.getStringValueFromPath("PaymentTypeGroup", data);
					if (paymentTypeGroup==="CREDIT_CARD") {
						return scBundleUtils.getString("Alt_CREDIT_CARD");
					}
					else if (paymentTypeGroup==="STORED_VALUE_CARD") {
						return scBundleUtils.getString("Alt_STORED_VALUE_CARD");
					}
					else if (paymentTypeGroup==="CUSTOMER_ACCOUNT") {
						return scBundleUtils.getString("Alt_CUSTOMER_ACCOUNT");
					}
					else {
						return scBundleUtils.getString("Alt_other_payment_method");
					}
					return "";
				};

				orderLineUtils.getAltForEmail = function(dataItem,gridRowRecord,gridReference){
					return scBundleUtils.getString("Email");
				};

				orderLineUtils.getAltForPhone = function(dataItem,gridRowRecord,gridReference){
					return scBundleUtils.getString("Phone_No");
				};
				
				orderLineUtils.getAltforPriceMatchStatus = function(dataItem,gridRowRecord,gridReference){
					var data = gridRowRecord;
					var status = scModelUtils.getStringValueFromPath("Status", data);
					if (status==="APPROVED") {
						return scBundleUtils.getString("Approved");
					}
					else if (status==="REJECTED") {
						return scBundleUtils.getString("Rejected");
					}
					return "" ;
				};

				orderLineUtils.getAltforMandatoryReasonCode = function(dataItem, gridRowRecord, gridReference, updatedGridRecord){
					if (updatedGridRecord && updatedGridRecord.ReasonCodeDesc && updatedGridRecord.ReasonCodeDesc[0] && updatedGridRecord.ReasonCodeDesc[0] === scBundleUtils.getString("Mandatory")){
						return scBundleUtils.getString("ReturnReasonMandatory");
					}else{
						return "";
					}
				};
				
				var indicatorFunctionMap = {
					'IsRelatedItem':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForRelatedItems, 'stylingInfoFunc':orderLineUtils.getImageStyleforRelatedItems, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforRelatedItems},
					'GiftFlag':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForGiftOptions, 'stylingInfoFunc':orderLineUtils.getImageStyleforGiftOptions, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforGiftOptions},
					'StopDeliveryRequestDetails.TotalNumberOfRecords':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForStopDelivery, 'stylingInfoFunc':orderLineUtils.getImageStyleforStopDelivery, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforStopDelivery},
					'IsOpenBox':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForOpenBox, 'stylingInfoFunc':orderLineUtils.getImageStyleforOpenBox, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforOpenBox},
					'HasReturnLines':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForReturn, 'stylingInfoFunc':orderLineUtils.getImageStyleforReturn, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforReturn},
					'ReshippedQty':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForReship, 'stylingInfoFunc':orderLineUtils.getImageStyleforReship, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforReship},
					'IsPriceMatched':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForPriceMatched, 'stylingInfoFunc':orderLineUtils.getImageStyleforPriceMatched, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforPriceMatched},
					'LinePriceInfo.IsPriceLocked':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForPriceOverridden, 'stylingInfoFunc':orderLineUtils.getImageStyleforPriceOverridden, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforPriceOverridden},
					'HasNotes':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForLineNotes, 'stylingInfoFunc':orderLineUtils.getImageStyleforLineNotes, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforLineNotes},
					'IsBundleParent':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForBundleParentItem, 'stylingInfoFunc':orderLineUtils.getImageStyleforBundleParentItem, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforBundleParentItem},
					'IsBundleComponent':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForBundleComponentItem, 'stylingInfoFunc':orderLineUtils.getImageStyleforBundleComponentItem, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforBundleComponentItem},
					'IsPickupAllowed':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForPickupAllowed, 'stylingInfoFunc':orderLineUtils.getImageStyleforIsPickupAllowed, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforIsPickupAllowed},
					'IsShippingAllowed':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForShippingAllowed, 'stylingInfoFunc':orderLineUtils.getImageStyleforIsShippingAllowed, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforIsShippingAllowed},
					'IsDeliveryAllowed':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForDeliveryAllowed, 'stylingInfoFunc':orderLineUtils.getImageStyleforIsDeliveryAllowed, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforIsDeliveryAllowed},
					'ReturnPolicyViolations.HasViolations':{'dynamicBindingFunc':orderLineUtils.getCellImageForReturnViolations, 'stylingInfoFunc':orderLineUtils.getImageStyleforReturnViolations, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforReturnViolations},
					'OrderLineUpdated':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForOrderLineUpdateIcon, 'stylingInfoFunc':isccsOrderUtils.getImageStyleForOrderLineUpdateIcon, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForOrderLineUpdate},
					'Errors':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForLineValidationErrors, 'stylingInfoFunc':orderLineUtils.getImageStyleforLineValidationErrors, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforLineValidationErrors},
					'NewLineIndicator':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForNewLineIndicator, 'stylingInfoFunc':isccsOrderUtils.handleNewLineIndicatorForLine, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForNewLineIndicator},
					'PickupRecipient':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForPickupRecipient, 'stylingInfoFunc':orderLineUtils.getImageStyleforPickupRecipient, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforPickupRecipient},
					'NoOfShipments':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForMergeLines, 'stylingInfoFunc':orderLineUtils.getImageStyleForMergeLines, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForMergeLines},
					'Status':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForHoldIcon, 'stylingInfoFunc':isccsOrderUtils.getImageStyleForHoldIcon, 'tooltipDynamicBindingFunc':isccsOrderUtils.getAltForHoldIcon},
					'DeliveryDetails':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForFulfillment, 'stylingInfoFunc':orderLineUtils.getImageStyleforFulfillment, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforDeliveryDetail},
					'PrimeLineNoFS':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForNewLineIndicator, 'stylingInfoFunc':isccsOrderUtils.handleNewLineIndicatorForLine, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForNewLineIndicator},
					'Availability':{'dynamicBindingFunc':isccsOrderUtils.getAvailabilityImage, 'stylingInfoFunc':isccsOrderUtils.getAvailabilityImageStyle},
					'SavedPaymentMethods':{'dynamicBindingFunc':isccsOrderUtils.getPaymentMethodImage, 'stylingInfoFunc':isccsOrderUtils.getPaymentMethodImageStyle, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForPaymentMethod},
					'EmailField':{'dynamicBindingFunc':isccsOrderUtils.getImageForEmail, 'stylingInfoFunc':isccsOrderUtils.getImageStyleForEmail, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForEmail},
					'PhoneField':{'dynamicBindingFunc':isccsOrderUtils.getImageForPhone, 'stylingInfoFunc':isccsOrderUtils.getImageStyleForPhone, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForPhone},
					'PriceMatchStatus':{'dynamicBindingFunc':isccsOrderUtils.getCellImageForPriceMatch, 'stylingInfoFunc':orderLineUtils.getImageStyleforStopDelivery, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforPriceMatchStatus},
					'ReturnReason':{'dynamicBindingFunc':orderLineUtils.getImageMandatoryReasonCode, 'stylingInfoFunc':orderLineUtils.getImageStyleMandatoryReasonCode, 'tooltipDynamicBindingFunc':orderLineUtils.getAltforMandatoryReasonCode}
				  };
				
				orderLineUtils.getImageTag = function(dataItem, gridRowRecord, gridReference,updatedGridRecord,columnIdentifier) {
					var functions = indicatorFunctionMap[columnIdentifier];
					var src, imageClass, tooltip = "";
					if(functions){
						if (functions.dynamicBindingFunc) {
							src = functions.dynamicBindingFunc(dataItem, gridRowRecord, gridReference, updatedGridRecord);
						}
						if (functions.stylingInfoFunc) {
							imgclass = functions.stylingInfoFunc(dataItem, gridRowRecord, gridReference, updatedGridRecord);
						}
						if (functions.tooltipDynamicBindingFunc) {
							tooltip = functions.tooltipDynamicBindingFunc(dataItem, gridRowRecord, gridReference, updatedGridRecord);
						}
						if(src.indexOf("/blank.gif") == -1)
							return '<img src="'+src+'" alt="'+tooltip+'" class="'+imgclass.imageStyleClass+'" title="'+tooltip+'">';
						else
							return "";
					}
					return "";
				}
				
				orderLineUtils.getProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					//ExtnParentLineSeqNo is present and not equal to PrimeLineNo set padding
					var sExtnParentLineSeqNo = scModelUtils.getStringValueFromPath("Extn.ExtnParentLineSeqNo",gridRowJSON);
					var sPrimeLineNo = scModelUtils.getStringValueFromPath("PrimeLineNo",gridRowJSON);
					if(sExtnParentLineSeqNo && !scBaseUtils.equals(sExtnParentLineSeqNo,sPrimeLineNo)){
						str += '<div style = "padding-left:20px" class="productImageDesc">';
					}else{
						str += '<div class="productImageDesc">';	
					}
					if(isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = orderLineUtils.getOrderLineItemImageLocation(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						var imageID = orderLineUtils.getOrderLineItemImageId(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage" alt="">';
					}
					
					var desc = isccsOrderUtils.getModifiedExtendedDisplayDesc(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					if(sExtnParentLineSeqNo && !scBaseUtils.equals(sExtnParentLineSeqNo,sPrimeLineNo)){
						//Grey out the childs present in the grid.
						str += '<span style = "color:gray" >'+ dHtmlEntities.encode(desc) +' </span>';	
					}else if(sExtnParentLineSeqNo && scBaseUtils.equals(sExtnParentLineSeqNo,sPrimeLineNo)){
						//if childs are present in the grid then bold the font
						str += '<span style = "font-weight: bold" >'+ dHtmlEntities.encode(desc) +' </span>';
					}else{
						//regular flow
						str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					}
					str += '</div>';
					
					var imageTags = "";
					var columnData = ['IsRelatedItem','GiftFlag','StopDeliveryRequestDetails.TotalNumberOfRecords','IsOpenBox',
					                  'HasReturnLines','ReshippedQty','IsPriceMatched','LinePriceInfo.IsPriceLocked','HasNotes','IsBundleParent',
					                  'IsBundleComponent','IsPickupAllowed','IsShippingAllowed','IsDeliveryAllowed'];
					
					for (var i = 0; i < columnData.length; i++) {
						imageTags += orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,columnData[i]);
					}
					
					if(!scBaseUtils.isVoid(imageTags)){
						str += '<div class="productIndicatorIcons">';
						str += imageTags;
						str += '</div>';
					}
					
					str += '</div>';
					return str;
				};
				
				orderLineUtils.getProductHTMLForReturn = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					str += '<div class="productImageDesc">';
					if(isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = orderLineUtils.getOrderLineItemImageLocation(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						var imageID = orderLineUtils.getOrderLineItemImageId(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage" alt="">';
					}
					
					var desc = isccsOrderUtils.getModifiedExtendedDisplayDesc(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					
					var imageTags = "";
					var columnData = ['IsRelatedItem','GiftFlag','StopDeliveryRequestDetails.TotalNumberOfRecords','IsOpenBox','HasReturnLines','ReshippedQty','IsPriceMatched','LinePriceInfo.IsPriceLocked','HasNotes'];
					
					for (var i = 0; i < columnData.length; i++) {
						imageTags += orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,columnData[i]);
					}
					
					if(!scBaseUtils.isVoid(imageTags)){
						str += '<div class="productIndicatorIcons">';
						str += imageTags;
						str += '</div>';
					}
					
					var violationTags = "";
					violationTags += orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'ReturnPolicyViolations.HasViolations');
					violationTags += '<span '+'class='+orderLineUtils.getTextStyleforReturnViolations(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord)+'>'+ dHtmlEntities.encode(orderLineUtils.getReturnPolicyViolationMessage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord)) +' </span>';
					
					if(!scBaseUtils.isVoid(violationTags)){
						str += '<div class="productViolationIcons">';
						str += violationTags;
						str += '</div>';
					}
					
					str += '</div>';
					return str;
				};
				
				orderLineUtils.getHTMLForAddItemsLineNo = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					
					var desc = gridRowJSON.PrimeLineNo;
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					
					var imageTags = "";
					var columnData = ['OrderLineUpdated','Errors','NewLineIndicator'];
					
					for (var i = 0; i < columnData.length; i++) {
						imageTags += orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,columnData[i]);
					}
					
					str += imageTags;
					
					str += '</div>';
					return str;
				};
				
				orderLineUtils.getSimpleProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div class="fulfillmentAddress">';
					var desc = gridRowJSON.ItemDetails.PrimaryInformation.ExtendedDisplayDescription;
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					str += '<div>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'PickupRecipient');
					str += imageTags;
					str += '</div>';
					return str;
				};

				orderLineUtils.getNoOfShipmentsHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					//var desc = gridReference.ownerScreen.getNumberOfInteraction(gridReference, gridRowJSON, columnIndex, gridRowJSON, unformattedValue);
					var desc = gridRowJSON.NoOfInteractions;
					if(!desc){
						desc="";
					}
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'NoOfShipments');
					str += imageTags;
					str += '</div>';
					return str;
				};

				orderLineUtils.getStatusHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var desc = gridRowJSON.DisplayStatus;
					if(scBaseUtils.isVoid(desc)){
						desc="";
					}
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'Status');
					str += imageTags;
					str += '</div>';
					return str;
				};
				
				
				orderLineUtils.getFSStatusHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var desc = gridRowJSON.DisplayStatus;
					if(scBaseUtils.isVoid(desc)){desc = gridRowJSON.Status};
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'Status');
					str += imageTags;
					str += '</div>';
					return str;
				};

				orderLineUtils.getDeliveryDetailsHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div class="fulfillmentAddress">';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'DeliveryDetails');
					str += imageTags;
					str += '</div>';
					str += '<div>';
					var desc = gridReference.ownerScreen.getFulfillmentDetailsForOrderLine(gridRowRecord, gridRowJSON, gridReference, colConfig, unformattedValue);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getPrimeLineNoFSHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var desc = gridRowJSON.PrimeLineNo;
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'PrimeLineNoFS');
					str += imageTags;
					str += '</div>';
					return str;
				};

				orderLineUtils.getAvailabilityHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'Availability');
					str += imageTags;
					var desc = gridReference.ownerScreen.getNodeAvailability(colConfig.bindingData, gridRowJSON, gridReference, unformattedValue);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getSavedPaymentMethodsHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'SavedPaymentMethods');
					str += imageTags;
					var desc = gridReference.ownerScreen.getPaymentTypeDisplay(colConfig.bindingData, gridRowJSON, gridReference);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getCustomerDetailsHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var billToId = gridRowJSON.BillToID;
					if(!scBaseUtils.isVoid(billToId)){
						str += '<span class="blockComponent">'+ billToId +' </span>';
						}
					var desc = gridReference.ownerScreen.customerNameConcat(colConfig.bindingData, gridRowJSON, gridReference);
					str += '<span class="blockComponent">'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getComponentProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					if(isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = gridRowJSON.Item.PrimaryInformation.ImageLocation;
						var imageID = gridRowJSON.Item.PrimaryInformation.ImageID;
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage">';
					}
					
					var desc = gridReference.ownerScreen.getItemDescription(colConfig.bindingData, gridRowJSON, gridReference);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getBundleProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					if(isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = orderLineUtils.getOrderLineItemImageLocation(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						var imageID = orderLineUtils.getOrderLineItemImageId(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage" alt="">';
					}
					
					var desc = gridReference.ownerScreen.getExtendedDisplayDescBinding(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					var imageTags = "";
					var columnData = ['IsBundleParent','IsBundleComponent'];
					
					for (var i = 0; i < columnData.length; i++) {
						imageTags += orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,columnData[i]);
					}
					
					if(!scBaseUtils.isVoid(imageTags)){
						str += '<div>';
						str += imageTags;
						str += '</div>';
					}
					
					return str;
				};

				orderLineUtils.getCreateOrderBusinessCustomerHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var custId = gridRowJSON.CustomerID;
					str += '<span class="blockComponent">'+ dHtmlEntities.encode(custId) +' </span>';
					var buyerOrgCode = gridRowJSON.BuyerOrganization.OrganizationName;
					str += '<span class="blockComponent">'+ dHtmlEntities.encode(buyerOrgCode) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getCreateOrderConsumerCustomerHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div class="fulfillmentAddress">';
					var custId = gridRowJSON.CustomerID;
					str += '<span class="blockComponent">'+ dHtmlEntities.encode(custId) +' </span>';
					str += '</div>';
					str += '<div class="fulfillmentAddress">';
					var custName = gridReference.ownerScreen.getNameDisplay(colConfig.bindingData,gridRowJSON,gridReference);
					str += '<span class="blockComponent">'+ dHtmlEntities.encode(custName) +' </span>';
					str += '</div>';
					str += '<div>';
					var emailImgTag = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,"EmailField");
					str += emailImgTag;
					var emailAdd = isccsOrderUtils.getEmailAddressFromCustomer(colConfig.bindingData,gridRowJSON,gridReference);
					var emailStyleInfo = isccsOrderUtils.getImageIconLabelStyle(colConfig.bindingData,gridRowJSON,gridReference);
					str += '<span class="'+ emailStyleInfo.textStyle +'" >'+ dHtmlEntities.encode(emailAdd) +' </span>';
					str += '</div>';
					str += '<div class="phoneIcon">';
					var phoneImgTag = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,"PhoneField");
					str += phoneImgTag;
					var phoneNo = isccsOrderUtils.getDayPhoneFromCustomer(colConfig.bindingData,gridRowJSON,gridReference);
					str += '<span class="'+ emailStyleInfo.textStyle +'" >'+ dHtmlEntities.encode(phoneNo) +' </span>';
					str += '</div>';
					return str;
				};
				

				orderLineUtils.getFSUnAvailableProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					str += '<div class="productImageDesc">';
					if(isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = null;
						if (gridRowJSON.Item.PrimaryInformation) {
							imageLocation = gridRowJSON.Item.PrimaryInformation.ImageLocation;
						}
						if (scBaseUtils.isVoid(imageLocation)){
							imageLocation = orderLineUtils.getOrderLineItemImageLocation(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}
						var imageID = null;
						if (gridRowJSON.Item.PrimaryInformation) {
							imageID = gridRowJSON.Item.PrimaryInformation.ImageID;
						}
						if (scBaseUtils.isVoid(imageID)){
							imageID = orderLineUtils.getOrderLineItemImageId(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage">';
					}
					
					var desc = gridRowJSON.ItemDetails.PrimaryInformation.ExtendedDisplayDescription;
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,"IsBundleParent");
					
					if(!scBaseUtils.isVoid(imageTags)){
						str += '<div class="productIndicatorIcons">';
						str += imageTags;
						str += '</div>';
					}
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getHoldsProductHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					if(gridRowJSON.OrderLine && isccsContextUtils.getDisplayItemImageRule()==="Y"){
						var imgsrc = "";
						var imageLocation = gridRowJSON.OrderLine.ItemDetails.PrimaryInformation.ImageLocation;
						var imageID = gridRowJSON.OrderLine.ItemDetails.PrimaryInformation.ImageID;
						if(scBaseUtils.isVoid(imageLocation) || scBaseUtils.isVoid(imageID)){
							imgsrc = isccsOrderUtils.getNoItemImage(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
						}else{
							if(imageID){
								if(scApplicationInfo.getShouldEncodeImageId()) {
									imageID = encodeURIComponent(imageID);
								}
							}
							imgsrc = encodeURI(imageLocation) + "/" + imageID;
						}
						
						str += '<img src="'+ imgsrc +'" class="itemImage">';
					}
					
					var desc = isccsOrderUtils.getModifiedExtendedDisplayDesc(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					if(scBaseUtils.isVoid(desc) || scBaseUtils.equals('null',desc))
						desc = "";
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getPriceMatchStatusHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'PriceMatchStatus');
					str += imageTags;
					var desc = gridReference.ownerScreen.changeStatusLowerCase(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				orderLineUtils.getReturnReasonHTML = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					var imageTags = orderLineUtils.getImageTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'ReturnReason');
					str += imageTags;
					var desc = orderLineUtils.getReturnReasonDesc(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					var returnStyle = orderLineUtils.getReturnReasonStyle(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord);
					str += '<span class="'+returnStyle.textStyle+'">'+ dHtmlEntities.encode(desc) +' </span>';
					str += '</div>';
					return str;
				};

				var linkFunctionMap = {
					'MultiUnitPrice':{'dynamicBindingFunc':orderLineUtils.getBundleLineUnitPriceForLink, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForUnitPrice},
					'AppeaseExpectedOn':{'dynamicBindingFunc':orderLineUtils.getFormattedDateRangeAppeasement, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForExpectedOn},
					'TrackingNo':{'dynamicBindingFunc':orderLineUtils.getTrackingNo, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForExpectedOn},
					'ExpectedOn':{'dynamicBindingFunc':orderLineUtils.getFormattedDateRange, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForExpectedOn},
					'BundleUnitPtice':{'dynamicBindingFunc':orderLineUtils.getStringForSeeComponents, 'tooltipDynamicBindingFunc':orderLineUtils.getAltForUnitPrice},
					'OrderNo':{'dynamicBindingFunc':orderLineUtils.getStringForOrderNo,'tooltipDynamicBindingFunc':orderLineUtils.getAltForOrderNoLink},
					'DisplayUserID':{'dynamicBindingFunc':orderLineUtils.getStringForDisplayUserID,'tooltipDynamicBindingFunc':orderLineUtils.getAltForLoginId},
					'PullCart':{'dynamicBindingFunc':orderLineUtils.getStringForPullCart,'tooltipDynamicBindingFunc':orderLineUtils.getAltForPullCart}
				};

				orderLineUtils.getAnchorTag = function(dataItem, gridRowRecord, gridReference,updatedGridRecord,columnIdentifier,linkUId) {
					var functions = linkFunctionMap[columnIdentifier];
					if(functions){
						var value = functions.dynamicBindingFunc(dataItem, gridRowRecord, gridReference, updatedGridRecord);
						var title = functions.tooltipDynamicBindingFunc(dataItem, gridRowRecord, gridReference, updatedGridRecord);
						if(!scBaseUtils.isVoid(value)){
							if(columnIdentifier=="ExpectedOn" && isccsDeliveryUtils.isServiceLine(gridRowRecord))//Show the value as regular text instead of link
								return dHtmlEntities.encode(value);
							else
								return '<a href="javascript:void(0)" title="'+ title +'" linkUId="'+ linkUId +'">'+dHtmlEntities.encode(value)+'</a>';
						}
						else
							return "";
					}
					return "";
				}
				
				orderLineUtils.getHTMLForOrderNo = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var scr = gridReference.ownerScreen;
					var isPopup = scScreenUtils.isPopup(scr);
					var str = '<div>';
					if(scBaseUtils.equals(false,isPopup)){
						var anchorTags = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'OrderNo','OrderNo');
						str += anchorTags;
					}
					else{
						str += dHtmlEntities.encode(value);
					}
					str += '</div>';
					return str;					
				},
				
				orderLineUtils.getHTMLForDisplayUserID = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var scr = gridReference.ownerScreen;
					var isPopup = scScreenUtils.isPopup(scr);
					var str = '<div>';
					if(scBaseUtils.equals(false,isPopup)){
						var anchorTags = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'DisplayUserID','DisplayUserID');
						str += anchorTags;
					}
					else{
						str += dHtmlEntities.encode(value);
					}
					str += '</div>';
					return str;
				};

				orderLineUtils.getHTMLForAddItemsUnitPrice = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					
					var anchorTags = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'MultiUnitPrice','MultiUnitPrice');
					str += anchorTags;

					var desc = orderLineUtils.getBundleLineUnitPriceForField(colConfig.bindingData, gridRowJSON, gridReference, gridRowRecord);
					if(!scBaseUtils.isVoid(desc))
						str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getHTMLForInvoiceLines = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';
					
					var value = gridReference.ownerScreen.getAssociatedInvoiceLink(colConfig.bindingData,gridRowJSON,gridReference);
					var title = orderLineUtils.getAltForInvoiceLines();
					title = scBaseUtils.isVoid(title)?"":title;
					if(!scBaseUtils.isVoid(value)){
						str += '<a href="javascript:void(0)" title="'+ title +'">'+dHtmlEntities.encode(value)+'</a>';
					}
					var desc = gridReference.ownerScreen.getAssociatedInvoiceValue(colConfig.bindingData, gridRowJSON, gridReference);
					if(!scBaseUtils.isVoid(desc))
						str += '<span>'+ dHtmlEntities.encode(desc) +' </span>';
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getHTMLForOverridePriceMatch = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div>';

					var desc1 = gridReference.ownerScreen.setPriceMatchAllowedValue(colConfig.bindingData, gridRowJSON, gridReference);
					if(!scBaseUtils.isVoid(desc1))
						str += '<span class="blockComponent">'+ dHtmlEntities.encode(desc1) +' </span>';

					var desc2 = gridReference.ownerScreen.setPriceMatchReasonValue(colConfig.bindingData, gridRowJSON, gridReference);
					if(!scBaseUtils.isVoid(desc2))
						str += '<span class="blockComponent">'+ dHtmlEntities.encode(desc2) +' </span>';
					
					var value = gridReference.ownerScreen.setPriceMatchOverrideValue(colConfig.bindingData,gridRowJSON,gridReference);
					if(!scBaseUtils.isVoid(value) && !scBaseUtils.equals(value, " ")){
						str += '<a href="javascript:void(0)">'+dHtmlEntities.encode(value)+'</a>';
					}
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getHTMLForExpectedOnCustAppease = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div class="fulfillmentAddress">';
					
					var expOnAnchorTag = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'AppeaseExpectedOn','ExpectedOn');
					str += expOnAnchorTag;
					
					str += '</div>';
					str += '<div class="fulfillmentAddress">';

					var trackAnchorTag = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'TrackingNo','TrackingNo');
					str += trackAnchorTag;
					
					str += '</div>';
					return str;
				};

				orderLineUtils.getHTMLForExpectedOn = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var str = '<div class="fulfillmentAddress">';
					
					str += '<div class="fulfillmentAddress">';
					var expOnAchorTag = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'ExpectedOn','ExpectedOn');
					str += expOnAchorTag;					
					str += '</div>';

					str += '<div class="fulfillmentAddress">';
					var bundleUnitPriceTag = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'BundleUnitPtice','BundleUnitPtice');
					str += bundleUnitPriceTag;					
					str += '</div>';

					str += '</div>';
					str += '<div class="fulfillmentAddress">';

					var trackAnchorTag = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'TrackingNo','TrackingNo');
					str += trackAnchorTag;
					
					str += '</div>';
					return str;
				};
				
				
				orderLineUtils.updatePriceMatchRecord = function(screen,gridId,selectedGridData){
					
					 var updatedData=selectedGridData._dataItem;
					 updatedData.PriceMatchAllowed="Y";
					 scGridUtils.updateRecordToGridUsingUId(screen, "OLST_listGrid", selectedGridData, updatedData, true, false);
					
					
				};
				
				/**
				* screen is assumed to be a List Screen
				*/
				orderLineUtils.getInputForPaginatedGridInListScreen = function(screen,gridId){
					var mashupInput = scPaginationUtils.getInitialInputForPaginatedGrid(screen, gridId);
					return mashupInput;
				};
				
				orderLineUtils.getHTMLForPullCart = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue,value,gridRowRecord,colConfig){
					var scr = gridReference.ownerScreen;
					var str = '<div>';
					var anchorTags = orderLineUtils.getAnchorTag(colConfig.bindingData,gridRowJSON,gridReference,gridRowRecord,'PullCart','PullCart');
					str += anchorTags;					
					str += '</div>';
					return str;					
				};
				
				return orderLineUtils;
});

