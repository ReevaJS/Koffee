package codes.som.koffee.insns.sugar

import codes.som.koffee.BlockAssembly
import codes.som.koffee.insns.InstructionAssembly
import codes.som.koffee.insns.jvm.goto
import codes.som.koffee.labels.LabelLike
import codes.som.koffee.labels.LabelScope
import codes.som.koffee.labels.coerceLabel
import codes.som.koffee.types.TypeLike
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodNode

public fun InstructionAssembly.makeLabel(): LabelNode = LabelNode()

public fun InstructionAssembly.placeLabel(label: LabelLike) {
    instructions.add(coerceLabel(label))
}

public fun InstructionAssembly.jump(condition: JumpCondition, label: LabelLike, interpretLikeBytecode: Boolean = false) {
    instructions.add(JumpInsnNode(if (interpretLikeBytecode) condition.opcode else condition.opposite, coerceLabel(label)))
}

/**
 * If-statement helper.
 *
 * Note that this helper evaluates it's conditions similar to Java, not
 * like JVM bytecode. In Java, if-statements run their code if the condition
 * is true, whereas in bytecode if-statements _skip_ their code if the
 * condition is true. As the latter is quite unintuitive, that is not the
 * default behavior here. However, it can be enabled by setting the
 * interpretLikeBytecode argument to true
 */
public fun InstructionAssembly.ifStatement(condition: JumpCondition, interpretLikeBytecode: Boolean = false, block: InstructionAssembly.() -> Unit) {
    val label = makeLabel()
    jump(condition, label, interpretLikeBytecode)
    this.block()
    placeLabel(label)
}

/**
 * Like [ifStatement] with an else block
 */
public fun InstructionAssembly.ifElseStatement(condition: JumpCondition, interpretLikeBytecode: Boolean = false, block: IfElseBuilder.() -> Unit) {
    val ifElse = IfElseBuilder()
    ifElse.block()
    require(ifElse.ifBlock == null || ifElse.elseBlock == null) {
        "ifElseStatement requires an if block and and else block"
    }

    val ifLabel = makeLabel()
    val endLabel = makeLabel()

    jump(condition, ifLabel, interpretLikeBytecode)

    this.apply(ifElse.ifBlock!!)
    goto(endLabel)
    placeLabel(ifLabel)
    this.apply(ifElse.elseBlock!!)
    placeLabel(endLabel)
}

public class IfElseBuilder {
    public var ifBlock: (InstructionAssembly.() -> Unit)? = null
    public var elseBlock: (InstructionAssembly.() -> Unit)? = null

    public fun ifBlock(block: InstructionAssembly.() -> Unit) {
        ifBlock = block
    }

    public fun elseBlock(block: InstructionAssembly.() -> Unit) {
        elseBlock = block
    }
}

public enum class JumpCondition(internal val opcode: Int, internal val opposite: Int) {
    True(IFNE, IFEQ),
    False(IFEQ, IFNE),
    Equal(IFEQ, IFNE),
    NotEqual(IFNE, IFEQ),
    LessThan(IFLT, IFGE),
    GreaterThan(IFGT, IFLE),
    LessThanOrEqual(IFLE, IFGT),
    GreaterThanOrEqual(IFGE, IFLT),
    Null(IFNULL, IFNONNULL),
    NonNull(IFNONNULL, IFNULL),
    RefEqual(IF_ACMPEQ, IF_ACMPNE),
    RefNotEqual(IF_ACMPNE, IF_ACMPEQ),
}
