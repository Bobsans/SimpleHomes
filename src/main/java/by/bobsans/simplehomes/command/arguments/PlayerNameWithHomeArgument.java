package by.bobsans.simplehomes.command.arguments;

import by.bobsans.simplehomes.core.PlayerData;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlayerNameWithHomeArgument implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("notch", "Bobsans");

    private Collection<String> existingWithoutCurrent(Player player) {
        if (player != null) {
            Stream<PlayerData> stream = PlayerDataManager.instance().getPlayerDataList().stream().filter((data) -> !data.name.equals(player.getName().toString()));
            return stream.map((warp) -> warp.name).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSourceStack) {
            try {
                return SharedSuggestionProvider.suggest(existingWithoutCurrent(((CommandSourceStack) context.getSource()).getPlayerOrException()), builder);
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

    public static PlayerNameWithHomeArgument userName() {
        return new PlayerNameWithHomeArgument();
    }

    public static String getUserName(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, String.class);
    }
}
