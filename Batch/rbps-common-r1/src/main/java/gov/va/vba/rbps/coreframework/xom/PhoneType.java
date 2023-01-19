package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum PhoneType {
	DAY("Daytime"), 
	NIGHT("Nighttime");

	private final List<String> values;

	private PhoneType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static PhoneType find(String name) {
		for (PhoneType phone : PhoneType.values()) {
			if (phone.values.contains(name))
				return phone;
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}
	
}
