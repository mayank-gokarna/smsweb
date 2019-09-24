<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <Order>
         <xsl:if test="(OrderStatusChange/OrderAudit/Order/@OrderNo)">
            <xsl:attribute name="OrderNo">
               <xsl:value-of select="OrderStatusChange/OrderAudit/Order/@OrderNo" />
            </xsl:attribute>
         </xsl:if>
		 
		 <xsl:if test="(OrderStatusChange/OrderAudit/Order/@OrderType)">
            <xsl:attribute name="OrderType">
               <xsl:value-of select="OrderStatusChange/OrderAudit/Order/@OrderType" />
            </xsl:attribute>
         </xsl:if>
		 
		 <xsl:if test="(OrderStatusChange/OrderAudit/Order/@Status)">
            <xsl:attribute name="Status">
               <xsl:value-of select="OrderStatusChange/OrderAudit/Order/@Status" />
            </xsl:attribute>
         </xsl:if>
         
         <Extn>
	         <xsl:if test="(OrderStatusChange/OrderAudit/Order/Extn/@ExtnBillCycleDate)">   
		            <xsl:attribute name="ExtnBillCycleDate">
		               <xsl:value-of select="OrderStatusChange/OrderAudit/Order/Extn/@ExtnBillCycleDate" />
		            </xsl:attribute>
	         </xsl:if>
	         
	         <xsl:if test="(OrderStatusChange/OrderAudit/Order/Extn/@ExtnOCSnBillingUpdateFlag)">
		            <xsl:attribute name="ExtnOCSnBillingUpdateFlag">
		               <xsl:value-of select="OrderStatusChange/OrderAudit/Order/Extn/@ExtnOCSnBillingUpdateFlag" />
		            </xsl:attribute>
	         </xsl:if>
         </Extn>
      </Order>
   </xsl:template>
</xsl:stylesheet>

