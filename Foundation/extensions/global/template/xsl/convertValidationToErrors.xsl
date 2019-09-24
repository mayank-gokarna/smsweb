<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
	<xsl:template match="/">
		<error>
		<xsl:for-each select="validation/validationResult/validationItem"> 
			<errorLine>
			<xsl:attribute name="ErrorCode"><xsl:value-of select="code" /></xsl:attribute>
			
			<xsl:for-each select="validationParameter">
				<errorItems>
				<xsl:attribute name="EntityType"><xsl:value-of select="code" /></xsl:attribute>
				<xsl:attribute name="EntityID"><xsl:value-of select="./value/stringValue" /></xsl:attribute>
				</errorItems>
			</xsl:for-each>
			
			</errorLine>
			
		</xsl:for-each>
		</error>
		

	</xsl:template >
</xsl:stylesheet >