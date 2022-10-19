package codes.som.koffee.util

import codes.som.koffee.types.TypeLike
import codes.som.koffee.types.coerceType
import org.objectweb.asm.Type

public fun constructMethodDescriptor(returnType: TypeLike, vararg parameterTypes: TypeLike): String {
    return Type.getMethodDescriptor(coerceType(returnType),
            *parameterTypes.map(::coerceType).toTypedArray())
}
