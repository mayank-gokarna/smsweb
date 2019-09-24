
scDefine(["dojo/text!./templates/OrderPortletExtn.html","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!idx/form/TextBox","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/SimpleDataBinder","scbase/loader!sc/plat/dojo/utils/BaseUtils"]
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
			    _idxTextBox
			 ,
			    _scplat
			 ,
			    _scSimpleDataBinder
			 ,
			    _scBaseUtils
){
return _dojodeclare("extn.home.portlets.OrderPortletExtnUI",
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
	  eventId: 'extn_txtPhoneNo_onKeyUp'

,	  sequence: '51'




,handler : {
methodName : "orderSearchOnEnter"

 
}
}
,
{
	  eventId: 'extn_txtEmail_Order_onKeyUp'

,	  sequence: '51'




,handler : {
methodName : "orderSearchOnEnter"

 
}
}

]
}

});
});


