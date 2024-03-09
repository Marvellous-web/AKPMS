package argus.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;


import org.apache.log4j.Logger;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import argus.domain.User;

/**
 *
 * @author jasdeep.s
 *
 */

public class FopXmlToPdfGenrator {

	private static final Logger LOGGER = Logger
			.getLogger(FopXmlToPdfGenrator.class);

	/**
	 *
	 * @param response
	 * @param session
	 * @param xslFo
	 * @param xml
	 */
	public static synchronized void generatePDFReport(HttpServletResponse response,
			HttpSession session, String xslFo, String xml, String fileName) {

		try {
			User user = AkpmsUtil.getLoggedInUser();
			LOGGER.info(user.getFirstName() +"IN generatePDFReport");
			ServletContext context = session.getServletContext();
			String realPath = context.getRealPath("/resources/xsl");
			LOGGER.info("XSL real path = " + realPath);
			File systemFile = new File(realPath, xslFo);
			InputStream is = new FileInputStream(systemFile);
			StreamSource transformSource = new StreamSource(is);
			// create an instance of fop factory
			FopFactory fopFactory = FopFactory.newInstance();
			// a user agent is needed for transformation
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			// to store output
			ByteArrayOutputStream pdfoutStream = new ByteArrayOutputStream();
			StreamSource source = new StreamSource(new ByteArrayInputStream(
					xml.getBytes()));
			Transformer xslfoTransformer;
			try {
				TransformerFactory transfact = TransformerFactory.newInstance();

				xslfoTransformer = transfact.newTransformer(transformSource);
				// Construct fop with desired output format
				Fop fop;
				try {
					fop = fopFactory.newFop(MimeConstants.MIME_PDF,
							foUserAgent, pdfoutStream);
					// Resulting SAX events (the generated FO)
					// must be piped through to FOP
					Result res = new SAXResult(fop.getDefaultHandler());

					// Start XSLT transformation and FOP processing
					try {
						// everything will happen here..
						xslfoTransformer.transform(source, res);

						response.setHeader("Content-Disposition",
								"attachment; filename="+fileName+".pdf");

						response.getOutputStream().write(
								pdfoutStream.toByteArray());

					} catch (TransformerException e) {
						e.printStackTrace();
					}
				} catch (FOPException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
			}
			LOGGER.info(user.getFirstName() +"OUT generatePDFReport");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
