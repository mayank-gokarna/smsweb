<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <ItemList>
         <xsl:attribute name="CallingOrganizationCode">H3G_UK</xsl:attribute>

         <xsl:for-each select="productOfferingSpecification">
            <Item>
               <xsl:attribute name="ItemID">
                  <xsl:choose>
                     <xsl:when test="(contains(@catalogId, ':'))">
                        <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:value-of select="@catalogId" />
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:attribute>

               <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

               <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

               <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

               <PrimaryInformation>
                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="Description">
                     <xsl:value-of select="description" />
                  </xsl:attribute>

                  <xsl:attribute name="ShortDescription">
                     <xsl:value-of select="name" />
                  </xsl:attribute>

                  <xsl:attribute name="ItemType">
                     <xsl:choose>
                        <!--START: Change for Disruptive Props -->
                        <xsl:when test="type='Bundle'">COMPOSITE_OFFER</xsl:when>
                        <!--END: Change for Disruptive Props -->
                        <xsl:otherwise>OFFER</xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>

                  <xsl:if test="(validFrom)">
                     <xsl:attribute name="EffectiveStartDate">
                        <xsl:value-of select="validFrom" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:if test="(validTo)">
                     <xsl:attribute name="EffectiveEndDate">
                        <xsl:value-of select="validTo" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:attribute name="Status">3000</xsl:attribute>
               </PrimaryInformation>

               <AdditionalAttributeList>
			   <xsl:attribute name="Reset">Y</xsl:attribute>
                  <xsl:if test="(storeInventory)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="storeInventory" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(accountType/id)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Account_Type</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="accountType/id" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(addon)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Is_Addon</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="addon" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(activation)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Activation</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="activation" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(commercial)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Is_Commcercial</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="commercial" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(compatibilityRule)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Compatibility_Rule</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="compatibilityRule" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(complex)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Is_Complex</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="complex" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(eligibilityRule)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Eligibility_Rule</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="eligibilityRule" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(pricePlanSpecification)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Price_Plan</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="pricePlanSpecification" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>

                  <xsl:if test="(shareable)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Is_Shareable</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="shareable" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>
               </AdditionalAttributeList>
			   <!--START: Change for Disruptive Props -->
               <xsl:if test="type='Bundle'">
               <!--END: Change for Disruptive Props -->
                  <ItemAliasList>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <ItemAlias>
                        <xsl:attribute name="AliasName">SPECIFICATION</xsl:attribute>

                        <xsl:attribute name="AliasValue">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="concat('COS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="concat('COS_',@catalogId)" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>
                     </ItemAlias>
                  </ItemAliasList>
               </xsl:if>

               <xsl:if test="(productSpecification)">
			   <xsl:choose>
                  <xsl:when test="(contains(@catalogId, ':'))">
                  <xsl:if test="(substring-before(substring-after(productSpecification, ':'), ':')!=substring-before(substring-after(@catalogId, ':'), ':'))">
                     <ItemAliasList>
					 <xsl:attribute name="Reset">Y</xsl:attribute>
                        <ItemAlias>
                           <xsl:attribute name="AliasName">SPECIFICATION</xsl:attribute>

                           <xsl:attribute name="AliasValue">
                              <xsl:choose>
                                 <xsl:when test="(contains(productSpecification, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(productSpecification, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="productSpecification" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>
                        </ItemAlias>
                     </ItemAliasList>
                  </xsl:if>
				  </xsl:when>

                  <xsl:otherwise>
				  
				  <xsl:if test="(productSpecification!=@catalogId)">
				  
                     <ItemAliasList>
					 <xsl:attribute name="Reset">Y</xsl:attribute>
                        <ItemAlias>
                           <xsl:attribute name="AliasName">SPECIFICATION</xsl:attribute>

                           <xsl:attribute name="AliasValue">
                              <xsl:choose>
                                 <xsl:when test="(contains(productSpecification, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(productSpecification, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="productSpecification" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>
                        </ItemAlias>
                     </ItemAliasList>
                  </xsl:if>
				  
				  
				  
				  </xsl:otherwise>
                 </xsl:choose>
               </xsl:if>

               <Extn>
                  <xsl:attribute name="ExtnSpecVersion">
                     <xsl:choose>
                        <xsl:when test="(contains(version, ':'))">
                           <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                        </xsl:when>

                        <xsl:otherwise>
                           <xsl:value-of select="version" />
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>
               </Extn>
            </Item>
            <!--START: Change for Disruptive Props -->
            <xsl:if test="type='Bundle'">
            <!--END: Change for Disruptive Props -->
               <Item>
                  <xsl:attribute name="ItemID">
					<xsl:choose>
                        <xsl:when test="(contains(@catalogId, ':'))">
							<xsl:value-of select="concat('COS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
						</xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="concat('COS_',@catalogId)" />
                        </xsl:otherwise>
                    </xsl:choose>
                  </xsl:attribute>

                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

                  <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

                  <PrimaryInformation>
                     <xsl:attribute name="ItemType">COMPOSITE_OFFER_SPEC</xsl:attribute>

                     <xsl:attribute name="KitCode">BUNDLE</xsl:attribute>

                     <xsl:if test="(description)">
                        <xsl:attribute name="Description">
                           <xsl:value-of select="description" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(name)">
                        <xsl:attribute name="ShortDescription">
                           <xsl:value-of select="name" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(validFrom)">
                        <xsl:attribute name="EffectiveStartDate">
                           <xsl:value-of select="validFrom" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(validTo)">
                        <xsl:attribute name="EffectiveEndDate">
                           <xsl:value-of select="validTo" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:attribute name="Status">3000</xsl:attribute>
                  </PrimaryInformation>

                  <AdditionalAttributeList>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <xsl:if test="(storeInventory)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="storeInventory" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(accountType/id)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Account_Type</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="accountType/id" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(addon)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Is_Addon</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="addon" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(activation)">
                        <AdditionalAttribute>
                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Name">Activation</xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="activation" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(commercial)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Is_Commcercial</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="commercial" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(compatibilityRule)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Compatibility_Rule</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="compatibilityRule" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(complex)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Is_Complex</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="complex" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(eligibilityRule)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Eligibility_Rule</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="eligibilityRule" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(pricePlanSpecification)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Price_Plan</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="pricePlanSpecification" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>

                     <xsl:if test="(shareable)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Is_Shareable</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="shareable" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>
                  </AdditionalAttributeList>

                  <Components>
                     <xsl:attribute name="Reset">Y</xsl:attribute>

                     <xsl:for-each select="referencedSpecification[@type='ns2:ProductOfferingRelationshipSpecification'][type='SUBOFFERING']">
                        <Component>
                           <xsl:attribute name="ComponentOrganizationCode">H3G_UK</xsl:attribute>

                           <xsl:attribute name="ComponentKitQuantity">1</xsl:attribute>

                           <xsl:attribute name="ComponentUnitOfMeasure">EACH</xsl:attribute>

                           <xsl:attribute name="ComponentItemID">
                              <xsl:choose>
                                 <xsl:when test="(contains(relatedSpecification, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(relatedSpecification, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="relatedSpecification" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <Extn>
                              <xsl:attribute name="ExtnKitMaxMultiplicity">
                                 <xsl:value-of select="maxMultiplicity" />
                              </xsl:attribute>

                              <xsl:attribute name="ExtnKitMinMultiplicity">
                                 <xsl:value-of select="minMultiplicity" />
                              </xsl:attribute>
                           </Extn>
                        </Component>
                     </xsl:for-each>
                  </Components>

                  <Extn>
                     <xsl:attribute name="ExtnSpecVersion">
                        <xsl:choose>
                           <xsl:when test="(contains(version, ':'))">
                              <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                           </xsl:when>

                           <xsl:otherwise>
                              <xsl:value-of select="version" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:attribute>
                  </Extn>
               </Item>
            </xsl:if>
         </xsl:for-each>

         <xsl:for-each select="productSpecification">
            <Item>
               <xsl:attribute name="ItemID">
                  <xsl:choose>
                     <xsl:when test="(contains(@catalogId, ':'))">
                        <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:value-of select="@catalogId" />
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:attribute>

               <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

               <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

               <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

               <PrimaryInformation>
                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="KitCode">BUNDLE</xsl:attribute>

                  <xsl:attribute name="Description">
                     <xsl:value-of select="description" />
                  </xsl:attribute>

                  <xsl:attribute name="ShortDescription">
                     <xsl:value-of select="name" />
                  </xsl:attribute>

                  <xsl:attribute name="ItemType">PRODUCT_SPECIFICATION</xsl:attribute>

                  <xsl:if test="(validFrom)">
                     <xsl:attribute name="EffectiveStartDate">
                        <xsl:value-of select="validFrom" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:if test="(validTo)">
                     <xsl:attribute name="EffectiveEndDate">
                        <xsl:value-of select="validTo" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:attribute name="Status">3000</xsl:attribute>
               </PrimaryInformation>

               <xsl:if test="(referencedSpecification[@type='ns2:GenericRelationshipSpecification'])">
                  <Components>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <xsl:for-each select="referencedSpecification[@type='ns2:GenericRelationshipSpecification']">
                        

                        <Component>
                           <xsl:attribute name="ComponentOrganizationCode">H3G_UK</xsl:attribute>

                           <xsl:attribute name="ComponentKitQuantity">1</xsl:attribute>

                           <xsl:attribute name="ComponentUnitOfMeasure">EACH</xsl:attribute>

                           <xsl:attribute name="ComponentItemID">
                              <xsl:choose>
                                 <xsl:when test="(contains(relatedSpecification, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(relatedSpecification, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="relatedSpecification" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <Extn>
                              <xsl:attribute name="ExtnKitMaxMultiplicity">
                                 <xsl:value-of select="maxMultiplicity" />
                              </xsl:attribute>

                              <xsl:attribute name="ExtnKitMinMultiplicity">
                                 <xsl:value-of select="minMultiplicity" />
                              </xsl:attribute>
                           </Extn>
                        </Component>
                     </xsl:for-each>
                  </Components>
               </xsl:if>

               <AdditionalAttributeList>
			   <xsl:attribute name="Reset">Y</xsl:attribute>
                  <xsl:if test="(storeInventory)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="storeInventory" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>
               </AdditionalAttributeList>

               <Extn>
                  <xsl:attribute name="ExtnSpecVersion">
                     <xsl:choose>
                        <xsl:when test="(contains(version, ':'))">
                           <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                        </xsl:when>

                        <xsl:otherwise>
                           <xsl:value-of select="version" />
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>
               </Extn>
            </Item>
         </xsl:for-each>

         <xsl:for-each select="resourceSpecification">
            <Item>
               <xsl:attribute name="ItemID">
                  <xsl:choose>
                     <xsl:when test="(contains(@catalogId, ':'))">
                        <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:value-of select="@catalogId" />
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:attribute>

               <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

               <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

               <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

               <PrimaryInformation>
                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="Description">
                     <xsl:value-of select="description" />
                  </xsl:attribute>

                  <xsl:attribute name="ShortDescription">
                     <xsl:value-of select="name" />
                  </xsl:attribute>

                  <xsl:attribute name="ItemType">
                     <xsl:choose>
                        <xsl:when test="(@type='ns2:PhysicalResourceSpecification')">PHYSICAL_RESOURCE</xsl:when>

                        <xsl:otherwise>
                           <xsl:if test="(@type='ns2:LogicalResourceSpecification')">LOGICAL_RESOURCE</xsl:if>
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>

                  <xsl:if test="(validFrom)">
                     <xsl:attribute name="EffectiveStartDate">
                        <xsl:value-of select="validFrom" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:if test="(validTo)">
                     <xsl:attribute name="EffectiveEndDate">
                        <xsl:value-of select="validTo" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:attribute name="Status">3000</xsl:attribute>
               </PrimaryInformation>

               <AdditionalAttributeList>
			   <xsl:attribute name="Reset">Y</xsl:attribute>
                  <xsl:if test="(storeInventory)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="storeInventory" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>
               </AdditionalAttributeList>

               <Extn>
                  <xsl:attribute name="ExtnSpecVersion">
                     <xsl:choose>
                        <xsl:when test="(contains(version, ':'))">
                           <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                        </xsl:when>

                        <xsl:otherwise>
                           <xsl:value-of select="version" />
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>
               </Extn>
               <!--Added for stamping Inventory parameters for resources-->
              <xsl:if test="atpMonitorRule">
						<InventoryParameters>
							<xsl:attribute name="InventoryMonitorRule">
								<xsl:value-of select="atpMonitorRule"/>
							</xsl:attribute>
							<xsl:attribute name="ATPRule">H3G_ATP_RULE</xsl:attribute>
						</InventoryParameters>
			  </xsl:if>
              
            </Item>
         </xsl:for-each>

         <xsl:for-each select="serviceSpecification">
            <Item>
               <xsl:attribute name="ItemID">
                  <xsl:choose>
                     <xsl:when test="(contains(@catalogId, ':'))">
                        <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:value-of select="@catalogId" />
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:attribute>

               <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

               <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

               <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

               <PrimaryInformation>
                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="Description">
                     <xsl:value-of select="description" />
                  </xsl:attribute>

                  <xsl:attribute name="ShortDescription">
                     <xsl:value-of select="name" />
                  </xsl:attribute>

                  <xsl:attribute name="ItemType">
                     <xsl:choose>
                        <xsl:when test="(@type='ns2:CustomerFacingServiceSpecification')">CUSTOMER_FACING_SERVICE</xsl:when>

                        <xsl:otherwise>
                           <xsl:if test="(@type='ns2:ResourceFacingServiceSpecification')">RESOURCE_FACING_SERVICE</xsl:if>
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>

                  <xsl:if test="(validFrom)">
                     <xsl:attribute name="EffectiveStartDate">
                        <xsl:value-of select="validFrom" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:if test="(validTo)">
                     <xsl:attribute name="EffectiveEndDate">
                        <xsl:value-of select="validTo" />
                     </xsl:attribute>
                  </xsl:if>

                  <xsl:attribute name="Status">3000</xsl:attribute>
               </PrimaryInformation>

               <AdditionalAttributeList>
			   <xsl:attribute name="Reset">Y</xsl:attribute>
                  <xsl:if test="(storeInventory)">
                     <AdditionalAttribute>
                        <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                        <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                        <xsl:attribute name="AttributeGroupID">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="@catalogId" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>

                        <xsl:attribute name="Value">
                           <xsl:value-of select="storeInventory" />
                        </xsl:attribute>
                     </AdditionalAttribute>
                  </xsl:if>
               </AdditionalAttributeList>

               <Extn>
                  <xsl:attribute name="ExtnSpecVersion">
                     <xsl:choose>
                        <xsl:when test="(contains(version, ':'))">
                           <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                        </xsl:when>

                        <xsl:otherwise>
                           <xsl:value-of select="version" />
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>
               </Extn>

               <xsl:if test="(referencedSpecification[@type='ns2:GenericRelationshipSpecification'])">
                  <ItemAliasList>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <ItemAlias>
                        <xsl:attribute name="AliasName">SPECIFICATION</xsl:attribute>

                        <xsl:attribute name="AliasValue">
                           <xsl:choose>
                              <xsl:when test="(@type='ns2:CustomerFacingServiceSpecification')">
								<xsl:choose>
									<xsl:when test="(contains(@catalogId, ':'))">
										<xsl:value-of select="concat('CS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="concat('CS_',@catalogId)" />
									</xsl:otherwise>
								</xsl:choose>
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:if test="(@type='ns2:ResourceFacingServiceSpecification')">
								 <xsl:choose>
									<xsl:when test="(contains(@catalogId, ':'))">
										<xsl:value-of select="concat('RS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="concat('RS_',@catalogId)" />
									</xsl:otherwise>
								  </xsl:choose>
                                 </xsl:if>
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:attribute>
                     </ItemAlias>
                  </ItemAliasList>
               </xsl:if>
            </Item>

            <xsl:if test="(referencedSpecification[@type='ns2:GenericRelationshipSpecification'])">
               <Item>
                  <xsl:attribute name="ItemID">
                     <xsl:choose>
                        <xsl:when test="(@type='ns2:CustomerFacingServiceSpecification')">
                           <xsl:choose>
                              <xsl:when test="(contains(@catalogId, ':'))">
                                 <xsl:value-of select="concat('CS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
                              </xsl:when>

                              <xsl:otherwise>
                                 <xsl:value-of select="concat('CS_',@catalogId)" />
                              </xsl:otherwise>
                           </xsl:choose>
                        </xsl:when>

                        <xsl:otherwise>
                           <xsl:if test="(@type='ns2:ResourceFacingServiceSpecification')">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="concat('RS_',substring-before(substring-after(@catalogId, ':'), ':'))" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="concat('RS_',@catalogId)" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:if>
                        </xsl:otherwise>
                     </xsl:choose>
                  </xsl:attribute>

                  <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                  <xsl:attribute name="ItemGroupCode">PROD</xsl:attribute>

                  <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>

                  <PrimaryInformation>
                     <xsl:attribute name="OrganizationCode">H3G_UK</xsl:attribute>

                     <xsl:attribute name="KitCode">BUNDLE</xsl:attribute>

                     <xsl:attribute name="Description">
                        <xsl:value-of select="description" />
                     </xsl:attribute>

                     <xsl:attribute name="ShortDescription">
                        <xsl:value-of select="name" />
                     </xsl:attribute>

                     <xsl:attribute name="ItemType">
                        <xsl:choose>
                           <xsl:when test="(@type='ns2:CustomerFacingServiceSpecification')">CUSTOMER_FACING_SERVICE_SPEC</xsl:when>

                           <xsl:otherwise>
                              <xsl:if test="(@type='ns2:ResourceFacingServiceSpecification')">RESOURCE_FACING_SERVICE_SPEC</xsl:if>
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:attribute>

                     <xsl:if test="(validFrom)">
                        <xsl:attribute name="EffectiveStartDate">
                           <xsl:value-of select="validFrom" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(validTo)">
                        <xsl:attribute name="EffectiveEndDate">
                           <xsl:value-of select="validTo" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:attribute name="Status">3000</xsl:attribute>
                  </PrimaryInformation>

                  <AdditionalAttributeList>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <xsl:if test="(storeInventory)">
                        <AdditionalAttribute>
                           <xsl:attribute name="Name">Store_Inventory</xsl:attribute>

                           <xsl:attribute name="AttributeDomainID">ItemAttribute</xsl:attribute>

                           <xsl:attribute name="AttributeGroupID">
                              <xsl:choose>
                                 <xsl:when test="(contains(@catalogId, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(@catalogId, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="@catalogId" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <xsl:attribute name="Value">
                              <xsl:value-of select="storeInventory" />
                           </xsl:attribute>
                        </AdditionalAttribute>
                     </xsl:if>
                  </AdditionalAttributeList>

                  <Components>
				  <xsl:attribute name="Reset">Y</xsl:attribute>
                     <xsl:for-each select="referencedSpecification[@type='ns2:GenericRelationshipSpecification']">
                        

                        <Component>
                           <xsl:attribute name="ComponentOrganizationCode">H3G_UK</xsl:attribute>

                           <xsl:attribute name="ComponentKitQuantity">1</xsl:attribute>

                           <xsl:attribute name="ComponentUnitOfMeasure">EACH</xsl:attribute>

                           <xsl:attribute name="ComponentItemID">
                              <xsl:choose>
                                 <xsl:when test="(contains(relatedSpecification, ':'))">
                                    <xsl:value-of select="substring-before(substring-after(relatedSpecification, ':'), ':')" />
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:value-of select="relatedSpecification" />
                                 </xsl:otherwise>
                              </xsl:choose>
                           </xsl:attribute>

                           <Extn>
                              <xsl:attribute name="ExtnKitMaxMultiplicity">
                                 <xsl:value-of select="maxMultiplicity" />
                              </xsl:attribute>

                              <xsl:attribute name="ExtnKitMinMultiplicity">
                                 <xsl:value-of select="minMultiplicity" />
                              </xsl:attribute>
                           </Extn>
                        </Component>
                     </xsl:for-each>
                  </Components>

                  <Extn>
                     <xsl:attribute name="ExtnSpecVersion">
                        <xsl:choose>
                           <xsl:when test="(contains(version, ':'))">
                              <xsl:value-of select="substring-before(substring-after(version, ':'), ':')" />
                           </xsl:when>

                           <xsl:otherwise>
                              <xsl:value-of select="version" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:attribute>
                  </Extn>
               </Item>
            </xsl:if>
         </xsl:for-each>
      </ItemList>
   </xsl:template>
</xsl:stylesheet>


