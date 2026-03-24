package net.myitian.schematicannon_processing_pattern.mixin;

import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern.BOOK_INPUT;
import static net.myitian.schematicannon_processing_pattern.SchematicannonProcessingPattern.BOOK_OUTPUT;

@Mixin(value = SchematicannonBlockEntity.class, remap = false)
abstract class SchematicannonBlockEntityMixin {
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
        return SchematicannonProcessingPattern.checkItem(inventory, value);
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
        ItemStack extractItem = inventory.extractItem(BOOK_INPUT, 1, true);
        if (SchematicannonProcessingPattern.isPatternLike(extractItem.getItem())) {
            inventory.extractItem(BOOK_INPUT, 1, false);
            ItemStack stack = SchematicannonProcessingPattern.getProcessingPattern((SchematicannonBlockEntity) (Object) this);
            stack.setCount(inventory.getStackInSlot(BOOK_OUTPUT).getCount() + 1);
            inventory.setStackInSlot(BOOK_OUTPUT, stack);
            sendUpdate = true;
            ci.cancel();
        }
    }
}