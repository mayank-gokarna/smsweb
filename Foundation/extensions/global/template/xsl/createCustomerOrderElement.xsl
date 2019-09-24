<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:variable name="typeProdOffer" select="'PRODUCT_OFFER'"/>
	<xsl:variable name="typeProdBundleOffer" select="'PRODUCT_BUNDLE_OFFER'"/>
	<xsl:variable name="typeLR" select="'LOGICAL_RESOURCE'"/>
	<xsl:variable name="typePR" select="'PHYSICAL_RESOURCE'"/>
	<xsl:variable name="typeCFS" select="'CFS'"/>
	<xsl:variable name="typeBIE" select="'BUSINESS_INTERACTION_ENTITY'"/>
	<xsl:variable name="bieName" select="'Business Interaction Entity 00'"/>
	<xsl:variable name="bieID" select="'BIE00'"/>
	<xsl:variable name="chID" select="'CH00'"/>
	<xsl:variable name="poiID" select="'POI00'"/>
	<xsl:template match="/">
		<customerOrder> 
			<customerOrderId>
				<xsl:value-of select="Order/@OrderNo"/>
			</customerOrderId>
			<entity>
				<id>
					<xsl:value-of select="Order/@OrderNo"/>
				</id>
				<name>
					<xsl:value-of select="Order/@OrderNo"/>
				</name>
				<type>CUSTOMER_ORDER</type>
			</entity>
			<productOrder>
				<entity>
					<id>
						<xsl:value-of select="Order/@OrderNo"/>
					</id>
					<name>
						<xsl:value-of select="Order/@OrderNo"/>
					</name>
					<type>PRODUCT_ORDER</type>
				</entity>
				<productOrderId>
					<xsl:value-of select="Order/@OrderNo"/>
				</productOrderId>
				<parentCustomerOrderRefId>
					<xsl:value-of select="Order/@OrderNo"/>
				</parentCustomerOrderRefId>
				<xsl:for-each select="Order/OrderLines/OrderLine">
					<productOrderItem>
						<orderType>
							<xsl:value-of select="@LineType"/>
						</orderType>
						<businessInteractionItem>
							<action>
								<xsl:value-of select="@Purpose"/>
							</action>
							<appointmentId>
								<xsl:value-of select="@ExtnAppointmentID"/>
							</appointmentId>
							<expectedDeliveryDate/>
							<requestedDate/>
							<scheduledDate>
								<xsl:value-of select="@ReqShipDate"/>
							</scheduledDate>
						</businessInteractionItem>
						<entity>
							<id>
								<xsl:value-of select="@CustomerPONo"/>
							</id>
							<name>
								<xsl:value-of select="concat($poiID,./Item/@ItemShortDesc)"/>
							</name>
							<type>
								<xsl:value-of select="concat(./@LineType,'_ITEM')"/>
							</type>
						</entity>
						<!--<xsl:for-each select="Order/OrderLines/OrderLine">-->
						<xsl:call-template name="OrderLine">
							<xsl:with-param name="elementName" select="'orderedProduct'"/>
						</xsl:call-template>
						<!--</xsl:for-each>-->
					</productOrderItem>
				</xsl:for-each>
			</productOrder>
		</customerOrder>
	</xsl:template>
	<xsl:template name="OrderLine">
		<xsl:param name="elementName"/>
		<xsl:variable name="plnNum" select="@PrimeLineNo"/>
		<xsl:variable name="slnNum" select="@SubLineNo"/>
		<xsl:variable name="uniqueNum" select="concat($plnNum,$slnNum)"/>
		<xsl:variable name="CatPath" select="./ItemDetails/CategoryList/Category/@CategoryPath"/>
		<xsl:variable name="SubCat" select="substring-before(substring-after ( substring-after (substring-after ($CatPath,  '/'),  '/'), '/'),'/')"/>
		<!-- If the product is TYPE_PRODUCT_OFFER -->
		<!--Element name will change based on whether it is invoked from productOrderItem or Component-->
		<xsl:if test="@LineType=  $typeProdOffer or @LineType=$typeProdBundleOffer">
			<xsl:element name="{$elementName}">
				<businessInteractionEntity>
					<catalogId>
						<xsl:value-of select="./Item/@ItemID"/>
					</catalogId>
					<entity>
					<!--CustomerOrderNo_CustomerPONo will uniquely identify an Orderline on the customer order-->
						<id>
							<xsl:value-of select="concat(//Order/@OrderNo,'_',@CustomerPONo)"/>
						</id>
						<name>
							<xsl:value-of select="concat($bieName,$uniqueNum)"/>
						</name>
						<type>
							<xsl:value-of select="$typeBIE"/>
						</type>
					</entity>
					<inventoryId/>
					<xsl:if test="@LineType=$typeProdOffer">
						<productSpecificationCatalogId>
							<xsl:value-of select="./ItemDetails/ItemAliasList/ItemAlias/@AliasValue"/>
						</productSpecificationCatalogId>
					</xsl:if>
				</businessInteractionEntity>
				<entity>
					<xsl:for-each select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
						<characteristic>
							<catalogId>
								<xsl:value-of select="@DescribedByCharacteristicName"/>
							</catalogId>
							<id>
								<xsl:value-of select="@CharacteristicEntityId"/>
							</id>
							<name>
								<xsl:value-of select="@DescribedByCharacteristic"/>
							</name>
							<!-- removed specifications here, as those were asked to ignore -->
							<value>
								<type>
									<xsl:value-of select="@DescribedByType"/>
								</type>
								<xsl:if test="@DescribedByType= 'Boolean'">
									<booleanValue>
										<xsl:value-of select="@DescribedByValue"/>
									</booleanValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'Integer'">
									<integerValue>
										<xsl:value-of select="@DescribedByValue"/>
									</integerValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'String'">
									<stringValue>
										<xsl:value-of select="@DescribedByValue"/>
									</stringValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'Date'">
									<dateValue>
										<xsl:value-of select="@DescribedByValue"/>
									</dateValue>
								</xsl:if>
							</value>
						</characteristic>
					</xsl:for-each>
					<id>
						<xsl:value-of select="concat('OP00',$uniqueNum)"/>
					</id>
					<name>
						<xsl:value-of select="./Item/@ItemShortDesc"/>
					</name>
					<type>PRODUCT</type>
				</entity>
				<xsl:for-each select="./ChildOrderLines/OrderLine">
					<xsl:call-template name="OrderLine">
						<xsl:with-param name="elementName" select="'componentProduct'"/>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:value-of select="./Extn/@ExtnProdCategory"/>
				<status>
					<xsl:value-of select="@Status"/>
				</status>
			</xsl:element>
		</xsl:if>
		<!-- If the product is TYPE_LOGICAL_RESOURCE -->
		<xsl:if test="@LineType=  $typeLR ">
			<logicalResource>
				<businessInteractionEntity>
					<catalogId>
						<xsl:value-of select="./Item/@ItemID"/>
					</catalogId>
					<entity>
					<!--CustomerOrderNo_CustomerPONo will uniquely identify an Orderline on the customer order-->
						<id>
							<xsl:value-of select="concat(//Order/@OrderNo,'_',@CustomerPONo)"/>
						</id>
						<name>
							<xsl:value-of select="concat($bieName,position(),$uniqueNum)"/>
						</name>
						<type>
							<xsl:value-of select="$typeBIE"/>
						</type>
					</entity>
				</businessInteractionEntity>
				<entity>
					<xsl:for-each select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
						<xsl:if test="@DescribedByCharacteristic != 'reservationId'">
							<characteristic>
								<catalogId>
									<xsl:value-of select="@DescribedByCharacteristicName"/>
								</catalogId>
								<id>
									<xsl:value-of select="@CharacteristicEntityId"/>
								</id>
								<name>
									<xsl:value-of select="@DescribedByCharacteristic"/>
								</name>
								<!-- specifications ignored -->
								<value>
									<type>
										<xsl:value-of select="@DescribedByType"/>
									</type>
									<xsl:if test="@DescribedByType= 'Boolean'">
										<booleanValue>
											<xsl:value-of select="@DescribedByValue"/>
										</booleanValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'Integer'">
										<integerValue>
											<xsl:value-of select="@DescribedByValue"/>
										</integerValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'String'">
										<stringValue>
											<xsl:value-of select="@DescribedByValue"/>
										</stringValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'Date'">
										<dateValue>
											<xsl:value-of select="@DescribedByValue"/>
										</dateValue>
									</xsl:if>
								</value>
							</characteristic>
						</xsl:if>
					</xsl:for-each>
					<id>
						<xsl:value-of select="concat('LR',position(),$uniqueNum)"/>
					</id>
					<name>
						<xsl:value-of select="./Item/@ItemShortDesc"/>
					</name>
					<type>LOGICAL_RESOURCE</type>
				</entity>
				<resourceDetails>
					<reservationId>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='reservationId']/@DescribedByValue"/>
					</reservationId>
					<reservationType>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='reservationId']/@DescribedByCharacteristicName"/>
					</reservationType>
				</resourceDetails>
				<status>RESERVED</status>
				<xsl:for-each select="./ChildOrderLines/OrderLine">
					<xsl:call-template name="OrderLine">
						<xsl:with-param name="elementName" select="'componentProduct'"/>
					</xsl:call-template>
				</xsl:for-each>
			</logicalResource>
		</xsl:if>
		<!-- If the product is TYPE_PHYSICAL_RESOURCE -->
		<xsl:if test="@LineType=  $typePR ">
			<physicalResource>
				<businessInteractionEntity>
					<catalogId>
						<xsl:value-of select="./Item/@ItemID"/>
					</catalogId>
					<entity>
					<!--CustomerOrderNo_CustomerPONo will uniquely identify an Orderline on the customer order-->
						<id>
							<xsl:value-of select="concat(//Order/@OrderNo,'_',@CustomerPONo)"/>
						</id>
						<name>
							<xsl:value-of select="concat($bieName,position(),$uniqueNum)"/>
						</name>
						<type>
							<xsl:value-of select="$typeBIE"/>
						</type>
					</entity>
				</businessInteractionEntity>
				<entity>
					<xsl:for-each select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
						<xsl:if test="@DescribedByCharacteristic != 'reservationId'">
							<characteristic>
								<catalogId>
									<xsl:value-of select="@DescribedByCharacteristicName"/>
								</catalogId>
								<id>
									<xsl:value-of select="@CharacteristicEntityId"/>
								</id>
								<name>
									<xsl:value-of select="@DescribedByCharacteristic"/>
								</name>
								<!-- specifications ignored -->
								<value>
									<type>
										<xsl:value-of select="@DescribedByType"/>
									</type>
									<xsl:if test="@DescribedByType= 'Boolean'">
										<booleanValue>
											<xsl:value-of select="@DescribedByValue"/>
										</booleanValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'Integer'">
										<integerValue>
											<xsl:value-of select="@DescribedByValue"/>
										</integerValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'String'">
										<stringValue>
											<xsl:value-of select="@DescribedByValue"/>
										</stringValue>
									</xsl:if>
									<xsl:if test="@DescribedByType= 'Date'">
										<dateValue>
											<xsl:value-of select="@DescribedByValue"/>
										</dateValue>
									</xsl:if>
								</value>
							</characteristic>
						</xsl:if>
					</xsl:for-each>
					<id>
						<xsl:value-of select="concat('PR',position(),$uniqueNum)"/>
					</id>
					<name>
						<xsl:value-of select="./Item/@ItemShortDesc"/>
					</name>
					<type>PHYSICAL_RESOURCE</type>
				</entity>
				<resourceDetails>
					<reservationId>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='reservationId']/@DescribedByValue"/>
					</reservationId>
					<reservationType>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='reservationId']/@DescribedByCharacteristicName"/>
					</reservationType>
				</resourceDetails>
				<serialNumber>276543</serialNumber>
				<status>RESERVED</status>
				<xsl:for-each select="./ChildOrderLines/OrderLine">
					<xsl:call-template name="OrderLine">
						<xsl:with-param name="elementName" select="'componentProduct'"/>
					</xsl:call-template>
				</xsl:for-each>
			</physicalResource>
		</xsl:if>
		<!-- If the product is TYPE_CFS -->
		<xsl:if test="@LineType=  $typeCFS">
			<customerFacingService>
				<businessInteractionEntity>
					<catalogId>
						<xsl:value-of select="./Item/@ItemID"/>
					</catalogId>
					<entity>
					<!--CustomerOrderNo_CustomerPONo will uniquely identify an Orderline on the customer order-->
						<id>
							<xsl:value-of select="concat(//Order/@OrderNo,'_',@CustomerPONo)"/>
						</id>
						<name>
							<xsl:value-of select="concat($bieName,position(),$uniqueNum)"/>
						</name>
						<type>
							<xsl:value-of select="$typeBIE"/>
						</type>
					</entity>
				</businessInteractionEntity>
				<entity>
					<xsl:for-each select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties">
						<characteristic>
							<catalogId>
								<xsl:value-of select="@DescribedByCharacteristicName"/>
							</catalogId>
							<id>
								<xsl:value-of select="@CharacteristicEntityId"/>
							</id>
							<name>
								<xsl:value-of select="@DescribedByCharacteristic"/>
							</name>
							<!-- removed specifications here, as those were asked to ignore -->
							<value>
								<type>
									<xsl:value-of select="@DescribedByType"/>
								</type>
								<xsl:if test="@DescribedByType= 'Boolean'">
									<booleanValue>
										<xsl:value-of select="@DescribedByValue"/>
									</booleanValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'Integer'">
									<integerValue>
										<xsl:value-of select="@DescribedByValue"/>
									</integerValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'String'">
									<stringValue>
										<xsl:value-of select="@DescribedByValue"/>
									</stringValue>
								</xsl:if>
								<xsl:if test="@DescribedByType= 'Date'">
									<dateValue>
										<xsl:value-of select="@DescribedByValue"/>
									</dateValue>
								</xsl:if>
							</value>
						</characteristic>
					</xsl:for-each>
					<id>
						<xsl:value-of select="concat('CF0',position(),$uniqueNum)"/>
					</id>
					<name>
						<xsl:value-of select="./Item/@ItemShortDesc"/>
					</name>
					<type>CUSTOMER_FACING_SERVICE</type>
				</entity>
				<serviceDetails>
					<hasStarted>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='hasStarted']/@DescribedByValue"/>
					</hasStarted>
					<mandatory>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='mandatory']/@DescribedByValue"/>
					</mandatory>
					<serviceEnabled>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='serviceEnabled']/@DescribedByValue"/>
					</serviceEnabled>
					<stateful>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='stateful']/@DescribedByValue"/>
					</stateful>
					<status>
						<xsl:value-of select="./Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristic='status']/@DescribedByValue"/>
					</status>
				</serviceDetails>
				<xsl:for-each select="./ChildOrderLines/OrderLine">
					<xsl:call-template name="OrderLine">
						<xsl:with-param name="elementName" select="'componentProduct'"/>
					</xsl:call-template>
				</xsl:for-each>
			</customerFacingService>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
