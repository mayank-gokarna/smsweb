<?xml version="1.0" encoding="UTF-8"?>
<!--
Name:  UpdateBillingInvoicingAndOnlineCharging_Interface.xsl
Created on:  14-JUN-17
Author:  IBM
Description:  XSL used to form the input to be pushed to the queue H3G_OUT_BILLING_REQUEST_Q
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/OrderList/Order">
		<xsl:element name="Order">
			<xsl:copy-of select="@OrderHeaderKey"></xsl:copy-of>
			<xsl:copy-of select="@BillToID"></xsl:copy-of>
			<xsl:copy-of select="@EntryType"></xsl:copy-of>
			<xsl:copy-of select="@ReqShipDate"></xsl:copy-of>
			<xsl:element name="Extn">
				<xsl:copy-of select="Extn/@ExtnTargetOSS"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnCustomerOrderNo"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnGroupID"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnBillCycleDate"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnOCSnBillingUpdateFlag"></xsl:copy-of>
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
			<xsl:copy-of select="@Purpose"></xsl:copy-of>
			<xsl:copy-of select="@ReqShipDate"></xsl:copy-of>
			<xsl:copy-of select="@OrderedQty"></xsl:copy-of>
			<xsl:copy-of select="@LineType"></xsl:copy-of>
			<xsl:copy-of select="@OrderLineKey"></xsl:copy-of>
			<xsl:copy-of select="@PrimeLineNo"></xsl:copy-of>
			<xsl:copy-of select="@SubLineNo"></xsl:copy-of>
			<xsl:element name="Item">
				<xsl:copy-of select="Item/@ItemID"></xsl:copy-of>
			</xsl:element>
			<xsl:element name="Extn">
				<xsl:copy-of select="Extn/@ExtnStatus"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnParentLineSeqNo"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnPricePlanSpecId"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnSpecVersion"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnCostCenter"></xsl:copy-of>
				<xsl:copy-of select="Extn/@ExtnInventoryId"></xsl:copy-of>
                <!--START: Change for Disruptive Props -->  
                <xsl:copy-of select="Extn/@ExtnAssociatedInventoryId"></xsl:copy-of>
                <!--END: Change for Disruptive Props -->
              	<!-- START: Change for subscriber alignment -->
				<xsl:copy-of select="Extn/@ExtnContractId"></xsl:copy-of>
				<!-- END: Change for subscriber alignment -->
				<xsl:element name="EXTNOrderLinePropertiesList">
					<xsl:apply-templates
						select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties"></xsl:apply-templates>
				</xsl:element>
			</xsl:element>

		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
