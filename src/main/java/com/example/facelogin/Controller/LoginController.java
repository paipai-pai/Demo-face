package com.example.facelogin.Controller;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import com.example.facelogin.Service.LoginService;
import com.example.facelogin.Utils.FileUtil;
import com.example.facelogin.Utils.GetToken;
import com.example.facelogin.Utils.GsonUtils;
import com.example.facelogin.Utils.HttpUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/login")
@SessionAttributes(value = "useinf")
public class LoginController {
    @Autowired
    LoginService loginService=null;
    //登录跳转
    @RequestMapping("/jumpGetface")
    public String getface(){
        return "getface.html";
    }
    //登录成功跳转
    @RequestMapping("/facesucceed")
    public String facesucceed(){
        return "succeed.html";
    }
    //注册跳转
    @RequestMapping("/jumpregisterface")
    public String registerface() {
		return "registerface.html";	
    }
    //人脸登录
    @RequestMapping("/searchface")
    @ResponseBody
    public String searchface(@RequestBody @RequestParam(name = "imagebast64") StringBuffer imagebast64, Model model,HttpServletRequest request) throws IOException {
        
    	Map<String, Object> searchface = loginService.searchface(imagebast64);
    	System.out.println("imagebast64"+"======================="+imagebast64);
        if(searchface==null||searchface.get("user_id")==null){
            System.out.println("我进来了");
            String flag="fail";
            String s = GsonUtils.toJson(flag);
            return s;
        }
            String user_id = searchface.get("user_id").toString();
            String score=searchface.get("score").toString().substring(0,2);
            int i = Integer.parseInt(score);
            if(i>80){
                model.addAttribute("userinf",user_id);
                HttpSession session = request.getSession();
                session.setAttribute("userinf",user_id);
                System.out.println("存入session");
            }
        System.out.println(searchface);
        String s = GsonUtils.toJson(searchface);
        return s;
    }
    /**
	    * 人脸注册
	     * @throws Exception 
	    */
	@RequestMapping("/Registerface")
	@ResponseBody
	public String add(@RequestBody @RequestParam(name = "imagebast64") StringBuffer imagebast64) throws Exception {

		String registerface = loginService.registerface(imagebast64);
		
		System.out.println("registerface"+"=============="+registerface);
		if(registerface == null) {
			String flag="error";
            String s = GsonUtils.toJson(flag);
            return s;
		}
		String flag="success";
        String s = GsonUtils.toJson(flag);
        return s;
	}

}
