package leui.woojoo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Getter @Setter
@NoArgsConstructor
public class HttpUtils {
    private HttpHeaders headers;
    private MultiValueMap<String, Object> body;
    private RestTemplate rt;
    private String url;
    HttpEntity<MultiValueMap<String, String>> entity;// = new HttpEntity<>(params, headers);

    public HttpUtils(String url, HttpHeaders headers, MultiValueMap<String, Object> body) {
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.rt = new RestTemplate();
    }

    public static HttpUtils of(String url, MultiValueMap<String, Object> body) {

        return new HttpUtils();
    }


}
