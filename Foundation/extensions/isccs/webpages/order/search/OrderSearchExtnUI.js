
scDefine(["dojo/text!./templates/OrderSearchExtn.html","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!idx/form/RadioButtonSet","scbase/loader!idx/form/TextBox","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/RadioSetDataBinder","scbase/loader!sc/plat/dojo/binding/SimpleDataBinder","scbase/loader!sc/plat/dojo/utils/BaseUtils"]
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
			    _idxRadioButtonSet
			 ,
			    _idxTextBox
			 ,
			    _scplat
			 ,
			    _scRadioSetDataBinder
			 ,
			    _scSimpleDataBinder
			 ,
			    _scBaseUtils
){
return _dojodeclare("extn.order.search.OrderSearchExtnUI",
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
	  eventId: 'beforeBehaviorMashupCall'

,	  sequence: '51'




,handler : {
methodName : "customBeforeBehaviourMashup"

 
}
}
,
{
	  eventId: 'extn_txt_custEmailId_onKeyUp'

,	  sequence: '51'




,handler : {
methodName : "SST_invokeApiOnEnter"

 
}
}
,
{
	  eventId: 'extn_txt_custPhone_onKeyUp'

,	  sequence: '51'




,handler : {
methodName : "SST_invokeApiOnEnter"

 
}
}
,
{
	  eventId: 'extn_txt_postalCode_onKeyUp'

,	  sequence: '51'




,handler : {
methodName : "SST_invokeApiOnEnter"

 
}
}

]
}

});
});


