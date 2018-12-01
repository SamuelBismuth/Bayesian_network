import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilList {

	/**
	 * This method concatenate a lists and one item into a new list.
	 * @param list
	 * @param item
	 * @return List<T>.
	 */
	static <T> List<T> concatenateItemWithlist(List<T> list, T item) {
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
	 * This method concatenate a lists and one item into a new list.
	 * @param list
	 * @param item
	 * @return List<T>.
	 */
	static <T> Set<T> concatenateItemWithSet(Set<T> list, T item) {
		if(list == null) 
			return (Set<T>) Collections.singleton(item);
		return Stream.concat(
				list.stream(), 
				Collections.singletonList(
						item).stream()).
				collect(Collectors.toSet());
	}
}
