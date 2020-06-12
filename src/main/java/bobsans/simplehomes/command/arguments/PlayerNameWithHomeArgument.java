package bobsans.simplehomes.command.arguments;

import bobsans.simplehomes.core.PlayerData;
import bobsans.simplehomes.core.PlayerDataManager;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PlayerNameWithHomeArgument implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("notch", "Bobsans");

    private Collection<String> existingWithoutCurrent(PlayerEntity player) {
        if (player != null) {
            Stream<PlayerData> stream = PlayerDataManager.load().getPlayerDataList().stream().filter((data) -> !data.name.equals(player.getName().toString()));
            return stream.map((warp) -> warp.name).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.readString();
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        if (context.getSource() instanceof CommandSource) {
            try {
                return ISuggestionProvider.suggest(existingWithoutCurrent(((CommandSource) context.getSource()).asPlayer()), builder);
            } catch (CommandSyntaxException e) {
                return Suggestions.empty();
            }
        } else if (context.getSource() instanceof ISuggestionProvider) {
            ISuggestionProvider isuggestionprovider = (ISuggestionProvider) context.getSource();
            return isuggestionprovider.getSuggestionsFromServer((CommandContext<ISuggestionProvider>) context, builder);
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

    public static String getUserName(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, String.class);
    }
}
