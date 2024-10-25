package hexlet.code.app.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Mapper for handling JsonNullable types.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class JsonNullableMapper {

    /**
     * Wraps an entity in a JsonNullable.
     * <p>
     * This method can be overridden to provide custom wrapping logic.
     *
     * @param entity the entity to wrap
     * @param <T> the type of the entity
     * @return a JsonNullable containing the entity
     */
    public <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    /**
     * Unwraps a JsonNullable to get the contained entity.
     * <p>
     * This method can be overridden to provide custom unwrapping logic.
     *
     * @param jsonNullable the JsonNullable to unwrap
     * @param <T> the type of the entity
     * @return the unwrapped entity, or null if not present
     */
    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    /**
     * Checks if a JsonNullable is present.
     * <p>
     * This method can be overridden to provide custom presence checking logic.
     *
     * @param nullable the JsonNullable to check
     * @param <T> the type of the entity
     * @return true if the JsonNullable is present, false otherwise
     */
    @Condition
    public <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }

}
