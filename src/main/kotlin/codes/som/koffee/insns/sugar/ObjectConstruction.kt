package codes.som.koffee.insns.sugar

import codes.som.koffee.insns.InstructionAssembly
import codes.som.koffee.insns.jvm.dup
import codes.som.koffee.insns.jvm.invokespecial
import codes.som.koffee.insns.jvm.new
import codes.som.koffee.types.TypeLike
import codes.som.koffee.types.void

/**
 * Construct an object of the given [type].
 * Pass the return type and parameter types with [parameterTypes].
 * Add elements to the stack as necessary by adding instructions with [initializerBlock].
 */
public fun InstructionAssembly.construct(
    type: TypeLike,
    vararg parameterTypes: TypeLike,
    initializerName: String = "<init>",
    initializerBlock: InstructionAssembly.() -> Unit = {}
) {
    new(type)
    dup
    initializerBlock(this)
    invokespecial(type, initializerName, void, *parameterTypes)
}

public inline fun <reified T> InstructionAssembly.construct(
    vararg parameterTypes: TypeLike,
    initializerName: String = "<init>",
    noinline initializerBlock: InstructionAssembly.() -> Unit = {}
) {
    construct(
        T::class,
        parameterTypes = parameterTypes,
        initializerName = initializerName,
        initializerBlock = initializerBlock,
    )
}
