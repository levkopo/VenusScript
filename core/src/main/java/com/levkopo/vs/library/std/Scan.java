package com.levkopo.vs.library.std;

import com.github.bloodshura.ignitium.activity.logging.Logger;
import com.github.bloodshura.ignitium.activity.scanning.XScanner;
import com.levkopo.vs.exception.runtime.InvalidValueTypeException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.DecimalValue;
import com.levkopo.vs.value.IntegerValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.TypeValue;
import com.levkopo.vs.value.Value;
import com.github.bloodshura.ignitium.worker.ParseWorker;

@MethodArgs(TypeValue.class)
@MethodName("scan")
public class Scan extends Method {
	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		Logger logger = context.getApplicationContext().getUserData("in", Logger.class);

		if (logger != null) {
			TypeValue value = (TypeValue) descriptor.get(0);
			Type type = value.value();

			while (true) {
				try {
					String line = XScanner.scan();

					if (type == PrimitiveType.BOOLEAN) {
						return new BoolValue(ParseWorker.toBoolean(line));
					}

					if (type == PrimitiveType.DECIMAL) {
						return new DecimalValue(ParseWorker.toDouble(line));
					}

					if (type == PrimitiveType.INTEGER) {
						return new IntegerValue(ParseWorker.toLong(line));
					}

					if (type == PrimitiveType.STRING) {
						return new StringValue(line);
					}

					if (type == PrimitiveType.TYPE) {
						Type lookup = PrimitiveType.forIdentifier(line);

						if (lookup != null) {
							return new TypeValue(lookup);
						}

						continue;
					}

					throw new InvalidValueTypeException(context, "Cannot scan for an input of type " + type);
				} catch (NumberFormatException ignored) {
				}
			}
		}

		throw new ScriptRuntimeException(context, "No input resource defined");
	}
}
