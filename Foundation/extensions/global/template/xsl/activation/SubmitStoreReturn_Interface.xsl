<?xml version="1.0" encoding="UTF-8"?>
<!--
Name:  SubmitStoreReturn_Interface.xsl
Created on:  12-FEB-18
Author:  IBM
Description:  XSL used to prepare Interface XML and push it to the queue H3G_OUT_RETURN_REQUEST_Q
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/OrderList/Order">
		<xsl:element name="Order">
			<xsl:copy-of select="@OrderHeaderKey"></xsl:copy-of>
			<xsl:copy-of select="@OrderNo"></xsl:copy-of>
			<xsl:copy-of select="@OrderDate"></xsl:copy-of>
			<xsl:element name="Extn">
					<xsl:copy-of select="Extn/@ExtnCustomerOrderNo"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="OrderLines">
				<xsl:copy-of select="@TotalNumberOfRecords"></xsl:copy-of>
				<xsl:apply-templates select="OrderLines/OrderLine"></xsl:apply-templates>
			</xsl:element>
		</xsl:element>
	</xsl:template>	
	<xsl:template match="OrderLine" name="OrderLines">
		<xsl:element name="OrderLine">			
			<xsl:copy-of select="@OrderedQty"></xsl:copy-of>			
			<xsl:copy-of select="@OrderLineKey"></xsl:copy-of>
			<xsl:copy-of select="@ShipNode"></xsl:copy-of>
			<xsl:copy-of select="@ShipmentNo"></xsl:copy-of>
			<xsl:attribute name="TransactionCode">Return</xsl:attribute>
			<xsl:element name="Item">
				<xsl:copy-of select="Item/@ItemID"></xsl:copy-of>
				<xsl:copy-of select="Item/@UnitOfMeasure"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="Extn">
				<xsl:element name="EXTNOrderLinePropertiesList">
				<xsl:for-each select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">	
					<xsl:element name="EXTNOrderLineProperties">
						<xsl:copy-of select="@DescribedByCharacteristic"></xsl:copy-of>
						<xsl:copy-of select="@DescribedByCharacteristicName"></xsl:copy-of>
						<xsl:copy-of select="@DescribedByValue"></xsl:copy-of>
						<xsl:copy-of select="@DescribedByType"></xsl:copy-of>
			
						<xsl:copy-of select="@CharacteristicEntityId"></xsl:copy-of>
						<xsl:copy-of select="@CharacteristicPath"></xsl:copy-of>
					</xsl:element>
				
				</xsl:for-each>
				</xsl:element>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>