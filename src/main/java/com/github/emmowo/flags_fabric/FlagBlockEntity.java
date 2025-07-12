package com.github.emmowo.flags_fabric;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity {
    public FlagBlockEntity( BlockPos pos, BlockState state) {
        super(Flags_fabric.FLAG_BLOCK_ENT, pos, state);
    }

    public String flagtype = "clear";

    @Override
    protected void writeData(WriteView view) {
        // Save the current value of the number to the nbt

        view.putString("flagtype",flagtype);
    }

    @Override
    /**
     * Reads data from {@code nbt}. Subclasses should override this if they
     * store a persistent data.
     *
     * <p>NBT is a storage format; therefore, a data from NBT is loaded to a
     * block entity instance's fields, which are used for other operations instead
     * of the NBT. The data is written back to NBT when saving the block entity.
     *
     * <p>{@code nbt} might not have all expected keys, or might have a key whose
     * value does not meet the requirement (such as the type or the range). This
     * method should fall back to a reasonable default value instead of throwing an
     * exception.
     *
     * @see #writeNbt
     */
    protected void readData(ReadView view) {

        flagtype = view.getString("flagtype","clear");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

}
