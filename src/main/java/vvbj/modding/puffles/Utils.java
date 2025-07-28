package vvbj.modding.puffles;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class Utils {
    public static NbtCompound getOrCreateNbt(ItemStack stack){
        var data = stack.get(DataComponentTypes.CUSTOM_DATA);
        if(data != null){
            return data.copyNbt();
        }
        return new NbtCompound();
    }
}
