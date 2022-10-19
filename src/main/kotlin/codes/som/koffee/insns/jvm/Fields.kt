package codes.som.koffee.insns.jvm

import codes.som.koffee.insns.InstructionAssembly
import codes.som.koffee.types.TypeLike
import codes.som.koffee.types.coerceType
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.FieldInsnNode
import org.objectweb.asm.tree.FieldNode

/**
 * Get the static field from [owner] with [name] and [type]
 *
 * (no stack consumed) -> [type]
 */
public fun InstructionAssembly.getstatic(owner: TypeLike, name: String, type: TypeLike) {
    instructions.add(FieldInsnNode(GETSTATIC, coerceType(owner).internalName, name, coerceType(type).descriptor))
}

/**
 * Get the static field from [owner] with [name] and type [T]
 *
 * (no stack consumed) -> [T]
 */
public inline fun <reified T> InstructionAssembly.getstatic(name: String, type: TypeLike) {
    getstatic(T::class, name, type)
}

/**
 * Get the instance field from [owner] with [name] and [type]
 *
 * A -> [type]
 */
public fun InstructionAssembly.getfield(owner: TypeLike, name: String, type: TypeLike) {
    instructions.add(FieldInsnNode(GETFIELD, coerceType(owner).internalName, name, coerceType(type).descriptor))
}

/**
 * Get the instance field from [owner] with [name] and type [T]
 *
 * A -> [T]
 */
public inline fun <reified T> InstructionAssembly.getfield(name: String, type: TypeLike) {
    getfield(T::class, name, type)
}

/**
 * Set the static field from [owner] with [name] and [type]
 *
 * [type] -> (no stack produced)
 */
public fun InstructionAssembly.putstatic(owner: TypeLike, name: String, type: TypeLike) {
    instructions.add(FieldInsnNode(PUTSTATIC, coerceType(owner).internalName, name, coerceType(type).descriptor))
}

/**
 * Set the static field from [owner] with [name] and type [T]
 *
 * [T] -> (no stack produced)
 */
public inline fun <reified T> InstructionAssembly.putstatic(name: String, type: TypeLike) {
    putstatic(T::class, name, type)
}

/**
 * Set the instance field from [owner] with [name] and [type]
 *
 * [type] A -> (no stack produced)
 */
public fun InstructionAssembly.putfield(owner: TypeLike, name: String, type: TypeLike) {
    instructions.add(FieldInsnNode(PUTFIELD, coerceType(owner).internalName, name, coerceType(type).descriptor))
}

/**
 * Set the instance field from [owner] with [name] and type [T]
 *
 * [T] A -> (no stack produced)
 */
public inline fun <reified T> InstructionAssembly.putfield(name: String, type: TypeLike) {
    putfield(T::class, name, type)
}

/**
 * Get the static field from [owner]'s [field]
 *
 * (no stack consumed) -> *Unknown type*
 */
public fun InstructionAssembly.getstatic(owner: TypeLike, field: FieldNode) {
    getstatic(owner, field.name, Type.getType(field.desc))
}

/**
 * Get the instance field from [owner]'s [field]
 *
 * A -> *Unknown type*
 */
public fun InstructionAssembly.getfield(owner: TypeLike, field: FieldNode) {
    getfield(owner, field.name, Type.getType(field.desc))
}

/**
 * Set the [owner]'s static [field]'s value.
 *
 * *Unknown type* -> (no stack produced)
 */
public fun InstructionAssembly.putstatic(owner: TypeLike, field: FieldNode) {
    putstatic(owner, field.name, Type.getType(field.desc))
}

/**
 * Set the [owner]'s instance [field]'s value.
 *
 * *Unknown type* A -> (no stack produced)
 */
public fun InstructionAssembly.putfield(owner: TypeLike, field: FieldNode) {
    putfield(owner, field.name, Type.getType(field.desc))
}
