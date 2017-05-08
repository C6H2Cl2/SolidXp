package c6h2cl2.solidxp.Proxy

import net.minecraft.client.Minecraft
import java.io.File

/**
 * @author C6H2Cl2
 */
class ClientProxy : CommonProxy() {
    override fun getDir(): File = Minecraft.getMinecraft().mcDataDir
}