<!--
Name:  createModifyCategoryItemInput.xsl
Created on:  14-Aug-17
Author:  IBM
Description:  XSL used to form the input to modifyCategoryItem API.
				This will be used in catalog feed consumption.
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <ModifyCategoryItem>
         <xsl:attribute name="CallingOrganizationCode">H3G_UK</xsl:attribute>

         <Category>
            <xsl:attribute name="CategoryPath">
               <xsl:choose>
                  <xsl:when test="(contains(//@catalogId, ':'))">
                     <xsl:value-of select="concat('/H3G_UKMasterCatalog/', substring-before(substring-after(//@catalogId, ':'), ':'))" />
                  </xsl:when>

                  <xsl:otherwise>
                     <xsl:value-of select="concat('/H3G_UKMasterCatalog/', //@catalogId)" />
                  </xsl:otherwise>
               </xsl:choose>
            </xsl:attribute>

            <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

            <CategoryItemList>
               <CategoryItem>
                  <xsl:attribute name="Action">Create</xsl:attribute>

                  <xsl:choose>
                     <xsl:when test="(contains(//@catalogId, ':'))">
                        <xsl:attribute name="ItemID">
                           <xsl:value-of select="substring-before(substring-after(//@catalogId, ':'), ':')" />
                        </xsl:attribute>
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:attribute name="ItemID">
                           <xsl:value-of select="//@catalogId" />
                        </xsl:attribute>
                     </xsl:otherwise>
                  </xsl:choose>

                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
               </CategoryItem>
            </CategoryItemList>
         </Category>
      </ModifyCategoryItem>
   </xsl:template>
</xsl:stylesheet>

