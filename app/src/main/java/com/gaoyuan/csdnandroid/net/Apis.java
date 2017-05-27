package com.gaoyuan.csdnandroid.net;

import com.gaoyuan.csdnandroid.bean.Blog;
import com.gaoyuan.csdnandroid.bean.Expert;
import com.gaoyuan.csdnandroid.bean.PStar;
import com.gaoyuan.csdnandroid.bean.PUser;
import com.gaoyuan.csdnandroid.bean.Preference;
import com.gaoyuan.csdnandroid.net.req.ExpertBolgsReqBean;
import com.gaoyuan.csdnandroid.net.req.ExpertsReqBean;
import com.gaoyuan.csdnandroid.net.req.LoginReqBean;
import com.gaoyuan.csdnandroid.net.req.PStarReqBean;
import com.gaoyuan.csdnandroid.net.req.RecommendReqBean;
import com.gaoyuan.csdnandroid.net.req.SearchReqBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface Apis {
    @POST("recommendAction_getRecommendBlogsByTypeIdByLogined")
    Observable<BaseResp<List<Blog>>> recommendAction_getRecommendBlogsByTypeIdByLogined(@Body RecommendReqBean recommendReqBean);

    @POST("recommendAction_getSearchByKeyWords")
    Observable<BaseResp<List<Blog>>> recommendAction_getSearchByKeyWords(@Body SearchReqBean searchReqBean);

    @POST("coreAction_getAuthorBlogsByAuthorId")
    Observable<BaseResp<List<Blog>>> coreAction_getAuthorBlogsByAuthorId(@Body ExpertBolgsReqBean expertBolgsReqBean);

    @POST("coreAction_getExpertsByTypeId")
    Observable<BaseResp<List<Expert>>> coreAction_getExpertsByTypeId(@Body ExpertsReqBean expertsReqBean);

    @POST("userAction_login")
    Observable<BaseResp<PUser>> userAction_login(@Body LoginReqBean loginReqBean);

    @POST("userAction_getPStarByCsdnId")
    Observable<BaseResp<List<Blog>>> userAction_getPStarByCsdnId(@Body ExpertBolgsReqBean expertBolgsReqBean);

    @POST("userAction_addStar")
    Observable<BaseResp<Boolean>> userAction_addStar(@Body PStarReqBean pStarReqBean);

    @POST("userAction_delStar")
    Observable<BaseResp<Boolean>> userAction_delStar(@Body PStar pStar);

    @POST("userAction_addPreference")
    Observable<BaseResp<Boolean>> userAction_addPreference(@Body Preference preference);

    @POST("userAction_updatePUser")
    Observable<BaseResp<Boolean>> userAction_updatePUser(@Body PUser pUser);

}
