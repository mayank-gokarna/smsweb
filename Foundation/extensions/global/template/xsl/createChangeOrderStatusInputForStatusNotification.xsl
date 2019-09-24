<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <OrderStatusChange>
         <xsl:attribute name="DocumentType">0002</xsl:attribute>

         <xsl:attribute name="EnterpriseCode">H3G_UK</xsl:attribute>

         <xsl:attribute name="QueryTimeout">10</xsl:attribute>

         <xsl:attribute name="SelectMethod">WAIT</xsl:attribute>

         <xsl:attribute name="TransactionId">ORDER_COMPLETE.0002.ex</xsl:attribute>

         <xsl:attribute name="OrderNo">
            <xsl:value-of select="Order/@OrderNo" />
         </xsl:attribute>

         <OrderLines>
            <xsl:for-each select="Order/OrderLines/OrderLine">
               <OrderLine>
                  <xsl:attribute name="BaseDropStatus">1100.080</xsl:attribute>

                  <xsl:attribute name="ChangeForAllAvailableQty">Y</xsl:attribute>

                  <xsl:attribute name="OrderLineKey">
                     <xsl:value-of select="@OrderLineKey" />
                  </xsl:attribute>
               </OrderLine>
            </xsl:for-each>
         </OrderLines>
      </OrderStatusChange>
   </xsl:template>
</xsl:stylesheet>

