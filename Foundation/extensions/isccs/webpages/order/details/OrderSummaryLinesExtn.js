scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/order/details/OrderSummaryLinesExtnUI"
		,"scbase/loader!sc/plat/dojo/utils/BaseUtils"
		,"scbase/loader!isccs/utils/UIUtils"
		,"scbase/loader!sc/plat/dojo/utils/ScreenUtils"
		,"scbase/loader!sc/plat/dojo/utils/ModelUtils"
		,"scbase/loader!isccs/utils/BaseTemplateUtils"
		,"scbase/loader!sc/plat/dojo/utils/GridxUtils"
		,"scbase/loader!sc/plat/dojo/utils/EventUtils"
		,"scbase/loader!isccs/utils/OrderUtils"
		,"scbase/loader!dojo/_base/lang"
		]
,
function(			 
			    _dojodeclare
			 ,
			    _extnOrderSummaryLinesExtnUI
			 , 	
				_scBaseUtils
			 ,
			    _isccsUIUtils
			 ,
			    _scScreenUtils
			 ,
			    _scModelUtils
			 ,
			    _isccsBaseTemplateUtils
			 ,
				_scGridxUtils
			 , 
				_scEventUtils
			 , 
				_isccsOrderUtils
			 ,
				dLang
){ 
	return _dojodeclare("extn.order.details.OrderSummaryLinesExtn", [_extnOrderSummaryLinesExtnUI],{


	/*
	 *Hide columns based on OrderType 	
	 */
	extnAfterScreenInit:function(event,bEvent,ctrl,args){
		var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineList_output");
		if(!_scBaseUtils.isVoid(orderLineModel)){			
			var sOrderType = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.OrderType",orderLineModel);
			if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType, "BuildPlan")){
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "PrimeLineNo",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_Action",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_tblColDesc",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "UnitPrice",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "Quantity",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "DeliveryMethod",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "ExpectedOn",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "Status",null);				
			}else{
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_DependentOn",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_OrderType",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_OrderNo",null);
				_scGridxUtils.hideTableColumn(this, "OLST_listGrid", "extn_LineSeqNo",null);				
			}	
			_scGridxUtils.refreshGridxLayout(this, "OLST_listGrid");
		}
	},
	
	/*
	 *Returns LinkedOrderNo value for LinkageType as ORDER.
	 */
	getBuildPlanOrderNo:function(event,bEvent,ctrl,args){
		return this.getStringBasedOnLinkageType(args,"LinkedOrderNo","ORDER");
	},
	
	/*
	 *Returns LinkedOrderLineType value for LinkageType as ORDER.
	 */
	getBuildPlanOrderType:function(event,bEvent,ctrl,args){
		return this.getStringBasedOnLinkageType(args,"LinkedOrderLineType","ORDER");
	},
	
	/*
	 *Populate list of LinkedCustomerPONo in an order line. 
	 */
	getBuildPlanDepandentOnList:function(event,bEvent,ctrl,args){
		var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineList_output");
		if(!_scBaseUtils.isVoid(orderLineModel)){
			var sOrderType = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.OrderType",orderLineModel);
			if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType, "BuildPlan")){
				if(args){
					var EXTNOrderLineLinksList = _scModelUtils.getStringValueFromPath("Extn.EXTNOrderLineLinksList.EXTNOrderLineLinks",args);
					if(EXTNOrderLineLinksList){
						var sCompleteLinkedCustomerPONo = null;
						var	length = 0;
						length = EXTNOrderLineLinksList.length;				
						for (i=0;length>i; i++){
							var eEXTNOrderLineLinks = EXTNOrderLineLinksList[i];
							if(eEXTNOrderLineLinks){
								var sLinkageType = _scModelUtils.getStringValueFromPath("LinkageType", eEXTNOrderLineLinks);
								if(!_scBaseUtils.isVoid(sLinkageType) && _scBaseUtils.equals(sLinkageType,"FINISH_TO_START")){
									var sLinkedCustomerPONo = _scModelUtils.getStringValueFromPath("LinkedOrderItem", eEXTNOrderLineLinks);
									if(!_scBaseUtils.isVoid(sLinkedCustomerPONo)){
										if(_scBaseUtils.isVoid(sCompleteLinkedCustomerPONo)){
											sCompleteLinkedCustomerPONo = sLinkedCustomerPONo;
										}else{
											sCompleteLinkedCustomerPONo = sCompleteLinkedCustomerPONo + ",\n" +  sLinkedCustomerPONo;
										}
									}								
								}
							}						
						}
						return sCompleteLinkedCustomerPONo;
					}
				}			
			}
		}
	},
	
	getStringBasedOnLinkageType:function(args,sValueToReturn,sLinkageTypeToValidate){
		var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineList_output");
		if(!_scBaseUtils.isVoid(orderLineModel)){
			var sOrderType = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.OrderType",orderLineModel);
			if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType, "BuildPlan")){
				if(args){
					var EXTNOrderLineLinksList = _scModelUtils.getStringValueFromPath("Extn.EXTNOrderLineLinksList.EXTNOrderLineLinks",args);
					if(EXTNOrderLineLinksList){
						var	length = 0;
						length = EXTNOrderLineLinksList.length;				
						for (i=0;length>i; i++){
							var eEXTNOrderLineLinks = EXTNOrderLineLinksList[i];
							if(eEXTNOrderLineLinks){
								var sLinkageType = _scModelUtils.getStringValueFromPath("LinkageType", eEXTNOrderLineLinks);
								if(!_scBaseUtils.isVoid(sLinkageType) && _scBaseUtils.equals(sLinkageType,sLinkageTypeToValidate)){
									return _scModelUtils.getStringValueFromPath(sValueToReturn, eEXTNOrderLineLinks);;
								}
							}
							
						}
					}
				}
				
			}
		}
		return "";
	},
	
	
	/*
	 * Create input to getOrderDetails API and call API.
     * This gets order details of selected link required to open the order in order summary screen.
	 */
	OpenBuildJobDetails:function(event,bEvent,ctrl,args){
		var cellJson = _scBaseUtils.getAttributeValue("cellJson",false,args);
		var screen = _scEventUtils.getScreenFromEventArguments(args);
		//setting additional data  for link image fields
		var cellJsonData = _scBaseUtils.getAttributeValue("cellJsonData",false,args);
		var itemData = _scBaseUtils.getAttributeValue("item",false,args);
		if (!_scBaseUtils.isVoid(itemData) && _scBaseUtils.isVoid(cellJsonData))
		_scBaseUtils.setAttributeValue("cellJsonData", itemData, args);
		var uniqueRowId = _scBaseUtils.getAttributeValue("uniqueRowId",false,args);
		var rowIndex = _scBaseUtils.getAttributeValue("rowIndex",false,args);
		if (!_scBaseUtils.isVoid(rowIndex) && _scBaseUtils.isVoid(uniqueRowId))
		_scBaseUtils.setAttributeValue("uniqueRowId", rowIndex, args);
		//
		if (!(_scBaseUtils.isVoid(cellJson))){
			var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineList_output");
			var item = _scBaseUtils.getAttributeValue("item",false,args);
			if (_scBaseUtils.equals("extn_OrderNo", _scBaseUtils.getAttributeValue("colField",false,cellJson))){				
				var sEnterpriseCode = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.EnterpriseCode",orderLineModel);
				var sLinkedOrderNo = this.getStringBasedOnLinkageType(args.item,"LinkedOrderNo","ORDER");
				if(!_scBaseUtils.isVoid(sLinkedOrderNo) 
						&& !_scBaseUtils.isVoid(sEnterpriseCode)){
					var newOrderModel = null;
					newOrderModel = _scModelUtils.createNewModelObjectWithRootKey("Order");
					_scModelUtils.setStringValueAtModelPath("Order.OrderNo", sLinkedOrderNo, newOrderModel);
					//_scModelUtils.setStringValueAtModelPath("Order.DraftOrderFlag", "N", newOrderModel);
					_scModelUtils.setStringValueAtModelPath("Order.DocumentType", "0001", newOrderModel);
					_scModelUtils.setStringValueAtModelPath("Order.EnterpriseCode", sEnterpriseCode, newOrderModel);
					_isccsUIUtils.callApi(this,newOrderModel,"extn_OrderSummaryLins_getCompleteOrderDetails",null)
					_scEventUtils.stopEvent(bEvent);
				}
				//_isccsOrderUtils.openOrder(screen, eDerivedOrder);
			}
			if(!_scBaseUtils.isVoid(orderLineModel)){			
				var sOrderType = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.OrderType",orderLineModel);
				if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType, "BuildPlan")){
					_scEventUtils.stopEvent(bEvent);
				}
			}
		}	
	},
	
	onExtnMashupCompletion:function(event, bEvent, ctrl, args){			
		var mashupRefs = null;
		var length = 0;
		var mashupRefId = null;
		var curmashupArrayObj = null;
		var mashupRefOutput = null;
		if(args){
			mashupRefs = dLang.getObject("mashupArray", false, args);
			if(mashupRefs){
				length = mashupRefs.length;	
				for (
				var counter = 0;
				_scBaseUtils.lessThan(
				counter, length);
				counter = _scBaseUtils.incrementNumber(
				counter, 1)){
					curmashupArrayObj = mashupRefs[counter];
					if(curmashupArrayObj){
						mashupRefId =	dLang.getObject("mashupRefId", false, curmashupArrayObj);
						if((_scBaseUtils.equals(mashupRefId,"extn_OrderSummaryLins_getCompleteOrderDetails"))){
							mashupRefOutput = dLang.getObject("mashupRefOutput", false, curmashupArrayObj);	
							if(mashupRefOutput){
								_isccsOrderUtils.openOrder(this, mashupRefOutput);
								//_isccsUIUtils.openWizardInEditor("isccs.order.wizards.orderSummary.OrderSummaryWizard", mashupRefOutput,"isccs.editors.OrderEditor",this);
							}							
						}
						if(_scBaseUtils.equals(mashupRefId,"extn_OrderSummaryLins_getOrderList")){
							mashupRefOutput = dLang.getObject("mashupRefOutput", false, curmashupArrayObj);
					           _scScreenUtils.setModel(this, "extn_OrderSummaryLins_getOrderList_output", mashupRefOutput, null);
						}
					}
				}
			}
		}
	},
	
	StopClickEvent:function(event, bEvent, ctrl, args){
		var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineList_output");
		if(!_scBaseUtils.isVoid(orderLineModel)){			
			var sOrderType = _scModelUtils.getStringValueFromPath("Page.Output.OrderLineList.Order.OrderType",orderLineModel);
			if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType, "BuildPlan")){
				_scEventUtils.stopEvent(bEvent);
			}
		}
	},
});
});

