package jakubfilipiak.ForbiddenZonesGeneratorWeb.commons;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
public interface Mapper<F, T> {

    T map(F dao);

    F reverseMap(T dto);
}
