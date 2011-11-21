package com.gramercysoftware.utils.helpers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class EmptyObjectChecker<T> {
	private Set<String> fieldsToCheck;
	private List<String> fieldsChecked;
	private List<String> nonEmptyFields;
	private Set<?> primitives;

	public EmptyObjectChecker() {
		reset();
	}

	/**
	 * Add a field name to the list of items this object should check
	 * 
	 * @param string
	 * @return
	 */
	public EmptyObjectChecker<T> append(String string) {
		this.fieldsToCheck.add(string);
		return this;
	}

	/**
	 * Check the previously appended fields in the object
	 * 
	 * @param objectToCheck
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public boolean isEmpty(T objectToCheck) {
		if (objectToCheck != null) {
			this.nonEmptyFields = new ArrayList<String>();
			if(this.fieldsToCheck.isEmpty()) {
				return checkAllFields(objectToCheck);
			} else {
				return checkNamedFields(objectToCheck);
			}
		} else {
			return true;
		}
	}

	private boolean checkNamedFields(T objectToCheck) {
		boolean isEmpty = true;
		for (String fieldName : this.fieldsToCheck) {
			
			Field field;
			try {
				field = objectToCheck.getClass().getDeclaredField(fieldName);
				checkAccessibility(field);
				
				if (!isPrimitive(field)) {
					isEmpty &= isFieldEmpty(objectToCheck, field);
				}
			} catch (Exception e) {
			}
		}
		
		return isEmpty;
	}

	private boolean checkAllFields(T objectToCheck) {
		boolean isEmpty = true;
		for (Field field : objectToCheck.getClass().getDeclaredFields()) {
			checkAccessibility(field);

			if (!isPrimitive(field)) {
				isEmpty &= isFieldEmpty(objectToCheck, field);
			}
		}
		
		return isEmpty;
	}

	private void checkAccessibility(Field field) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isFieldEmpty(T objectToCheck, Field field) {
		this.getFieldsChecked().add(field.getName());
		
		Object value = null;
		try {
			value = field.get(objectToCheck);
		} catch (Exception e) {
			this.nonEmptyFields.add(field.getName());
			return false;
		}

		if (isNull(value)) {
			return true;
		} else {
			if (field.getType().isAssignableFrom(String.class)) {
				if (StringUtils.isEmpty((String) value)) {
					return true;
				}
			} else if(field.getType().isAssignableFrom(Collection.class)) {
				if(CollectionUtils.isEmpty((Collection)value)) {
					return true;
				}
			} else if(field.getType().isAssignableFrom(Map.class)) {
				if(MapUtils.isEmpty((Map)value)) {
					return true;
				}
			} else if(field.getType().isAssignableFrom(List.class)) {
				if(((List)value).isEmpty()) {
					return true;
				}
			}

			this.nonEmptyFields.add(field.getName());
			return false;
		}
	}

	private boolean isPrimitive(Field declaredField) {
		return primitives.contains(declaredField.getType());
	}

	private boolean isNull(Object object) {
		return null == object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set createPrimitivesList() {
		Set list = Collections.synchronizedSet(new HashSet<Class>());
		list.add(boolean.class);
		list.add(byte.class);
		list.add(char.class);
		list.add(short.class);
		list.add(int.class);
		list.add(long.class);
		list.add(float.class);
		list.add(double.class);
		return list;
	}

	/**
	 * Resets the class, removing the list of fields to be checked.
	 */
	public void reset() {
		this.fieldsToCheck = new HashSet<String>();
		this.fieldsChecked = new ArrayList<String>();
		this.primitives = createPrimitivesList();
	}

	/**
	 * Show text representation of list of non-empty fields or friendly message if all fields are
	 * empty
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		StringBuilder builder = new StringBuilder();
		if (CollectionUtils.isEmpty(this.nonEmptyFields)) {
			builder.append("All checked fields are empty.");
		} else {
			builder.append("Non-empty fields: ");
			builder.append(StringUtils.join(this.nonEmptyFields.toArray(), ", "));
		}
		return builder.toString();
	}

	Set<String> getFieldsToCheck() {
		return this.fieldsToCheck;
	}

	/**
	 * Gets the list of field names that were not empty.
	 * 
	 * @return
	 */
	public List<String> getNonEmptyFields() {
		return this.nonEmptyFields;
	}

	/**
	 * Gets the list of fields checked
	 * 
	 * @return
	 */
	public List<String> getFieldsChecked() {
		return fieldsChecked;
	}
}
