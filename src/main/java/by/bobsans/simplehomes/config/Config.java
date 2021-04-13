package by.bobsans.simplehomes.config;

import by.bobsans.boblib.config.ConfigBase;
import by.bobsans.simplehomes.Reference;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config extends ConfigBase {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class Common {
        public final ForgeConfigSpec.BooleanValue ALLOW_WARP_POINTS;
        public final ForgeConfigSpec.IntValue MAXIMUM_WARP_POINTS;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("common");
            ALLOW_WARP_POINTS = builder
                .comment("Allow warp points")
                .translation(Reference.MODID + ".config.common.allowWarpPoints")
                .define("allowWarpPoints", true);
            MAXIMUM_WARP_POINTS = builder
                .comment("Maximum amount of warp points")
                .translation(Reference.MODID + ".config.common.maximumWarpPoints")
                .defineInRange("maximumWarpPoints", 10, 0, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, spec);
        loadConfig(spec, FMLPaths.CONFIGDIR.get().resolve(Reference.MODID + "-common.toml"));
    }
}