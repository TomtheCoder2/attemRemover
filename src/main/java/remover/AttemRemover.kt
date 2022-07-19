package remover

import arc.*
import arc.util.*
import mindustry.game.EventType.*
import mindustry.mod.*
import mindustry.world.blocks.logic.LogicBlock.*
import java.util.concurrent.*

class AttemRemover : Plugin() {
    private val pool = Executors.newSingleThreadExecutor {
        Thread(it, "Attem Matcher").apply {
            isDaemon = true
            uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, e -> Log.err("Error in attem matching thread", e) }
        }
    }

    init {
        Log.info("Starting AttemRemover...")
        Events.on(ConfigEvent::class.java) { event: ConfigEvent ->
            val player = event.player ?: return@on
            val proc = event.tile as? LogicBuild ?: return@on

            pool.execute { // The regex can be slow
                val patched = ProcessorPatcher.patch(proc.code)
                if (patched != proc.code) {
                    Core.app.post {
                        proc.configure(compress(patched, proc.relativeConnections()))
                        Log.info("Found attem at (${proc.tile.x}, ${proc.tile.y})!")
                        player.sendMessage("[scarlet]Please do not use this logic, as it is attem83 logic and is bad to use. For more information please read [cyan]www.mindustry.dev/attem")
                    }
                }
            }
        }
    }
}