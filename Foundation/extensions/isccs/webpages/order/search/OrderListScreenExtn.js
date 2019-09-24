scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/order/search/OrderListScreenExtnUI","scbase/loader!sc/plat/dojo/utils/ModelUtils","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!sc/plat/dojo/utils/ScreenUtils", "scbase/loader!isccs/utils/ModelUtils"]
,
function(			 
			    _dojodeclare
			 ,
			    _extnOrderListScreenExtnUI,_scModelUtils, scBaseUtils, scScreenUtils, dModelUtils
){ 
	return _dojodeclare("extn.order.search.OrderListScreenExtn", [_extnOrderListScreenExtnUI],{
	
	customBeforeBehaviourMashup: function(
		event, bEvent, ctrl, args) {
		var mashupRefsList = _scModelUtils.getModelObjectFromPath("mashupRefs", args);
		if(!scBaseUtils.isVoid(mashupRefsList)){
			var mashupRefsLength = Object.keys(mashupRefsList).length;
				for ( var counter = 0; counter < mashupRefsLength; counter++ ) {
					var mashupRef = mashupRefsList[counter];
					var mashupRefId = _scModelUtils.getModelObjectFromPath("mashupRefId", mashupRef);
					if(scBaseUtils.equals(mashupRefId,"getOrderList")){
						var sOrderMashupInput = _scModelUtils.getModelObjectFromPath("mashupInputObject", mashupRef);
						var sOrderType = _scModelUtils.getModelObjectFromPath("Order.OrderType", sOrderMashupInput);
						var sOrderStatus = _scModelUtils.getModelObjectFromPath("Order.Status", sOrderMashupInput);
						if("CustomerOrder" != sOrderType){
							dModelUtils.removeAttributeFromModel("Order.OrderType", sOrderMashupInput);
						}
						if("All" == sOrderStatus){
							dModelUtils.removeAttributeFromModel("Order.Status", sOrderMashupInput);
						}
					}
				}	
		}

	}
});
});

