<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output indent="yes"/>
	<xsl:template match="/">
		<Order>
			<xsl:copy-of select="Order/@OrderHeaderKey"/>
			<Extn>
				<xsl:copy-of select="Order/Extn/@ExtnCustomerOrderNo"/>
			</Extn>
			<OrderLines>
				<xsl:for-each select="Order/OrderLines/OrderLine[Item/@ItemID='SIM']">
					<OrderLine>
						<xsl:copy-of select="@OrderLineKey"/>
						<Extn>
							<EXTNOrderLinePropertiesList>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_SERIAL'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_SERIAL']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">123124124124</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_TYPE'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_TYPE']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">nano</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_IMSI'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_IMSI']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">441234567890123</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_ENCKEY'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_ENCKEY']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445566</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_TRANSPORT_KEY'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_TRANSPORT_KEY']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">001</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_SIM_PROFILE'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_SIM_PROFILE']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">GA6I119</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_ID'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_ID']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">A0000000018434d090a0BC000000</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_ALGO_NAME'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_ALGO_NAME']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">TripleDES_ECB</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_DEF_SYCID'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_DEF_SYCID']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">default</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_IMPL_RAN'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_IMPL_RAN']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">5</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_PROP_RAN'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_PROP_RAN']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">5</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_SIM_STATE'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_SIM_STATE']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">ACTIVE</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_SYNC_VALUE'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_SYNC_VALUE']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">1</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">1</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">2</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">8</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_VERSION']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KIC'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445566</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445567</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445568</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KID'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445566</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445567</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445568</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KIK'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445566</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445567</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">11223344556677889900112233445568</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KIC_AN'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIC_AN']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KID_AN'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">5</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">5</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">5</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KID_AN']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KIK_AN'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">3</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KIK_AN']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">1E21</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">1E21</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">1E21</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SPI']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">0000000001</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">0000000001</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">0000000001</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SV']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
								<xsl:if test="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties/@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID'">
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@*[name()!='DescribedByValue']"/>
										<xsl:attribute name="DescribedByValue">DomainKeyset2</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">DomainKeyset2</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@CharacteristicEntityId,'_2')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@CharacteristicPath,'|2')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
									<EXTNOrderLineProperties>
										<xsl:copy-of select="Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@*[name()!='DescribedByValue' and name()!='CharacteristicEntityId' and name()!='CharacteristicPath']"/>
										<xsl:attribute name="DescribedByValue">DomainKeyset2</xsl:attribute>
										<xsl:attribute name="CharacteristicEntityId">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@CharacteristicEntityId,'_3')"/>
										</xsl:attribute>
										<xsl:attribute name="CharacteristicPath">
											<xsl:value-of select="concat(Extn/EXTNOrderLinePropertiesList/EXTNOrderLineProperties[@DescribedByCharacteristicName='CH_OTASD_KEYSET_SID']/@CharacteristicPath,'|3')"/>
										</xsl:attribute>
									</EXTNOrderLineProperties>
								</xsl:if>
							</EXTNOrderLinePropertiesList>
						</Extn>
						<Item ItemID="SIM"/>
					</OrderLine>
				</xsl:for-each>
			</OrderLines>
		</Order>
	</xsl:template>
</xsl:stylesheet>