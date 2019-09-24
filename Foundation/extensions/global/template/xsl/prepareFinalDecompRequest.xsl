<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">


		<IBMOMDecompositionRequest
			xmlns="http://www.ibm.com/rules/decisionservice/IBMOrderManagerRules/IBMOMDecomposition">
			<dcReq
				xmlns="http://www.ibm.com/rules/decisionservice/IBMOrderManagerRules/IBMOMDecomposition/param">
				<dcReq xmlns="">
					<xsl:for-each select="MergedDoc/decomposition">
						<amendedDecomposition>
							<xsl:copy-of select="*" />
						</amendedDecomposition>
					</xsl:for-each>
					<customerConfiguration>
						<xsl:copy-of select="MergedDoc/customerConfiguration/*" />
					</customerConfiguration>
					<customerOrder>
						<xsl:copy-of select="MergedDoc/customerOrder/*" />
					</customerOrder>
					<xsl:for-each select="MergedDoc/productSpecifications">
						<productSpecifications>
							<xsl:copy-of select="*" />
						</productSpecifications>
					</xsl:for-each>
					<externalParameters>
						<xsl:copy-of select="MergedDoc/externalParameters/*" />
					</externalParameters>

				</dcReq>
			</dcReq>
		</IBMOMDecompositionRequest>

	</xsl:template>
</xsl:stylesheet>