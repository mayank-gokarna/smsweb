<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      
	<Shipment>
        <xsl:attribute name="SellerOrganizationCode">
   
			<xsl:value-of select="Order/@SellerOrganizationCode"/>
        </xsl:attribute>
                       
		<xsl:attribute name="ShipNode">
                              
			<xsl:value-of select="Order/OrderLines/OrderLine/@ShipNode"/>
                                         
		</xsl:attribute>
                                            
		<xsl:attribute name="Action">Create</xsl:attribute>
   
        <ShipmentLines>
   
            <xsl:for-each select="Order/OrderLines/OrderLine">
   
            <ShipmentLine>
		
		        <xsl:attribute name="OrderLineKey">
   
   
                    <xsl:value-of select="@OrderLineKey"/>
   
				</xsl:attribute>
   
                <xsl:attribute name="OrderHeaderKey">
                                                                                   
					<xsl:value-of select="../../@OrderHeaderKey"/>
   
                </xsl:attribute>
	
	            <xsl:attribute name="ItemDetails/ItemId">
   
                    <xsl:value-of select="@ItemId"/>
   
                </xsl:attribute>
   
                <xsl:attribute name="Quantity">
   
                    <xsl:value-of select="@OrderedQty"/>
                
				</xsl:attribute>
   
	            </ShipmentLine>
	            
				</xsl:for-each>
                                    
		</ShipmentLines>
                                                           
	</Shipment>
                                                   
    </xsl:template>
</xsl:stylesheet> 	
  
   
   