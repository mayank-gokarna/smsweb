scDefine(["dojo/text!./templates/OrderPromotion.html", "scbase/loader!dojo/_base/declare", "scbase/loader!dojo/_base/kernel", "scbase/loader!dojo/_base/lang", "scbase/loader!dojo/text", "scbase/loader!idx/layout/ContentPane", "scbase/loader!isccs/utils/BaseTemplateUtils", "scbase/loader!sc/plat", "scbase/loader!sc/plat/dojo/binding/CurrencyDataBinder", "scbase/loader!sc/plat/dojo/binding/ImageDataBinder", "scbase/loader!sc/plat/dojo/layout/AdvancedTableLayout", "scbase/loader!sc/plat/dojo/utils/BaseUtils", "scbase/loader!sc/plat/dojo/utils/EventUtils", "scbase/loader!sc/plat/dojo/utils/ScreenUtils", "scbase/loader!sc/plat/dojo/widgets/Image", "scbase/loader!sc/plat/dojo/widgets/Label", "scbase/loader!sc/plat/dojo/widgets/Screen"], function(
templateText, _dojodeclare, _dojokernel, _dojolang, _dojotext, _idxContentPane, _isccsBaseTemplateUtils, _scplat, _scCurrencyDataBinder, _scImageDataBinder, _scAdvancedTableLayout, _scBaseUtils, _scEventUtils, _scScreenUtils, _scImage, _scLabel, _scScreen) {
    return _dojodeclare("isccs.order.promotions.OrderPromotionUI", [_scScreen], {
        templateString: templateText,
        postMixInProperties: function() {
            if (this.getScreenMode() != "default") {
                var origArgs = arguments;
                var htmlName = "templates/OrderPromotion_" + this.getScreenMode() + ".html";
                this.templateString = dojo.cache("isccs.order.promotions", htmlName);
                var modeUIJSClassString = "isccs.order.promotions.OrderPromotion_" + this.getScreenMode() + "UI";
                var that = this;
                var _scUtil = _dojolang.getObject("dojo.utils.Util", true, _scplat);
                _scUtil.getInstance(modeUIJSClassString, null, null, function(instance) {
                    _scBaseUtils.screenModeMixin(that, instance);
                    that.inherited(origArgs);
                });
            }
        },
        baseTemplate: {
            url: _dojokernel.moduleUrl("isccs.order.promotions.templates", "OrderPromotion.html"),
            shared: true
        },
        uId: "OrderPromotion",
        packageName: "isccs.order.promotions",
        className: "OrderPromotion",
        extensible: true,
        title: "Screen",
        screen_description: "This screen displays the promotions that may be applicable to this order.",
        namespaces: {
            targetBindingNamespaces: [],
            sourceBindingNamespaces: [{
                value: 'OrderPromotion',
                description: 'This contains the details of the important event.'
            }]
        },
        showRelatedTask: false,
        isDirtyCheckRequired: true,
        hotKeys: [],
        events: [{
            name: 'saveCurrentPage'
        }, {
            name: 'reloadScreen'
        }],
        subscribers: {
            local: [{
                eventId: 'afterScreenInit',
                sequence: '30',
                handler: {
                    methodName: "initializeScreen"
                }
            }, {
                eventId: 'saveCurrentPage',
                sequence: '25',
                handler: {
                    methodName: "save"
                }
            }],
        },
        initializeScreen: function(
        event, bEvent, ctrl, args) {
            var orderPromotionModel = null;
            orderPromotionModel = _scScreenUtils.getModel(
            this, "OrderPromotion");
        },
        handleMashupCompletion: function(
        mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data) {
            _isccsBaseTemplateUtils.handleMashupCompletion(
            mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data, this);
        },
        save: function() {
            var eventDefinition = null;
            _scEventUtils.fireEventToParent(
            this, "onSaveSuccess", eventDefinition);
        }
    });
});