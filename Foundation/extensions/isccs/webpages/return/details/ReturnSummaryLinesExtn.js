
scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/return/details/ReturnSummaryLinesExtnUI"]
,
function(			 
			    _dojodeclare
			 ,
			    _extnReturnSummaryLinesExtnUI
){ 
	return _dojodeclare("extn.return.details.ReturnSummaryLinesExtn", [_extnReturnSummaryLinesExtnUI],{
	
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
	}
});
});

