/* This file is a part of Unikko Utility Mod: https://github.com/jnkyto/Unikko which is
distributed under CC0-1.0: https://creativecommons.org/publicdomain/zero/1.0/legalcode
*/

package com.ahenkeshi.unikko.cmd;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeCommandSource extends ServerCommandSource {
    public FakeCommandSource(ClientPlayerEntity player) {
        super(player, player.getPos(), player.getRotationClient(), null, 0, player.getEntityName(),
                player.getName(), null, player);
    }

    @Override
    public Collection<String> getPlayerNames() {
        return Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).getPlayerList()
                .stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
    }
}
