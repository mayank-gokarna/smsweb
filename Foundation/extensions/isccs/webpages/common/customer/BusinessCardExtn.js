
scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/common/customer/BusinessCardExtnUI","scbase/loader!sc/plat/dojo/utils/ModelUtils","scbase/loader!isccs/utils/BaseTemplateUtils","scbase/loader!sc/plat/dojo/utils/ScreenUtils","scbase/loader!sc/plat/dojo/utils/BaseUtils"]
,
function(			 
			    _dojodeclare
			 ,
			    _extnBusinessCardExtnUI,
				_scModelUtils,
				_isccsBaseTemplateUtils,
				_scScreenUtils,
				_scBaseUtils
){ 
	return _dojodeclare("extn.common.customer.BusinessCardExtn", [_extnBusinessCardExtnUI],{
		
	/*
	This method is an override method for getCustomerModel function in BusinessCard.js to avoid calling getCustomerDetails API 
	as customer details link is not required.
	*/
	getCustomerModel: function(
        editorInput) {
            var customerDetailModel = null;
            customerDetailModel = _scScreenUtils.getModel(
            this, "CustomerInfo");
            var sBillTo = null;
            sBillTo = _scModelUtils.getStringValueFromPath("Order.BillToID", editorInput);
            var sCurBillTo = null;
            if (!(
            _scBaseUtils.isVoid(
            customerDetailModel))) {
                sCurBillTo = _scModelUtils.getStringValueFromPath("Customer.CustomerID", customerDetailModel);
            }
            if (!(
            _scBaseUtils.equals(
            sCurBillTo, sBillTo))) {
                var sEntCode = null;
                sEntCode = _scModelUtils.getStringValueFromPath("Order.EnterpriseCode", editorInput);
				this.switchNameDisplay(
                    false);
            }
        }
});
});

