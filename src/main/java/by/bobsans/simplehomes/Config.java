package by.bobsans.simplehomes;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    public static class Common {
        protected static ForgeConfigSpec SPEC;

        public static ForgeConfigSpec.BooleanValue ALLOW_WARP_POINTS;
        public static ForgeConfigSpec.IntValue MAXIMUM_WARP_POINTS;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

            builder.push("general");

            ALLOW_WARP_POINTS = builder
                .comment("Allow warp points")
                .translation(Reference.MOD_ID + ".config.common.allowWarpPoints")
                .define("allowWarpPoints", true);
            MAXIMUM_WARP_POINTS = builder
                .comment("Maximum amount of warp points")
                .translation(Reference.MOD_ID + ".config.common.maximumWarpPoints")
                .defineInRange("maximumWarpPoints", 10, 0, Integer.MAX_VALUE);

            builder.pop();

            SPEC = builder.build();
        }
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, Common.SPEC);
    }
}