
scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/editors/OrderEditorRTExtnUI"
		,"scbase/loader!sc/plat/dojo/utils/ScreenUtils"
		,"scbase/loader!sc/plat/dojo/utils/BaseUtils"
		,"scbase/loader!isccs/utils/UIUtils"
		,"scbase/loader!sc/plat/dojo/utils/ModelUtils"
		,"scbase/loader!isccs/utils/BaseTemplateUtils"
		,"scbase/loader!sc/plat/dojo/utils/GridxUtils"
		,"scbase/loader!sc/plat/dojo/utils/EventUtils"
		,"scbase/loader!isccs/utils/OrderUtils"
		,"scbase/loader!dojo/_base/lang"
		,"scbase/loader!sc/plat/dojo/utils/EditorUtils"
		, "scbase/loader!sc/plat/dojo/utils/WidgetUtils"]
,
function(			 
			    _dojodeclare
			 ,
			    _extnOrderEditorRTExtnUI
			 , 	
				_scScreenUtils
			 , 	
				_scBaseUtils
			 ,
			    _isccsUIUtils

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
			 ,
				_scEditorUtils
			 ,	
				_scWidgetUtils
){ 
	return _dojodeclare("extn.editors.OrderEditorRTExtn", [_extnOrderEditorRTExtnUI],{
	// custom code here
	
	//hide View Build Plan Link based on OrderType CustomerOrder
	hideViewBuildPlanDetails:function(event,bEvent,ctrl,args){
		var hideViewBuildPlanDetailsWidget = true;
		var initialInput = null;
        initialInput = _scScreenUtils.getInitialInputData(
        _scEditorUtils.getCurrentEditor());
		if(initialInput){
			var sOrderType = _scModelUtils.getStringValueFromPath("Order.OrderType", initialInput);
			if(!_scBaseUtils.isVoid(sOrderType) && _scBaseUtils.equals(sOrderType,"CustomerOrder")){
				var length = 0;
				var arrEXTNOrderHeaderLinks = _scModelUtils.getStringValueFromPath("Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks", initialInput);
				if(arrEXTNOrderHeaderLinks){
					length = arrEXTNOrderHeaderLinks.length;				
					for (i=0;length>i; i++){
						var eEXTNOrderHeaderLink = arrEXTNOrderHeaderLinks[i];
						//LinkedOrderType="BuildPlan"
						if(eEXTNOrderHeaderLink){
							var sLinkedOrderType = _scModelUtils.getStringValueFromPath("LinkedOrderType", eEXTNOrderHeaderLink);
							if(!_scBaseUtils.isVoid(sLinkedOrderType) && _scBaseUtils.equals(sLinkedOrderType,"BuildPlan")){
								//Show widget
								hideViewBuildPlanDetailsWidget = false;
								break;
							}	
						}
						
					}
				}
			}
		}

		if(hideViewBuildPlanDetailsWidget){
			_scWidgetUtils.hideWidget(this, "extn_BuilPlanLink", true);
		}else{
			_scWidgetUtils.showWidget(this, "extn_BuilPlanLink");
		}
	},	
	
	//open build plan order details
	openBuildPlanDetails:function(event,bEvent,ctrl,args){
		var initialInput = null;
        initialInput = _scScreenUtils.getInitialInputData(
        _scEditorUtils.getCurrentEditor());
		//var orderModel = _scScreenUtils.getModel(this,"extn_getCompleteOrderDetails_output");
		//Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks
		if(initialInput){
			var length = 0;
			var arrEXTNOrderHeaderLinks = _scModelUtils.getStringValueFromPath("Order.Extn.EXTNOrderHeaderLinksList.EXTNOrderHeaderLinks", initialInput);
			if(arrEXTNOrderHeaderLinks){
				length = arrEXTNOrderHeaderLinks.length;				
				for (i=0;length>i; i++){
					var eEXTNOrderHeaderLink = arrEXTNOrderHeaderLinks[i];
					//LinkedOrderType="BuildPlan"
					var sLinkedOrderType = _scModelUtils.getStringValueFromPath("LinkedOrderType", eEXTNOrderHeaderLink);
					if(!_scBaseUtils.isVoid(sLinkedOrderType) && _scBaseUtils.equals(sLinkedOrderType,"BuildPlan")){
						var sLinkedOrderHeaderKey = _scModelUtils.getStringValueFromPath("LinkedOrderHeaderKey", eEXTNOrderHeaderLink);
						if(sLinkedOrderHeaderKey){
							var newOrderModel = null;
							newOrderModel = _scModelUtils.createNewModelObjectWithRootKey("Order");
							_scModelUtils.setStringValueAtModelPath("Order.DraftOrderFlag", "N", newOrderModel);
							_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey", sLinkedOrderHeaderKey, newOrderModel);
							//_isccsUIUtils.callApi(that,newOrderModel,"getCompleteOrderDetailsBehavior",null);
							_isccsOrderUtils.openOrder(this, newOrderModel);
						}
					}
				}
			}
		}
	},
});
});

