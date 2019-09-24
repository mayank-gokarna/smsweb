
scDefine(["dojo/text!./templates/ReturnSummaryExtn.html","scbase/loader!dijit/form/Button","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!extn/extnUtils/CSGUtils","scbase/loader!gridx/Grid","scbase/loader!gridx/modules/ColumnResizer","scbase/loader!gridx/modules/ColumnWidth","scbase/loader!gridx/modules/HLayout","scbase/loader!gridx/modules/select/Row","scbase/loader!idx/form/TextBox","scbase/loader!idx/layout/ContentPane","scbase/loader!idx/layout/TitlePane","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/ButtonDataBinder","scbase/loader!sc/plat/dojo/binding/GridxDataBinder","scbase/loader!sc/plat/dojo/utils/BaseUtils"]
 , function(			 
			    templateText
			 ,
			    _dijitButton
			 ,
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojolang
			 ,
			    _dojotext
			 ,
			    _extnCSGUtils
			 ,
			    _gridxGrid
			 ,
			    _gridxColumnResizer
			 ,
			    _gridxColumnWidth
			 ,
			    _gridxHLayout
			 ,
			    _gridxRow
			 ,
			    _idxTextBox
			 ,
			    _idxContentPane
			 ,
			    _idxTitlePane
			 ,
			    _scplat
			 ,
			    _scButtonDataBinder
			 ,
			    _scGridxDataBinder
			 ,
			    _scBaseUtils
){
return _dojodeclare("extn.return.details.ReturnSummaryExtnUI",
				[], {
			templateString: templateText
	
	
	
	
	
	
	
					,	
	namespaces : {
		targetBindingNamespaces :
		[
			{
	  value: 'extn_orderMoreCharactersticks'
						,
	  scExtensibilityArrayItemId: 'extn_TargetNamespaces_1'
						
			}
			,
			{
	  value: 'extn_ChangeOrderInput'
						,
	  scExtensibilityArrayItemId: 'extn_TargetNamespaces_2'
						
			}
			
		],
		sourceBindingNamespaces :
		[
			{
	  value: 'extn_orderMoreCharactersticks'
						,
	  scExtensibilityArrayItemId: 'extn_SourceNamespaces_1'
						
			}
			
		]
	}

	
	,
	hotKeys: [ 
	]

,events : [
	]

,subscribers : {

local : [

{
	  eventId: 'afterScreenInit'

,	  sequence: '51'




,handler : {
methodName : "extnAfterScreenInit"

 
}
}
,
{
	  eventId: 'extn_pnlMoreAttributes_onShow'

,	  sequence: '51'




,handler : {
methodName : "loadMoreAttributes"

 
}
}
,
{
	  eventId: 'extn_btnSave_onClick'

,	  sequence: '51'




,handler : {
methodName : "invokeChangeOrder"

 
}
}
,
{
	  eventId: 'extn_Retry_Validation_btn_onClick'

,	  sequence: '51'




,handler : {
methodName : "onClickRetryValidation"

 
}
}
,
{
	  eventId: 'extn_Retrybtn_onClick'

,	  sequence: '51'




,handler : {
methodName : "onClickRetrybtn"

 
}
}
,
{
	  eventId: 'extn_MarkAsComplete_btn_onClick'

,	  sequence: '51'




,handler : {
methodName : "onClickMarkAsComplete"

 
}
}
,
{
	  eventId: 'extn_gridOrderLinks_ScDoubleClick'

,	  sequence: '51'




,handler : {
methodName : "CSGDoubleClickHandler"

 
}
}
,
{
	  eventId: 'extn_gridOrderLinks_Link_ScHandleLinkClicked'

,	  sequence: '51'




,handler : {
methodName : "CSGDoubleClickHandler"

 
}
}

]
}

});
});


