package codes.som.koffee.insns.sugar

import codes.som.koffee.MethodAssembly
import codes.som.koffee.insns.jvm.*

public fun MethodAssembly.aload(local: Local) {
    aload(local.index)
}

public fun MethodAssembly.astore(): Local {
    astore(currentLocalIndex)
    return Local(currentLocalIndex++, LocalType.Object)
}

public fun MethodAssembly.astore(local: Local) {
    astore(local.index)
}

public fun MethodAssembly.dload(local: Local) {
    dload(local.index)
}

public fun MethodAssembly.dstore(): Local {
    dstore(currentLocalIndex)
    return Local(currentLocalIndex++, LocalType.Double)
}

public fun MethodAssembly.dstore(local: Local) {
    dstore(local.index)
}

public fun MethodAssembly.fload(local: Local) {
    fload(local.index)
}

public fun MethodAssembly.fstore(): Local {
    fstore(currentLocalIndex)
    return Local(currentLocalIndex++, LocalType.Float)
}

public fun MethodAssembly.fstore(local: Local) {
    fstore(local.index)
}

public fun MethodAssembly.iload(local: Local) {
    iload(local.index)
}

public fun MethodAssembly.istore(): Local {
    istore(currentLocalIndex)
    return Local(currentLocalIndex++, LocalType.Int)
}

public fun MethodAssembly.istore(local: Local) {
    istore(local.index)
}

public fun MethodAssembly.lload(local: Local) {
    lload(local.index)
}

public fun MethodAssembly.lstore(): Local {
    lstore(currentLocalIndex)
    return Local(currentLocalIndex++, LocalType.Long)
}

public fun MethodAssembly.lstore(local: Local) {
    lstore(local.index)
}

public fun MethodAssembly.load(local: Local) {
    when (local.type) {
        LocalType.Object -> aload(local)
        LocalType.Float -> fload(local)
        LocalType.Int -> iload(local)
        LocalType.Double -> dload(local)
        LocalType.Long -> lload(local)
    }
}

public fun MethodAssembly.store(local: Local) {
    when (local.type) {
        LocalType.Object -> astore(local)
        LocalType.Float -> fstore(local)
        LocalType.Int -> istore(local)
        LocalType.Double -> dstore(local)
        LocalType.Long -> lstore(local)
    }
}

public data class Local(val index: Int, val type: LocalType)

public enum class LocalType {
    Object,
    Float,
    Int,
    Double,
    Long
}
