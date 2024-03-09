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
									<fo:block font-size="7pt"><xsl:value-of select="empty"/></fo:block>
								</fo:block>
							</xsl:when>
							<xsl:otherwise>
							<fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" text-align="center" font-family="Verdana, Geneva, sans-serif" font-size="7pt" font-weight="normal">
					        <fo:block font-size="14pt" font-weight="bold">
					        	ARGUS MEDICAL MANAGEMENT, LLC.
					        </fo:block>
					        <fo:block>
					        	PAYMENTS BATCH TRACKING SYSTEM
					        </fo:block>
					        </fo:block>
                        <fo:block  space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif" font-size="7pt" font-weight="normal">

	                            <fo:table display-align="center" text-align="center" width="7.5in" border="solid 0.1mm black" space-before.optimum="5pt">
	                               <fo:table-column column-width="1.6cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.6cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="1.7cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2.2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="2cm" border="solid 0.1mm black"/>
	                               <fo:table-column column-width="3.5cm" border="solid 0.1mm black"/>
		                               <fo:table-header >
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold">Ticket # </fo:block>
								            </fo:table-cell>
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold">Doctor/Group Name</fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold">Billing Month </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold">Deposit Date  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Deposit Amount  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Posted Total-CT  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Posted Date  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Posted By  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold"> Payment Type  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell margin-left="0.3mm" margin-right="0.2mm" >
								            	<fo:block font-weight="bold"> Insurance  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> ERA Check #  </fo:block>
								            </fo:table-cell>
								            <fo:table-cell >
								            	<fo:block font-weight="bold"> Comment  </fo:block>
								            </fo:table-cell>
									   </fo:table-header>
									   <xsl:for-each select="pb/list/PaymentBatch">
		                               <fo:table-body  height="9in" border="solid 0.1mm black">

											<fo:table-row border="solid 0.1mm black">

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="id"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block wrap-option="wrap">
														<xsl:value-of select="doctor/name"/>
													</fo:block>
													<fo:block wrap-option="wrap" >
														<xsl:value-of select="phDoctor/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="billingMonth"/> / <xsl:value-of select="billingYear"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="depositDate"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$<xsl:value-of select="format-number(depositAmount,'0.00')"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														$<xsl:value-of select="format-number(ctPostedTotal,'0.00')"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="datePosted"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="postedBy"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="paymentType/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="insurance/name"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell >
	                                                <fo:block>
														<xsl:value-of select="eraCheckNo"/>
													</fo:block>
												</fo:table-cell>

												<fo:table-cell text-align="left">
	                                                <fo:block hyphenate="true"
	                                                hyphenation-character="-"
	                                                hyphenation-ladder-count="2"
	                                                wrap-option="wrap">
														<xsl:value-of select="comment"/>
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
									</fo:table-body>
								</xsl:for-each>
							</fo:table >
						</fo:block>
						<fo:block space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif" font-size="7pt" font-weight="normal">
								<fo:table display-align="center" text-align="center"  border="solid 0.1mm black">
									<fo:table-column column-width="5cm" border="solid 0.1mm black"/>
									<fo:table-column column-width="2cm" border="solid 0.1mm black"/>
									<fo:table-column column-width="2cm" border="solid 0.1mm black"/>
									<fo:table-header >
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold"> </fo:block>
								            </fo:table-cell>
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold">Deposit Amount </fo:block>
								            </fo:table-cell>
								            <fo:table-cell  margin-left="0.3mm" margin-right="0.2mm">
								            	<fo:block font-weight="bold">Posted Total </fo:block>
								            </fo:table-cell>
								    </fo:table-header>
									<fo:table-body border="solid 0.1mm black">
										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Total</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="pb/depositTotal"/></fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="pb/postedTotal"/></fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
						</fo:block>
						<fo:block start-indent="1em" space-before.minimum="10pt"
					        space-before.maximum="30pt"
					        space-before.optimum="15pt"
					        space-after.minimum="2pt"
					        space-after.maximum="6pt"
					        space-after.optimum="4pt" font-family="Verdana, Geneva, sans-serif" font-size="7pt" font-weight="normal">
								<fo:table display-align="center" >
									<fo:table-column column-width="5cm" border="solid 0.1mm black"/>
									<fo:table-column column-width="2cm" border="solid 0.1mm black"/>
									<fo:table-body >
										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Deposit Total</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="pb/depositTotal"/></fo:block>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Posted Total</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="pb/postedTotal"/></fo:block>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Over/Under Posting</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="format-number(pb/overUnderPosting,'0.00')"/></fo:block>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Agency Money</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="format-number(pb/agencyMoneyTotal,'0.00')"/></fo:block>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Other Income</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="format-number(pb/otherIncomeTotal,'0.00')"/></fo:block>
											</fo:table-cell>
										</fo:table-row>

										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">Old Prior AR</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="format-number(pb/oldPriorARTotal,'0.00')"/></fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row line-height="24pt">
											<fo:table-cell>
												<fo:block font-weight="bold">CT NSF/System Refund</fo:block>
											</fo:table-cell>
											<fo:table-cell>
												<fo:block>$<xsl:value-of select="format-number(pb/ctNFSSystemRefundTotal,'0.00')"/></fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:block>
						</xsl:otherwise>
					</xsl:choose>
               	</fo:flow>
             </fo:page-sequence>
    </fo:root>
	</xsl:template>
</xsl:stylesheet>
