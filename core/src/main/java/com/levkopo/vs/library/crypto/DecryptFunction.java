package com.levkopo.vs.library.crypto;

import com.github.bloodshura.ignitium.collection.view.XArrayView;
import com.github.bloodshura.ignitium.collection.view.XView;
import com.github.bloodshura.ignitium.cryptography.Decrypter;
import com.github.bloodshura.ignitium.cryptography.exception.CryptoException;
import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.type.PrimitiveType;
import com.levkopo.vs.type.Type;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;
import com.levkopo.vs.value.VariableRefValue;

import javax.annotation.Nonnull;

public class DecryptFunction implements Function {
	private final XView<Type> argumentTypes;
	private final Decrypter decrypter;
	private final String name;

	public DecryptFunction(String name, Decrypter decrypter) {
		this.argumentTypes = new XArrayView<>(PrimitiveType.STRING, PrimitiveType.VARIABLE_REFERENCE);
		this.decrypter = decrypter;
		this.name = name;
	}

	@Override
	public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
		StringValue value = (StringValue) descriptor.get(0);
		VariableRefValue reference = (VariableRefValue) descriptor.get(1);

		try {
			String result = getDecrypter().decryptToStr(value.value());

			context.setVar(reference.value(), new StringValue(result));

			return new BoolValue(true);
		} catch (CryptoException exception) {
			return new BoolValue(false);
		}
	}

	@Override
	public XView<Type> getArgumentTypes() {
		return argumentTypes;
	}

	public Decrypter getDecrypter() {
		return decrypter;
	}

	@Nonnull
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isVarArgs() {
		return false;
	}
}
