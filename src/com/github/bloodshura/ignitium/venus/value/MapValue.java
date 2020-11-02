package com.github.bloodshura.ignitium.venus.value;

import com.github.bloodshura.ignitium.venus.exception.runtime.InvalidMapAccessException;
import com.github.bloodshura.ignitium.venus.exception.runtime.ScriptRuntimeException;
import com.github.bloodshura.ignitium.venus.executor.Context;
import com.github.bloodshura.ignitium.venus.type.PrimitiveType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class MapValue extends IterableValue{

	private final Map<Object, Value> map;

	public MapValue(Map<Object, Value> map) {
		super(PrimitiveType.MAP);
		this.map = map;
	}

	@Override
	public MapValue clone() {
		return new MapValue(new HashMap<>(map));
	}

	@Override
	public Iterator<Value> iterator() {
		return map.values().iterator();
	}

	@Override
	public void forEach(Consumer<? super Value> action) {
		map.values().forEach(action);
	}

	@Override
	public Spliterator<Value> spliterator() {
		return map.values().spliterator();
	}

	public Value get(Context context, Value index) throws ScriptRuntimeException {
		if (!map.containsKey(index.value())) {
			throw new InvalidMapAccessException(context, "Oops.. Map not contains this value");
		}

		return map.get(index.value());
	}

	public Map<Object, Value> getMap() {
		return map;
	}

	@Override
	public Map<Object, Value> value() {
		return map;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
