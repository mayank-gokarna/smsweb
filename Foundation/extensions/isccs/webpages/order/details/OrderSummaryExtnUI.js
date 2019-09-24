
scDefine(["dojo/text!./templates/OrderSummaryExtn.html","scbase/loader!dijit/form/Button","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!extn/extnUtils/CSGUtils","scbase/loader!gridx/Grid","scbase/loader!gridx/modules/CellWidget","scbase/loader!gridx/modules/ColumnResizer","scbase/loader!gridx/modules/ColumnWidth","scbase/loader!gridx/modules/Edit","scbase/loader!gridx/modules/HLayout","scbase/loader!gridx/modules/select/Row","scbase/loader!idx/form/TextBox","scbase/loader!idx/layout/ContentPane","scbase/loader!idx/layout/TitlePane","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/ButtonDataBinder","scbase/loader!sc/plat/dojo/binding/CurrencyDataBinder","scbase/loader!sc/plat/dojo/binding/GridxDataBinder","scbase/loader!sc/plat/dojo/layout/AdvancedTableLayout","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!sc/plat/dojo/widgets/DataLabel","scbase/loader!sc/plat/dojo/widgets/Link"]
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
			    _gridxCellWidget
			 ,
			    _gridxColumnResizer
			 ,
			    _gridxColumnWidth
			 ,
			    _gridxEdit
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
			    _scCurrencyDataBinder
			 ,
			    _scGridxDataBinder
			 ,
			    _scAdvancedTableLayout
			 ,
			    _scBaseUtils
			 ,
			    _scDataLabel
			 ,
			    _scLink
){
return _dojodeclare("extn.order.details.OrderSummaryExtnUI",
				[], {
			templateString: templateText
	
	
	
	
	
	
	
					,	
	namespaces : {
		targetBindingNamespaces :
		[
			{
	  value: 'extn_ChangeOrderInput'
						,
	  scExtensibilityArrayItemId: 'extn_TargetNamespaces_1'
						
			}
			
		],
		sourceBindingNamespaces :
		[
			{
	  value: 'extn_orderMoreCharactersticks'
						,
	  scExtensibilityArrayItemId: 'extn_SourceNamespaces_3'
						
			}
			,
			{
	  value: 'extn_TestNS'
						,
	  scExtensibilityArrayItemId: 'extn_SourceNamespaces_4'
						
			}
			,
			{
	  value: 'extn_GetOrderHierarchy_output'
						,
	  scExtensibilityArrayItemId: 'extn_SourceNamespaces_5'
						
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
	  eventId: 'onExtnMashupCompletion'

,	  sequence: '51'




,handler : {
methodName : "handleMashupCompletion"

 
}
}
,
{
	  eventId: 'extn_contentpane_test_onShow'

,	  sequence: '51'




,handler : {
methodName : "loadTestData"

 
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
	  eventId: 'extn_Retry_Validation_btn_onClick'

,	  sequence: '51'




,handler : {
methodName : "onClickRetryValidation"

 
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
	  eventId: 'extn_Retrybtn_onClick'

,	  sequence: '51'




,handler : {
methodName : "onClickRetrybtn"

 
}
}

]
}

});
});


