<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
   
   <xsl:template match="/">
		<OrderLine>
			<ComplexQuery>
				<xsl:attribute name="Operator">AND</xsl:attribute>
				<And>
					<Or>
						<xsl:for-each select="Shipment/ShipmentLines/ShipmentLine">
							<Exp>
								<xsl:attribute name="Name">OrderLineKey</xsl:attribute>
								<xsl:attribute name="Value">
									<xsl:value-of select="@OrderLineKey" />
								</xsl:attribute>
								<xsl:attribute name="OrderLineKeyQryType">EQ</xsl:attribute>
							</Exp>
						</xsl:for-each>
					</Or>
				</And>
			</ComplexQuery>
	   </OrderLine>
   </xsl:template>
</xsl:stylesheet>