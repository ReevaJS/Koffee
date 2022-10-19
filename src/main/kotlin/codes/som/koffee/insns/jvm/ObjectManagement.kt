package codes.som.koffee.insns.jvm

import codes.som.koffee.insns.InstructionAssembly
import codes.som.koffee.types.TypeLike
import codes.som.koffee.types.coerceType
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.TypeInsnNode

/**
 * Create a new instance of the given [type].
 *
 * (no stack consumed) -> A
 */
public fun InstructionAssembly.new(type: TypeLike) {
    instructions.add(TypeInsnNode(NEW, coerceType(type).internalName))
}

/**
 * Create a new instance of the given [T].
 *
 * (no stack consumed) -> A
 */
public inline fun <reified T> InstructionAssembly.new() {
    new(T::class)
}

/**
 * Try to cast the top of the stack to the given [type],
 * throwing a [ClassCastException] if it fails.
 *
 * A -> A
 */
public fun InstructionAssembly.checkcast(type: TypeLike) {
    instructions.add(TypeInsnNode(CHECKCAST, coerceType(type).internalName))
}

/**
 * Try to cast the top of the stack to the given [T],
 * throwing a [ClassCastException] if it fails.
 *
 * A -> A
 */
public inline fun <reified T> InstructionAssembly.checkcast() {
    checkcast(T::class)
}

/**
 * Check if the top of the stack is of the given [type].
 *
 * A -> I (booleans are ints internally)
 */
public fun InstructionAssembly.instanceof(type: TypeLike) {
    instructions.add(TypeInsnNode(INSTANCEOF, coerceType(type).internalName))
}

/**
 * Check if the top of the stack is of the given [T].
 *
 * A -> I (booleans are ints internally)
 */
public inline fun <reified T> InstructionAssembly.instanceof() {
    instanceof(T::class)
}
