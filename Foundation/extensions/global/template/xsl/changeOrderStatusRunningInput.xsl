<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/" >
						  
	<xsl:variable name="varDocumentType">
       <xsl:value-of select="OrderStatusChange/OrderAudit/Order/@DocumentType"/>
    </xsl:variable>
 	    
    <OrderStatusChange>
		<xsl:attribute name="BaseDropStatus">1100.040</xsl:attribute>	
		<xsl:attribute name="TransactionId">
			<xsl:if test="'0001'=$varDocumentType">
       			<xsl:value-of select="'PROCESS_ORDER.0001.ex'"/>    
           	</xsl:if>
           	<xsl:if test="'0002'=$varDocumentType">
       			<xsl:value-of select="'PROCESS_ORDER.0002.ex'"/>    
           	</xsl:if>
		</xsl:attribute>
		<xsl:attribute name="QueryTimeout">10</xsl:attribute>
		<xsl:attribute name="SelectMethod">WAIT</xsl:attribute>			
		<xsl:attribute name="OrderHeaderKey"><xsl:value-of select="OrderStatusChange/@OrderHeaderKey" /> </xsl:attribute>
	</OrderStatusChange>
		
		</xsl:template>
</xsl:stylesheet>
