<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:barcode="org.krysalis.barcode4j.xalan.BarcodeExt" xmlns:common="http://exslt.org/common"
                xmlns:xalan="http://xml.apache.org" exclude-result-prefixes="barcode common xalan">
	<xsl:template match="/">
	<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
		<fo:layout-master-set>
            <fo:simple-page-master master-name="simple"
                            page-height="8in" page-width="11in" margin-left="0.2cm"
                            margin-right="0.2cm" margin-bottom="1cm" margin-top="0.5cm">
                            <fo:region-body margin-top="0.5cm" />
            </fo:simple-page-master>
            </fo:layout-master-set>
            	<fo:page-sequence master-reference="simple">
                	<fo:flow flow-name="xsl-region-body">
						<xsl:choose>
							<xsl:when test="empty">
								<fo:block>
									<fo:block font-size="8pt"><xsl:value-of select="empty"/></fo:block>
								</fo:block>
							</xsl:when>
							<xsl:otherwise>
							<fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" text-align="center" font-family="Verdana, Geneva, sans-serif" font-size="8pt" font-weight="normal">
					        <fo:block font-size="12pt" font-weight="bold">
					        	ARGUS MEDICAL MANAGEMENT, LLC.
					        </fo:block>
					        <fo:block>
					        	PAYMENTS PRODUCTIVITY TRACKING SYSTEM
					        </fo:block>
					        </fo:block>
                        <fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif" font-size="8pt" font-weight="normal">

	                            <fo:table display-align="center" text-align="center" width="10.8in" border="solid 0.1mm black" space-before.optimum="5pt">
	                               <fo:table-column column-width="0.5cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.8cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.3cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.7cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="auto" border="solid 0.1mm black"/>
		                               <fo:table-header >
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold"> # </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold">Date Recd</fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold">Ticket Name  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Dr Office </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Insurance</fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Deposit Amount  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Manual Posted Amount</fo:block>
								            </fo:table-cell>
								            <fo:table-cell margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold"> Electronic Posted Amount </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Amount Posted In CT </fo:block>
								            </fo:table-cell>
								            <fo:table-cell margin-left="0.3mm" margin-right="0.2mm" >
								            	<fo:block font-weight="bold"> Scan Date </fo:block>
								            </fo:table-cell>
											<fo:table-cell margin-left="0.3mm" margin-right="0.2mm" >
								            	<fo:block font-weight="bold"> Total time taken  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Manual Transactions  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Electronic Transactions  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Total Transactions  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Date Posted  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Posted By  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Remarks  </fo:block>
								            </fo:table-cell>
									   </fo:table-header>
									   <xsl:for-each select="pp/list/PaymentProductivity">
		                               <fo:table-body  height="9in" border="solid 0.1mm black">

											<fo:table-row border="solid 0.1mm black">

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="position()"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="createdOn"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="paymentBatch/id"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentBatch/doctor/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentBatch/insurance/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(paymentBatch/depositAmount)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number((paymentBatch/depositAmount + paymentBatch/ndba),'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(paymentBatch/manuallyPostedAmt)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(paymentBatch/manuallyPostedAmt,'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(paymentBatch/elecPostedAmt)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(paymentBatch/elecPostedAmt,'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(paymentBatch/ctPostedTotal)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number((paymentBatch/manuallyPostedAmt + paymentBatch/elecPostedAmt),'0.00')"/> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="scanDate"/>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell >
	                                                <fo:block>
	                                                <xsl:choose>
		                                                <xsl:when test="time">
		                                                	<xsl:value-of select="time"/>
		                                                </xsl:when>
		                                                <xsl:otherwise>
		                                                	<xsl:text>0</xsl:text>
		                                                </xsl:otherwise>
													</xsl:choose>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                <xsl:choose>
		                                                <xsl:when test="manualTransaction">
		                                                	<xsl:value-of select="manualTransaction"/>
		                                                </xsl:when>
		                                                <xsl:otherwise>
		                                                	<xsl:text>0</xsl:text>
		                                                </xsl:otherwise>
													</xsl:choose>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                <xsl:choose>
		                                                <xsl:when test="elecTransaction">
		                                                	<xsl:value-of select="elecTransaction"/>
		                                                </xsl:when>
		                                                <xsl:otherwise>
		                                                	<xsl:text>0</xsl:text>
		                                                </xsl:otherwise>
													</xsl:choose>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:choose>
	                                                		<xsl:when test="manualTransaction ">
	                                                			<xsl:choose>
	                                                				<xsl:when test="elecTransaction">
	                                                					<xsl:value-of select="manualTransaction + elecTransaction"/>
	                                                				</xsl:when>
	                                                				<xsl:otherwise>
	                                                					<xsl:value-of select="manualTransaction"/>
	                                                				</xsl:otherwise>
	                                                			</xsl:choose>
	                                                		</xsl:when>
	                                                		<xsl:otherwise>
																<xsl:choose>
	                                                				<xsl:when test="manualTransaction ">
	                                                					<xsl:value-of select="manualTransaction + elecTransaction"/>
	                                                				</xsl:when>
	                                                				<xsl:otherwise>
	                                                					<xsl:value-of select="elecTransaction"/>
	                                                				</xsl:otherwise>
	                                                			</xsl:choose>
	                                                		</xsl:otherwise>
	                                                	</xsl:choose>

	                                                	<!-- <xsl:choose>
	                                             				<xsl:when test="manualTransaction + elecTransaction">
	                                             					<xsl:value-of select="manualTransaction + elecTransaction"/>
	                                             				</xsl:when>
	                                             				<xsl:otherwise>
	                                             					<xsl:text>0</xsl:text>
	                                             				</xsl:otherwise>
	                                                	</xsl:choose> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentBatch/datePosted"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentBatch/postedBy"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell text-align="left">
	                                                <fo:block wrap-option="wrap">
														<xsl:value-of select="remark"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
									</fo:table-body>
								</xsl:for-each>
								<fo:table-body  height="9in" border="solid 0.1mm black">

											<fo:table-row border="solid 0.1mm black">

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(pp/totalDepositAmount)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(pp/totalDepositAmount,'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(pp/totalManualAmount)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(pp/totalManualAmount,'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(pp/totalElecAmount)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number(pp/totalElecAmount,'0.00')"/> -->
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$
														<xsl:call-template name="convertSciToNumString">
														    <xsl:with-param name="inputVal"><xsl:value-of select="(pp/totalPostedInCT)" /></xsl:with-param>
														</xsl:call-template>
														<!-- <xsl:value-of select="format-number((pp/totalManualAmount + pp/totalElecAmount),'0.00')"/> -->
													</fo:block>
												</fo:table-cell>
												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>
												<fo:table-cell >
	                                                <fo:block>
	                                                <xsl:value-of select="pp/totalTime"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="pp/totalManual"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="pp/totalElec"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
	                                                	<xsl:value-of select="pp/totalManual + pp/totalElec"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell text-align="left">
	                                                <fo:block wrap-option="wrap">
														<xsl:text></xsl:text>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
									</fo:table-body>
							</fo:table >
						</fo:block>
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
			      <xsl:text>0</xsl:text>
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
				<xsl:value-of select="format-number(number($numCoefficient) * number($numMantisa),'0.00', 'zero')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
