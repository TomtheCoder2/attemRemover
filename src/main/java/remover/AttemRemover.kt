package remover;

import arc.Events;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.mod.Plugin;
import mindustry.world.blocks.logic.LogicBlock;

public class AttemRemover extends Plugin {
    public AttemRemover() {
        System.out.println("Starting AttemRemover...");
        Events.on(EventType.ConfigEvent.class, event -> {
            if (event.tile == null || event.player == null) return;
            if (event.tile.tile.build.block == Blocks.microProcessor ||
                    event.tile.tile.build.block == Blocks.logicProcessor ||
                    event.tile.tile.build.block == Blocks.hyperProcessor) {
                LogicBlock.LogicBuild p = (LogicBlock.LogicBuild) event.tile.tile.build;
                Thread t = new RThread(p.code, event.tile.tile.build, event.player);
                t.start();
            }
        });
        Events.on(EventType.BlockBuildEndEvent.class, event -> {
            if (event.tile == null || event.unit.getPlayer() == null || event.tile.build == null) return;
            if (event.tile.build.block == Blocks.microProcessor ||
                    event.tile.build.block == Blocks.logicProcessor ||
                    event.tile.build.block == Blocks.hyperProcessor) {
                LogicBlock.LogicBuild p = (LogicBlock.LogicBuild) event.tile.build;
                Thread t = new RThread(p.code, event.tile.build, event.unit.getPlayer());
                t.start();
            }
        });
    }
}
