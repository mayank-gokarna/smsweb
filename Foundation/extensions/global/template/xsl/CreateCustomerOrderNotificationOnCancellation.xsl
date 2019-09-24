<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <Order>
         <xsl:if test="(Order/@OrderNo)">
            <xsl:attribute name="OrderNo">
               <xsl:value-of select="Order/@OrderNo" />
            </xsl:attribute>
         </xsl:if>

         <xsl:if test="(Order/@OrderType)">
            <xsl:attribute name="OrderType">
               <xsl:value-of select="Order/@OrderType" />
            </xsl:attribute>
         </xsl:if>

         <xsl:choose>
            <xsl:when test="(Order/@MinOrderStatus='9000')">
               <xsl:attribute name="Status">
                  <xsl:value-of select="Order/@Status" />
               </xsl:attribute>
            </xsl:when>

            <xsl:otherwise>
               <OrderLines>
                  <xsl:for-each select="Order/OrderLines/OrderLine">
                     <OrderLine>
                        <xsl:if test="(@CustomerPONo)">
                           <xsl:attribute name="CustomerPONo">
                              <xsl:value-of select="@CustomerPONo" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="(@OrderedQty)">
                           <xsl:attribute name="OrderedQty">
                              <xsl:value-of select="@OrderedQty" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="(@Status)">
                           <xsl:attribute name="Status">
                              <xsl:value-of select="@Status" />
                           </xsl:attribute>
                        </xsl:if>
						
						<xsl:if test="(@OrderLineKey)">
                           <xsl:attribute name="OrderLineKey">
                              <xsl:value-of select="@OrderLineKey" />
                           </xsl:attribute>
                        </xsl:if>						

                        <Item>
                           <xsl:if test="(Item/@ItemID)">
                              <xsl:attribute name="ItemID">
                                 <xsl:value-of select="Item/@ItemID" />
                              </xsl:attribute>
                           </xsl:if>
                        </Item>

                        <Extn>
                           <EXTNOrderLinePropertiesList>
                              <xsl:for-each select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
                                 <EXTNOrderLineProperties>
                                    <xsl:if test="(@DescribedByCharacteristic)">
                                       <xsl:attribute name="DescribedByCharacteristic">
                                          <xsl:value-of select="@DescribedByCharacteristic" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:if test="(@DescribedByCharacteristicName)">
                                       <xsl:attribute name="DescribedByCharacteristicName">
                                          <xsl:value-of select="@DescribedByCharacteristicName" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:if test="(@DescribedByType)">
                                       <xsl:attribute name="DescribedByType">
                                          <xsl:value-of select="@DescribedByType" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:if test="(@DescribedByValue)">
                                       <xsl:attribute name="DescribedByValue">
                                          <xsl:value-of select="@DescribedByValue" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:if test="(@CharacteristicEntityId)">
                                       <xsl:attribute name="CharacteristicEntityId">
                                          <xsl:value-of select="@CharacteristicEntityId" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:if test="(@CharacteristicPath)">
                                       <xsl:attribute name="CharacteristicPath">
                                          <xsl:value-of select="@CharacteristicPath" />
                                       </xsl:attribute>
                                    </xsl:if>
                                 </EXTNOrderLineProperties>
                              </xsl:for-each>
                           </EXTNOrderLinePropertiesList>
                        </Extn>
                     </OrderLine>
                  </xsl:for-each>
               </OrderLines>
            </xsl:otherwise>
         </xsl:choose>
      </Order>
   </xsl:template>
</xsl:stylesheet>


