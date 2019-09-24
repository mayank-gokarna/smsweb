scDefine([
		"scbase/loader!dojo/_base/declare",
		"scbase/loader!dojo/_base/lang", 
		"scbase/loader!extn"
],
function(
		_dojodeclare,
		dLang,
		extnPkg
) {
	var _CSGUtil = dLang.getObject("extnUtils.CSGUtils", true, extnPkg);
	
	_CSGUtil.getHTMLforLinkedOrderNo = function(gridReference, rowIndex, columnIndex, gridRowJSON, unformattedValue, value, gridRowRecord, colConfig ){
		var scr = gridReference.ownerScreen;
		var linkUID = "extn_view_OrderNo";
		var linkText = value;
		return '<a href="javascript:void(0)" title="'+ linkText + '" linkUID="'+ linkUID +'">'+linkText+'</a>';

	};	

	return _CSGUtil;
	
});
