<!--
Name:  createGetItemListInput.xsl
Created on:  14-Aug-17
Author:  IBM
Description:  XSL used to form the input to getItemList API.
				This will be used in catalog feed consumption.
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <Item>
         <xsl:attribute name="ItemID">
            <xsl:choose>
               <xsl:when test="(contains(//@catalogId, ':'))">
                  <xsl:value-of select="substring-before(substring-after(//@catalogId, ':'), ':')" />
               </xsl:when>

               <xsl:otherwise>
                  <xsl:value-of select="//@catalogId" />
               </xsl:otherwise>
            </xsl:choose>
         </xsl:attribute>

         <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

         <xsl:attribute name="GetUnpublishedItems">Y</xsl:attribute>

         <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      </Item>
   </xsl:template>  
</xsl:stylesheet>

