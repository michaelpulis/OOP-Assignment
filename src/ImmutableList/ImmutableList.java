package ImmutableList;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.List;

public class ImmutableList <T> implements ImmutableListInterface<T>
{
	T[] data;
	@Override
	public ImmutableList add(T element) {
		
		return null ;
	}

	public ImmutableList (T[] data) {
		this.data = data.clone();
	}
	
}
