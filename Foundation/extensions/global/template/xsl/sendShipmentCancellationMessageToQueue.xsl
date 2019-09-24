<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
	<Shipment>
        <xsl:attribute name="ReceivingNode">
			<xsl:value-of select="Shipment/@ReceivingNode"/>
        </xsl:attribute>
		<xsl:attribute name="CarrierServiceCode">
			<xsl:value-of select="Shipment/@CarrierServiceCode"/>
        </xsl:attribute>
		<xsl:attribute name="DeliveryMethod">
			<xsl:value-of select="Shipment/@DeliveryMethod"/>
        </xsl:attribute>
		<xsl:attribute name="ShipmentNo">
			<xsl:value-of select="Shipment/@ShipmentNo"/>
        </xsl:attribute>
		<xsl:attribute name="ShipmentKey">
			<xsl:value-of select="Shipment/@ShipmentKey"/>
        </xsl:attribute>
		<xsl:attribute name="SellerOrganizationCode">
			<xsl:value-of select="Shipment/@SellerOrganizationCode"/>
        </xsl:attribute>
		<xsl:attribute name="OrderHeaderKey">
			<xsl:value-of select="Shipment/@OrderHeaderKey"/>
        </xsl:attribute>
		<xsl:attribute name="ShipNode">
			<xsl:value-of select="Shipment/@ShipNode"/>
		</xsl:attribute> 
		<xsl:attribute name="Action">Cancel</xsl:attribute>
			<ToAddress>
				<xsl:attribute name="ZipCode">
					<xsl:value-of select="Shipment/ToAddress/@ZipCode"/>
				</xsl:attribute>
				<xsl:attribute name="LastName">
					<xsl:value-of select="Shipment/ToAddress/@LastName"/>
				</xsl:attribute>
				<xsl:attribute name="FirstName">
					<xsl:value-of select="Shipment/ToAddress/@FirstName"/>
				</xsl:attribute>
				<xsl:attribute name="EMailID">
					<xsl:value-of select="Shipment/ToAddress/@EMailID"/>
				</xsl:attribute>
				<xsl:attribute name="DayPhone">
					<xsl:value-of select="Shipment/ToAddress/@DayPhone"/>
				</xsl:attribute>
				<xsl:attribute name="Country">
					<xsl:value-of select="Shipment/ToAddress/@Country"/>
				</xsl:attribute>
				<xsl:attribute name="State">
					<xsl:value-of select="Shipment/ToAddress/@State"/>
				</xsl:attribute>
				<xsl:attribute name="City">
					<xsl:value-of select="Shipment/ToAddress/@City"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine1">
					<xsl:value-of select="Shipment/ToAddress/@AddressLine1"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine2">
					<xsl:value-of select="Shipment/ToAddress/@AddressLine2"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine3">
					<xsl:value-of select="Shipment/ToAddress/@AddressLine3"/>
				</xsl:attribute>
			</ToAddress>
						<BillToAddress>
				<xsl:attribute name="ZipCode">
					<xsl:value-of select="Shipment/BillToAddress/@ZipCode"/>
				</xsl:attribute>
				<xsl:attribute name="LastName">
					<xsl:value-of select="Shipment/BillToAddress/@LastName"/>
				</xsl:attribute>
				<xsl:attribute name="FirstName">
					<xsl:value-of select="Shipment/BillToAddress/@FirstName"/>
				</xsl:attribute>
				<xsl:attribute name="EMailID">
					<xsl:value-of select="Shipment/BillToAddress/@EMailID"/>
				</xsl:attribute>
				<xsl:attribute name="DayPhone">
					<xsl:value-of select="Shipment/BillToAddress/@DayPhone"/>
				</xsl:attribute>
				<xsl:attribute name="Country">
					<xsl:value-of select="Shipment/BillToAddress/@Country"/>
				</xsl:attribute>
				<xsl:attribute name="State">
					<xsl:value-of select="Shipment/BillToAddress/@State"/>
				</xsl:attribute>
				<xsl:attribute name="City">
					<xsl:value-of select="Shipment/BillToAddress/@City"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine1">
					<xsl:value-of select="Shipment/BillToAddress/@AddressLine1"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine2">
					<xsl:value-of select="Shipment/BillToAddress/@AddressLine2"/>
				</xsl:attribute>
				<xsl:attribute name="AddressLine3">
					<xsl:value-of select="Shipment/BillToAddress/@AddressLine3"/>
				</xsl:attribute>
			</BillToAddress>
        <ShipmentLines>
            <xsl:for-each select="Shipment/ShipmentLines/ShipmentLine">
			<xsl:if test="(@QuantityReduced &gt; 0)">
            <ShipmentLine>
		        <xsl:attribute name="Action">Cancel</xsl:attribute>
                <xsl:attribute name="UnitOfMeasure">                              
					<xsl:value-of select="@UnitOfMeasure"/>
                </xsl:attribute>
	            <xsl:attribute name="ShipmentLineNo">
                    <xsl:value-of select="@ShipmentLineNo"/>
                </xsl:attribute>
				<xsl:attribute name="ReleaseNo">
                    <xsl:value-of select="@ReleaseNo"/>
                </xsl:attribute>
				<xsl:attribute name="QuantityReduced">
                    <xsl:value-of select="@QuantityReduced"/>
                </xsl:attribute>
				<xsl:attribute name="ProductClass">
                    <xsl:value-of select="@ProductClass"/>
                </xsl:attribute>
				<xsl:attribute name="ItemID">
                    <xsl:value-of select="@ItemID"/>
                </xsl:attribute>
                <xsl:attribute name="Quantity">
                    <xsl:value-of select="@Quantity"/>
				</xsl:attribute>
				
				<OrderLine>
					<xsl:attribute name="ReqShipDate">
						<xsl:value-of select="OrderLine/@ReqShipDate"/>
					</xsl:attribute>
					<xsl:attribute name="OrderLineKey">
						<xsl:value-of select="OrderLine/@OrderLineKey"/>
					</xsl:attribute>
					<Extn>
						<xsl:attribute name="ExtnRequestType">
							<xsl:value-of select="OrderLine/Extn/@ExtnRequestType"/>
						</xsl:attribute>
						<EXTNOrderLinePropertiesList>
						<xsl:for-each select="OrderLine/Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
						<EXTNOrderLineProperties>
							<xsl:attribute name="DescribedByValue">
								<xsl:value-of select="@DescribedByValue"/>
							</xsl:attribute>
							<xsl:attribute name="DescribedByType">
								<xsl:value-of select="@DescribedByType"/>
							</xsl:attribute>
							<xsl:attribute name="DescribedByCharacteristicName">
								<xsl:value-of select="@DescribedByCharacteristicName"/>
							</xsl:attribute>
							<xsl:attribute name="DescribedByCharacteristic">
								<xsl:value-of select="@DescribedByCharacteristic"/>
							</xsl:attribute>
							<xsl:attribute name="CharacteristicPath">
								<xsl:value-of select="@CharacteristicPath"/>
							</xsl:attribute>
							<xsl:attribute name="CharacteristicEntityId">
								<xsl:value-of select="@CharacteristicEntityId"/>
							</xsl:attribute>
						</EXTNOrderLineProperties>
						</xsl:for-each>
						</EXTNOrderLinePropertiesList>
					</Extn>
				</OrderLine>
				<Order>
					<xsl:attribute name="OrderDate">
								<xsl:value-of select="Order/@OrderDate"/>
					</xsl:attribute>
					<xsl:attribute name="OrderNo">
								<xsl:value-of select="Order/@OrderNo"/>
					</xsl:attribute>
					<Extn>
						<xsl:attribute name="ExtnCustomerType">
								<xsl:value-of select="Order/Extn/@ExtnCustomerType"/>
						</xsl:attribute>
						<xsl:attribute name="ExtnCustomerOrderNo">
									<xsl:value-of select="Order/Extn/@ExtnCustomerOrderNo"/>
						</xsl:attribute>
					</Extn>
				</Order>
	        </ShipmentLine>
			</xsl:if>
				</xsl:for-each>    
		</ShipmentLines>
		<ShipmentHoldTypes>
		<xsl:for-each select="Shipment/ShipmentHoldTypes/ShipmentHoldType">
			<ShipmentHoldType>
							<xsl:attribute name="Status">
								<xsl:value-of select="@Status"/>
							</xsl:attribute>
							<xsl:attribute name="HoldType">
								<xsl:value-of select="@HoldType"/>
							</xsl:attribute>
			</ShipmentHoldType>
			</xsl:for-each> 
		</ShipmentHoldTypes>                              
	</Shipment>                        
    </xsl:template>
</xsl:stylesheet> 	
  
   
   