<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
   
   <xsl:template match="/">
   	<Shipment>
   		<ShipmentLines >
		   	<ShipmentLine >
				<Order>
					<ComplexQuery>
					<xsl:attribute name="Operator">AND</xsl:attribute>
					<And>
						<Or>
							<Exp>
									<xsl:attribute name="Name">DocumentType</xsl:attribute>
									<xsl:attribute name="Value">0001</xsl:attribute>
									<xsl:attribute name="DocumentTypeQryType">EQ</xsl:attribute>
							</Exp>
							<Exp>
									<xsl:attribute name="Name">DocumentType</xsl:attribute>
									<xsl:attribute name="Value">0006</xsl:attribute>
									<xsl:attribute name="DocumentTypeQryType">EQ</xsl:attribute>
							</Exp>
						</Or>
					</And>
					</ComplexQuery>
					<xsl:attribute name="EnterpriseCode">H3G_UK</xsl:attribute>
					<Extn>
						<xsl:attribute name="ExtnCustomerOrderNo">
										<xsl:value-of select="Order/@OrderNo" />
						</xsl:attribute>
					</Extn>
				</Order>
			</ShipmentLine>
	   	</ShipmentLines>
	  </Shipment>
   </xsl:template>
</xsl:stylesheet>
