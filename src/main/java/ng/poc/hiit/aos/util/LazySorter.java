package ng.poc.hiit.aos.util;

import java.util.Comparator;

import ng.poc.hiit.aos.entity.Tweet;

import org.primefaces.model.SortOrder;

/**
 * Personalized sorter that implements a comparator
 * 
 * @author aos
 *
 */
public class LazySorter implements Comparator<Tweet> {
	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public int compare(Tweet car1, Tweet car2) {
		try {
			Object value1 = Tweet.class.getField(this.sortField).get(car1);
			Object value2 = Tweet.class.getField(this.sortField).get(car2);

			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
