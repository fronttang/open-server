package com.xmzy.bank.ghb;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fronttang
 * @date 2021/09/15
 */
@Slf4j
public class FileDownloadTester {

    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        HttpClient client = httpClientBuilder.build();

        String url =
            "https://xmzytest01.oss-cn-shenzhen.aliyuncs.com/batchTest/GHB_518510003_INVOICE_20211025.txt?Expires=1635155887&OSSAccessKeyId=LTAI4G7SGJ716ePb7uS9EXJM&Signature=7nsNSulqyCcCts40%2F5UzDVifkKA%3D";
        //
        HttpGet get = new HttpGet(url);
        //
        HttpResponse httpResponse = client.execute(get, HttpClientContext.create());
        HttpEntity entity = httpResponse.getEntity();
        //
        InputStream is = entity.getContent();
        //
        // // File file = new File("D:/fileDownload.txt");
        // //
        // // FileOutputStream fileOutputStream = new FileOutputStream(file);
        // // IoUtil.copy(is, fileOutputStream);
        // // is.close();
        // // fileOutputStream.close();
        //
        String content = IoUtil.readUtf8(is);
        log.info(content);

        // RestTemplate restTemplate = new RestTemplate();
        // RestTemplateUtils.init(restTemplate);
        //
        // String content = RestTemplateUtils.exchange(url, HttpMethod.GET, null, null, String.class);
        // log.info(content);

        // String content = HttpUtil.get(
        // "https://xmzytest01.oss-cn-shenzhen.aliyuncs.com/batchTest/GHB_518510003_INVOICE_20211025.txt?Expires=1635154348&OSSAccessKeyId=LTAI4G7SGJ716ePb7uS9EXJM&Signature=%2Fdd5vzdsXIdJqGx6qTWzaZ%2F4YdE%3D");
        // log.info(content);

        // FileInputStream fileInputStream =
        // new FileInputStream(new File("C:/Users/tangf/Desktop/GHB_10207023_COLLECTION_20210917145859517.zip"));
        //
        // FileOutputStream fileOutputStream =
        // new FileOutputStream(new File("C:/Users/tangf/Desktop/GHB_10207023_COLLECTION_DecryptFile.zip"));
        //
        // byte[] secretKeyHex = HexUtil.decodeHex("AFE4A821D7701F500F867778347B48BA");
        // SymmetricCrypto sm4 = new SM4(Mode.CBC, Padding.PKCS5Padding, secretKeyHex, secretKeyHex);
        // sm4.decrypt(fileInputStream, fileOutputStream, true);

    }
}
