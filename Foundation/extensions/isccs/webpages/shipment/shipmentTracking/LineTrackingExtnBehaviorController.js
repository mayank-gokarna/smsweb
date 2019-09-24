


scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/text","scbase/loader!extn/shipment/shipmentTracking/LineTrackingExtn","scbase/loader!sc/plat/dojo/controller/ExtnServerDataController"]
 , function(			 
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojotext
			 ,
			    _extnLineTrackingExtn
			 ,
			    _scExtnServerDataController
){

return _dojodeclare("extn.shipment.shipmentTracking.LineTrackingExtnBehaviorController", 
				[_scExtnServerDataController], {

			
			 screenId : 			'extn.shipment.shipmentTracking.LineTrackingExtn'

			
			
			
			
			
						,

			
			
			 mashupRefs : 	[
	 		{
		 extnType : 			'MODIFY'
,
		 mashupId : 			'lineTrack_getCompleteOrderLineDetails_Output'
,
		 mashupRefId : 			'getCompleteOrderLineDetails'

	}

	]

}
);
});

