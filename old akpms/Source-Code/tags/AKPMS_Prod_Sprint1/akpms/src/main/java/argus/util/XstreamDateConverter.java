package argus.util;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XstreamDateConverter implements Converter {

	private static final Log LOGGER = LogFactory
			.getLog(XstreamDateConverter.class);

	public boolean canConvert(Class clazz) {
		LOGGER.info("IN DATE Converter");
		return Timestamp.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		LOGGER.info("In Date Marshal");
		try {
			Timestamp date = (Timestamp) value;
			writer.setValue(AkpmsUtil.akpmsDateFormat(new Date(date.getTime()),
					Constants.DATE_FORMAT));
		} catch (Exception e) {
			LOGGER.error(Constants.EXCEPTION, e);
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return "";
	}

}
