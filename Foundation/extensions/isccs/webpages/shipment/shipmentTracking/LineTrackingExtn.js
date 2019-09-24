
scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/shipment/shipmentTracking/LineTrackingExtnUI","scbase/loader!isccs/utils/BaseTemplateUtils", "scbase/loader!sc/plat", "scbase/loader!sc/plat/dojo/utils/BaseUtils", "scbase/loader!sc/plat/dojo/utils/EventUtils", "scbase/loader!sc/plat/dojo/utils/ModelUtils", "scbase/loader!sc/plat/dojo/utils/ScreenUtils", "scbase/loader!sc/plat/dojo/widgets/Screen"]
,
function(			 
			    _dojodeclare
			 ,
			    _extnLineTrackingExtnUI, _isccsBaseTemplateUtils, _scplat, _scBaseUtils, _scEventUtils, _scModelUtils, _scScreenUtils, _scScreen
){ 
	return _dojodeclare("extn.shipment.shipmentTracking.LineTrackingExtn", [_extnLineTrackingExtnUI],{
	 
	   handleMashupOutput: function(
        mashupRefId, modelOutput, mashupInput, mashupContext, applySetModel) {
            if (
            _scBaseUtils.equals(
            mashupRefId, "getCompleteOrderLineDetails")) {
                var setModelOptions = null;
                setModelOptions = {};
                _scBaseUtils.setAttributeValue("clearOldVals", true, setModelOptions);
               
                // set Tracking URL
                var orderLineBreakups =  _scBaseUtils.getValueFromPath("OrderLine.LineTracking.LineBreakups.LineBreakup", modelOutput);

                for(var i = 0; i < orderLineBreakups.length ; i++){
                	var orderLineBreakup =  orderLineBreakups[i];
					var shipmentLine = orderLineBreakup.ShipmentLine;
					if(!( _scBaseUtils.isVoid(shipmentLine))){
						var shipment = shipmentLine.Shipment;
						var trackingUrl = shipment.Extn.ExtnTrackingUrl;
						if(!( _scBaseUtils.isVoid(trackingUrl))){
							applySetModel = true;
						  _scModelUtils.setStringValueAtModelPath("URL", trackingUrl, shipment);
						   _scModelUtils.setStringValueAtModelPath("TrackingNo", shipment.ShipmentNo, shipment);
						}	
					}
					
                }
				
                if (!(
                _scBaseUtils.equals(
                false, applySetModel))) {
                    _scScreenUtils.setModel(
                    this, "getCompleteOrderLineDetails_Output", modelOutput, null);
                }
				
				
                var alterationCount = null;
                alterationCount = _scModelUtils.getNumberValueFromPath("OrderLine.AlterationCount", modelOutput);
                var isOnHold = null;
                isOnHold = _scBaseUtils.getValueFromPath("OrderLine.Order.HoldFlag", modelOutput);
                if (!(
                _scBaseUtils.equals(
                this.skipCSRMessages, true))) {
                    if (
                    alterationCount > 0) {
                        var test1 = null;
                    } else if (
                    _scBaseUtils.equals("Y", isOnHold)) {
                        var test2 = null;
                    } else {
                        var test3 = null;
                    }
                }
            }
        }
});
});

