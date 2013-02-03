package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.Type;

public class Annotation implements Comparable<String> {

	private String name;
	private Type type;
	private Object value;
	private Object defaultValue;

	public Annotation(String name, Type type, Object value) {
		this.name = name;
		this.type = type;
		this.value = value;

		if (!value.getClass().equals(type.getType())) {
			throw new IllegalArgumentException("Value type "
					+ value.getClass().getSimpleName()
					+ " does not match to Column Type "
					+ type.getType().getSimpleName());
		}

	}

	public Annotation(String name, Type type, Object value, Object defaultValue) {
		this.name = name;
		this.type = type;
		this.value = value;

		if (!value.getClass().equals(type.getType())) {
			throw new IllegalArgumentException("Value type "
					+ value.getClass().getSimpleName()
					+ " does not match to Column Type "
					+ type.getType().getSimpleName());
		}
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Annotation other = (Annotation) obj;
		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public int compareTo(String t) {
		return this.name.compareTo(t);
	}

	@Override
	public String toString() {
		return this.name;
	}

}
