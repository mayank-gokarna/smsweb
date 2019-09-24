<?xml version='1.0' encoding='utf-8' ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<Inventory>
				<xsl:attribute name="YantraMessageGroupID">
					<xsl:value-of select="EOF/@YantraMessageGroupID"/>
				</xsl:attribute>
				<xsl:attribute name="ShipNode">
					<xsl:value-of select="EOF/@ShipNode"/>
				</xsl:attribute>
				<xsl:attribute name="ApplyDifferences">Y</xsl:attribute>
				<xsl:attribute name="RemoveInventoryNodeControl">Y</xsl:attribute>
				<xsl:attribute name="SynchNonOnHandInventory">Y</xsl:attribute>
				<SupplyTypes>
					<SupplyType>
						<xsl:attribute name="SupplyType">FUTURE_SUPPLY.ex</xsl:attribute>
					</SupplyType>
				</SupplyTypes>
		</Inventory>
	</xsl:template>
</xsl:stylesheet>