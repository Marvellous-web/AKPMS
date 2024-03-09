package argus.mvc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import argus.domain.Files;
import argus.repo.file.FileDao;
import argus.util.Constants;

@Controller
@RequestMapping(value = "/file")
public class FileController {

	@Autowired
	private FileDao fileDao;

	private static final Logger LOGGER = Logger
			.getLogger(FileController.class);

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
	public void downloadAttachment(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("id") Long id,
			HttpSession session) {
		LOGGER.info("In download method. Id = " + id);
		try {
			if (id != null) {
				Files file = fileDao.getAttachedFile(id);

				String realPath = messageSource.getMessage(
						"attachments.storage.space.arProductivity", null,
						Locale.ENGLISH).trim();

				File systemFile = new File(realPath, file.getSystemName());
				InputStream is = new FileInputStream(systemFile);

				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + file.getName() + "\"");
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteAttachment(@RequestParam("id") Long id) {
		LOGGER.info("IN Delete Attachment menthod");
		boolean succeed = false;
		try {
			if (id != null) {
				int updatecount = fileDao.deleteAttachedFile(id);
				if (updatecount > Constants.ZERO) {
					LOGGER.info("Attachment has deleted successfully");
					succeed = true;
				}
				return succeed;
			} else {
				LOGGER.info("File id is coming null");
			}
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
		return succeed;
	}

}
