<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:variable name="primeLineNo" select="0" />

   <xsl:variable name="subLineNo" select="1" />

   <xsl:variable name="entryType" select="//entity/characteristic[catalogId='CH_ENTRY_TYPE']/value/stringValue" />

   <xsl:template match="/">
      <MultiApi>
         <xsl:variable name="billToId">
            <xsl:value-of select="IBMOMDecompositionResponse/@BillToID" />
         </xsl:variable>

         <xsl:variable name="billToKey">
            <xsl:value-of select="IBMOMDecompositionResponse/@BillToKey" />
         </xsl:variable>

         <xsl:variable name="entCode">
            <xsl:choose>
               <xsl:when test="IBMOMDecompositionResponse/@EnterpriseCode != ''">
                  <xsl:value-of select="IBMOMDecompositionResponse/@EnterpriseCode" />
               </xsl:when>

               <xsl:otherwise>
                  <xsl:value-of select="'H3G_UK'">
                  </xsl:value-of>
               </xsl:otherwise>
            </xsl:choose>
         </xsl:variable>

         <xsl:variable name="customerOrderNo">
            <xsl:value-of select="IBMOMDecompositionResponse/dcResp/dcResp/customerOrder/customerOrderId" />
         </xsl:variable>
		
<!--Start: Get ExtnBillCycleDate and ExtnOCSnBillingUpdateFlag from input  -->
		  <xsl:variable name="extnBillCycleDate">
	      	<xsl:value-of select="IBMOMDecompositionResponse/dcResp/dcResp/customerOrder/productOrder/entity/characteristic[catalogId ='CH_BILL_CYCLE_DATE']/value/stringValue"/>
	      </xsl:variable>
	      <xsl:variable name="extnOCSnBillingUpdateFlag">
	       	<xsl:value-of select="IBMOMDecompositionResponse/dcResp/dcResp/customerOrder/productOrder/entity/characteristic[catalogId ='CH_OCSN_BILLING_UPDATE_FLAG']/value/stringValue"/>
	      </xsl:variable>
<!--End: Set ExtnBillCycleDate and ExtnOCSnBillingUpdateFlag from input  -->
		
         <xsl:variable name="listProdBundleOfferItem" />

         <xsl:variable name="productBundledOfferItemList">
            <xsl:for-each select="IBMOMDecompositionResponse/dcResp/dcResp/customerOrder/productOrder/productOrderItem">
               <xsl:if test="entity/type = 'PRODUCT_BUNDLE_OFFER_ITEM'">
                  <xsl:value-of select="concat($listProdBundleOfferItem,',',orderedProduct/businessInteractionEntity/catalogId)" />
               </xsl:if>
            </xsl:for-each>
         </xsl:variable>

         <xsl:for-each select="IBMOMDecompositionResponse/dcResp/dcResp/decomposition/*">
            <xsl:variable name="orderTypeReference" select="name()" />

<!--Ignore Customer Order and Product Order elements -->
            <xsl:if test="$orderTypeReference='resourceOrder' or $orderTypeReference='serviceOrder' or $orderTypeReference='productOrder' ">
               <xsl:variable name="Status">
                  <xsl:choose>
                     <xsl:when test="'resourceOrder' = $orderTypeReference">
                        <xsl:value-of select="resourceOrderStatus" />
                     </xsl:when>

                     <xsl:when test="'serviceOrder' = $orderTypeReference">
                        <xsl:value-of select="serviceOrderStatus" />
                     </xsl:when>

                     <xsl:when test="'productOrder' = $orderTypeReference">
                        <xsl:value-of select="productOrderStatus" />
                     </xsl:when>
                  </xsl:choose>
               </xsl:variable>

               <xsl:variable name="varTargetOSS">
                  <xsl:value-of select="entity/characteristic[catalogId ='CH_TARGET_OSS']/value/stringValue">
                  </xsl:value-of>
               </xsl:variable>

<!--Changes made to introduce CH_TARGET_ENDPOINT: Start -->
               <xsl:variable name="varTargetEndPoint">
                  <xsl:value-of select="entity/characteristic[catalogId ='CH_TARGET_ENDPOINT']/value/stringValue">
                  </xsl:value-of>
               </xsl:variable>

<!--Changes made to introduce CH_TARGET_ENDPOINT: End -->
<!-- Changes made to introduce CH_REQUESTTYPE : Start -->
               <xsl:variable name="varRequestType">
                  <xsl:choose>
                     <xsl:when test="'resourceOrder' = $orderTypeReference">
                        <xsl:value-of select="resourceOrderItem/*[name()='logicalResource' or name()='physicalResource']/entity/characteristic[catalogId ='CH_REQUESTTYPE']/value/stringValue">
                        </xsl:value-of>
                     </xsl:when>

                     <xsl:when test="'serviceOrder' = $orderTypeReference">
                        <xsl:value-of select="serviceOrderItem/*[name()='customerFacingService' or name()='resourceFacingService']/entity/characteristic[catalogId ='CH_REQUESTTYPE']/value/stringValue">
                        </xsl:value-of>
                     </xsl:when>

                     <xsl:when test="'productOrder' = $orderTypeReference">
                        <xsl:value-of select="productOrderItem/orderedProduct/entity/characteristic[catalogId ='CH_REQUESTTYPE']/value/stringValue">
                        </xsl:value-of>
                     </xsl:when>
                  </xsl:choose>
               </xsl:variable>

<!--Changes made to introduce CH_REQUESTTYPE: End -->
<!-- Changes made to introduce Purpose : Start -->
               <xsl:variable name="varPurpose">
                  <xsl:value-of select="*[name() = 'productOrderItem' or name() = 'resourceOrderItem' or name() = 'serviceOrderItem']/businessInteractionItem/action">
                  </xsl:value-of>
               </xsl:variable>

<!--Changes made to introduce Purpose: End -->
<!-- Changes made to introduce Document type : Start -->
               <xsl:variable name="varDocumentType">
                  <xsl:choose>
                     <xsl:when test="'resourceOrder' = $orderTypeReference and 'SHIPPING' = $varTargetOSS and 'DISCONNECT' = $varPurpose">
                        <xsl:value-of select="'0003'">
                        </xsl:value-of>

<!-- Return Order -->
                     </xsl:when>

                     <xsl:when test="'resourceOrder' = $orderTypeReference and 'SHIPPING' = $varTargetOSS and 'DISCONNECT' != $varPurpose">
                        <xsl:value-of select="'0001'">
                        </xsl:value-of>

<!-- Sales Order -->
                     </xsl:when>

                     <xsl:otherwise>
                        <xsl:value-of select="'0002'">
                        </xsl:value-of>

<!-- Planned Order -->
                     </xsl:otherwise>
                  </xsl:choose>
               </xsl:variable>



<!-- Changes made to introduce Document type : End -->
               <xsl:if test="$Status='CREATED' or $Status='MODIFIED' ">
                  <API>
                     <xsl:choose>
                        <xsl:when test="'CREATED' = $Status">
                           <xsl:attribute name="Name">createOrder</xsl:attribute>
                        </xsl:when>

                        <xsl:when test="'MODIFIED' = $Status">
                           <xsl:attribute name="Name">changeOrder</xsl:attribute>
                        </xsl:when>
                     </xsl:choose>

                     <Input>
                        <Order DraftOrderFlag="N" DefaultCustomerInformation="Y" Override="Y">
                           <xsl:variable name="orderNo" select="entity/id" />

                           <xsl:attribute name="DocumentType">
                              <xsl:value-of select="$varDocumentType" />
                           </xsl:attribute>

                           <xsl:attribute name="BillToID">
                              <xsl:value-of select="$billToId" />
                           </xsl:attribute>

                           <xsl:attribute name="BillToKey">
                              <xsl:value-of select="$billToKey" />
                           </xsl:attribute>

                           <xsl:attribute name="EnterpriseCode">
                              <xsl:value-of select="$entCode" />
                           </xsl:attribute>

                           <xsl:attribute name="EntryType">
                              <xsl:value-of select="$entryType" />
                           </xsl:attribute>

                           <xsl:attribute name="SellerOrganizationCode">
                              <xsl:value-of select="$entCode" />
                           </xsl:attribute>

                           <xsl:attribute name="OrderNo">
                              <xsl:value-of select="$orderNo" />
                           </xsl:attribute>

                           <xsl:attribute name="OrderType">
                              <xsl:choose>
                                 <xsl:when test="'resourceOrder' = $orderTypeReference">ResourceOrder</xsl:when>

                                 <xsl:when test="'serviceOrder' = $orderTypeReference">ServiceOrder</xsl:when>

                                 <xsl:when test="'productOrder' = $orderTypeReference">ProductOrder</xsl:when>
                              </xsl:choose>
                           </xsl:attribute>

<!-- Chnages for Shipment Consolidation -->
                           <xsl:if test="resourceOrderItem/physicalResource/entity/characteristic[catalogId='CH_DELMETHOD']/value/stringValue='PICK' and 'SHIPPING' = $varTargetOSS">
<!-- If Pick DoNotConsolidate='Y'  -->
                              <xsl:attribute name="DoNotConsolidate">Y</xsl:attribute>
                           </xsl:if>

                           <xsl:attribute name="CustomerPONo">
                              <xsl:value-of select="$orderNo" />
                           </xsl:attribute>

                           <xsl:attribute name="ReqShipDate">
                              <xsl:choose>
                                 <xsl:when test="'resourceOrder' = $orderTypeReference">
                                    <xsl:value-of select="resourceOrderItem/businessInteractionItem/scheduledDate" />
                                 </xsl:when>

                                 <xsl:when test="'serviceOrder' = $orderTypeReference">
                                    <xsl:value-of select="serviceOrderItem/businessInteractionItem/scheduledDate" />
                                 </xsl:when>

                                 <xsl:when test="'productOrder' = $orderTypeReference">
                                    <xsl:value-of select="productOrderItem/businessInteractionItem/scheduledDate" />
                                 </xsl:when>
                              </xsl:choose>
                           </xsl:attribute>

<!--This needs to be set to the customer ID on the customer order. 
                                        It will be available in the getOrderDetails call in the request -->
                           <Extn>
                              <xsl:attribute name="ExtnCustomerOrderNo">
                                 <xsl:value-of select="$customerOrderNo" />
                              </xsl:attribute>
								
							  <xsl:if test="'productOrder' = $orderTypeReference">
                                    <xsl:attribute name="ExtnBillCycleDate">
                                    	<xsl:value-of select="$extnBillCycleDate" />
                                    </xsl:attribute>
                                    <xsl:attribute name="ExtnOCSnBillingUpdateFlag">
                                    	<xsl:value-of select="$extnOCSnBillingUpdateFlag" />
                                    </xsl:attribute>	
                              </xsl:if>	
								
                              <xsl:attribute name="ExtnCustomerType">
                                 <xsl:value-of select="../../../../@ExtnCustomerType" />
                              </xsl:attribute>

                              <xsl:attribute name="ExtnGroupID">
                                 <xsl:value-of select="../../../../@ExtnGroupID" />
                              </xsl:attribute>

<!-- Return related attributes -->
                              <xsl:attribute name="ExtnReturnNumber">
                                 <xsl:value-of select="../../../../@ExtnReturnNumber" />
                              </xsl:attribute>

                              <xsl:attribute name="ExtnMIP27ACreated">
                                 <xsl:value-of select="../../../../@ExtnMIP27ACreated" />
                              </xsl:attribute>

<!--Changes made to append value of CH_TARGET_OSS with CH_TARGET_ENDPOINT value for ExtnTargetOSS : Start -->
                              <xsl:choose>
                                 <xsl:when test="string-length($varTargetEndPoint) &gt; 0">
                                    <xsl:attribute name="ExtnTargetOSS">
                                       <xsl:choose>
                                          <xsl:when test="string-length($varTargetOSS) &gt; 0">
                                             <xsl:value-of select="concat($varTargetOSS,',',$varTargetEndPoint)" />
                                          </xsl:when>

                                          <xsl:otherwise>
                                             <xsl:value-of select="$varTargetEndPoint" />
                                          </xsl:otherwise>
                                       </xsl:choose>
                                    </xsl:attribute>
                                 </xsl:when>

                                 <xsl:otherwise>
                                    <xsl:if test="string-length($varTargetOSS) &gt; 0">
                                       <xsl:attribute name="ExtnTargetOSS">
                                          <xsl:value-of select="$varTargetOSS" />
                                       </xsl:attribute>
                                    </xsl:if>
                                 </xsl:otherwise>
                              </xsl:choose>

<!--Changes made to append value of CH_TARGET_OSS with CH_TARGET_ENDPOINT value for ExtnTargetOSS: End -->
                              <EXTNOrderHeaderLinksList>
                                 <EXTNOrderHeaderLinks LinkageType="PARENT">
                                    <xsl:variable name="parentProdOrderID" select="parentProductOrderRefId" />

                                    <xsl:variable name="parentServOrderID" select="parentServiceOrderRefId" />

                                    <xsl:variable name="parentCustOrderID" select="parentCustomerOrderRefId" />

                                    <xsl:choose>
                                       <xsl:when test="parentProductOrderRefId">
                                          <xsl:attribute name="LinkedOrderNo">
                                             <xsl:value-of select="$parentProdOrderID" />
                                          </xsl:attribute>

                                          <xsl:attribute name="LinkedOrderType">ProductOrder</xsl:attribute>
                                       </xsl:when>

                                       <xsl:when test="parentServiceOrderRefId">
                                          <xsl:attribute name="LinkedOrderNo">
                                             <xsl:value-of select="$parentServOrderID" />
                                          </xsl:attribute>

                                          <xsl:attribute name="LinkedOrderType">ServiceOrder</xsl:attribute>
                                       </xsl:when>

                                       <xsl:when test="parentCustomerOrderRefId">
                                          <xsl:attribute name="LinkedOrderNo">
                                             <xsl:value-of select="$parentCustOrderID" />
                                          </xsl:attribute>

                                          <xsl:attribute name="LinkedOrderType">CustomerOrder</xsl:attribute>
                                       </xsl:when>
                                    </xsl:choose>
                                 </EXTNOrderHeaderLinks>
                              </EXTNOrderHeaderLinksList>
                           </Extn>

                           <OrderLines>
                              <xsl:for-each select="serviceOrderItem | resourceOrderItem | productOrderItem">
                                 <xsl:variable name="selectedItemID" select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/catalogId" />

<!-- START: CIT -41 Changes  -->
                                 <xsl:variable name="entityId" select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/id" />

<!--  xsl:variable name="entityIdForProductOrder"
                                                select="*[name() = 'orderedProduct' ]/entity/id" /-->
<!-- END: CIT -41 Changes  -->
                                 <xsl:variable name="purpose" select="businessInteractionItem/action" />

<!-- Start: CIT60 - Count for  order lines without component orders-->
                                 <xsl:variable name="currentNodePosition" select="position()" />

                                 <xsl:variable name="varTotalOrderLinesSoFar" select="count(./../*[name() = 'serviceOrderItem' or name() = 'resourceOrderItem' or name() = 'productOrderItem'][position() &lt; $currentNodePosition]/*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct' or name() = 'componentProduct'])">
                                 </xsl:variable>

<!-- Start: ExtnAssociatedInventoryId -->
                                 <xsl:variable name="productRelationshipInventoryId" select="*[name() = 'orderedProduct']/productRelationship/product/businessInteractionEntity/inventoryId" />

<!-- End: ExtnAssociatedInventoryId -->
<!-- Count for component order lines -->
                                 <xsl:variable name="componentOrderlines" select="count(./../*[name() = 'serviceOrderItem' or name() = 'resourceOrderItem' or name() = 'productOrderItem'][position() &lt; $currentNodePosition]/*[name() = 'orderedProduct']/*[ name() = 'componentProduct']/*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct'])">
                                 </xsl:variable>

                                 <xsl:variable name="primeLineNo">
                                    <xsl:choose>
                                       <xsl:when test="$varTotalOrderLinesSoFar &gt; 0">
                                          <xsl:value-of select="$varTotalOrderLinesSoFar+$currentNodePosition+$componentOrderlines" />
                                       </xsl:when>

                                       <xsl:otherwise>
                                          <xsl:value-of select="$currentNodePosition" />
                                       </xsl:otherwise>
                                    </xsl:choose>
                                 </xsl:variable>

<!-- End: CIT60 -->
                                 <OrderLine OrderedQty="1">
                                    <xsl:if test="'SHIPPING' = $varTargetOSS">
                                       <xsl:attribute name="ShipmentConsolidationGroupId">
                                          <xsl:value-of select="$customerOrderNo" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:attribute name="Purpose">
                                       <xsl:value-of select="$purpose" />
                                    </xsl:attribute>

                                    <xsl:if test="string-length($varTargetOSS) &gt; 0">
                                       <xsl:attribute name="ConditionVariable1">
                                          <xsl:value-of select="$varTargetOSS" />
                                       </xsl:attribute>
                                    </xsl:if>

                                    <xsl:attribute name="PrimeLineNo">
                                       <xsl:value-of select="$primeLineNo" />
                                    </xsl:attribute>

                                    <xsl:attribute name="SubLineNo">
                                       <xsl:value-of select="$subLineNo" />
                                    </xsl:attribute>

                                    <xsl:attribute name="CustomerPONo">
                                       <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/entity/id" />
                                    </xsl:attribute>

                                    <xsl:attribute name="LineType">
                                       <xsl:choose>
                                          <xsl:when test="*[name() = 'customerFacingService']">CFS</xsl:when>

                                          <xsl:when test="*[name() = 'resourceFacingService']">RFS</xsl:when>

                                          <xsl:when test="*[name() = 'logicalResource']">LOGICAL_RESOURCE</xsl:when>

                                          <xsl:when test="*[name() = 'physicalResource']">PHYSICAL_RESOURCE</xsl:when>

                                          <xsl:when test="*[name() = 'orderedProduct']">
                                             <xsl:choose>
                                                <xsl:when test="contains($productBundledOfferItemList,$selectedItemID)">PRODUCT_BUNDLE_OFFER</xsl:when>

                                                <xsl:otherwise>PRODUCT_OFFER</xsl:otherwise>
                                             </xsl:choose>
                                          </xsl:when>
                                       </xsl:choose>
                                    </xsl:attribute>

                                    <xsl:attribute name="ReturnReason">
                                       <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_RETURNREASON']/value/stringValue" />
                                    </xsl:attribute>

<!-- START: CIT -7 Changes  -->
                                    <xsl:if test="*[name() = 'physicalResource' ] and $varTargetOSS='SHIPPING'">
                                       <xsl:attribute name="ShipNode">
                                          <xsl:value-of select="*[name() = 'physicalResource' ]/entity/characteristic[catalogId='CH_SHIPNODE']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:variable name="varDeliveryMethod">
                                          <xsl:value-of select="*[name() = 'physicalResource' ]/entity/characteristic[catalogId='CH_DELMETHOD']/value/stringValue" />
                                       </xsl:variable>

                                       <xsl:attribute name="DeliveryMethod">
                                          <xsl:value-of select="$varDeliveryMethod" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ShipToKey">
                                          <xsl:value-of select="*[name() = 'physicalResource' ]/entity/characteristic[catalogId='CH_SHIPTO']/value/stringValue" />
                                       </xsl:attribute>
                                    </xsl:if>

<!-- END: CIT -7 Changes  -->
<!--  START : Defect 411 and 427 -->
                                    <xsl:if test="string-length(businessInteractionItem/scheduledDate) &gt; 0">
                                       <xsl:attribute name="ReqShipDate">
                                          <xsl:value-of select="businessInteractionItem/scheduledDate">
                                          </xsl:value-of>
                                       </xsl:attribute>
                                    </xsl:if>

<!--  END : Defect 411 and 427  -->
<!--Start: Changes made for Reservation-->
                                    <xsl:variable name="itemId">
                                       <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/catalogId" />
                                    </xsl:variable>

                                    <xsl:if test="*[name() = 'physicalResource' ] and $varTargetOSS='SHIPPING'">
                                       <xsl:variable name="varReservationId">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationId" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationNode">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationNode" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationShipDate">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationShipDate" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationItemId">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationItemId" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationItemUOM">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationItemUOM" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationQty">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationQty" />
                                       </xsl:variable>

                                       <xsl:variable name="varReservationProductClass">
                                          <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationProductClass" />
                                       </xsl:variable>

<!-- Changes made for Defect 750 Start-->
                                       <xsl:if test="string-length($varReservationId) &gt; 0">
                                          <OrderLineReservations>
                                             <OrderLineReservation DemandType="RSRV_ORDER">
                                                <xsl:attribute name="ReservationID">
                                                   <xsl:value-of select="$varReservationId" />
                                                </xsl:attribute>

                                                <xsl:attribute name="Node">
                                                   <xsl:value-of select="$varReservationNode" />
                                                </xsl:attribute>

                                                <xsl:attribute name="RequestedReservationDate">
                                                   <xsl:value-of select="$varReservationShipDate" />
                                                </xsl:attribute>

                                                <xsl:attribute name="ItemID">
                                                   <xsl:value-of select="$varReservationItemId" />
                                                </xsl:attribute>

                                                <xsl:attribute name="UnitOfMeasure">
                                                   <xsl:value-of select="$varReservationItemUOM" />
                                                </xsl:attribute>

                                                <xsl:attribute name="Quantity">
                                                   <xsl:value-of select="$varReservationQty" />
                                                </xsl:attribute>

                                                <xsl:attribute name="ProductClass">
                                                   <xsl:value-of select="$varReservationProductClass" />
                                                </xsl:attribute>
                                             </OrderLineReservation>
                                          </OrderLineReservations>
                                       </xsl:if>

<!-- Changes made for Defect 750 End-->
                                    </xsl:if>

<!--End: Changes made for Reservation-->
                                    <Item UnitOfMeasure="EACH" ProductClass="Good">
                                       <xsl:attribute name="ItemID">
                                          <xsl:value-of select="$itemId" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ManufacturerItem">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/entity/id" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ManufacturerItemDesc">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/entity/name" />
                                       </xsl:attribute>

                                       <xsl:attribute name="CustomerItem">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/id" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ItemDesc">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/name" />
                                       </xsl:attribute>
                                    </Item>

                                    <Extn>
<!-- <xsl:attribute name="ExtnReservationId">
                                                        <xsl:value-of select="*[name() = 'physicalResource']/resourceDetails/reservationId" />
                                                    </xsl:attribute> -->
                                       <xsl:attribute name="ExtnInventoryId">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/businessInteractionEntity/inventoryId" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnParentLineSeqNo">
                                          <xsl:value-of select="concat($primeLineNo,'.',$subLineNo)" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnSpecVersion">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_SPECVERSION']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnPricePlanSpecId">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_PRICEPLANSPECID']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnRequestType">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_REQUESTTYPE']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnContractId">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_CONTRACTID']/value/stringValue" />
                                       </xsl:attribute>

<!--  Changes made for exchange and return: Start -->
                                       <xsl:attribute name="ExtnParentOrderNo">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_PARENTORDERNO']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnParentLineCustomerPONo">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_PARENTLINECUSTOMERPONO']/value/stringValue" />
                                       </xsl:attribute>

                                       <xsl:attribute name="ExtnReturnLineNumber">
                                          <xsl:value-of select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic[catalogId='CH_RETURNLINENUMBER']/value/stringValue" />
                                       </xsl:attribute>

<!--  Changes made for exchange and return: End -->
<!-- Start: ExtnAssociatedInventoryId -->
                                       <xsl:attribute name="ExtnAssociatedInventoryId">
                                          <xsl:value-of select="$productRelationshipInventoryId" />
                                       </xsl:attribute>

<!-- End: ExtnAssociatedInventoryId -->
<!-- START: CIT -41 Changes  -->
<!-- CIT122: Fixes Start-->
                                       <xsl:attribute name="ExtnEntityId">
                                          <xsl:value-of select="$entityId" />
                                       </xsl:attribute>

<!-- CIT122: Fixes End -->
<!-- END: CIT -41 Changes  -->
                                       <EXTNOrderLinePropertiesList>
                                          <xsl:for-each select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristicValueGroup">
                                             <xsl:variable name="charValGroupItemID" select="catalogId" />
                                             <!-- Decomp response handling for multiple characteristicValueGroup: Start-->
                                             <xsl:variable name="varIndex" select="index"/>
                                             <xsl:variable name="varTotalNumberOfCatalogIdExists" 
	                                             	select="count(../*[name() = 'characteristicValueGroup'][catalogId = $charValGroupItemID])"/>
											 <!-- Decomp response handling for multiple characteristicValueGroup: End-->
                                             <xsl:for-each select="characteristic">
                                                <EXTNOrderLineProperties>
                                                   <xsl:attribute name="DescribedByCharacteristicName">
                                                      <xsl:value-of select="catalogId" />
                                                   </xsl:attribute>

                                                   <xsl:attribute name="DescribedByCharacteristic">
                                                      <xsl:value-of select="name" />
                                                   </xsl:attribute>

                                                   <xsl:attribute name="DescribedByType">
                                                      <xsl:value-of select="value/type" />
                                                   </xsl:attribute>

                                                   <xsl:if test="value/type = 'Integer'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/integerValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>

                                                   <xsl:if test="value/type = 'String'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/stringValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>

                                                   <xsl:if test="value/type = 'Boolean'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/booleanValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>
												   <!-- Decomp response handling for multiple characteristicValueGroup: Start-->	
												   <xsl:choose>
                     							   		<xsl:when test= "$varTotalNumberOfCatalogIdExists > 1 ">	
															<xsl:attribute name="CharacteristicEntityId">
		                                                      <xsl:value-of select="concat(id,'_',$varIndex+1)" />
		                                                   </xsl:attribute>

		                                                   <xsl:attribute name="CharacteristicPath">
		                                                      <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID,'/',$charValGroupItemID, '|',$varIndex+1)" />
		                                                   </xsl:attribute>
                                                   		</xsl:when>
                                                   		<xsl:otherwise>
                                                   			<xsl:attribute name="CharacteristicEntityId">
																<xsl:value-of select="id" />
															</xsl:attribute>

															<xsl:attribute name="CharacteristicPath">
			                                                  <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID,'/',$charValGroupItemID)" />
															</xsl:attribute>
                                                   		</xsl:otherwise>
                                                  </xsl:choose>
                                                  <!-- Decomp response handling for multiple characteristicValueGroup: End-->    		
                                                </EXTNOrderLineProperties>
                                             </xsl:for-each>

                                             <xsl:for-each select="componentCharacteristicValueGroup">
                                                <xsl:variable name="componentCharValGroupItemID" select="catalogId" />

                                                <xsl:for-each select="characteristic">
                                                   <EXTNOrderLineProperties>
                                                      <xsl:attribute name="DescribedByCharacteristicName">
                                                         <xsl:value-of select="catalogId" />
                                                      </xsl:attribute>

                                                      <xsl:attribute name="DescribedByCharacteristic">
                                                         <xsl:value-of select="name" />
                                                      </xsl:attribute>

                                                      <xsl:attribute name="DescribedByType">
                                                         <xsl:value-of select="value/type" />
                                                      </xsl:attribute>

                                                      <xsl:if test="value/type = 'Integer'">
                                                         <xsl:attribute name="DescribedByValue">
                                                            <xsl:value-of select="value/integerValue" />
                                                         </xsl:attribute>
                                                      </xsl:if>

                                                      <xsl:if test="value/type = 'String'">
                                                         <xsl:attribute name="DescribedByValue">
                                                            <xsl:value-of select="value/stringValue" />
                                                         </xsl:attribute>
                                                      </xsl:if>

                                                      <xsl:if test="value/type = 'Boolean'">
                                                         <xsl:attribute name="DescribedByValue">
                                                            <xsl:value-of select="value/booleanValue" />
                                                         </xsl:attribute>
                                                      </xsl:if>

                                                      <xsl:attribute name="CharacteristicEntityId">
                                                         <xsl:value-of select="id" />
                                                      </xsl:attribute>

                                                      <xsl:attribute name="CharacteristicPath">
                                                         <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID,'/',$charValGroupItemID,'/',$componentCharValGroupItemID)" />
                                                      </xsl:attribute>
                                                   </EXTNOrderLineProperties>
                                                </xsl:for-each>
                                             </xsl:for-each>
                                          </xsl:for-each>

                                          <xsl:for-each select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']/entity/characteristic">
<!--<xsl:if test="catalogId !='CH_ENTRY_TYPE' and catalogId !='CH_DELMETHOD' and catalogId !='CH_SHIPTO' and catalogId !='CH_SHIPNODE'">-->
                                             <xsl:if test="(catalogId !='CH_ENTRY_TYPE') and (catalogId !='CH_DELMETHOD') and (catalogId !='CH_SHIPTO') and (catalogId !='CH_SHIPNODE') and (catalogId !='CH_REQUESTTYPE') and (catalogId !='CH_SPECVERSION') and (catalogId !='CH_PRICEPLANSPECID') and (catalogId !='CH_INVENTORYID') and (catalogId !='CH_PARENTORDERNO') and (catalogId !='CH_PARENTLINECUSTOMERPONO') and (catalogId !='CH_CONTRACTID') and (catalogId !='CH_RETURNLINENUMBER') and (catalogId !='CH_RETURNREASON')">
                                                <EXTNOrderLineProperties>
                                                   <xsl:attribute name="DescribedByCharacteristicName">
                                                      <xsl:value-of select="catalogId" />
                                                   </xsl:attribute>

                                                   <xsl:attribute name="DescribedByCharacteristic">
                                                      <xsl:value-of select="name" />
                                                   </xsl:attribute>

                                                   <xsl:attribute name="DescribedByType">
                                                      <xsl:value-of select="value/type" />
                                                   </xsl:attribute>

                                                   <xsl:if test="value/type = 'Integer'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/integerValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>

                                                   <xsl:if test="value/type = 'String'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/stringValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>

                                                   <xsl:if test="value/type = 'Boolean'">
                                                      <xsl:attribute name="DescribedByValue">
                                                         <xsl:value-of select="value/booleanValue" />
                                                      </xsl:attribute>
                                                   </xsl:if>

                                                   <xsl:attribute name="CharacteristicEntityId">
                                                      <xsl:value-of select="id" />
                                                   </xsl:attribute>

                                                   <xsl:attribute name="CharacteristicPath">
                                                      <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID)" />
                                                   </xsl:attribute>
                                                </EXTNOrderLineProperties>
                                             </xsl:if>
                                          </xsl:for-each>
                                       </EXTNOrderLinePropertiesList>
                                    </Extn>
                                 </OrderLine>

                                 <xsl:for-each select="*[name() = 'orderedProduct']/*[name() = 'componentProduct' or name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']">
                                    <xsl:variable name="varLastPositionProcessed">
                                       <xsl:value-of select="position() -1">
                                       </xsl:value-of>
                                    </xsl:variable>

                                    <xsl:variable name="nodeType">
                                       <xsl:value-of select="name()">
                                       </xsl:value-of>
                                    </xsl:variable>

                                    <xsl:call-template name="componentProduct">
                                       <xsl:with-param name="varTargetOSS" select="$varTargetOSS" />

                                       <xsl:with-param name="purpose" select="$purpose" />

                                       <xsl:with-param name="primeLineNoOfPreviousOrderLine" select="$primeLineNo" />

                                       <xsl:with-param name="selectedItemID" select="./businessInteractionEntity/catalogId" />

                                       <xsl:with-param name="componentType" select="$nodeType" />

                                       <xsl:with-param name="productBundledOfferItemList" select="$productBundledOfferItemList" />

                                       <xsl:with-param name="extnParentLineSeqNo" select="concat($primeLineNo,'.',$subLineNo)" />

                                       <xsl:with-param name="varTotalLinesSoFar" select="count(./../../*[name() = 'orderedProduct']/componentProduct[position() &lt;= $varLastPositionProcessed]/*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct'])" />
                                    </xsl:call-template>
                                 </xsl:for-each>
                              </xsl:for-each>
                           </OrderLines>
                        </Order>
                     </Input>
                  </API>
               </xsl:if>

               <xsl:if test="$Status='CANCELLED'">
                  <API Name="changeOrder">
                     <Input>
                        <Order Action="CANCEL" Override="Y">
                           <xsl:variable name="serviceOrdNo" select="entity/id" />

                           <xsl:attribute name="DocumentType">
                              <xsl:value-of select="$varDocumentType" />
                           </xsl:attribute>

                           <xsl:attribute name="EnterpriseCode">
                              <xsl:value-of select="$entCode" />
                           </xsl:attribute>

                           <xsl:attribute name="OrderNo">
                              <xsl:value-of select="$serviceOrdNo" />
                           </xsl:attribute>
                        </Order>
                     </Input>
                  </API>
               </xsl:if>
            </xsl:if>
         </xsl:for-each>
      </MultiApi>
   </xsl:template>

   <xsl:template name="componentProduct">
      <xsl:param name="varTargetOSS" />

      <xsl:param name="purpose" />

      <xsl:param name="primeLineNoOfPreviousOrderLine" />

      <xsl:param name="selectedItemID" />

      <xsl:param name="componentType" />

      <xsl:param name="productBundledOfferItemList" />

      <xsl:param name="extnParentLineSeqNo" />

      <xsl:param name="varTotalLinesSoFar" />

      <xsl:variable name="primeLineNo">
         <xsl:choose>
            <xsl:when test="not(number($varTotalLinesSoFar))">
               <xsl:value-of select="$primeLineNoOfPreviousOrderLine + position()" />
            </xsl:when>

            <xsl:otherwise>
               <xsl:value-of select="$primeLineNoOfPreviousOrderLine + $varTotalLinesSoFar + position()" />
            </xsl:otherwise>
         </xsl:choose>
      </xsl:variable>

      <xsl:variable name="updateExtnParentLineSeqNo">
         <xsl:choose>
            <xsl:when test="$componentType = 'componentProduct'">
               <xsl:value-of select="concat($primeLineNoOfPreviousOrderLine + $varTotalLinesSoFar + position(),'.',$subLineNo)" />
            </xsl:when>

            <xsl:otherwise>
               <xsl:value-of select="$extnParentLineSeqNo" />
            </xsl:otherwise>
         </xsl:choose>
      </xsl:variable>

      <OrderLine OrderedQty="1">
         <xsl:attribute name="componentType">
            <xsl:value-of select="$componentType" />
         </xsl:attribute>

         <xsl:attribute name="Purpose">
            <xsl:value-of select="$purpose" />
         </xsl:attribute>

         <xsl:attribute name="CustomerPONo">
            <xsl:value-of select="./businessInteractionEntity/entity/id" />
         </xsl:attribute>

         <xsl:attribute name="LineType">
            <xsl:choose>
               <xsl:when test="$componentType = 'customerFacingService'">CFS</xsl:when>

               <xsl:when test="$componentType = 'resourceFacingService'">RFS</xsl:when>

               <xsl:when test="$componentType = 'logicalResource'">LOGICAL_RESOURCE</xsl:when>

               <xsl:when test="$componentType = 'physicalResource'">PHYSICAL_RESOURCE</xsl:when>

               <xsl:when test="$componentType = 'orderedProduct'">
                  <xsl:choose>
                     <xsl:when test="contains($productBundledOfferItemList,$selectedItemID)">PRODUCT_BUNDLE_OFFER</xsl:when>

                     <xsl:otherwise>PRODUCT_OFFER</xsl:otherwise>
                  </xsl:choose>
               </xsl:when>

               <xsl:when test="$componentType = 'componentProduct'">
                  <xsl:choose>
                     <xsl:when test="contains($productBundledOfferItemList,$selectedItemID)">PRODUCT_BUNDLE_OFFER</xsl:when>

                     <xsl:otherwise>PRODUCT_OFFER</xsl:otherwise>
                  </xsl:choose>
               </xsl:when>
            </xsl:choose>
         </xsl:attribute>

         <xsl:if test="string-length($varTargetOSS) &gt; 0">
            <xsl:attribute name="ConditionVariable1">
               <xsl:value-of select="$varTargetOSS" />
            </xsl:attribute>
         </xsl:if>

         <xsl:attribute name="PrimeLineNo">
            <xsl:value-of select="$primeLineNo" />
         </xsl:attribute>

         <xsl:attribute name="SubLineNo">
            <xsl:value-of select="$subLineNo" />
         </xsl:attribute>

<!--  START : Defect 411 and 427 -->
         <xsl:if test="string-length(businessInteractionItem/scheduledDate) &gt; 0">
            <xsl:attribute name="ReqShipDate">
               <xsl:value-of select="./businessInteractionItem/scheduledDate">
               </xsl:value-of>
            </xsl:attribute>
         </xsl:if>

<!--Start: Changes made for Reservation-->
         <xsl:variable name="itemId">
            <xsl:value-of select="./businessInteractionEntity/catalogId" />
         </xsl:variable>

         <xsl:if test="*[$componentType = 'physicalResource' ] and $varTargetOSS='SHIPPING'">
            <xsl:variable name="varReservationId">
               <xsl:value-of select="./resourceDetails/reservationId" />
            </xsl:variable>

<!-- Changes made for Defect 705 Start-->
            <xsl:if test="string-length($varReservationId) &gt; 0">
               <OrderLineReservations>
                  <OrderLineReservation DemandType="RSRV_ORDER" UnitOfMeasure="EACH" ProductClass="Good" Quantity="1.00">
                     <xsl:attribute name="ReservationID">
                        <xsl:value-of select="$varReservationId" />
                     </xsl:attribute>

                     <xsl:attribute name="Node">
                        <xsl:value-of select="./entity/characteristic[catalogId ='CH_SHIPNODE']/value/stringValue" />
                     </xsl:attribute>

                     <xsl:attribute name="ItemID">
                        <xsl:value-of select="$itemId" />
                     </xsl:attribute>
                  </OrderLineReservation>
               </OrderLineReservations>
            </xsl:if>

<!-- Changes made for Defect 705 End-->
         </xsl:if>

<!--End: Changes made for Reservation-->
<!--  END : Defect 411 and 427  -->
         <Item UnitOfMeasure="EACH" ProductClass="Good">
            <xsl:attribute name="ItemID">
               <xsl:value-of select="$itemId" />
            </xsl:attribute>

            <xsl:attribute name="ManufacturerItem">
               <xsl:value-of select="./businessInteractionEntity/entity/id" />
            </xsl:attribute>

            <xsl:attribute name="ManufacturerItemDesc">
               <xsl:value-of select="./businessInteractionEntity/entity/name" />
            </xsl:attribute>

            <xsl:attribute name="CustomerItem">
               <xsl:value-of select="./entity/id" />
            </xsl:attribute>

            <xsl:attribute name="ItemDesc">
               <xsl:value-of select="./entity/name" />
            </xsl:attribute>
         </Item>

         <Extn>
            <xsl:attribute name="ExtnParentLineSeqNo">
               <xsl:value-of select="$extnParentLineSeqNo" />
            </xsl:attribute>

            <xsl:attribute name="ExtnInventoryId">
               <xsl:value-of select="./businessInteractionEntity/inventoryId" />
            </xsl:attribute>

<!-- CIT122: Fixes Start -->
            <xsl:attribute name="ExtnSpecVersion">
               <xsl:value-of select="./entity/characteristic[catalogId='CH_SPECVERSION']/value/stringValue" />
            </xsl:attribute>

            <xsl:attribute name="ExtnPricePlanSpecId">
               <xsl:value-of select="./entity/characteristic[catalogId='CH_PRICEPLANSPECID']/value/stringValue" />
            </xsl:attribute>

            <xsl:attribute name="ExtnRequestType">
               <xsl:value-of select="./entity/characteristic[catalogId='CH_REQUESTTYPE']/value/stringValue" />
            </xsl:attribute>

            <xsl:attribute name="ExtnContractId">
               <xsl:value-of select="./entity/characteristic[catalogId='CH_CONTRACTID']/value/stringValue" />
            </xsl:attribute>

            <xsl:attribute name="ExtnReturnLineNumber">
               <xsl:value-of select="./entity/characteristic[catalogId='CH_RETURNLINENUMBER']/value/stringValue" />
            </xsl:attribute>

            <xsl:if test="$componentType = 'customerFacingService' or $componentType = 'resourceFacingService' or $componentType = 'logicalResource' or $componentType = 'physicalResource' or $componentType = 'orderedProduct'">
               <xsl:attribute name="ExtnEntityId">
                  <xsl:value-of select="./entity/id" />
               </xsl:attribute>
            </xsl:if>

<!--CIT122: Fixes End -->
<!-- Start: ExtnAssociatedInventoryId -->
            <xsl:if test="$componentType = 'orderedProduct'">
               <xsl:attribute name="ExtnAssociatedInventoryId">
                  <xsl:value-of select="./productRelationship/product/businessInteractionEntity/inventoryId" />
               </xsl:attribute>
            </xsl:if>

<!-- End: ExtnAssociatedInventoryId -->
            <EXTNOrderLinePropertiesList>
               <xsl:for-each select="./entity/characteristicValueGroup">
                  <xsl:variable name="charValGroupItemID" select="catalogId" />

                  <xsl:for-each select="characteristic">
                     <EXTNOrderLineProperties>
                        <xsl:attribute name="DescribedByCharacteristicName">
                           <xsl:value-of select="catalogId" />
                        </xsl:attribute>

                        <xsl:attribute name="DescribedByCharacteristic">
                           <xsl:value-of select="name" />
                        </xsl:attribute>

                        <xsl:attribute name="DescribedByType">
                           <xsl:value-of select="value/type" />
                        </xsl:attribute>

                        <xsl:if test="value/type = 'Integer'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/integerValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="value/type = 'String'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/stringValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="value/type = 'Boolean'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/booleanValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:attribute name="CharacteristicEntityId">
                           <xsl:value-of select="id" />
                        </xsl:attribute>

                        <xsl:attribute name="CharacteristicPath">
                           <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID,'/',$charValGroupItemID)" />
                        </xsl:attribute>
                     </EXTNOrderLineProperties>
                  </xsl:for-each>

                  <xsl:for-each select="componentCharacteristicValueGroup">
                     <xsl:variable name="componentCharValGroupItemID" select="catalogId" />

                     <xsl:for-each select="characteristic">
                        <EXTNOrderLineProperties>
                           <xsl:attribute name="DescribedByCharacteristicName">
                              <xsl:value-of select="catalogId" />
                           </xsl:attribute>

                           <xsl:attribute name="DescribedByCharacteristic">
                              <xsl:value-of select="name" />
                           </xsl:attribute>

                           <xsl:attribute name="DescribedByType">
                              <xsl:value-of select="value/type" />
                           </xsl:attribute>

                           <xsl:if test="value/type = 'Integer'">
                              <xsl:attribute name="DescribedByValue">
                                 <xsl:value-of select="value/integerValue" />
                              </xsl:attribute>
                           </xsl:if>

                           <xsl:if test="value/type = 'String'">
                              <xsl:attribute name="DescribedByValue">
                                 <xsl:value-of select="value/stringValue" />
                              </xsl:attribute>
                           </xsl:if>

                           <xsl:if test="value/type = 'Boolean'">
                              <xsl:attribute name="DescribedByValue">
                                 <xsl:value-of select="value/booleanValue" />
                              </xsl:attribute>
                           </xsl:if>

                           <xsl:attribute name="CharacteristicEntityId">
                              <xsl:value-of select="id" />
                           </xsl:attribute>

                           <xsl:attribute name="CharacteristicPath">
                              <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID,'/',$charValGroupItemID,'/',$componentCharValGroupItemID)" />
                           </xsl:attribute>
                        </EXTNOrderLineProperties>
                     </xsl:for-each>
                  </xsl:for-each>
               </xsl:for-each>

               <xsl:for-each select="./entity/characteristic">
                  <xsl:if test="(catalogId !='CH_ENTRY_TYPE') and (catalogId !='CH_DELMETHOD') and (catalogId !='CH_SHIPTO') and (catalogId !='CH_SHIPNODE') and (catalogId !='CH_REQUESTTYPE') and (catalogId !='CH_SPECVERSION') and (catalogId !='CH_PRICEPLANSPECID') and (catalogId !='CH_INVENTORYID') and (catalogId !='CH_PARENTORDERNO') and (catalogId !='CH_PARENTLINECUSTOMERPONO') and (catalogId !='CH_CONTRACTID') and (catalogId !='reservationItemId') and (catalogId !='reservationNode') and (catalogId !='reservationId') and (catalogId !='reservationQty') and (catalogId !='reservationItemUOM') and (catalogId !='reservationProductClass') and (catalogId !='reservationShipDate')">
                     <EXTNOrderLineProperties>
                        <xsl:attribute name="DescribedByCharacteristicName">
                           <xsl:value-of select="catalogId" />
                        </xsl:attribute>

                        <xsl:attribute name="DescribedByCharacteristic">
                           <xsl:value-of select="name" />
                        </xsl:attribute>

                        <xsl:attribute name="DescribedByType">
                           <xsl:value-of select="value/type" />
                        </xsl:attribute>

                        <xsl:if test="value/type = 'Integer'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/integerValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="value/type = 'String'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/stringValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:if test="value/type = 'Boolean'">
                           <xsl:attribute name="DescribedByValue">
                              <xsl:value-of select="value/booleanValue" />
                           </xsl:attribute>
                        </xsl:if>

                        <xsl:attribute name="CharacteristicEntityId">
                           <xsl:value-of select="id" />
                        </xsl:attribute>

                        <xsl:attribute name="CharacteristicPath">
                           <xsl:value-of select="concat('/ItemAttribute/',$selectedItemID)" />
                        </xsl:attribute>
                     </EXTNOrderLineProperties>
                  </xsl:if>
               </xsl:for-each>
            </EXTNOrderLinePropertiesList>
         </Extn>
      </OrderLine>

      <xsl:for-each select="*[name() = 'customerFacingService' or name() = 'resourceFacingService' or name() = 'logicalResource' or name() = 'physicalResource' or name() = 'orderedProduct']">
         <xsl:call-template name="componentProduct">
            <xsl:with-param name="varTargetOSS" select="$varTargetOSS" />

            <xsl:with-param name="purpose" select="$purpose" />

            <xsl:with-param name="primeLineNoOfPreviousOrderLine" select="$primeLineNo" />

            <xsl:with-param name="selectedItemID" select="./businessInteractionEntity/catalogId" />

            <xsl:with-param name="componentType" select="name()" />

            <xsl:with-param name="productBundledOfferItemList" select="$productBundledOfferItemList" />

            <xsl:with-param name="extnParentLineSeqNo" select="$updateExtnParentLineSeqNo" />

            <xsl:with-param name="varTotalLinesSoFar" select="0" />
         </xsl:call-template>
      </xsl:for-each>
   </xsl:template>
</xsl:stylesheet>

