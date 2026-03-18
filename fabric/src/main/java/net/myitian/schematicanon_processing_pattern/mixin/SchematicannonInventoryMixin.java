package net.myitian.schematicanon_processing_pattern.mixin;

import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicannonInventory.class, remap = false)
abstract class SchematicannonInventoryMixin {
    @Inject(
        method = "isItemValid(ILnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;I)Z",
        at = @At("HEAD"),
        require = 0,
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemVariant stack, int count, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2) {
            Item item = stack.getItem();
            if (item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem) {
                cir.setReturnValue(true);
            }
        }
    }
    @Inject(
        method = "isItemValid(ILnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;)Z",
        at = @At("HEAD"),
        require = 0,
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemVariant stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2) {
            Item item = stack.getItem();
            if (item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem) {
                cir.setReturnValue(true);
            }
        }
    }
}