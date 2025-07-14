package vvbj.modding.puffles;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static vvbj.modding.puffles.PufflesMod.MOD_ID;

public class Sounds {
    public static final Identifier PUFFLE_DEATH_SOUND_ID = new Identifier(MOD_ID, "puffle_death");
    public static final SoundEvent PUFFLE_DEATH_SOUND = SoundEvent.of(
            PUFFLE_DEATH_SOUND_ID
    );

    public static void register(){
        Registry.register(Registries.SOUND_EVENT, PUFFLE_DEATH_SOUND_ID, PUFFLE_DEATH_SOUND);

    }
}
