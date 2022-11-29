package by.bobsans.simplehomes.command;

import by.bobsans.simplehomes.Reference;
import by.bobsans.simplehomes.command.arguments.PlayerWithHomeArgument;
import by.bobsans.simplehomes.command.arguments.WarpPointArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArgumentTypesRegistry {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> REGISTRY = DeferredRegister.create(Registry.COMMAND_ARGUMENT_TYPE_REGISTRY, Reference.MOD_ID);

    public static final RegistryObject<SingletonArgumentInfo<PlayerWithHomeArgument>> PLAYER_WITH_HOME = REGISTRY
        .register(Reference.MOD_ID + "_player_data", () -> ArgumentTypeInfos.registerByClass(
            PlayerWithHomeArgument.class,
            SingletonArgumentInfo.contextFree(PlayerWithHomeArgument::userName)
        ));

    public static final RegistryObject<SingletonArgumentInfo<WarpPointArgument>> WARP_POINT = REGISTRY
        .register(Reference.MOD_ID + "_warp_point", () -> ArgumentTypeInfos.registerByClass(
            WarpPointArgument.class,
            SingletonArgumentInfo.contextFree(WarpPointArgument::warpPoint)
        ));

}
