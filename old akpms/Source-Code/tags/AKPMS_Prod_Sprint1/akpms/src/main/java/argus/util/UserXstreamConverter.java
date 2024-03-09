package argus.util;

import argus.domain.User;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UserXstreamConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		User person = (User) value;
		writer.startNode("name");
		writer.setValue(person.getFirstName() + " " + person.getLastName());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		User person = new User();
		reader.moveDown();
		person.setFirstName(reader.getValue());
		reader.moveUp();
		return person;
	}

}
