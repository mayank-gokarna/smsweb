<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <Order>
         <xsl:if test="(Order/@OrderNo)">
            <xsl:attribute name="OrderNo">
               <xsl:value-of select="Order/@OrderNo" />
            </xsl:attribute>
         </xsl:if>
		 
		 <xsl:if test="(Order/@OrderType)">
            <xsl:attribute name="OrderType">
               <xsl:value-of select="Order/@OrderType" />
            </xsl:attribute>
         </xsl:if>
		 
		 <xsl:if test="(Order/@ExtnNotificationStatus)">
            <xsl:attribute name="Status">
               <xsl:value-of select="Order/@ExtnNotificationStatus" />
            </xsl:attribute>
         </xsl:if>
		 
		 <xsl:if test="(Order/@NotificationType)">
            <xsl:attribute name="NotificationType">
               <xsl:value-of select="Order/@NotificationType" />
            </xsl:attribute>
         </xsl:if>
      </Order>
   </xsl:template>
</xsl:stylesheet>

