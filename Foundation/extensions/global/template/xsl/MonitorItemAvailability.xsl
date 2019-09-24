<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/" >
		<MonitorItemAvailability>
			<xsl:attribute name="ItemID">
				<xsl:value-of select="Item/@ItemID" />
			</xsl:attribute>
			<xsl:attribute name="DistributionRuleId">H3G_RTAM_DG</xsl:attribute>
			<xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>
			<xsl:attribute name="ProductClass">Good</xsl:attribute>
			<xsl:attribute name="RaiseEventOnAllAvailabilityChanges">N</xsl:attribute>
			<xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
		</MonitorItemAvailability>
	</xsl:template>
</xsl:stylesheet>
