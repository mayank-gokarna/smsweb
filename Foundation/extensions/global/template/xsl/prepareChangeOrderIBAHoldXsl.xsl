<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/" >
						  
	<Order>
	<xsl:attribute name="OrderHeaderKey">
			<xsl:value-of select="Order/@OrderHeaderKey" />
		</xsl:attribute>
	<OrderStatusChange>
		<xsl:attribute name="QueryTimeout">10</xsl:attribute>
		<xsl:attribute name="SelectMethod">WAIT</xsl:attribute>
		</OrderStatusChange>
		
		<OrderHoldTypes>
			<OrderHoldType>
				<xsl:attribute name="HoldType">H3G_IBA_HOLD</xsl:attribute>	
				<xsl:attribute name="Status">1300</xsl:attribute>	
			</OrderHoldType>
		</OrderHoldTypes>
	</Order>
		</xsl:template>
</xsl:stylesheet>
