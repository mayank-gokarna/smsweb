<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
	<xsl:template match="/">
		<InventoryNodeControl>
			<xsl:attribute name="NodeControlType">ON_HOLD</xsl:attribute>
			<xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>
			<xsl:attribute name="ProductClass">Good</xsl:attribute>
			<xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
			<ComplexQuery>
				<xsl:attribute name="Operator">AND</xsl:attribute>
				<Or>
					<xsl:for-each select="Items/Item">
						<And>
							<Exp>
								<xsl:attribute name="Name">ItemID</xsl:attribute>
								<xsl:attribute name="Value">
									<xsl:value-of select="@ItemID" />
								</xsl:attribute>
								<xsl:attribute name="ItemIDQryType">EQ</xsl:attribute>
							</Exp>
							<Exp>
								<xsl:attribute name="Name">Node</xsl:attribute>
								<xsl:attribute name="Value">
									<xsl:value-of select="@ReceivingNode" />
								</xsl:attribute>
								<xsl:attribute name="NodeQryType">EQ</xsl:attribute>
							</Exp>
						</And>
					</xsl:for-each>				
				</Or>
			</ComplexQuery>
		</InventoryNodeControl>
	</xsl:template>
</xsl:stylesheet> 
			