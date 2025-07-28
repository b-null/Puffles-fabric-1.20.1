package vvbj.modding.puffles.item;

import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vvbj.modding.puffles.entity.PuffleEntity;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static vvbj.modding.puffles.Utils.getOrCreateNbt;

public class PuffleBoxItem extends Item {

    public PuffleBoxItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        // Desc
        tooltip.add(Text.translatable("tooltip.puffles.puffle_box.desc").formatted(Formatting.GRAY));
        // Count
        tooltip.add(Text.translatable("tooltip.puffles.puffle_box.count", getPuffleCount(stack)));
        // Each puffle data
        NbtCompound nbt = getOrCreateNbt(stack);
        NbtList data = nbt.getList("puffles", NbtElement.COMPOUND_TYPE);
        if(data != null){
            for(int i = 0; i < data.size(); i++){
                NbtCompound puffle = data.getCompound(i);
                int variant = puffle.getInt("variant");
                String name = "Puffle";
                if(puffle.contains("CustomName")){
                    name = puffle.getString("CustomName");
                    name = name.substring(1, name.length() - 1).replaceAll("(\\\\(?=[^\\\\]))", "");
                }

                tooltip.add(getTextFromPuffle(name, variant));
            }
        }
        // Write
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }


    private Text getTextFromPuffle(String name, int variant){
        MutableText output = Text.literal(name);
        switch (variant){
            case 0:
                output.append(" [").append("Red").formatted(Formatting.RED).append("]");
                break;
            case 1:
                output.append(" [").append("Blue").formatted(Formatting.BLUE).append("]");
                break;
            case 2:
                output.append(" [").append("Green").formatted(Formatting.GREEN).append("]");
                break;
            case 3:
                output.append(" [").append("Yellow").formatted(Formatting.YELLOW).append("]");
                break;
            case 4:
                output.append(" [").append("Orange").formatted(Formatting.GOLD).append("]");
                break;
            case 5:
                output.append(" [").append("Purple").formatted(Formatting.DARK_PURPLE).append("]");
                break;
            case 6:
                output.append(" [").append("Brown").setStyle(Style.EMPTY.withColor(0x784102)).append("]");
                break;
            case 7:
                output.append(" [").append("Pink").setStyle(Style.EMPTY.withColor(0xF79494)).append("]");
                break;
            case 8:
                output.append(" [").append("Black").formatted(Formatting.DARK_GRAY).append("]");
                break;
            case 9:
                output.append(" [White]");
                break;
        }
        return output;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(user.getWorld().isClient) return ActionResult.CONSUME;

        if(getPuffleCount(stack) >= 4) return ActionResult.PASS;

        if(entity instanceof PuffleEntity puffle){
            if(puffle.isTamed() && puffle.getOwnerUuid().equals(user.getUuid())) {
                NbtCompound puffleData = new NbtCompound();
                if (puffle.saveSelfNbt(puffleData)) {
                    addPuffle(puffleData, stack);

                    puffle.discard();

                    user.getWorld().playSound(null, user.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.PLAYERS, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().isClient) return ActionResult.CONSUME;

        BlockPos pos = context.getBlockPos().offset(context.getSide());
        NbtCompound lastPuffle = getLastPuffle(context.getStack());
        if(lastPuffle != null){
            Entity puffleEntity = EntityType.loadEntityWithPassengers(lastPuffle, context.getWorld(), Function.identity());
            if(puffleEntity != null){
                puffleEntity.refreshPositionAndAngles(pos, 0, 0);
                context.getWorld().spawnEntity(puffleEntity);

                return ActionResult.SUCCESS;
            }else{
                Objects.requireNonNull(context.getPlayer()).sendMessage(Text.literal("An error has occurred. Couldn't spawn puffle! Please, report this").formatted(Formatting.RED));
                return ActionResult.FAIL;
            }
        }
        return super.useOnBlock(context);
    }

    private void addPuffle(NbtCompound data, ItemStack stack){
        NbtCompound nbt = getOrCreateNbt(stack);
        NbtList list = nbt.getList("puffles", NbtElement.COMPOUND_TYPE);
        if(list != null) {
            list.add(data);
            nbt.put("puffles", list);
        }else{
            list = new NbtList();
            list.add(data);
            nbt.put("puffles", list);
        }
        // Write
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    private NbtCompound getLastPuffle(ItemStack stack){
        NbtCompound nbt = getOrCreateNbt(stack);
        NbtList list = nbt.getList("puffles", NbtElement.COMPOUND_TYPE);
        if(list != null && !list.isEmpty()){
            NbtCompound puffle = list.getCompound(list.size() - 1);
            // now remove it
            list.remove(list.size() - 1);
            nbt.put("puffles", list);

            // Write
            stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
            return puffle;
        }
        return null;
    }

    private int getPuffleCount(ItemStack stack){
        NbtCompound nbt = getOrCreateNbt(stack);
        NbtList list = nbt.getList("puffles", NbtElement.COMPOUND_TYPE);
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public static boolean hasPuffles(ItemStack stack){
        NbtCompound nbt = getOrCreateNbt(stack);
        return nbt != null && nbt.contains("puffles", NbtElement.LIST_TYPE) &&
                !nbt.getList("puffles", NbtElement.COMPOUND_TYPE).isEmpty();
    }
}
