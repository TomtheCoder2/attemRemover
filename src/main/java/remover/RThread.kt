package remover

import mindustry.gen.Building
import mindustry.gen.Player
import mindustry.world.blocks.logic.LogicBlock
import mindustry.world.blocks.logic.LogicBlock.LogicBuild

class RThread(var code: String, var block: Building, var player: Player) : Thread() {
    override fun run() {
        val p = block.tile.build as LogicBuild
        val patched = ProcessorPatcher.patch(code)
        if (patched != code) {
            block.configure(LogicBlock.compress(patched, p.relativeConnections()))
            println("Found attem at x:" + block.tile.x + " y:" + block.tile.y + "!")
            player.sendMessage("[scarlet]Please do not use this logic, as it is attem83 logic and is bad to use. For more information please read [cyan]www.mindustry.dev/attem")
        }
    }
}