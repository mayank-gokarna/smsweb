<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/OrderHierarchy">
    <OrderHierarchyForD3Tree>
      <xsl:attribute name="ChartInput">
      <xsl:text>{</xsl:text>
        <xsl:apply-templates />
      <xsl:text>}</xsl:text>
      </xsl:attribute>
    </OrderHierarchyForD3Tree>
  </xsl:template>
  
  <xsl:template match="Order">
    <xsl:text>'orderHeaderKey': '</xsl:text>
    <xsl:value-of select="@OrderHeaderKey" />
    <xsl:text>',</xsl:text>

    <xsl:text>'orderNo': '</xsl:text>
    <xsl:value-of select="@OrderNo" />
    <xsl:text>',</xsl:text>

    <xsl:text>'documentType': '</xsl:text>
    <xsl:value-of select="@DocumentType" />
    <xsl:text>',</xsl:text>

    <xsl:text>'enterpriseCode': '</xsl:text>
    <xsl:value-of select="@EnterpriseCode" />
    <xsl:text>',</xsl:text>

    <xsl:text>'status': '</xsl:text>
    <xsl:value-of select="@Status" />
    <xsl:text>',</xsl:text>

    <xsl:text>'draftOrderFlag': '</xsl:text>
    <xsl:value-of select="@DraftOrderFlag" />
    <xsl:text>',</xsl:text>

    <xsl:text>'itemId': '</xsl:text>
    <xsl:value-of select="OrderLines/OrderLine/Item/@ItemID" />
    <xsl:text>',</xsl:text>

    <xsl:text>'purpose': '</xsl:text>
    <xsl:value-of select="OrderLines/OrderLine/@Purpose" />
    <xsl:text>',</xsl:text>

    <xsl:text>'billToId': '</xsl:text>
    <xsl:value-of select="@BillToID" />
    <xsl:text>',</xsl:text>
    
    <xsl:text>'orderType': '</xsl:text>
    <xsl:value-of select="@OrderType" />
    <xsl:text>',</xsl:text>

    <xsl:text>'children':[</xsl:text>
    <xsl:for-each select="ChildOrderList/Order">
      <xsl:text>{</xsl:text>
	  <xsl:text>'extnTargetOss': '</xsl:text>
    <xsl:variable name="TargetOSS" select="Extn/@ExtnTargetOSS"/>
        <xsl:choose>
          <xsl:when test="$TargetOSS!=''">
               <xsl:value-of select="$TargetOSS" />
          </xsl:when>
          <xsl:otherwise>
               <xsl:value-of select="@OrderType"/>
          </xsl:otherwise>
        </xsl:choose>
	<xsl:text>',</xsl:text>
      <xsl:apply-templates select="."/>
      <xsl:text>}</xsl:text>
      <xsl:choose>
        <xsl:when test="position() != last()">
          <xsl:text>,</xsl:text>
        </xsl:when>
      </xsl:choose>
    </xsl:for-each>
    <xsl:text>]</xsl:text>
  </xsl:template>

 </xsl:stylesheet>