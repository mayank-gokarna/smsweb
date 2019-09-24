


scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/text","scbase/loader!extn/order/details/OrderSummaryExtn","scbase/loader!sc/plat/dojo/controller/ExtnServerDataController"]
 , function(			 
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojotext
			 ,
			    _extnOrderSummaryExtn
			 ,
			    _scExtnServerDataController
){

return _dojodeclare("extn.order.details.OrderSummaryExtnBehaviorController", 
				[_scExtnServerDataController], {

			
			 screenId : 			'extn.order.details.OrderSummaryExtn'

			
			
			
			
			
						,

			
			
			 mashupRefs : 	[
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'orderSummary_getCompleteOrderDetails'
,
		 mashupRefId : 			'getCompleteOrderDetailsBehavior'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_getOrderList'
,
		 mashupRefId : 			'extn_getOrderList'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_OrderSummaryLins_getOrderList'
,
		 mashupRefId : 			'extn_OrderSummaryLins_getOrderList'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_RetryValidationService'
,
		 mashupRefId : 			'extn_RetryValidationService'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_UpdateServiceOrderStatus'
,
		 mashupRefId : 			'extn_UpdateServiceOrderStatus'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_GetOrderHierarchyBehavior'
,
		 mashupRefId : 			'extn_GetOrderHierarchyBehavior'

	}
,
	 		{
		 extnType : 			'ADD'
,
		 mashupId : 			'extn_OLCChangeOrder'
,
		 mashupRefId : 			'extn_OHSCChangeOrder'

	}

	]

}
);
});

