package oauth.fundamental.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Getter @Service
@Builder
public class Attributes {

    private Map<String, Object> mainAttributes;
    private Map<String, Object> subAttributes;
    private Map<String, Object> otherAttributes;
}
