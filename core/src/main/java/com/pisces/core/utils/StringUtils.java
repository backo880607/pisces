package com.pisces.core.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

public class StringUtils {
	public static <T> String toString(T obj, Function<T, String> fun) {
		if (obj == null) {
			return "";
		}
		return fun != null ? fun.apply(obj) : obj.toString();
	}
	
	public static <T> String join(Collection<T> collection, String separator) {
		return join(collection.iterator(), separator, null);
	}
	
	public static <T> String join(Collection<T> collection, String separator, Function<T, String> fun) {
		if (collection == null) {
			return "";
		}
		
		return join(collection.iterator(), separator, fun);
	}
	
	public static <T> String join(Iterator<T> iterator, String separator, Function<T, String> fun) {
		if (iterator == null || !(iterator.hasNext())) {
			return "";
		}
		
		T first = iterator.next();
		if (!(iterator.hasNext())) {
			return StringUtils.toString(first, fun);
		}
		
		StringBuffer buf = new StringBuffer(256);
		buf.append(StringUtils.toString(first, fun));
		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			
			buf.append(StringUtils.toString(iterator.next(), fun));
		}
		
		return buf.toString();
	}
	
	public static String getHead(String value, String sep) {
		int index = value.indexOf(sep);
		return index >= 0 ? value.substring(0, index) : "";
	}
	
	public static String getTail(String value, String sep) {
		int index = value.lastIndexOf(sep);
		return index >= 0 ? value.substring(index) : "";
	}
}
