package oauth.fundamental.common.converters;

/**
 * T가 입력 타입
 * R이 반환 타입
 */
public interface ProviderUserConverter<T, R> {
    R converter(T t);
}
