package hack.com.myapplication.APIService;

import hack.com.myapplication.dangKi.Obj.PhanHoiDangKy;
import hack.com.myapplication.login.Obj.LoginUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @POST("users/register")
    Call<PhanHoiDangKy> Regist(@Query("user_name")String userName, @Query("password")String pass);

    @GET("users/login")
    Call<LoginUser> Login(@Query("user_name")String userName, @Query("password")String pass);
}
