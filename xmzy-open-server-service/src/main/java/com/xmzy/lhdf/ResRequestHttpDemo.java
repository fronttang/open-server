package com.xmzy.lhdf;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author ljh
 * @version 1.0
 * @date 2019/7/17 21:41
 */
public class ResRequestHttpDemo {
    private final static Logger logger = LoggerFactory.getLogger(ResRequestHttpDemo.class);
    private final static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
        .setConnectionRequestTimeout(5000).setSocketTimeout(20000).build();

    // 测试参数
    String appkey = "2312345U11";
    String publicKey = "";
    String reform = "1";
    String bind_tel = "18569505892";
    String sign = "";

    // 公钥 加密方式 以及http请求
    @Test
    public void encryptAndDecrypt() throws Exception {

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appkey", appkey);
        paramsMap.put("publicKey", publicKey);
        paramsMap.put("bind_tel", bind_tel);

        StringBuilder publicKeyBur = new StringBuilder();
        publicKeyBur.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJoPARiRqtFeIlxXVAl4wbNWytjP3dduCfK3VPSVOzi8");
        publicKeyBur.append("m27OR5nW0D280i8k36rkl5Y7kMPJEKmZ/zi4gqX0hv0UxNtT2C9Pd+c84jQmYDFV2Vah/lvOd1W772Ck2G5gSG4L");
        publicKeyBur.append("rEP8Vq1YbedDxcbcP7UDl+Qos5HSi3jWU9skqryQIDAQAB");

        // 字符串格式的公钥
        publicKey = publicKeyBur.toString();

        StringBuilder privateKey = new StringBuilder();
        privateKey.append("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAImg8BGJGq0V4iXFdUCXjBs1bK2M/d124");
        privateKey.append("J8rdU9JU7OLybbs5HmdbQPbzSLyTfquSXljuQw8kQqZn/OLiCpfSG/RTE21PYL0935zziNCZgMVXZVqH+W853VbvvY");
        privateKey
            .append("KTYbmBIbgusQ/xWrVht50PFxtw/tQOX5CizkdKLeNZT2ySqvJAgMBAAECgYB5WFuyqlbJ2yyMrv+FltjiOIV8s341oJXF4");
        privateKey
            .append("TRKVtWkzZBpZ46+p42xxD+nG0qjxr1jeQh0AIbFeNPtkM3axi3/kEOX8SFgMTELZND/0YV08tpbcrtqCNGa/L53ItIo2B");
        privateKey
            .append("g+qvgeV7eBq/zF4pRzcy4boPaK1+AijYuDlxWO0U3FHQJBAOGmDOyywRHT8jLRD/kINF2iKlJ/dv2B4HpNET6Zb49r7");
        privateKey.append("brTUIM3jS91duCA1yXPA/8dVsPk5dpqO1bt3NjxBssCQQCcJAV+MvNRnQ11Ex436x3Py+gl3dNEanFKOb1FaKlDvpX");
        privateKey.append("UKi0vA27LVHDqLAQ0NC8RevKClX8uLZJZQeHN6/E7AkEA3Y/MFQYX6UdxA1CgHGhf8qhuYf7ieVzza2MM0aibD/pr");
        privateKey.append("vQwMfCspqDXwedRIbMluJStLrKGux92TUnHQbfuMLQJAZe4CA/uA6jNrmJUNchWz1XZYq3efNpeCM6Fz1L7ZWNc");
        privateKey
            .append("u+l06N+m2x1ftioGHkQjL/U5UyDiN2Ph5BHnEzegMBwJAX/por9SndEufE1c6nf6HP70ZC/v9TjS+QcfazES31fhv9uAs");
        privateKey.append("J2AjtX9c7u78+vrKAZFs0anVTCsdoOodftArWg==");

        PrivateKey prKey = RSACryptography.getPrivateKey(privateKey.toString());
        PublicKey puKey = RSACryptography.getPublicKey(publicKey);

        // 组合需要加密的参数,将接口文档所有参数JSON序列后传入加密方法
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", appkey);
        jsonObject.put("reform", reform);
        jsonObject.put("bind_tel", bind_tel);

        // 将JSON字符串转为16进制 解决中文字符占位问题
        String paramhex = RSACryptography.str2HexStr(jsonObject.toJSONString());
        // 加密字符串
        String sign = RSACryptography.encryptByPublicKey(paramhex, (RSAPublicKey)puKey);
        // byte[] encryptedBytes = RSACryptography.encrypt(jsonObject.toJSONString().getBytes(), puKey);
        // sign = new String(encryptedBytes);
        logger.info("加密后：" + sign);
        // 组合最终请求参数体
        paramsMap.put("sign", sign);
        paramsMap.put("publicKey", publicKey);
        // http请求,以贵司方法为准,demo中方法仅供参考; 注 : content-type 必须为 x-www-form-urlencoded
        String result = this.postJson("", paramsMap);
        logger.info("请求返回:{}", result);

        // 解密
        String s2 = RSACryptography.decryptByPrivateKey(sign, (RSAPrivateKey)prKey);
        String paramJson = RSACryptography.hexStr2Str(s2);
        logger.info("解密后参数:{}", s2);
        logger.info("解密后参数:{}", paramJson);
    }

    // HTTP请求
    private String postJson(String url, Map<String, String> params) {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String s = "";
        try {
            ArrayList<BasicNameValuePair> list = new ArrayList<>();
            params.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                s = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static void close(CloseableHttpClient httpclient, CloseableHttpResponse httpResponse) {
        // 关闭连接,释放资源
        try {
            if (httpclient != null) {
                httpclient.close();
            }
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (IOException e) {
            logger.error("关闭流异常", e);
        }
    }

}
