package net.myitian.schematicanon_processing_pattern.mixin;

import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicannonInventory.class, remap = false)
abstract class SchematicannonInventoryMixin {
    @Inject( // 1.20.1 new version
        method = "isItemValid(ILnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;I)Z",
        at = @At("HEAD"),
        require = 0,
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemVariant stack, int count, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2 && SchematicanonProcessingPattern.isPatternLike(stack.getItem())) {
            cir.setReturnValue(true);
        }
    }

    @Inject( // 1.19.2 new version
        method = "isItemValid(ILnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;J)Z",
        at = @At("HEAD"),
        require = 0,
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemVariant stack, long amount, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2 && SchematicanonProcessingPattern.isPatternLike(stack.getItem())) {
            cir.setReturnValue(true);
        }
    }

    @Inject( // legacy version
        method = "isItemValid(ILnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;)Z",
        at = @At("HEAD"),
        require = 0,
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemVariant stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2 && SchematicanonProcessingPattern.isPatternLike(stack.getItem())) {
            cir.setReturnValue(true);
        }
    }
}