


scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/text","scbase/loader!extn/return/details/ReturnSummaryExtn","scbase/loader!sc/plat/dojo/controller/ExtnServerDataController"]
 , function(			 
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojotext
			 ,
			    _extnReturnSummaryExtn
			 ,
			    _scExtnServerDataController
){

return _dojodeclare("extn.return.details.ReturnSummaryExtnBehaviorController", 
				[_scExtnServerDataController], {

			
			 screenId : 			'extn.return.details.ReturnSummaryExtn'

			
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

