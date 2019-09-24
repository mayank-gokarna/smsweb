<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
   <Shipment>
   		<xsl:variable name="varOrdHdrKey">
   			<xsl:value-of select="/OrderList/Order/@OrderHeaderKey"></xsl:value-of>
   		</xsl:variable>
   		
	  <xsl:attribute name="SellerOrganizationCode"><xsl:value-of select="OrderList/Order/@SellerOrganizationCode"/></xsl:attribute>
      <xsl:attribute name="IsSingleOrder"><xsl:value-of select="'Y'"/></xsl:attribute>
	  <xsl:attribute name="EnterpriseCode"><xsl:value-of select="OrderList/Order/@EnterpriseCode"/></xsl:attribute>
	  <xsl:attribute name="OrderHeaderKey"><xsl:value-of select="$varOrdHdrKey"/></xsl:attribute>
	  <xsl:attribute name="ShipNode"><xsl:value-of select="OrderList/Order/OrderLines/OrderLine/@ShipNode"/></xsl:attribute>
      <ShipmentLines>
         <xsl:for-each select="OrderList/Order/OrderLines/OrderLine">
            <ShipmentLine>
			   <xsl:attribute name="OrderHeaderKey"><xsl:value-of select="$varOrdHdrKey"/></xsl:attribute>
			   <xsl:attribute name="OrderLineKey"><xsl:value-of select="@OrderLineKey"/></xsl:attribute>
			   <xsl:attribute name="Quantity"><xsl:value-of select="@OrderedQty"/></xsl:attribute>
            </ShipmentLine>
         </xsl:for-each>
      </ShipmentLines>
   </Shipment>
   </xsl:template>
</xsl:stylesheet>