package com.example.demo.common.kakao;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoLocalService {

    @Value("${kakao.kakao-rest-key}")
    String kakaoRestKey;

    public HttpHeaders getHeaders() {
        String auth = "KakaoAK " + kakaoRestKey;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    // 주소로 위도, 경도 찾기
    public double[] getCoord(String address)
            throws URISyntaxException, UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String apiURL =
                "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&page=1&size=10&query="
                        + URLEncoder.encode(address, "UTF-8");
        URI url = new URI(apiURL);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity,
                String.class);
        double[] latlng = new double[2];
        try{
            String jsonStr = res.getBody().toString();
            Object obj = JSONValue.parse(jsonStr);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jArr = (JSONArray) jsonObject.get("documents");
            JSONObject subJobj = (JSONObject) jArr.get(0);
            latlng[0] = Double.parseDouble((String) subJobj.get("x"));
            latlng[1] = Double.parseDouble((String) subJobj.get("y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlng;
    }

    // 위도, 경도로 주소 찾기
    public String getAddress(Double lat, Double lon) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHeaders();

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String apiURL =
                "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+lat+"&y="+lon;
        URI url = new URI(apiURL);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity,
                String.class);
        String address = "";
        try {
            String jsonStr = res.getBody().toString();
            Object obj = JSONValue.parse(jsonStr);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jArr = (JSONArray) jsonObject.get("documents");
            JSONObject subJobj = (JSONObject) jArr.get(0);
            JSONObject subJobj2 = (JSONObject) subJobj.get("road_address");
            address = (String) subJobj2.get("address_name");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
