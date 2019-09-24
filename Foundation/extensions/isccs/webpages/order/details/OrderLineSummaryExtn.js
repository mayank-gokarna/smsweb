scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/order/details/OrderLineSummaryExtnUI","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!isccs/utils/UIUtils","scbase/loader!sc/plat/dojo/utils/ScreenUtils","scbase/loader!sc/plat/dojo/utils/ModelUtils","scbase/loader!isccs/utils/BaseTemplateUtils"]
,
function(			 
			    _dojodeclare,
			    _extnOrderLineSummaryExtnUI,
			    _scBaseUtils,
			    _isccsUIUtils,
			    _scScreenUtils,
			    _scModelUtils,
			    _isccsBaseTemplateUtils
){ 
	return _dojodeclare("extn.order.details.OrderLineSummaryExtn", [_extnOrderLineSummaryExtnUI],{


	// custom code here
	invokeChangeOrder:function(event,bEvent,ctrl,args){
		var changeOrderInputModel= _scScreenUtils.getTargetModel(this,"extn_ChangeOrderInput",null);
		var orderLineModel = _scScreenUtils.getModel(this,"getCompleteOrderLineDetails_output");
		_scModelUtils.setStringValueAtModelPath("Order.Override","Y",changeOrderInputModel);
		_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",_scModelUtils.getStringValueFromPath("OrderLine.Order.OrderHeaderKey",orderLineModel),changeOrderInputModel);
		_scModelUtils.setStringValueAtModelPath("Order.OrderLines.OrderLine.OrderLineKey",
			_scModelUtils.getStringValueFromPath("OrderLine.OrderLineKey",orderLineModel),changeOrderInputModel);
		_scModelUtils.setStringValueAtModelPath("Order.Action","MODIFY",changeOrderInputModel);

		_scModelUtils.setStringValueAtModelPath("Order.OrderLines.OrderLine.Extn.EXTNOrderLinePropertiesList.EXTNOrderLineProperties.Operation","Modify",changeOrderInputModel);

		_isccsUIUtils.callApi(this,changeOrderInputModel,"extn_OLSCChangeOrder",null);	

	},

	handleMashupCompletion: function(mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data) {
		_isccsBaseTemplateUtils.handleMashupCompletion(mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data, this);
		if(!_scBaseUtils.equals(hasError, true)){
//			_scScreenUtils.disableDirtyCheck(this);
			this.isDirtyCheckRequired = false;
			//_scScreenUtils.reloadScreen(this);
			_scScreenUtils.showInfoMessageBox(this,"Save completed Successfully");
						
		}else{
			_isccsBaseTemplateUtils.showMessage(this, "Save Operation Failed","error", null);
		}
	}
});
});

