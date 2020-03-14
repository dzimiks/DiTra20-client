package src.models;

import java.util.*;

public class Record implements Comparable<Record> {

	private List<Object> data;
	private Map<Attribute, Object> attributes;
	private Entity entity;

	public Record() {
		this.data = new ArrayList<>();
		this.attributes = new LinkedHashMap<>();
	}

	public Record(Entity entity) {
		this.entity = entity;
		this.data = new ArrayList<>();
		this.attributes = new LinkedHashMap<>();
	}

	public void addObject(Object object) {
		data.add(object);
	}

	public void removeObject(Object object) {
		data.remove(object);
	}

	public void addAttribute(Attribute attribute, Object value) {
		if (!entity.getChildren().contains(attribute)) {
			return;
		}

		if (value != null && attribute.getValueClass() != value.getClass()) {
			return;
		}

		attributes.put(attribute, value);
	}

	public List<Object> getData() {
		return data;
	}

	public Map<Attribute, Object> getAttributes() {
		return attributes;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String result;

		for (Object o : data) {
			sb.append(o).append(" | ");
		}

		result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	@Override
	public int compareTo(Record record) {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Record)) {
			return false;
		}

		Record record = (Record) obj;

		if (record.getData().size() != data.size()) {
			return false;
		}

		if (record.getAttributes().size() != data.size()) {
			return false;
		}

		for (int i = 0; i < entity.getChildCount(); i++) {
			Attribute attr = (Attribute) entity.getChildAt(i);
			Object valMe = attributes.get(attr);
			Object valOther = record.getAttributes().get(attr);

			System.out.println("RECORD COMPARE: " + valMe + " => " + valOther);

			if (!valMe.equals(valOther)) {
				return false;
			}
		}

		return true;
	}
}
