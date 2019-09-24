<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:template match="/">
      <Order>
         <xsl:attribute name="OrderType">BuildPlan</xsl:attribute>

         <xsl:if test="Shipment/@ShipmentConsolidationGroupId">
            <Extn>
               <xsl:attribute name="ExtnCustomerOrderNo">
                  <xsl:value-of select="Shipment/@ShipmentConsolidationGroupId" />
               </xsl:attribute>
            </Extn>
         </xsl:if>

         <OrderLine>
            <xsl:if test="OrderStatusChange/OrderAudit/Order/@OrderNo">
               <xsl:attribute name="CustomerPONo">
                  <xsl:value-of select="OrderStatusChange/OrderAudit/Order/@OrderNo" />
               </xsl:attribute>
            </xsl:if>
         </OrderLine>
         <OrderStatus>
			<xsl:attribute name="Status">9000</xsl:attribute>
			<xsl:attribute name="StatusQryType">NE</xsl:attribute>
		</OrderStatus>
      </Order>
   </xsl:template>
</xsl:stylesheet>

