<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
   <Receipt>
    <xsl:attribute name="DocumentType">
	<xsl:value-of select="ShipmentLine/@DocumentType"/>
	</xsl:attribute>
    <xsl:attribute name="ReceivingNode">
	<xsl:value-of select="ShipmentLine/Shipment/@ReceivingNode"/>
	</xsl:attribute>
	<xsl:attribute name="ShipmentKey">
	<xsl:value-of select="ShipmentLine/@ShipmentKey"/>
	</xsl:attribute>
	<Shipment>
	<xsl:attribute name="OrderHeaderKey">
	<xsl:value-of select="ShipmentLine/@OrderHeaderKey"/>
	</xsl:attribute>
	</Shipment>
	</Receipt>
</xsl:template>
</xsl:stylesheet> 

