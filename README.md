# 课程设计-人脸识别和AI换脸项目
## 人脸识别百度云API与springboot
### 人脸识别部分
主要是使用人脸识别作为登录功能
参考：[CSDN](https://blog.csdn.net/D99888/article/details/109220666?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&dist_request_id=&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control)
[Github](https://github.com/dxt1998/Face-recognition-login.git)
[BiliBili](https://search.bilibili.com/all?keyword=%E4%BA%BA%E8%84%B8%E8%AF%86%E5%88%AB)
#### 实现思路

1. 前端调用摄像头获取图片、拍照
2. 通过Ajax将图片数据提交后台处理，后台返回处理后的数据
3. 后台使用Java调用百度云API接口进行图片对比
4. 返回的结果后台解析之后返回给前端，验证成功登录，不成功提示注册

#### 调用百度云api在spring boot的web服务中实现人脸识别登录功能
需要的工具类FileUtil,Base64Util,HttpUtil,GsonUtils等，直接从官方文档下载：
https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
百度云API文档：[点击下载百度云API官方文档](https://githubpicture.oss-cn-beijing.aliyuncs.com/FACE.pdf) 
##### 接口方法
Face包下的的BaiduAiFace类是整个核心功能的接口  
FaceRegistration方法为人脸注册   
FaceUpdate方法为人脸更新  
FindPersonFaceList方法为查询人脸信息
FindGroupList为查询本组的面部信息
DelPersonFace为删除人脸 
FaceSearch为查找人脸
##### 接口参数
每个参数的具体含义与要提供的参数参考百度云官方文档中的详细描述
```java
@Component
public class Setingmodel {
    private String imgpath;
    private String GroupID;
    private String UserID;
    private String Quality_Control;
    private String Image_Type;
    private String Liveness_Control;
    private String Userinf;

    public String getUserinf() {
        return Userinf;
    }

    public void setUserinf(String userinf) {
        Userinf = userinf;
    }

    public Setingmodel() {
        /**
         * 图片类型
         * BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
         * URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
         * FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时
         * 会为每个人脸图片赋予一个唯一的FACE_TOKEN
         * 同一张图片多次检测得到的FACE_TOKEN是同一个。
         */
        this.Image_Type = "BASE64";
        /**
         * 图片质量控制
         * NONE: 不进行控制
         * LOW:较低的质量要求
         * NORMAL: 一般的质量要求
         * HIGH: 较高的质量要求
         * 默认 NONE
         */
        this.Quality_Control = "NONE";
        /**
         * 活体检测控制
         * NONE: 不进行控制
         * LOW:较低的活体要求(高通过率 低攻击拒绝率)
         * NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
         * HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
         * 默认NONE
         */
        this.Liveness_Control = "NONE";
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getQuality_Control() {
        return Quality_Control;
    }

    public void setQuality_Control(String quality_Control) {
        Quality_Control = quality_Control;
    }

    public String getImage_Type() {
        return Image_Type;
    }

    public void setImage_Type(String image_Type) {
        Image_Type = image_Type;
    }

    public String getLiveness_Control() {
        return Liveness_Control;
    }

    public void setLiveness_Control(String liveness_Control) {
        Liveness_Control = liveness_Control;
    }
}

```
#### 