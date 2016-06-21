package neu.pecko.bookshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private EditText userEditText, passwordEditText;
    private  String userString, passwordString;
    private static final String urlJson = "http://swiftcodingthai.com/neu/get_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5); // อย่าลืม alt + Enter เพื่อ Cast


    } // Main Method

    // Create Inner Class แสดงข้อมุลออกมาบางส่วน
    private class MySynchronize extends AsyncTask<Void, Void, String> { // alt + Enter

        private Context contex;
        private String urlString;
        private boolean statusBoonlean = true;
        private String truePasswordString;

        public MySynchronize(Context contex, String urlString) {
            this.contex = contex;
            this.urlString = urlString;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlString).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                return null;
            }

        } // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("BookShopV1", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (userString.equals(jsonObject.getString("User"))) {
                        statusBoonlean = false;
                        truePasswordString = jsonObject.getString("Password");
                    } // if

                } // for

                // checkUser
                if (statusBoonlean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(contex, "ไม่มี User นี้นะจ๊ะ",
                            "ไม่มียูสเซอร์ " + userString + " ในฐานข้อมูลเราจ้า");
                } else if (passwordString.equals(truePasswordString)) {
                    // Password True
                    Toast.makeText(contex, "Welcome User: " + userString, Toast.LENGTH_LONG).show();

                } else {
                    // Password False
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(contex, "Password ไม่ถูกจ้า", "ลองใส่พาสเวิร์ดใหม่นะ");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } // onPost
    } // Class


    public void clickSignIn(View view) {

        // Get Value from Edit Text
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("")  || passwordString.equals("")) {
            // Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "เกิดช่องว่าง", "กรุณากรอกข้อมูลให้ครบทุกช่องจ้า");

        } else {
            // No Space

            searchUserAndPassword(); // alt + Enter
            //uploadToServer();

        }


    } // click SignIn ปุ่มจะต้องสร้างเป็น Method แล้วไปที่ปุ่ม เลือก onClick เลือก clickSignIn

    private void searchUserAndPassword() {

        MySynchronize mySynchronize = new MySynchronize(this, urlJson);
        mySynchronize.execute();

    } // SearchUser

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


}  // Main Class
