package com.example.facelogin.Service;

import com.example.facelogin.Face.BaiduAIFace;
import com.example.facelogin.SetingModel.Setingmodel;
import com.example.facelogin.Utils.GetToken;
import com.example.facelogin.Utils.GsonUtils;
import com.example.facelogin.Utils.HttpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    BaiduAIFace faceapi;
    @Autowired
    Setingmodel setingmodel;
    
    private static String token = GetToken.getAuth();
    
    public Map<String,Object> searchface(StringBuffer imagebase64) throws IOException {
        String substring = imagebase64.substring(imagebase64.indexOf(",")+1, imagebase64.length());
        setingmodel.setImgpath(substring);
        setingmodel.setGroupID("Face_login");
        System.out.println(substring);
        Map map = faceapi.FaceSearch(setingmodel);
        return map;
    }
    /**
     * 人脸注册
     * @param imagebase64
     * @return
     * @throws Exception 
     */
    public String registerface(StringBuffer imagebast64) throws Exception {
    	String result = faceapi.registerface(imagebast64);
            
        return result;
       
    }
}
