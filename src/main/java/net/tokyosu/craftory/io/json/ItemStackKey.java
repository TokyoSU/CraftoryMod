package net.tokyosu.craftory.io.json;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ItemStackKey(@NotNull ItemStack stack) {
    public ItemStackKey(@NotNull ItemStack stack) {
        this.stack = stack.copy();
    }

    @Override
    public boolean equals(@NotNull Object o) {
        if (!(o instanceof ItemStackKey other)) return false;
        return ItemStack.isSameItemSameTags(this.stack, other.stack);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public int hashCode() {
        int result = this.stack.getItem().hashCode();
        if (this.stack.hasTag()) {
            result = 31 * result + this.stack.getTag().hashCode();
        }
        return result;
    }
}
