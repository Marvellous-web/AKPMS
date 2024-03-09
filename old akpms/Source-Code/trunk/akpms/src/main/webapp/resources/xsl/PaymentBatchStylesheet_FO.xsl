<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt" xmlns:common="http://exslt.org/common"
	xmlns:xalan="http://xml.apache.org" exclude-result-prefixes="barcode common xalan">
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simple"
					page-height="11in" page-width="8in" margin-left="0.2cm"
					margin-right="0.2cm" margin-bottom="1cm" margin-top="0.5cm">
					<fo:region-body margin-top="0.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simple">
				<fo:flow flow-name="xsl-region-body">

					<!-- <fo:block text-indent="1em" font-family="sans-serif" font-size="14pt"
						space-before.minimum="2pt" space-before.maximum="6pt" space-before.optimum="4pt"
						space-after.minimum="2pt" space-after.maximum="6pt" space-after.optimum="4pt">
						<fo:block>Argus Medical Management, LLC.</fo:block> <fo:block>5150 E.Pacific
						Coast Hwy, Suite 500</fo:block> <fo:block>Long Beach CA 90804</fo:block>
						</fo:block> -->
					<xsl:choose>
						<xsl:when test="empty">
							<fo:block>
								<fo:block font-size="9pt">
									<xsl:value-of select="empty" />
								</fo:block>
							</fo:block>
						</xsl:when>
						<xsl:otherwise>

							<xsl:for-each select="list/PaymentBatch">

								<fo:block space-before.minimum="10pt"
									space-before.maximum="30pt" space-before.optimum="15pt"
									space-after.minimum="2pt" space-after.maximum="6pt"
									space-after.optimum="4pt" text-align="center"
									font-family="Verdana, Geneva, sans-serif" font-size="12pt"
									font-weight="normal">
									<fo:block font-size="17pt">
										ARGUS MEDICAL MANAGEMENT, LLC.
									</fo:block>
									<fo:block>
										PAYMENTS BATCH TRACKING SYSTEM TICKET
									</fo:block>
								</fo:block>

								<fo:block start-indent="1em" space-after.minimum="2pt"
									space-after.maximum="6pt" space-after.optimum="4pt"
									font-family="Verdana, Geneva, sans-serif" font-size="10pt"
									font-weight="normal">
									<fo:table space-before.optimum="15pt">
										<fo:table-column column-number="1"
											column-width="1.5in" />
										<fo:table-column column-number="2"
											column-width="1.8in" />
										<fo:table-body height="9in">
											<fo:table-row line-height="24pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Ticket Number
													</fo:block>
												</fo:table-cell>
												<fo:table-cell>
													<fo:block font-size="15pt">
														<xsl:value-of select="id" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>


									<fo:table space-before.optimum="15pt">
										<fo:table-column column-number="1"
											column-width="1.8in" />
										<fo:table-column column-number="2"
											column-width="1.8in" />
										<fo:table-column column-number="3"
											column-width="1.8in" />
										<fo:table-column column-number="4"
											column-width="2in" />
										<fo:table-body height="9in">

											<!-- <fo:table-row > <fo:table-cell text-align="left" number-columns-spanned="4">
												<fo:block font-family="sans-serif" font-weight="bold" font-size="13pt"> Part
												1 Doctor Office </fo:block> </fo:table-cell> </fo:table-row> -->

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Billing Month
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="billingMonth" />
														/
														<xsl:value-of select="billingYear" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Group/Doctor Name
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="doctor/name" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Payment Type
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="paymentType/name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														PH Doctor Name
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="phDoctor/name" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Insurance
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="insurance/name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Revenue Type
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="revenueType/name" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Money Source
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="moneySource/name" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">

													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>

													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Deposit Date
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="depositDate" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Agency Money
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">


													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="agencyMoney" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(agencyMoney,'0.00')" /> -->

													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Deposit Amount
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="depositAmount" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(depositAmount,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														OLD Prior AR
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="oldPriorAr" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(oldPriorAr,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														NDBA
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="ndba" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(ndba,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														CT Posted Total
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="ctPostedTotal" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of
															select="format-number(manuallyPostedAmt + elecPostedAmt,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Other Income
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="otherIncome" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(otherIncome,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														+/- Posting
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="posting" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(posting,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														NSF/Sys Ref
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="nsfSysRef" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(nsfSysRef,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Suspense Account
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="suspenseAccount" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(suspenseAccount,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														ERA-Check #
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="eraCheckNo" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Offset
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="offsetPlus" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(offsetPlus,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Refund Chk
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="refundChk" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(refundChk,'0.00')" /> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">

													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>

													</fo:block>
												</fo:table-cell>
											</fo:table-row>


											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left"
													number-columns-spanned="4">
													<fo:block>
														<fo:inline font-weight="bold"> Comment: </fo:inline>
														<xsl:value-of select="comment" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Created By
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="createdBy" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Created On
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="createdOn" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<fo:table-row line-height="20pt">
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Revised By
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="revisedBy" />
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block font-weight="bold">
														Revised On
													</fo:block>
												</fo:table-cell>
												<fo:table-cell text-align="left">
													<fo:block>
														<xsl:value-of select="revisedOn" />
													</fo:block>
												</fo:table-cell>
											</fo:table-row>

											<xsl:if test="postedBy">
												<fo:table-row line-height="20pt">
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Posted By
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="postedBy" />
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block font-weight="bold">
															Posted On
														</fo:block>
													</fo:table-cell>
													<fo:table-cell text-align="left">
														<fo:block>
															<xsl:value-of select="postedOn" />
														</fo:block>
													</fo:table-cell>
												</fo:table-row>

											</xsl:if>

										</fo:table-body>
									</fo:table>


									<fo:block start-indent="1em" space-before.minimum="10pt"
										space-before.maximum="30pt" space-before.optimum="30pt"
										space-after.minimum="2pt" space-after.maximum="6pt"
										space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif"
										font-size="11pt" font-weight="normal">

									</fo:block>

									 <xsl:if test="not(position()=last())">
								       	<fo:block break-before="page" />
								     </xsl:if>

								</fo:block>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:variable name="max-exp">
		<xsl:value-of
			select="'0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000'" />
	</xsl:variable>
	<xsl:decimal-format name="zero" NaN="0.00"/>
	<xsl:template name="convertSciToNumString" >
		  <xsl:param name="inputVal" select="0"/>

		  <xsl:variable name="inputCor">
		    <xsl:choose>
		<!-- <xsl:when test="starts-with($inputVal,'.')">  inaccessible in XSLT 1.0  -->
		      <xsl:when test="substring($inputVal, 1, 1) = '.'"><xsl:value-of select="concat('0', $inputVal)"/></xsl:when>
		      <xsl:when test="substring($inputVal, 1, 2) = '+.'"><xsl:value-of select="concat('0', substring($inputVal, 2))"/></xsl:when>
		      <xsl:when test="substring($inputVal, 1, 2) = '-.'"><xsl:value-of select="concat('-0', substring($inputVal, 2))"/></xsl:when>
		      <xsl:otherwise><xsl:value-of select="$inputVal"/></xsl:otherwise>
		    </xsl:choose>
		  </xsl:variable>

		  <xsl:variable name="numInput">
		    <xsl:value-of select="translate(string($inputCor),'e','E')"/>
		  </xsl:variable>

			  <xsl:choose>
			<!-- <xsl:when test="ends-with($numInput,'.')">  inaccessible in XSLT 1.0  -->
			    <xsl:when test="substring($numInput, string-length($numInput), 1) = '.'">
			      <xsl:value-of select="substring-before($numInput,'.')"/>
			    </xsl:when>
			    <xsl:when test="$numInput = '0'">
			      <xsl:text>0.00</xsl:text>
			    </xsl:when>
			    <xsl:when test="$numInput = '0.0'">
			      <xsl:text>0.00</xsl:text>
			    </xsl:when>
			    <xsl:when test="number($numInput)">
			      <xsl:value-of select="format-number($numInput,'0.00')"/>
			    </xsl:when>
			    <xsl:otherwise>

			      <!-- ==== Mantisa ==== -->
			      <xsl:variable name="numMantisa">
			        <xsl:choose>
			            <xsl:when test="contains($numInput,'.E')">
			                <xsl:value-of select="substring-before($numInput,'.E')"/>
			            </xsl:when>
			            <xsl:otherwise>
			                <xsl:value-of select="substring-before($numInput,'E')"/>
			            </xsl:otherwise>
			        </xsl:choose>
			      </xsl:variable>

				<!-- ==== Exponent ==== -->
				<xsl:variable name="numExponent">
					<xsl:choose>
						<xsl:when test="contains($numInput,'E+')">
							<xsl:value-of select="substring-after($numInput,'E+')" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="substring-after($numInput,'E')" />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<!-- ==== Coefficient ==== -->
				<xsl:variable name="numCoefficient">
					<xsl:choose>
						<xsl:when test="$numExponent > 0">
							<xsl:text>1</xsl:text>
							<xsl:value-of select="substring($max-exp, 1, number($numExponent))" />
						</xsl:when>
						<xsl:when test="$numExponent &lt; 0">
							<xsl:text>0.</xsl:text>
							<xsl:value-of select="substring($max-exp, 1, -number($numExponent)-1)" />
							<xsl:text>1</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							1
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<!-- <xsl:value-of select="format-number(agencyMoney,'0.00')" /> -->
				<xsl:value-of select="format-number(number($numCoefficient) * number($numMantisa),'0.00','zero')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
