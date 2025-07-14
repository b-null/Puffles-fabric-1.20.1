package vvbj.modding.puffles.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import vvbj.modding.puffles.PufflesMod;

public class BlockRegistry {

    public static Block PUFFLE_O_BUSH = new PuffleOBush(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY));

    public static void register(){
        Registry.register(Registries.BLOCK, Identifier.of(PufflesMod.MOD_ID, "puffle_o_bush"), PUFFLE_O_BUSH);
    }
}
