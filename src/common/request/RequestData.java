package common.request;

import java.io.Serializable;

public record RequestData(RequestType header, Object data) implements Serializable {
}
