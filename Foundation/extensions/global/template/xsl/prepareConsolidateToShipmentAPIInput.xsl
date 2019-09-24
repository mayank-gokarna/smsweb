<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <OrderRelease>
          <xsl:attribute name="OrderReleaseKey">
            <xsl:value-of select="OrderRelease/@OrderReleaseKey" />
         </xsl:attribute>
      </OrderRelease>
   </xsl:template>
</xsl:stylesheet>

