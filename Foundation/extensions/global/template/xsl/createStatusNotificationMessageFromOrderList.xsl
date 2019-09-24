<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

   <xsl:template match="/">
      <Order>
         <xsl:if test="(OrderList/Order/@OrderNo)">
            <xsl:attribute name="OrderNo">
               <xsl:value-of select="OrderList/Order/@OrderNo" />
            </xsl:attribute>
         </xsl:if>

         <xsl:if test="(OrderList/Order/@OrderHeaderKey)">
            <xsl:attribute name="OrderHeaderKey">
               <xsl:value-of select="OrderList/Order/@OrderHeaderKey" />
            </xsl:attribute>
         </xsl:if>

         <xsl:if test="(OrderList/Order/@OrderType)">
            <xsl:attribute name="OrderType">
               <xsl:value-of select="OrderList/Order/@OrderType" />
            </xsl:attribute>
         </xsl:if>

         <xsl:if test="(OrderList/@ExtnOrderNotificationStatus)">
            <xsl:attribute name="Status">
               <xsl:value-of select="OrderList/@ExtnOrderNotificationStatus" />
            </xsl:attribute>
         </xsl:if>

         <OrderLines>
            <xsl:for-each select="OrderList/Order/OrderLines/OrderLine">
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

                  <xsl:if test="(../../../@ExtnNotificationStatus)">
                     <xsl:attribute name="Status">
                        <xsl:value-of select="../../../@ExtnNotificationStatus" />
                     </xsl:attribute>
                  </xsl:if>
				  
				  <xsl:if test="(../../../@ExtnTrackingUrl)">
                     <xsl:attribute name="ExtnTrackingUrl">
                        <xsl:value-of select="../../../@ExtnTrackingUrl" />
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
				  <!-- Changes for CIT8 task - Gaurav A- Start  -->
                     	<xsl:if test="(Extn/@ExtnInventoryId)">
                              <xsl:attribute name="ExtnInventoryId">
                                     <xsl:value-of select="Extn/@ExtnInventoryId" />
                              </xsl:attribute>
                        </xsl:if>
                  <!-- Changes for CIT8 task - Gaurav A- End  -->
                    <!--START: Change for Disruptive Props -->
                    	<xsl:if test="(Extn/@ExtnAssociatedInventoryId)">
                              <xsl:attribute name="ExtnAssociatedInventoryId">
                                     <xsl:value-of select="Extn/@ExtnAssociatedInventoryId" />
                              </xsl:attribute>
                        </xsl:if>
                    <!--END: Change for Disruptive Props -->
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
      </Order>
   </xsl:template>
</xsl:stylesheet>
