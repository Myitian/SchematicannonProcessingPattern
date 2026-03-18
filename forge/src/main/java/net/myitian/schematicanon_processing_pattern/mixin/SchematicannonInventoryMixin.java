package net.myitian.schematicanon_processing_pattern.mixin;

import appeng.core.definitions.AEItems;
import appeng.crafting.pattern.EncodedPatternItem;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicannonInventory.class, remap = false)
abstract class SchematicannonInventoryMixin {
    @Inject(
        method = "isItemValid",
        at = @At("HEAD"),
        cancellable = true,
        remap = false)
    private void isItemValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 2) {
            Item item = stack.getItem();
            if (item == AEItems.BLANK_PATTERN.asItem() || item instanceof EncodedPatternItem) {
                cir.setReturnValue(true);
            }
        }
    }
}