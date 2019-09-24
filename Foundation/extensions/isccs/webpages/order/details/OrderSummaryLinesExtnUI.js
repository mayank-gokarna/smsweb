
scDefine(["dojo/text!./templates/OrderSummaryLinesExtn.html","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!extn/utils/OrderLineUtils","scbase/loader!gridx/Grid","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/GridxDataBinder","scbase/loader!sc/plat/dojo/utils/BaseUtils"]
 , function(			 
			    templateText
			 ,
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojolang
			 ,
			    _dojotext
			 ,
			    _extnOrderLineUtils
			 ,
			    _gridxGrid
			 ,
			    _scplat
			 ,
			    _scGridxDataBinder
			 ,
			    _scBaseUtils
){
return _dojodeclare("extn.order.details.OrderSummaryLinesExtnUI",
				[], {
			templateString: templateText
	
	
	
	
	
	
	
	
	,
	hotKeys: [ 
	]

,events : [
	]

,subscribers : {

local : [

{
	  eventId: 'OLST_listGrid_ScDoubleClick'

,	  sequence: '19'




,handler : {
methodName : "StopClickEvent"

 
}
}
,
{
	  eventId: 'OLST_listGrid_ScRowSelect'

,	  sequence: '19'




,handler : {
methodName : "StopClickEvent"

 
}
}
,
{
	  eventId: 'OLST_listGrid_Link_ScHandleLinkClicked'

,	  sequence: '19'




,handler : {
methodName : "OpenBuildJobDetails"

 
}
}
,
{
	  eventId: 'afterScreenInit'

,	  sequence: '51'




,handler : {
methodName : "extnAfterScreenInit"

 
}
}
,
{
	  eventId: 'onExtnMashupCompletion'

,	  sequence: '51'




,handler : {
methodName : "onExtnMashupCompletion"

 
}
}

]
}

});
});


