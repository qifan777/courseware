package com.jarcheng.courseware;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.jarcheng.mbg.common.RedisKey;
import com.jarcheng.mbg.uitls.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

@SpringBootTest
class CoursewareApplicationTests {
    @Autowired
    CommonUtils commonUtils;
    @Value("${wxmini.secret}")
    String secret;
    @Value("${wxmini.appid}")
    String appid;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
        String code = "051pCC000cuucL1HOW300AL9oH3pCC00";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        url = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);

        String s = HttpUtil.get(url);
        System.out.println(s);
    }
    @Test
    void phoneDecode() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        String encryptedData="YHzfXfWs6oPGS2cksH2AAtGAbB06jwbb65Cogyh6Wv9NVOEuY7bhc/0k/wl5C3+u3eWoxVll33ZPyNUQznSmN+39lYccnVJefZbv5IRaM6xQkwDvJDKxZWDnds4u0113YGvSn4Opvk2MxpM28XN8ZEadMzgY88+q49e7mEfFQ6JOvxA6NzYSJSXXYqZjG//8G0pciCFntkW93qBLcdpyNw==";
        String vi="YvZNnNXhKZjD5Tf69rwrMw==";
        String sessionId="1d595c0c-f551-4159-9f8e-a0e3420d8efe";
        // 开始解密
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(RedisKey.WX_SESSION_ID + sessionId);
        String sessionKey = (String) jsonObject.get("session_key");
        byte[] encData = Base64.decode(encryptedData);
        byte[] iv = Base64.decode(vi);
        byte[] key = Base64.decode(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
// 将解密结果 return
        String s = new String(cipher.doFinal(encData), "UTF-8");
        System.out.println(s);

    }

}
