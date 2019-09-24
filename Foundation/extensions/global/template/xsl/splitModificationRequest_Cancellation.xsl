<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
 	<xsl:template match="/">
		<Order>
			<xsl:copy-of select="/Order/@*" />
			<OrderLines>
				<xsl:for-each select="Order/OrderLines/OrderLine">
					<xsl:if test="(@Action='CANCEL')">
						<OrderLine>
							<xsl:copy-of select="@*" />
						</OrderLine>
					</xsl:if >
				</xsl:for-each>
			</OrderLines>
		</Order>
	</xsl:template>
</xsl:stylesheet>