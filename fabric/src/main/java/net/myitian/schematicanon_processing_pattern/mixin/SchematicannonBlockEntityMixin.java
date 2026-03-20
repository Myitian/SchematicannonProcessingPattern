package net.myitian.schematicanon_processing_pattern.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;
import net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.myitian.schematicanon_processing_pattern.SchematicanonProcessingPattern.BOOK_OUTPUT;

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
        return SchematicanonProcessingPattern.checkItem(inventory, value);
    }

    @Inject(
        method = "tickPaperPrinter",
        at = @At(
            value = "INVOKE",
            target = "Lio/github/fabricators_of_create/porting_lib/transfer/TransferUtil;extract(Lnet/fabricmc/fabric/api/transfer/v1/storage/Storage;Ljava/lang/Object;J)J",
            shift = At.Shift.AFTER,
            remap = false),
        cancellable = true,
        remap = false)
    private void tickPaperPrinter_customItem(CallbackInfo ci, @Local(name = "extractItem") ItemStack extractItem) {
        if (SchematicanonProcessingPattern.isPatternLike(extractItem.getItem())) {
            ItemStack stack = SchematicanonProcessingPattern.getProcessingPattern((SchematicannonBlockEntity) (Object) this);
            stack.setCount(inventory.getStackInSlot(BOOK_OUTPUT).getCount() + 1);
            inventory.setStackInSlot(BOOK_OUTPUT, stack);
            sendUpdate = true;
            ci.cancel();
        }
    }
}