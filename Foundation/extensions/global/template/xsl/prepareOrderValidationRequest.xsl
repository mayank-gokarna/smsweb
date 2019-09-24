<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<IBMOMValidationRequest
			xmlns="http://www.ibm.com/rules/decisionservice/IBMOrderManagerRules/IBMOMValidation">
			<inVReq
				xmlns="http://www.ibm.com/rules/decisionservice/IBMOrderManagerRules/IBMOMValidation/param">
				<inVReq xmlns="">
					<!--  <amendedDecomposition>
						<xsl:copy-of select="MergedDoc/decomposition/*" />
					</amendedDecomposition> -->
					<customerConfiguration>
						<xsl:copy-of select="MergedDoc/customerConfiguration/*" />
					</customerConfiguration>
					<customerOrder>
						<xsl:copy-of select="MergedDoc/customerOrder/*" />
					</customerOrder>
				</inVReq>
			</inVReq>
		</IBMOMValidationRequest>
	</xsl:template>
</xsl:stylesheet>