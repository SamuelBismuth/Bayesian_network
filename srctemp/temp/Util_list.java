package temp;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sam
 * This class is a library for the lists.
 */
public class Util_list {

	/**
	 * This method concatenate two lists into one.
	 * @param list1
	 * @param list2
	 * @return List<T>.
	 */
	static <T> List<T> concatenate_two_list(List<T> list1, List<T> list2) {
		return Stream.concat(
				list1.stream(), 
				list2.stream()).
				collect(Collectors.toList());
	}

	/**
	 * This method concatenate a lists and one item into a new list.
	 * @param list
	 * @param item
	 * @return List<T>.
	 */
	static <T> List<T> concatenate_item_with_list(List<T> list, T item) {
		if(list == null) 
			return Collections.singletonList(
					item);
		return Stream.concat(
				list.stream(), 
				Collections.singletonList(
						item).stream()).
				collect(Collectors.toList());
	}

	/**
	 * This function gives the intersection between to list.
	 * @param list1
	 * @param list2
	 * @return
	 */
	static <T> List<T> get_match_from_two_list(List<T> list1, List<T> list2) {
		return list1.stream().
				filter(list2::contains).
				collect(Collectors.toList());
	}
}
