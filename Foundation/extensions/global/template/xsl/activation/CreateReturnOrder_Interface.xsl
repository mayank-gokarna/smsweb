<?xml version="1.0" encoding="UTF-8"?>
<!--
Name:  CreateReturnOrder_Interface.xsl
Created on:  12-JUl-17
Author:  IBM
Description:  XSL used to prepare Interface XML and push it to the queue H3G_OUT_RETURN_REQUEST_Q
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/OrderList/Order">
		<xsl:element name="Order">
			<xsl:copy-of select="@OrderHeaderKey"></xsl:copy-of>
			<xsl:copy-of select="@BillToID"></xsl:copy-of>
			<xsl:element name="Extn">
				<xsl:copy-of select="Extn/@ExtnTargetOSS"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnCustomerOrderNo"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnGroupdID"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="OrderLines">
				<xsl:apply-templates select="OrderLines/OrderLine"></xsl:apply-templates>
			</xsl:element>
		</xsl:element>
	</xsl:template>	
	<xsl:template match="EXTNOrderLineProperties" name="Properties">
		<EXTNOrderLineProperties>
			<xsl:copy-of select="@DescribedByCharacteristic"></xsl:copy-of>
			<xsl:copy-of select="@DescribedByCharacteristicName"></xsl:copy-of>
			<xsl:copy-of select="@DescribedByValue"></xsl:copy-of>
			<xsl:copy-of select="@DescribedByType"></xsl:copy-of>
			<xsl:copy-of select="@DescribingSpecApplicationDN"></xsl:copy-of>
			<xsl:copy-of select="@DescribingSpecPrimaryKey"></xsl:copy-of>
			<xsl:copy-of select="@DescribingSpecType"></xsl:copy-of>
			<xsl:copy-of select="@CharacteristicEntityId"></xsl:copy-of>
			<xsl:copy-of select="@CharacteristicPath"></xsl:copy-of>
		</EXTNOrderLineProperties>
	</xsl:template>
	<xsl:template match="OrderLine" name="OrderLines">
		<xsl:element name="OrderLine">			
			<xsl:copy-of select="@OrderedQty"></xsl:copy-of>			
			<xsl:copy-of select="@OrderLineKey"></xsl:copy-of>
			<xsl:copy-of select="@ReturnReason"></xsl:copy-of>
			<!-- Start ATP: Return and Exchange -->
			<xsl:copy-of select="@ReturnType"></xsl:copy-of>
			<!-- End ATP: Return and Exchange -->
			<xsl:element name="Item">
				<xsl:copy-of select="Item/@ItemID"></xsl:copy-of>
				<xsl:copy-of select="Item/@UnitOfMeasure"></xsl:copy-of>
				<xsl:copy-of select="Item/@ProductClass"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="PersonInfoShipTo">
				<xsl:copy-of select="PersonInfoShipTo/@AddressLine1"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@AddressLine2"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@AddressLine3"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@City"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@Country"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@DayPhone"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@EMailID"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@FirstName"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@IsAddressVerified"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@LastName"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@State"></xsl:copy-of>
				<xsl:copy-of select="PersonInfoShipTo/@ZipCode"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="Extn">
				<xsl:copy-of select="Extn/@ExtnStatus"></xsl:copy-of>
				<!-- Start ATP: Return and Exchange -->
				<xsl:copy-of select=" /Extn/@ExtnRequestType"></xsl:copy-of>
				<xsl:copy-of select=" /Extn/@ExtnParentOrderNo"></xsl:copy-of>
				<xsl:copy-of select=" /Extn/@ExtnParentLineCustomerPONo"></xsl:copy-of>
				<!-- End ATP: Return and Exchange -->
				<xsl:element name="EXTNOrderLinePropertiesList">
					<xsl:apply-templates
						select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties"></xsl:apply-templates>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>