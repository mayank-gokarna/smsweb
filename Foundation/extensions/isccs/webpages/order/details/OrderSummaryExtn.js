scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/order/details/OrderSummaryExtnUI","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!isccs/utils/UIUtils","scbase/loader!sc/plat/dojo/utils/ScreenUtils","scbase/loader!sc/plat/dojo/utils/ModelUtils","scbase/loader!sc/plat/dojo/utils/EventUtils","scbase/loader!isccs/utils/BaseTemplateUtils" ,"scbase/loader!isccs/utils/ModelUtils","scbase/loader!sc/plat/dojo/utils/GridxUtils","scbase/loader!dojo/_base/lang","scbase/loader!sc/plat/dojo/info/ApplicationInfo", "scbase/loader!sc/plat/dojo/utils/WidgetUtils","scbase/loader!isccs/utils/OrderUtils"]
,
function(			 
			    _dojodeclare,
			    _extnOrderSummaryExtnUI,
			    _scBaseUtils,
			    _isccsUIUtils,
			    _scScreenUtils,
			    _scModelUtils,
			    _scEventUtils,
			    _isccsBaseTemplateUtils,
				_isccsModelUtils,
				_scGridxUtils,
				dLang,
				_scApplicationInfo,
				_scWidgetUtils,
				_isccsOrderUtils

){ 
	return _dojodeclare("extn.order.details.OrderSummaryExtn", [_extnOrderSummaryExtnUI],{
	
	/*
	* This method handles doube click on row of linked order in linked order panel
	*
	*
	*/
	CSGDoubleClickHandler: function(event,bEvent,ctrl,args){
		var item=null;
		item = _scBaseUtils.getModelValueFromBean("item",args);
		
		var sOrderHeaderKey = _scModelUtils.getStringValueFromPath("LinkedOrderHeaderKey",item);
		if(_scBaseUtils.isVoid(sOrderHeaderKey)){
			 var newOrderModel = null;
             newOrderModel = _scModelUtils.createNewModelObjectWithRootKey("Order");
            _scModelUtils.setStringValueAtModelPath("Order.OrderNo", item.LinkedOrderNo, newOrderModel);
            _scModelUtils.setStringValueAtModelPath("Order.DraftOrderFlag", "N", newOrderModel);
			//_scModelUtils.setStringValueAtModelPath("Order.DocumentType", "0001", newOrderModel);
			_isccsUIUtils.callApi(this,newOrderModel,"extn_getOrderList",null);	
		}
		else 
		{
			this.openOrderDetails(item);
		}
	},

	
	/*
	*
	* This method save the change made on the More Attribute grid
	*
	*/
	invokeChangeOrder:function(event,bEvent,ctrl,args){
		var changeOrderInputModel= _scScreenUtils.getTargetModel(this,"extn_ChangeOrderInput",null);
		var options = null;
        var tModel = null;
        options = {};
        options["modified"] = "true";
        options["deleted"] = "true";
        tmodel = _scBaseUtils.getTargetModel(this, "extn_ChangeOrderInput", options);
		var orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var orderline = _scModelUtils.getStringValueFromPath("Order.OrderLines.OrderLine", orderModel);
			if(orderline){
				var changeOrderInput = _scModelUtils.createNewModelObjectWithRootKey("Order");
				var arr = _scBaseUtils.getNewArrayInstance();
				_scModelUtils.setStringValueAtModelPath("Order.OrderLines.OrderLine", arr , changeOrderInput);
				_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",_scModelUtils.getStringValueFromPath("Order.OrderHeaderKey",orderModel),changeOrderInput);
			   	_scModelUtils.setStringValueAtModelPath("Order.Override","Y",changeOrderInput);
				_scModelUtils.setStringValueAtModelPath("Order.Action","MODIFY",changeOrderInput);	
			    var length = 0;
			    length = orderline.length;
			    for(var i = 0; i<length; i++ ){
			   	var eOrderLine = orderline[i];
			   	var eventDefn = _scBaseUtils.getNewBeanInstance();
			   	var sOrderLineKey = _scModelUtils.getStringValueFromPath("OrderLineKey", eOrderLine);
			   	_scBaseUtils.setAttributeValue("OrderLineKey", sOrderLineKey, eventDefn);
			   	_scModelUtils.setStringValueAtModelPath("Action", "MODIFY" , eventDefn);
			   	var arrProp = _scBaseUtils.getNewArrayInstance();
				_scModelUtils.setStringValueAtModelPath("Extn.EXTNOrderLinePropertiesList.EXTNOrderLineProperties", arrProp , eventDefn);
				_scBaseUtils.appendToArray(arr, eventDefn);
				var modifiedData = _scModelUtils.getStringValueFromPath("Order.Extn.EXTNOrderHeaderPropertiesList.EXTNOrderHeaderProperties", tmodel);
				//this is where we have the edited data from the grid
				//If its not present then we have a else case as fail safe
				//In this fail safe we get the edited data directly from the grid insted of using get target model.
				if(modifiedData){
					var modifiedDataPropLength = 0;
					modifiedDataPropLength = modifiedData.length;
					for(var iModDataProp = 0; iModDataProp<modifiedDataPropLength; iModDataProp++){
						var eModifiedData = modifiedData[iModDataProp]
						var sModDataOrderLineKey = _scModelUtils.getStringValueFromPath("OrderLineKey", eModifiedData);
						if(_scBaseUtils.equals(sModDataOrderLineKey,sOrderLineKey)){
							var sModDataDescribedByValue = _scModelUtils.getStringValueFromPath("DescribedByValue", eModifiedData);
							if(!sModDataDescribedByValue){
								_scBaseUtils.setAttributeValue("DescribedByValue", "", eModifiedData);
							}
							_scBaseUtils.appendToArray(arrProp, eModifiedData);
						}
					}
				}else{
					var sExtnOrderLinePropertyList =  _scModelUtils.getStringValueFromPath("Extn.EXTNOrderLinePropertiesList.EXTNOrderLineProperties", eOrderLine);
					if(sExtnOrderLinePropertyList){
						var propLength = 0;
						propLength = sExtnOrderLinePropertyList.length;
						for(var iProp = 0; iProp<propLength; iProp++){
							var eExtnOrderLineProperty = sExtnOrderLinePropertyList[iProp];
								var sPropOrderLineKey = _scModelUtils.getStringValueFromPath("OrderLineKey", eExtnOrderLineProperty);
								var sOriginalPropertyKey = _scModelUtils.getStringValueFromPath("PropertyKey", eExtnOrderLineProperty);
								//Getting data from the grid as a part of fail safe logic
								var initialRowCount = null;
								initialRowCount = _scGridxUtils.getRowCount(this, "extn_gridMoreAttributes");
								if(initialRowCount){
									for (
									var z = 0;
									_scBaseUtils.lessThan(
									z, initialRowCount);
									z = _scBaseUtils.incrementNumber(
									z, 1)) {
										var srcRowData = null;
										srcRowData = _scGridxUtils.getItemFromRowIndexUsingUId(
										this, "extn_gridMoreAttributes", z);
										if(srcRowData){
											var sChangeOrderPropertyKey = _scModelUtils.getStringValueFromPath("extn_PropertyKey.0", srcRowData);
											if(_scBaseUtils.equals(sChangeOrderPropertyKey,sOriginalPropertyKey)){
												var sDescribedByCharacteristic = _scModelUtils.getStringValueFromPath("extn_Characteristic.0", srcRowData);
												var sDescribedByValue = _scModelUtils.getStringValueFromPath("extn_Value.0", srcRowData);
												var eventCOProp = _scBaseUtils.getNewBeanInstance();
												_scBaseUtils.appendToArray(arrProp, eventCOProp);
												_scBaseUtils.setAttributeValue("DescribedByCharacteristic", sDescribedByCharacteristic, eventCOProp);
												_scBaseUtils.setAttributeValue("DescribedByValue", sDescribedByValue, eventCOProp);
												_scBaseUtils.setAttributeValue("OrderLineKey", sOrderLineKey, eventCOProp);
												_scBaseUtils.setAttributeValue("PropertyKey", sChangeOrderPropertyKey, eventCOProp);
											}
										}
									}
								}
							}
						}
				}

			    }
			    _isccsUIUtils.callApi(this,changeOrderInput,"extn_OHSCChangeOrder",null);
			}
		}
	},

	handleMashupCompletion: function(mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data) {
		var sModelObj = _scModelUtils.getStringValueFromPath("0.mashupRefId",mashupRefList);
		var sModelObjItem = _scModelUtils.getStringValueFromPath("mashupArray.0.mashupRefId",inputData);
		if(hasError){
			_isccsBaseTemplateUtils.showMessage(this, _scScreenUtils.getString(this, "extn_OperationFailedErrorMsg"),"error", null);
		} 
		else {
			this.isDirtyCheckRequired = false;
			if(_scBaseUtils.equals("extn_getOrderList",sModelObj) || _scBaseUtils.equals("extn_getOrderList",sModelObjItem) 
				|| _scBaseUtils.equals("extn_OrderSummaryLins_getOrderList",sModelObj) || _scBaseUtils.equals("extn_OrderSummaryLins_getOrderList",sModelObjItem)
			|| _scBaseUtils.equals("extn_GetOrderHierarchyBehavior",sModelObj) || _scBaseUtils.equals("extn_GetOrderHierarchyBehavior",sModelObjItem)){
				_isccsBaseTemplateUtils.handleMashupCompletion(
            mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data, this);
			}else{
				if(_scBaseUtils.equals("extn_RetryValidationService",sModelObj) || _scBaseUtils.equals("extn_RetryValidationService",sModelObjItem)){
					 var modelOutput = _scBaseUtils.getModelValueFromBean("mashupRefOutput", mashupRefList[0]);
					 var stringModelOutput = JSON.stringify(modelOutput);
					 if(stringModelOutput.includes('validationResult":{"correct":"false"')){
						 _isccsBaseTemplateUtils.showMessage(this, _scScreenUtils.getString(this, "extn_OperationFailedErrorMsg"),"error", null);
					 }else{
						_scScreenUtils.showInfoMessageBox(this,_scScreenUtils.getString(this, "extn_ValidationSuccessInfoMsg"));
					 }
				}else if(_scBaseUtils.equals("extn_UpdateServiceOrderStatus",sModelObj) || _scBaseUtils.equals("extn_UpdateServiceOrderStatus",sModelObjItem)){
					var requestStatus = _scModelUtils.getStringValueFromPath("mashupArray.0.mashupInputObject.Order.RequestStatus",inputData);
					if(_scBaseUtils.isVoid(requestStatus)){
						requestStatus = _scModelUtils.getStringValueFromPath("0.mashupInputObject.Order.Status",inputData);
					}
					requestStatus = _scModelUtils.getStringValueFromPath("0.mashupInputObject.Order.RequestStatus",inputData);
					if(requestStatus){
						if(_scBaseUtils.equals(requestStatus,"Complete")){
							_scScreenUtils.showInfoMessageBox(this,_scScreenUtils.getString(this, "extn_MarkAsCompletedInfoMsg"));
						}else if(_scBaseUtils.equals(requestStatus,"Retry")){
							_scScreenUtils.showInfoMessageBox(this,_scScreenUtils.getString(this, "extn_RetrySuccsessfulInfoMsg"));
						}
					}					
				}else if(_scBaseUtils.equals("extn_OHSCChangeOrder",sModelObj) || _scBaseUtils.equals("extn_OHSCChangeOrder",sModelObjItem)){
					_scScreenUtils.showInfoMessageBox(this,_scScreenUtils.getString(this, "extn_SaveSuccsessfulInfoMsg"));
				}
			}
		}
	},
	
	 handleMashupOutput: function(
        mashupRefId, modelOutput, mashupInput, mashupContext, applySetModel) {
            if (
            _scBaseUtils.equals(
            mashupRefId, "getCompleteOrderDetailsBehavior")) {
                if (!(
                _scBaseUtils.equals(
                false, applySetModel))) {
                    _scScreenUtils.setModel(
                    this, "getCompleteOrderDetails_output", modelOutput, null);
                }
            }
			if(_scBaseUtils.equals(mashupRefId, "extn_getOrderList")){
				var newItem = _scModelUtils.createNewModelObjectWithRootKey("item");
				_scModelUtils.setStringValueAtModelPath("LinkedOrderHeaderKey", 
						_scModelUtils.getStringValueFromPath("OrderList.Order.0.OrderHeaderKey",modelOutput), newItem);
				_scModelUtils.setStringValueAtModelPath("LinkedOrderNo", 
						_scModelUtils.getStringValueFromPath("OrderList.Order.0.OrderNo",modelOutput), newItem);
				this.openOrderDetails(newItem);
			}
			if(_scBaseUtils.equals(mashupRefId,"extn_OrderSummaryLins_getOrderList")){
				if(modelOutput){
					_scScreenUtils.setModel(this, "extn_OrderSummaryLins_getOrderList_output", modelOutput, null);
					this.updateItemIDToOrderLinkGrid(modelOutput);
				}
				
				
			}
			if(_scBaseUtils.equals(mashupRefId,"extn_GetOrderHierarchyBehavior")){
				_scScreenUtils.setModel(this, "extn_GetOrderHierarchy_output", modelOutput, null);
				this.updateDataTree(modelOutput);
			}
        },
		
	/*
	*
	*This method recreate a d3 tree after getOrderHierarchy call is complete.
	*
	*/	
	updateDataTree:function(getOrderHierarchy_output){
		var orderModel = null;
        orderModel = _scScreenUtils.getModel(this, "getCompleteOrderDetails_output");
        if(orderModel && getOrderHierarchy_output){
        	var sTreeOrderType = _scModelUtils.getStringValueFromPath("Order.OrderType",orderModel);
			//chnages to show Order tree for CustomerOrder orderType only
        	if(!_scBaseUtils.isVoid(sTreeOrderType) && _scBaseUtils.equals(sTreeOrderType,"CustomerOrder")){
				var treeData = null;
				treeData = _scModelUtils.getStringValueFromPath("OrderHierarchyForD3Tree.ChartInput",getOrderHierarchy_output);
				treeData = treeData.replace(/'/g, '"');
				var treeData = JSON.parse(treeData);
				if(treeData && !_scBaseUtils.isBeanEmpty(treeData)){
					_scWidgetUtils.showWidget(this, "extn_OrderTreePanel");
					_scWidgetUtils.showWidget(this, "extn_treeLegend_contentpane");
					this.loadOrderTree(treeData);
				}else{
					//hide extn_OrderTreePanel
					_scWidgetUtils.hideWidget(this, "extn_OrderTreePanel", true);
					_scWidgetUtils.hideWidget(this, "extn_treeLegend_contentpane", true);
				}
        	}else{
				//hide extn_OrderTreePanel
				_scWidgetUtils.hideWidget(this, "extn_OrderTreePanel", true);
				_scWidgetUtils.hideWidget(this, "extn_treeLegend_contentpane", true);
			}
        }
	},	
		
	openOrderDetails: function(item){
		var editorInput = {Order:{}};
		editorInput.Order.OrderHeaderKey=_scModelUtils.getStringValueFromPath("LinkedOrderHeaderKey",item);
		
		editorInput.Order.OrderNo=_scModelUtils.getStringValueFromPath("LinkedOrderNo",item);
		editorInput.Order.DraftOrderFlag="N";
		
		_isccsUIUtils.openWizardInEditor("isccs.order.wizards.orderSummary.OrderSummaryWizard",editorInput,"isccs.editors.OrderEditor",this);
	},

	loadMoreAttributes: function(event,bEvent,ctrl,args){
		var	orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var length = 0;
			var orderline = _scModelUtils.getStringValueFromPath("Order.OrderLines.OrderLine", orderModel);
			if(orderline){
				var extn_MoreAttributeListNS = _scModelUtils.createNewModelObjectWithRootKey("MoreAttributeList");
				var arr = _scBaseUtils.getNewArrayInstance();
				_scModelUtils.setStringValueAtModelPath("MoreAttributeList.MoreAttribute", arr , extn_MoreAttributeListNS);
				length = orderline.length;				
				for (i=0;length>i; i++){
					var eOrderLine = orderline[i];
					var sItemID = _scModelUtils.getStringValueFromPath("ItemDetails.ItemID", eOrderLine);
					var extnOrderlineProperties = _scModelUtils.getStringValueFromPath("Extn.EXTNOrderLinePropertiesList.EXTNOrderLineProperties", eOrderLine);
					if(extnOrderlineProperties){
						var len = 0;
						len = extnOrderlineProperties.length;
						for (j=0; len>j ; j++){
							var eExtnOrderlineProp = extnOrderlineProperties[j];
							var sDescribedByCharacteristic = _scModelUtils.getStringValueFromPath("DescribedByCharacteristic", eExtnOrderlineProp);
							var sDescribedByValue = _scModelUtils.getStringValueFromPath("DescribedByValue", eExtnOrderlineProp);
							var sOrderLineKey = _scModelUtils.getStringValueFromPath("OrderLineKey", eExtnOrderlineProp);
							var sPropertyKey = _scModelUtils.getStringValueFromPath("PropertyKey", eExtnOrderlineProp);
							var sDescribedByCharacteristicName = _scModelUtils.getStringValueFromPath("DescribedByCharacteristicName", eExtnOrderlineProp);
							var sExtnEntityId = _scModelUtils.getStringValueFromPath("CharacteristicEntityId", eExtnOrderlineProp);
							if(!_scBaseUtils.isVoid(sDescribedByCharacteristicName)){
								 var eventDefn = _scBaseUtils.getNewBeanInstance();
								_scBaseUtils.setAttributeValue("DescribedByCharacteristic", sDescribedByCharacteristic, eventDefn);
								_scBaseUtils.setAttributeValue("DescribedByValue", sDescribedByValue, eventDefn);
								_scBaseUtils.setAttributeValue("OrderLineKey", sOrderLineKey, eventDefn);
								_scBaseUtils.setAttributeValue("PropertyKey", sPropertyKey, eventDefn);
								_scBaseUtils.setAttributeValue("DescribedByCharacteristicName", sDescribedByCharacteristicName, eventDefn);
								_scBaseUtils.setAttributeValue("CharacteristicEntityId", sExtnEntityId, eventDefn);
								if(sItemID){
									_scBaseUtils.setAttributeValue("ItemID", sItemID, eventDefn);
								}
								_scBaseUtils.appendToArray(arr, eventDefn);
							}
							
						}					
					}
				}
				_scScreenUtils.setModel(this, "extn_orderMoreCharactersticks", extn_MoreAttributeListNS, null);
			}
		}	
	},
	
	loadTestData: function(event,bEvent,ctrl,args){
		//TODO
	},
	
	extnAfterScreenInit:function(event,bEvent,ctrl,args){
		
		//PhaseII -- Show CustomerOrder summary in a tree structure.
		var getOrderHierarchy_output = null;
		var orderModel = null;
        orderModel = _scScreenUtils.getModel(this, "getCompleteOrderDetails_output");
        getOrderHierarchy_output = _scScreenUtils.getModel(this, "extn_GetOrderHierarchy_output");//
		//Hide Adress Panel if OrderType is BuildPlan
		if(orderModel){
			var sAddPanelOrderType = _scModelUtils.getStringValueFromPath("Order.OrderType",orderModel);
			if(!_scBaseUtils.isVoid(sAddPanelOrderType) && _scBaseUtils.equals(sAddPanelOrderType,"BuildPlan")){
				_scWidgetUtils.hideWidget(this, "DST_SecondaryInfoPanel", true);
			}
		}
		// Hide OrderLink Panel if OrderType is not CustomerOrder
		if(orderModel){
			var sOrderLinkPanelOrderType = _scModelUtils.getStringValueFromPath("Order.OrderType",orderModel);
			if(!_scBaseUtils.isVoid(sOrderLinkPanelOrderType) && _scBaseUtils.equals(sOrderLinkPanelOrderType,"CustomerOrder")){
				_scWidgetUtils.hideWidget(this, "extn_tpOrderLinks", true);
			}
		}
		
        if(orderModel && getOrderHierarchy_output){
        	var sTreeOrderType = _scModelUtils.getStringValueFromPath("Order.OrderType",orderModel);
			//chnages to show Order tree for CustomerOrder orderType only
        	if(!_scBaseUtils.isVoid(sTreeOrderType) && _scBaseUtils.equals(sTreeOrderType,"CustomerOrder")){
				var treeData = null;
				treeData = _scModelUtils.getStringValueFromPath("OrderHierarchyForD3Tree.ChartInput",getOrderHierarchy_output);
				//remove single quote with bouble quote
				treeData = treeData.replace(/'/g, '"');
				var treeData = JSON.parse(treeData);
				if(treeData && !_scBaseUtils.isBeanEmpty(treeData)){
					this.loadOrderTree(treeData);
				}else{
					this.callGetOrderDetailsWithExtnCustomerOrderNo(event,bEvent,ctrl,args);
					//hide extn_OrderTreePanel
					_scWidgetUtils.hideWidget(this, "extn_OrderTreePanel", true);
					_scWidgetUtils.hideWidget(this, "extn_treeLegend_contentpane", true);
				}


				if(!_scBaseUtils.isVoid(sTreeOrderType) && _scBaseUtils.equals(sTreeOrderType,"CustomerOrder")){					
					this.evaluateButtonsForCustomerOrder(orderModel);
				}else{
					this.evaluateButtonsDecomposedOrders(orderModel);
				}
        	} else{
			 
				_scWidgetUtils.hideWidget(this, "extn_OrderTreePanel", true);
				_scWidgetUtils.hideWidget(this, "extn_treeLegend_contentpane", true);
				//_scWidgetUtils.hideWidget(this, "extn_pnlMoreAttributes", true);
				if(!_scBaseUtils.isVoid(sTreeOrderType) && _scBaseUtils.equals(sTreeOrderType,"CustomerOrder")){					
					this.evaluateButtonsForCustomerOrder(orderModel);
				}else{
					this.evaluateButtonsDecomposedOrders(orderModel);
				}
				
			}
        }
		//
        //creating getOrderList Input 
		var getOrderListInput = _scBaseUtils.getNewBeanInstance();
		var arr  = _scBaseUtils.getNewArrayInstance();
		_scModelUtils.setStringValueAtModelPath("Order.ComplexQuery.And.Or.Exp", arr , getOrderListInput);
		_scModelUtils.setStringValueAtModelPath("Order.ComplexQuery.Operator", "AND" , getOrderListInput);
		_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode",	
					 _scModelUtils.getStringValueFromPath("Order.EnterpriseCode",orderModel), getOrderListInput);
		//_scModelUtils.setStringValueAtModelPath("Order.DocumentType", "0001" , getOrderListInput);
		//

		//getting linkedOrderNo details form order lines
		if(!_scBaseUtils.isVoid(orderModel)){
			var EXTNOrderHeaderLinks = _scModelUtils.getStringValueFromPath("Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks",orderModel);
			var length = 0;
			if(EXTNOrderHeaderLinks){
				length = EXTNOrderHeaderLinks.length;
					for(var i = 0; i<length; i++){
						var eEXTNOrderHeaderLink = EXTNOrderHeaderLinks[i];
						var sLinkedOrderNo = _scModelUtils.getStringValueFromPath("LinkedOrderNo",eEXTNOrderHeaderLink);
						if(sLinkedOrderNo){
							var eventDefn = _scBaseUtils.getNewBeanInstance();
							_scBaseUtils.setAttributeValue("Name", "OrderNo", eventDefn);
							_scBaseUtils.setAttributeValue("Value", sLinkedOrderNo, eventDefn);
							_scBaseUtils.setAttributeValue("QryType", "EQ", eventDefn);
							_scBaseUtils.appendToArray(arr, eventDefn);							
						}
					}
				}
			
			var sLinkedOrderArray = _scModelUtils.getStringValueFromPath("Order.ComplexQuery.And.Or.Exp",getOrderListInput);
			if(!_scBaseUtils.isVoid(sLinkedOrderArray) && sLinkedOrderArray.length>0)
			_isccsUIUtils.callApi(this,getOrderListInput,"extn_OrderSummaryLins_getOrderList",null);	
		}
	},
	/**
	This method will evaluate buttons to show and hide for customer order.
	**/
	evaluateButtonsForCustomerOrder:function(orderData){
		//hide retry and mark as complete button by default
		_scWidgetUtils.hideWidget(this, "extn_Retrybtn", true);
		_scWidgetUtils.hideWidget(this, "extn_MarkAsComplete_btn", true);
		var maxStatus = _scModelUtils.getStringValueFromPath("Order.MaxOrderStatus",orderData);
		if(!_scBaseUtils.equals(maxStatus,"1100") && !_scBaseUtils.equals(maxStatus,"1100.020")){
			// hide save button when status is not created or rejected
			_scWidgetUtils.hideWidget(this, "extn_btnSave", true)
		}
		if( !_scBaseUtils.equals(maxStatus,"1100.020")){
			// hide retry validation if order status is not rejected 
			_scWidgetUtils.hideWidget(this, "extn_Retry_Validation_btn", true)
		}
  
		
	},
	/**
	This method will evaluate buttons to show and hide for resource , service and product order.
	**/
	evaluateButtonsDecomposedOrders:function(orderData){
		// hide retry validation by default for all non-customer orders.
		_scWidgetUtils.hideWidget(this, "extn_Retry_Validation_btn");
		var maxStatus = _scModelUtils.getStringValueFromPath("Order.MaxOrderStatus",orderData);
		// hide Save and Retry button if order status is not suspended.
		if( !_scBaseUtils.equals(maxStatus,"1100.050")){
			_scWidgetUtils.hideWidget(this, "extn_btnSave", true)
			_scWidgetUtils.hideWidget(this, "extn_Retrybtn", true);
			
		} 
		// Hide mark as complete if order status is not suspended/awaiting error resolution.
		if(!_scBaseUtils.equals(maxStatus,"1100.050") && !_scBaseUtils.equals(maxStatus,"1100.060")  ){
			_scWidgetUtils.hideWidget(this, "extn_MarkAsComplete_btn");
		}
		
		
	},
	callGetOrderDetailsWithExtnCustomerOrderNo:function(event,bEvent,ctrl,args){
		var orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var sOrderNo = _scModelUtils.getStringValueFromPath("Order.Extn.ExtnCustomerOrderNo",orderModel);
			var sEnterpriseCode = _scModelUtils.getStringValueFromPath("Order.EnterpriseCode",orderModel);
			var sDocumentType = _scModelUtils.getStringValueFromPath("Order.DocumentType",orderModel);
			if(sOrderNo){
				var orderModeInput = _scBaseUtils.getNewBeanInstance();
				_scModelUtils.setStringValueAtModelPath("Order.OrderNo",sOrderNo,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode",sEnterpriseCode,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.DocumentType",sDocumentType,orderModeInput);
				_isccsUIUtils.callApi(this,orderModeInput,"extn_GetOrderHierarchyBehavior",null);
			}			
		}
	},
	
	getItemIDForLinkedOrderNo:function(event,bEvent,ctrl,args){
		var orderModel = null;
        orderModel = _scScreenUtils.getModel(this, "extn_OrderSummaryLins_getOrderList_output");
	},
	
	updateItemIDToOrderLinkGrid:function(data){
		var orderModel = null;
        orderModel = _scScreenUtils.getModel(this, "getCompleteOrderDetails_output");
		//getting linkedOrderNo details form order lines
		if(!_scBaseUtils.isVoid(orderModel)){
			var EXTNOrderHeaderLinks = _scModelUtils.getStringValueFromPath("Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks",orderModel);
			var length = 0;
			if(EXTNOrderHeaderLinks){
				length = EXTNOrderHeaderLinks.length;
				for(var i = 0; i<length; i++){
					var eEXTNOrderHeaderLink = EXTNOrderHeaderLinks[i];
					var sLinkedOrderNo = _scModelUtils.getStringValueFromPath("LinkedOrderNo",eEXTNOrderHeaderLink);
					if(sLinkedOrderNo){
						var OrderListArray = _scModelUtils.getModelListFromPath("OrderList.Order", data);
						var len = 0;
						if(OrderListArray){
							len = OrderListArray.length;
							//Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks
							for(var j=0;j<len;j++){
								var eOrder = OrderListArray[j];
								var sOrderNo = _scModelUtils.getStringValueFromPath("OrderNo",eOrder);
								var sOrderType = _scModelUtils.getStringValueFromPath("OrderType",eOrder);
								if(!_scBaseUtils.isVoid(sOrderNo) && _scBaseUtils.equals(sLinkedOrderNo,sOrderNo)
									&& !(_scBaseUtils.equals(sOrderType,"BuildPlan") || _scBaseUtils.equals(sOrderType,"CustomerOrder"))){
									var sExtendedDisplayDescription = _scModelUtils.getStringValueFromPath("OrderLines.OrderLine.0.ItemDetails.PrimaryInformation.ExtendedDisplayDescription",eOrder);
									if(_scBaseUtils.isVoid(sExtendedDisplayDescription)){
										sExtendedDisplayDescription = _scModelUtils.getStringValueFromPath("OrderLines.OrderLine.0.ItemDetails.ItemID",eOrder);
									}
									if(!_scBaseUtils.isVoid(sExtendedDisplayDescription)){
										_scModelUtils.setStringValueAtModelPath("ExtendedDisplayDescription",  sExtendedDisplayDescription, eEXTNOrderHeaderLink);
									}
									
									break;
								}
								
							}
						}							
					}
				}
			}
		}
		this.updateGrid(orderModel);//LinkedOrderNoItemID
	},
	
	updateGrid: function(newModelData) {
			var initialRowCount = null;
            initialRowCount = _scGridxUtils.getRowCount(this, "extn_gridOrderLinks");
			var mergeOrderLine = _scModelUtils.getModelListFromPath("Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks", newModelData);
            for (
            var z = 0;
            _scBaseUtils.lessThan(
            z, initialRowCount);
            z = _scBaseUtils.incrementNumber(
            z, 1)) {
                var beanItemToMerg = null;
                beanItemToMerg = _scBaseUtils.getArrayBeanItemByIndex(
                mergeOrderLine, z);
                var srcRowData = null;
                srcRowData = _scGridxUtils.getItemFromRowIndexUsingUId(
                this, "extn_gridOrderLinks", z);
                _scGridxUtils.updateRecordToGridUsingUId(
                this, "extn_gridOrderLinks", srcRowData, beanItemToMerg, true, false);
            }
            _scGridxUtils.deselectAllRowsInGridUsingUId(
            this, "extn_gridOrderLinks");
        },
	

	/*
	*
	*This method creates the d3 tree.
	*It represend the relate order to the cusotmer order in a tree line structure.
	*
	*/	
	loadOrderTree:function(treeData){
		
		var that =  this;
		var cp = this.getWidgetByUId("extn_OrderTreePanel");
		
		var linearScale = d3.scale.linear()
            .domain([0,1000])
           .range([0,900]);
		   
		var verticalScale = d3.scale.linear()
            .domain([0,1000])
           .range([0,700]);   
		
		var totalNodes = 0;
		var maxLabelLength = 0;
		
		function visit(parent, visitFn, childrenFn) {
			if (!parent) return;

			visitFn(parent);

			var children = childrenFn(parent);
			if (children) {
				var count = children.length;
				for (var i = 0; i < count; i++) {
					visit(children[i], visitFn, childrenFn);
				}
			}
		}

		// Call visit function to establish maxLabelLength
		visit(treeData, function(d) {
			totalNodes++;
			maxLabelLength = Math.max(d.orderNo.length, maxLabelLength);

		}, function(d) {
			return d.children && d.children.length > 0 ? d.children : null;
		});		
		
		var levelWidth = [1];
        var childCount = function(level, n) {

            if (n.children && n.children.length > 0) {
                if (levelWidth.length <= level + 1) levelWidth.push(0);

                levelWidth[level + 1] += n.children.length;
                n.children.forEach(function(d) {
                    childCount(level + 1, d);
                });
            }
        };
        childCount(0, treeData);
        var newHeight = d3.max(levelWidth) * 80;		
		
		var tree = d3.layout.tree()
		.size([newHeight, 1000])
		.separation(function(a, b) { return ((a.parent == b.parent)) ? 1 : 1; });

		var svgLegend = d3.select(cp.domNode)
			.append("svg")
			.attr("width",1230)
			.attr("height", 50)
			.append("g")
			.attr("transform", "translate(" + 50 + "," + 10 + ")");		
		
		var svg = d3.select(cp.domNode)
			.append("svg")
			.attr("width",1230)
			.attr("height", newHeight+30)
			.call(zm = d3.behavior.zoom().scaleExtent([.7,1.5]).on("zoom",redraw))
			.append("g")
			.attr("transform", "translate(" + 115 + "," + 10 + ")");

		//necessary so that zoom knows where to zoom and unzoom from
		zm.translate([115, 10]);
		
		var nodes = tree.nodes(treeData),
        links = tree.links(nodes);
		
		
		// Set widths between levels based on maxLabelLength.
        nodes.forEach(function(d) {
            d.y = (d.depth * (maxLabelLength * 10)); //maxLabelLength * 10px

        });
		
		//Select ContentPane dom node and create an SVG element.
		
		svg.selectAll(".link")
			.data(links)
			.enter()
			.append("path")
			.attr("class","link")
			.attr("fill","none")
			.attr("stroke","black")
			.attr("stroke-width",2)
			.attr("shape-rendering", "crispEdges")
			.attr("d",elbow)
		
		var node = svg.selectAll("g.node")
			.data(nodes)
			.enter()
			.append("g")
				.attr("transform", function(d){return "translate("+(d.y)+","+(d.x)+")";})
				
		node.append("image")
		   .attr("id", "bg")
		   .attr("xlink:href", function (d){			   
			   //Image path
			   	var sImageToDisplay = "/extn/isccs/resources/css/icons/images/CCOrder.png"; 
				if(d){
					if(d.orderType){
						if(d.orderType === "CustomerOrder" ){
							sImageToDisplay = "/extn/isccs/resources/css/icons/images/redPersonCust.png"; 							
						}else if(d.orderType === "ServiceOrder"){
							sImageToDisplay = "/extn/isccs/resources/css/icons/images/purpleWheelService.png";
						}else if(d.orderType === "ProductOrder"){
							sImageToDisplay = "/extn/isccs/resources/css/icons/images/greenProdTag.png"; 							
						}else if(d.orderType === "BuildPlan"){
							sImageToDisplay = "/extn/isccs/resources/css/icons/images/blueSK.png"; 
						}
					}
				}
				return _scApplicationInfo.getApplicationContext() + sImageToDisplay;
			})
		    .attr("x", -15)
			.attr("y", -16)
			.attr("width", 30)
			.attr("height", 30);		
		
		//ItemID to be displayed in the tree nodes
		node.append("svg:a")
			.attr("xlink:href", function(d){return "#";})
			.append("text")
		    .style("text-anchor", "left")
			.attr('x', function(d) { return "15px";})
			.attr("font-size","15px")
			//.attr("font-family", "Arial")
            .attr('y', -5)
            .attr('fill', 'black')
			.attr("font-weight","bold")
			.attr("font-family", "calibri")
		    .text(function(d) {
				if(d && d.itemId){
					if(d.billToId){
						if(d.orderType && d.orderType ==="CustomerOrder")
						return d.billToId;
					}
					// changes to  map ItemId to ExtnTargetOss for Child Orders
					if(d.extnTargetOss){
						return d.extnTargetOss;
					}
					else{
					return d.itemId;
					}
				}
			})
			.on("click", function(d) { 
				toggle(d);
			});
		
		//Purpose to be displayed in the tree nodes			
		node.append("svg:a")
			.attr("xlink:href", function(d){return "#";})
			.append("text")
		    .style("text-anchor", "left")
			.attr('x', function(d) { return "15px";})
			.attr("font-size","10px")
			//.attr("font-family", "Arial")
            .attr('y', 12)
            .attr('fill', 'black')
		    .text(function(d) { 
				if(d && d.purpose){
					if(d.orderType && d.orderType !="CustomerOrder")
					return d.purpose;
				}
			}).on("click", function(d) { 
				toggle(d);
			});	
			
		//Order number to be displayed in the tree nodes			
		node.append("svg:a")
			.attr("xlink:href", function(d){return "#";})
			.append("text")
		    .style("text-anchor", "left")
			.attr('x', function(d) { return "15px";})
			.attr("font-size","10px")
			//.attr("font-family", "Arial")
            .attr('y', function(d) { 
				if(d && d.purpose){
					if(d.orderType && d.orderType !="CustomerOrder"){
						return 22;
					}else{
						return 12;
					}					
				}
			})
            .attr('fill', 'black')
		    .text(function(d) { 
				if(d && d.orderNo){
					return d.orderNo;
				}
			}).on("click", function(d) { 
				toggle(d);
			});
				
		//Order Status to be displayed in the tree nodes	
		node.append("svg:a")
			.attr("xlink:href", function(d){return "#";})
			.append("text")
		    .style("text-anchor", "left")
			.attr('x', function(d) { return "15px";})
			.attr("font-size","10px")
			//.attr("font-family", "Arial")
            .attr('y', function(d) { 
				if(d && d.purpose){
					if(d.orderType && d.orderType !="CustomerOrder"){
						return 32;
					}else{
						return 22;
					}					
				}
			})
            .attr('fill', 'black')
		    .text(function(d) {
				if(d && d.status){				
					return d.status;
				}
			})
			.on("click", function(d) { 
				toggle(d);
			});	
				
		function toggle(d) {
		  if (d && d.orderHeaderKey) {
			var newOrderModel = null;
			newOrderModel = _scModelUtils.createNewModelObjectWithRootKey("Order");
			_scModelUtils.setStringValueAtModelPath("Order.OrderNo", d.orderNo, newOrderModel);
			_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey", d.orderHeaderKey, newOrderModel);
			_scModelUtils.setStringValueAtModelPath("Order.DraftOrderFlag", d.draftOrderFlag, newOrderModel);
			_scModelUtils.setStringValueAtModelPath("Order.DocumentType", d.documentType, newOrderModel);
			_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode", d.enterpriseCode, newOrderModel);
			_isccsOrderUtils.openOrder(that, newOrderModel);
		  }
		}
			
		//Redraw for zoom
		function redraw() {
		  //console.log("here", d3.event.translate, d3.event.scale);
		  svg.attr("transform",
			  "translate(" + d3.event.translate + ")"
			  + " scale(" + d3.event.scale + ")");
		}

		//For bracket diagram
		function elbow(d, i) {
		  return "M" + d.source.y + "," + d.source.x
			   + "H" + d.target.y + "V" + d.target.x
			   + (d.target.children.length ? "" : "h" + 110);
		}
		
		
	
	//legend   extn_treeLegend_contentpane
		
	svgLegend.append("image")
		   .attr("id", "bg")
		   .attr("xlink:href",  _scApplicationInfo.getApplicationContext() +"/extn/isccs/resources/css/icons/images/redPersonCust.png")
		    .attr("x", 50)
			.attr("y", 0)
			.attr("width", 30)
			.attr("height", 30);

	svgLegend.append("image")
		   .attr("id", "bg")
		   .attr("xlink:href",  _scApplicationInfo.getApplicationContext() +"/extn/isccs/resources/css/icons/images/greenProdTag.png")
		    .attr("x", 250)
			.attr("y", 0)
			.attr("width", 30)
			.attr("height", 30);
			
	svgLegend.append("image")
		   .attr("id", "bg")
		   .attr("xlink:href",  _scApplicationInfo.getApplicationContext() +"/extn/isccs/resources/css/icons/images/purpleWheelService.png")
		    .attr("x", 450)
			.attr("y", 0)
			.attr("width", 30)
			.attr("height", 30);

	svgLegend.append("text")
		    .style("text-anchor", "left")
			.attr('x', 100)
			.attr("font-size","15px")
			//.attr("font-family", "Arial")
            .attr('y', 20)
            .attr('fill', 'black')
			//.attr("font-weight","bold")
			.attr("font-family", "calibri")
		    .text("Customer Order");

	svgLegend.append("text")
		    .style("text-anchor", "left")
			.attr('x', 300)
			.attr("font-size","15px")
			//.attr("font-family", "Arial")
            .attr('y', 20)
            .attr('fill', 'black')
			//.attr("font-weight","bold")
			.attr("font-family", "calibri")
		    .text("Product Order");
	
	svgLegend.append("text")
		    .style("text-anchor", "left")
			.attr('x', 500)
			.attr("font-size","15px")
			//.attr("font-family", "Arial")
            .attr('y', 20)
            .attr('fill', 'black')
			//.attr("font-weight","bold")
			.attr("font-family", "calibri")
		    .text("Service Order");	

	svgLegend.append("path")
			.attr("fill","none")
			.attr("stroke","black")
			.attr("stroke-width",2)
			.attr("shape-rendering", "crispEdges")
			.attr("d",function(d) {
				return "M" + 40 + "," + 40
			   + "H" + 590;
			});			

						
		
	},	
	
	/*
	*
	*Called when Retry Validation button is clicked.
	*
	*/
	onClickRetryValidation:function(event,bEvent,ctrl,args){
		var orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var sOrderHeaderKey = _scModelUtils.getStringValueFromPath("Order.OrderHeaderKey",orderModel);
			var sOrderNo = _scModelUtils.getStringValueFromPath("Order.OrderNo",orderModel);
			var sEnterpriseCode = _scModelUtils.getStringValueFromPath("Order.EnterpriseCode",orderModel);
			var sDocumentType = _scModelUtils.getStringValueFromPath("Order.DocumentType",orderModel);
			if(sOrderHeaderKey){
				var orderModeInput = _scBaseUtils.getNewBeanInstance();
				_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",sOrderHeaderKey,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.OrderNo",sOrderNo,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode",sEnterpriseCode,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.DocumentType",sDocumentType,orderModeInput);
				_isccsUIUtils.callApi(this,orderModeInput,"extn_RetryValidationService",null);
			}			
		}
	},
	
	/*
	*
	*Called when Mark as complete button is clicked.
	*
	*/
	onClickMarkAsComplete:function(event,bEvent,ctrl,args){
		_scScreenUtils.showConfirmMessageBox(this,_scScreenUtils.getString(
                    this, "extn_MarkAscompleteConfirmationMsg"),"markAsCompleteHandler",null,null);
	},
      
	markAsCompleteHandler:function(response,args){
		if(_scBaseUtils.equals(response,"Ok")){
		var orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var sOrderHeaderKey = _scModelUtils.getStringValueFromPath("Order.OrderHeaderKey",orderModel);
			var sOrderNo = _scModelUtils.getStringValueFromPath("Order.OrderNo",orderModel);
			var sEnterpriseCode = _scModelUtils.getStringValueFromPath("Order.EnterpriseCode",orderModel);
			var sDocumentType = _scModelUtils.getStringValueFromPath("Order.DocumentType",orderModel);
			if(sOrderHeaderKey){
				var orderModeInput = _scBaseUtils.getNewBeanInstance();
				_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",sOrderHeaderKey,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.OrderNo",sOrderNo,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode",sEnterpriseCode,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.DocumentType",sDocumentType,orderModeInput);
					_scModelUtils.setStringValueAtModelPath("Order.RequestStatus","Complete",orderModeInput);
					_scModelUtils.setStringValueAtModelPath("Order.Status","1100.080",orderModeInput);
				_isccsUIUtils.callApi(this,orderModeInput,"extn_UpdateServiceOrderStatus",null);
			}			
		}
		}
		
	},
	
	/*
	*
	*Called when Retry button is clicked.
	*
	*/
	onClickRetrybtn:function(event,bEvent,ctrl,args){
		_scScreenUtils.showConfirmMessageBox(this,_scScreenUtils.getString(
                    this, "extn_RetryButtonConfirmationMsg"),"retryHandler",null,null);
		
	},
	
	/*
	*
	*Called as callback method after Confirm message for Retry Button
	*
	*/
	retryHandler:function(response,args){
		if(_scBaseUtils.equals(response,"Ok")){
		var orderModel = _scScreenUtils.getModel(this,"getCompleteOrderDetails_output");
		if(orderModel){
			var sOrderHeaderKey = _scModelUtils.getStringValueFromPath("Order.OrderHeaderKey",orderModel);
			var sOrderNo = _scModelUtils.getStringValueFromPath("Order.OrderNo",orderModel);
			var sEnterpriseCode = _scModelUtils.getStringValueFromPath("Order.EnterpriseCode",orderModel);
			var sDocumentType = _scModelUtils.getStringValueFromPath("Order.DocumentType",orderModel);
			if(sOrderHeaderKey){
				var orderModeInput = _scBaseUtils.getNewBeanInstance();
				_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",sOrderHeaderKey,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.OrderNo",sOrderNo,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode",sEnterpriseCode,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.DocumentType",sDocumentType,orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.Status","1100.020",orderModeInput);
				_scModelUtils.setStringValueAtModelPath("Order.RequestStatus","Retry",orderModeInput);
				_isccsUIUtils.callApi(this,orderModeInput,"extn_UpdateServiceOrderStatus",null);
			}			
		}
	}
	}

});
});

