package vvbj.modding.puffles.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import vvbj.modding.puffles.PufflesMod;
import vvbj.modding.puffles.block.BlockRegistry;
import vvbj.modding.puffles.entity.PuffleVariant;

public class ItemRegistry {

    public static Item RED_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.RED);
    public static Item BLUE_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.BLUE);
    public static Item GREEN_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.GREEN);
    public static Item YELLOW_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.YELLOW);
    public static Item ORANGE_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.ORANGE);
    public static Item PURPLE_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.PURPLE);
    public static Item BROWN_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.BROWN);
    public static Item PINK_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.PINK);
    public static Item BLACK_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.BLACK);
    public static Item WHITE_PUFFLE_SPAWN_EGG = new PuffleSpawnEgg(PuffleVariant.WHITE);

    public static Item O_BERRY = new AliasedBlockItem(BlockRegistry.O_BERRY_BUSH, new Item.Settings());

    public static void register(){
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "red_puffle_spawn_egg"), RED_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "blue_puffle_spawn_egg"), BLUE_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "green_puffle_spawn_egg"), GREEN_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "yellow_puffle_spawn_egg"), YELLOW_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "orange_puffle_spawn_egg"), ORANGE_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "purple_puffle_spawn_egg"), PURPLE_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "brown_puffle_spawn_egg"), BROWN_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "pink_puffle_spawn_egg"), PINK_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "black_puffle_spawn_egg"), BLACK_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "white_puffle_spawn_egg"), WHITE_PUFFLE_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(PufflesMod.MOD_ID, "o_berry"), O_BERRY);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(RED_PUFFLE_SPAWN_EGG);
            entries.add(BLUE_PUFFLE_SPAWN_EGG);
            entries.add(GREEN_PUFFLE_SPAWN_EGG);
            entries.add(YELLOW_PUFFLE_SPAWN_EGG);
            entries.add(ORANGE_PUFFLE_SPAWN_EGG);
            entries.add(PURPLE_PUFFLE_SPAWN_EGG);
            entries.add(BROWN_PUFFLE_SPAWN_EGG);
            entries.add(PINK_PUFFLE_SPAWN_EGG);
            entries.add(BLACK_PUFFLE_SPAWN_EGG);
            entries.add(WHITE_PUFFLE_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(O_BERRY);
        });
    }
}
