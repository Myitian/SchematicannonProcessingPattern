package net.myitian.schematicanon_processing_pattern.mixin;

import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SchematicannonBlockEntity.class, remap = false)
abstract class SchematicannonBlockEntityMixin {
    @Unique
    private final int _$BookInput = 2;
    @Unique
    private final int _$BookOutput = 3;
    @Shadow
    public SchematicannonInventory inventory;
    @Shadow
    public boolean sendUpdate;

    @ModifyVariable(
        method = "tickPaperPrinter",
        at = @At(
            value = "STORE",
            ordinal = 0,
            remap = false),
        name = "outputFull",
        remap = false)
    private boolean tickPaperPrinter_customCheck(boolean value) {
        Item itemIn = inventory.getStackInSlot(_$BookInput).getItem();
        boolean isPatternIn = itemIn == AEItems.BLANK_PATTERN.asItem() || itemIn instanceof EncodedPatternItem;
        ItemStack itemOut = inventory.getStackInSlot(_$BookOutput);
        boolean notPatternOut = !itemOut.isEmpty() && itemOut.getItem() != AEItems.PROCESSING_PATTERN.asItem();
        if (isPatternIn && notPatternOut) {
            return true;
        }
        return value;
    }

    @Inject(
        method = "tickPaperPrinter",
        at = @At(
            value = "INVOKE",
            target = "Lcom/simibubi/create/content/schematics/cannon/SchematicannonInventory;extractItem(IIZ)Lnet/minecraft/world/item/ItemStack;",
            ordinal = 1,
            remap = false),
        cancellable = true,
        remap = false)
    private void tickPaperPrinter_customItem(CallbackInfo ci) {
        ItemStack extractItem = inventory.extractItem(_$BookInput, 1, true);
        Item item = extractItem.getItem();
        if (item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem) {
            inventory.extractItem(_$BookInput, 1, false);
            ItemStack stack = SchematicanonProcessingPattern.getProcessingPattern((SchematicannonBlockEntity) (Object) this);
            stack.setCount(inventory.getStackInSlot(_$BookOutput).getCount() + 1);
            inventory.setStackInSlot(_$BookOutput, stack);
            sendUpdate = true;
            ci.cancel();
        }
    }
}