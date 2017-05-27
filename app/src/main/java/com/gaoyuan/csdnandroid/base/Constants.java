package com.gaoyuan.csdnandroid.base;

/**
 * 作者：wgyscsf on 2016/12/6 20:26
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class Constants {
    public static class Http {
        //本地API
        // public static String API_URL = "http://192.168.1.108:8080/CsdnService/";
        //供开发者测试的API
        // public static String API_URL = "http://192.168.1.108:8080/CsdnService/";
        //线上API
        public static String API_URL = "http://***/CsdnService/";

        /**
         * key
         */
        // 数据
        public static final String RESULT_DATA = "data";
        // 表示连接状态，在捕获到异常的时候为连接失败。
        public static final String RESULT_CODE = "code";
        // 返回描述
        public static final String RESULT_MESSAGE = "message";
        // 数据大小
        public static final String RESULT_SIZE = "size";

        /**
         * 返回码
         */

        // 正确
        public static final int SUCCESS = 200;
        // 请求失败。
        public static final int REQUEST_ERROR = 501;
        // 服务器异常
        public static final int SERVICE_ERROR = 500;
        public static String UPDATE_PLAN = API_URL + "updateplan.html";
    }

    public static class SP {
        public static final String SP_ROOT = "CsdnAndroid";
        public static final String KEY_COLL = "COLL_KEY";
        public static final String PUSER = "PUSER";
        public static final String SEARCH_HISTORY = "SEARCH_HISTORY";
        public static String FIRST_SUPPERT = "FIRST_SUPPERT";
        public static String FIRST_ABOUT = "FIRST_ABOUT";
    }

    public static class Keys {
        public static final String INTENT_WEB_KEY = "INTENT_WEB_KEY";
        public static final String INTENT_EXPERTDETAILS_KEY = "INTENT_EXPERTDETAILS_KEY";

        public static final String BLOG = "BLOG";
        public static final String KEYWORDS = "KEYWORDS";
        public static final String SEARCHTYPE = "SEARCHTYPE";
        public static final String INTENT_WEB_TITLE = "INTENT_WEB_TITLE";
    }

    public static class Pager {
        /*
         * 规定全局分页，每页显示最多条数
		 */
        public static final int MAX_PERPAGER_SIZE = 30;
        /*
         * 规定每页默认大小
         */
        public static final int DEF_PERPAGER_SIZE = 10;
        /*
         * 规定默认起始页为0
         */
        public static final int DEF_PAGER_START = 0;
    }

    //去除广告规则
    public static class CsdnAdFilter {
        /**
         * 手机端
         */
        //上一篇、下一篇下面的广告
        public static final String M_BLOWNEXTARTCILE_AD1 = "javascript:function setTop1(){var div=document.getElementById('BAIDU_SSP__wrapper_u2634430_0');if(div!=null) div.parentNode.removeChild(div);} setTop1();";
        //发表评论下面的广告
        public static final String M_WRITECOMMONT_AD2 = "javascript:function setTop2(){var div=document.getElementById('BAIDU_SSP__wrapper_u2901277_0');if(div!=null) div.parentNode.removeChild(div);} setTop2();";
        //热门文章下面的广告
        public static final String M_MYHOTARTICLES_AD3 = "javascript:function setTop3(){var div=document.getElementById('BAIDU_SSP__wrapper_u2901286_0');if(div!=null) div.parentNode.removeChild(div);} setTop3();";
        //导航栏下面的广告
        public static final String M_BLOWBAR_AD4 = "javascript:function setTop4(){var div=document.getElementById('BAIDU_SSP__wrapper_u2901270_0');if(div!=null) div.parentNode.removeChild(div);} setTop4();";
        //csdnAPP下载的小广告
        public static final String M_CSDNAPP_AD5 = "javascript:function setTop5(){var div=document.getElementsByClassName('ad_box')[0];if(div!=null) div.parentNode.removeChild(div);} setTop5();";
        //导航栏小广告
        public static final String M_BAR_AD6 = "javascript:function setTop6(){var div=document.getElementsByClassName('blog_top_wrap')[0];if(div!=null) div.parentNode.removeChild(div);} setTop6();";
        //快速回到顶部
        public static final String M_QUICKUP_AD7 = "javascript:function setTop7(){var div=document.getElementsByClassName('backToTop')[0];if(div!=null) div.parentNode.removeChild(div);} setTop7();";
        //手机端去除广告集合
        public static final String MOBLE_CLEAR_AD = M_BLOWNEXTARTCILE_AD1 + M_WRITECOMMONT_AD2 + M_MYHOTARTICLES_AD3 + M_BLOWBAR_AD4 + M_CSDNAPP_AD5 + M_BAR_AD6 + M_QUICKUP_AD7;
    }

    public static class MyEmails {
        public static final String MEAIL_HOST = "smtp.qq.com";
        public static final String FROM = "414850132@qq.com";
        public static final String FROMPSW = "wpgzveshqwhebihf";
        public static final String TO = "15649872559@sina.cn";
    }

    //广告
    public static class MyAd {
        public static final String APPID = "1106179878";
        public static final String SplashPosID = "4000627247675402";
    }
}
