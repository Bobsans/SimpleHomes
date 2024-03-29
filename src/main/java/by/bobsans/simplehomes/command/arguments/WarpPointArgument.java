package by.bobsans.simplehomes.command.arguments;

import by.bobsans.simplehomes.core.PlayerDataManager;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class WarpPointArgument implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("carry", "village");

    private Collection<String> userWarpPoints(Player player) {
        return PlayerDataManager.instance().getOrCreate(player).warps.values().stream().map((warp) -> warp.name).collect(Collectors.toList());
    }

    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            try {
                return SharedSuggestionProvider.suggest(userWarpPoints(((CommandSourceStack) context.getSource()).getPlayerOrException()), builder);
            } catch (CommandSyntaxException e) {
                return Suggestions.empty();
            }
        } else if (context.getSource() instanceof SharedSuggestionProvider) {
            return ((SharedSuggestionProvider) context.getSource()).customSuggestion(context);
        } else {
            return Suggestions.empty();
        }
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static WarpPointArgument warpPoint() {
        return new WarpPointArgument();
    }

    public static String getName(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, String.class);
    }
}
