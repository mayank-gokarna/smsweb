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

         <xsl:if test="(Order/@MinOrderStatus = '1100.080' or Order/@MinOrderStatus = '1100.010' or Order/@MinOrderStatus = '1100.020' or Order/@MinOrderStatus = '9000')">
            <xsl:attribute name="Status">
               <xsl:value-of select="Order/@MinOrderStatus" />
            </xsl:attribute>

            <xsl:if test="(Order/@MinOrderStatusDesc)">
               <xsl:attribute name="StatusDescription">
                  <xsl:value-of select="Order/@MinOrderStatusDesc" />
               </xsl:attribute>
            </xsl:if>
         </xsl:if>

         <xsl:if test="(Order/@NotificationType)">
            <xsl:attribute name="NotificationType">
               <xsl:value-of select="Order/@NotificationType" />
            </xsl:attribute>
         </xsl:if>
         
         <xsl:if test="(Order/@BillToID)">
            <xsl:attribute name="BillToID">
               <xsl:value-of select="Order/@BillToID" />
            </xsl:attribute>
         </xsl:if>
         <xsl:if test="(Order/Extn)">
         	<Extn>
         		<xsl:if test="(Order/Extn/@ExtnBillCycleDate)">   
		            <xsl:attribute name="ExtnBillCycleDate">
		               <xsl:value-of select="Order/Extn/@ExtnBillCycleDate" />
		            </xsl:attribute>
	         	</xsl:if>
         	</Extn>
         </xsl:if>

         <xsl:if test="(Order/OrderLines/OrderLine)">
            <OrderLines>
               <xsl:for-each select="Order/OrderLines/OrderLine">
                  <OrderLine>
                     <xsl:if test="(@CustomerPONo)">
                        <xsl:attribute name="CustomerPONo">
                           <xsl:value-of select="@CustomerPONo" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@ExpectedShipmentDate)">
                        <xsl:attribute name="ExpectedShipmentDate">
                           <xsl:value-of select="@ExpectedShipmentDate" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@ExpectedDeliveryDate)">
                        <xsl:attribute name="ExpectedDeliveryDate">
                           <xsl:value-of select="@ExpectedDeliveryDate" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@ActualShipmentDate)">
                        <xsl:attribute name="ActualShipmentDate">
                           <xsl:value-of select="@ActualShipmentDate" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@TrackingNo)">
                        <xsl:attribute name="TrackingNo">
                           <xsl:value-of select="@TrackingNo" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@DispositionCode)">
                        <xsl:attribute name="DispositionCode">
                           <xsl:value-of select="@DispositionCode" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@OrderedQty)">
                        <xsl:attribute name="StatusQuantity">
                           <xsl:value-of select="@OrderedQty" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@MinLineStatus)">
                        <xsl:attribute name="Status">
                           <xsl:value-of select="@MinLineStatus" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@MinLineStatusDesc)">
                        <xsl:attribute name="StatusDescription">
                           <xsl:value-of select="@MinLineStatusDesc" />
                        </xsl:attribute>
                     </xsl:if>

                     <xsl:if test="(@ExtnTrackingUrl)">
                        <xsl:attribute name="ExtnTrackingUrl">
                           <xsl:value-of select="@ExtnTrackingUrl" />
                        </xsl:attribute>
                     </xsl:if>

                     <Item>
                        <xsl:if test="(Item/@ItemID)">
                           <xsl:attribute name="ItemID">
                              <xsl:value-of select="Item/@ItemID" />
                           </xsl:attribute>
                        </xsl:if>
                     </Item>

                     <xsl:if test="(Extn)">
                        <Extn>
                           <xsl:if test="(Extn/@ExtnReturnCancellationReasonCode)">
                              <xsl:attribute name="ExtnReturnCancellationReasonCode">
                                 <xsl:value-of select="Extn/@ExtnReturnCancellationReasonCode" />
                              </xsl:attribute>
                           </xsl:if>
						   <xsl:if test="(Extn/@ExtnPACCode)">
                              <xsl:attribute name="ExtnPACCode">
                                 <xsl:value-of select="Extn/@ExtnPACCode" />
                              </xsl:attribute>
                           </xsl:if>
                           <xsl:if test="(Extn/@ExtnContractId)">
                              <xsl:attribute name="ExtnContractId">
                                 <xsl:value-of select="Extn/@ExtnContractId" />
                              </xsl:attribute>
                           </xsl:if>
                           <xsl:if test="(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties)">
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
                           </xsl:if>
                        </Extn>
                     </xsl:if>
                  </OrderLine>
               </xsl:for-each>
            </OrderLines>
         </xsl:if>
      </Order>
   </xsl:template>
</xsl:stylesheet>

