package c6h2cl2.asm

import net.minecraft.launchwrapper.IClassTransformer

class TransformerSolidXp : IClassTransformer {
    override fun transform(name: String, transformedName: String, basicClass: ByteArray): ByteArray {
        if (transformedName != TARGET_CLASSES_NAMES) {
            return basicClass
        }
        return basicClass
    }

    companion object {
        //改変対象の完全修飾名
        private val TARGET_CLASSES_NAMES = "net.minecraft.entity.player.EntityPlayer"
    }
}
