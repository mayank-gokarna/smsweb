<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
   
   <xsl:template match="/">
	   <ShipmentLine >
			<ComplexQuery>
				<xsl:attribute name="Operator">AND</xsl:attribute>
				<And>
					<Or>
						<xsl:for-each select="Items/Item">
							<Exp>
								<xsl:attribute name="Name">ItemID</xsl:attribute>
								<xsl:attribute name="Value">
									<xsl:value-of select="@ItemID" />
								</xsl:attribute>
								<xsl:attribute name="ItemIDQryType">EQ</xsl:attribute>
							</Exp>
						</xsl:for-each>
					</Or>
					<Exp Name="Quantity" Value="1" QuantityQryType="EQ"/>
				</And>
			</ComplexQuery>
			<Shipment>
				<xsl:attribute name="DocumentType">0006</xsl:attribute>
				<xsl:attribute name="EnterpriseCode">H3G_UK</xsl:attribute>
				<xsl:attribute name="Status">1400</xsl:attribute>
					<ComplexQuery>
						<xsl:attribute name="Operator">AND</xsl:attribute>
							<And>
								<Or>
									<xsl:for-each select="Items/Item">
										<Exp>
											<xsl:attribute name="Name">ReceivingNode</xsl:attribute>
											<xsl:attribute name="Value">
												<xsl:value-of select="@ReceivingNode"/>
											</xsl:attribute>
											<xsl:attribute name="ReceivingNodeQryType">EQ</xsl:attribute>
										</Exp>
									</xsl:for-each>
								</Or>
							</And>
					</ComplexQuery>
			</Shipment>
         	<OrderBy>
				<xsl:attribute name="Desc">N</xsl:attribute>
				<xsl:attribute name="Name">OrderHeaderKey</xsl:attribute>
			</OrderBy>
	   </ShipmentLine>
   </xsl:template>
</xsl:stylesheet>
