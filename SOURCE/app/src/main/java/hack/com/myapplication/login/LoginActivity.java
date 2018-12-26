package hack.com.myapplication.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hack.com.myapplication.APIService.APIService;
import hack.com.myapplication.Products.ProductActivity;
import hack.com.myapplication.R;
import hack.com.myapplication.dangKi.DangKyActivity;
import hack.com.myapplication.login.Obj.LoginUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText edtLoginUserName, edtLoginPass;
    Button btnLogin;
    TextView tvRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }
    void init(){
        edtLoginUserName = findViewById(R.id.edt_login_user_name);
        edtLoginPass = findViewById(R.id.edt_login_pass);
        btnLogin = findViewById(R.id.btn_login);
        tvRegist = findViewById(R.id.tv_regist);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy data
                String user, pass;
                user = edtLoginUserName.getText().toString();
                pass = edtLoginPass.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.12:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofit.create(APIService.class).Login(user,pass).enqueue(new Callback<LoginUser>() {
                    @Override
                    public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                        LoginUser loginUser = response.body();
                        if (loginUser.getStatus() == 1){
                            Intent intent = new Intent(LoginActivity.this, ProductActivity.class);
                            intent.putExtra("loginUser",loginUser);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this, ""+loginUser.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginUser> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });


    }
}
