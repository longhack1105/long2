package hack.com.myapplication.dangKi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hack.com.myapplication.APIService.APIService;
import hack.com.myapplication.R;
import hack.com.myapplication.dangKi.Obj.PhanHoiDangKy;
import hack.com.myapplication.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKyActivity extends AppCompatActivity {

    EditText edtDangKySdt, edtDangKyMatKhau, edtDangKyMatKhauNhapLai;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        init();
    }
    void init(){
        edtDangKySdt = findViewById(R.id.edt_dang_ky_sdt);
        edtDangKyMatKhau = findViewById(R.id.edt_dang_ky_mat_khau);
        edtDangKyMatKhauNhapLai = findViewById(R.id.edt_dang_ky_mat_khau_nhap_lai);

        btnDangKy = findViewById(R.id.btn_dang_ky);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, pass, repass;

                userName = edtDangKySdt.getText().toString();
                pass = edtDangKyMatKhau.getText().toString();
                repass = edtDangKyMatKhauNhapLai.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.12:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofit.create(APIService.class).Regist(userName,pass).enqueue(new Callback<PhanHoiDangKy>() {
                    @Override
                    public void onResponse(Call<PhanHoiDangKy> call, Response<PhanHoiDangKy> response) {
                        PhanHoiDangKy phanHoiDangKy = response.body();
                        if (phanHoiDangKy.status == 1){
                            Toast.makeText(DangKyActivity.this, ""+phanHoiDangKy.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangKyActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(DangKyActivity.this, ""+phanHoiDangKy.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PhanHoiDangKy> call, Throwable t) {
                        Toast.makeText(DangKyActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
