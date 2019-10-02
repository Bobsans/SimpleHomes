package bobsans.simplehomes.config;

import net.minecraftforge.common.config.Property;

import static net.minecraftforge.common.config.Property.Type.BOOLEAN;
import static net.minecraftforge.common.config.Property.Type.INTEGER;

public enum ConfigItem {
    ALLOW_WARP_POINTS("allowWarpPoints", "Allow players set warp points.", BOOLEAN),
    MAXIMUM_WARP_POINTS("maximumWarpPoints", "Maximum warp points per user.", INTEGER);

    private String key;
    private String langKey;
    private String desc;
    private Property.Type propertyType;
    private String[] validStrings;

    ConfigItem(String key, String desc, Property.Type propertyType, String[] validStrings) {
        this.key = key;
        this.langKey = "bobsans.simplehomes.config." + key;
        this.desc = desc;
        this.propertyType = propertyType;
        this.validStrings = validStrings;
    }

    ConfigItem(String key, String desc, Property.Type propertyType) {
        this(key, desc, propertyType, new String[0]);
    }

    public String key() {
        return key;
    }

    public String languageKey() {
        return langKey;
    }

    public String desc() {
        return desc;
    }

    public Property.Type propertyType() {
        return propertyType;
    }

    public String[] validStrings() {
        return validStrings;
    }
}
