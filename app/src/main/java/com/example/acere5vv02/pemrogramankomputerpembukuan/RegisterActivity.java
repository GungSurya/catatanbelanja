package com.example.acere5vv02.pemrogramankomputerpembukuan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Acer E5 Vv02 on 9/26/2018.
 */

public class RegisterActivity extends AppCompatActivity {
    public static int USERID = 0;
    final  int REQUEST_CODE_GALLERY = 999;
    Toolbar toolbarRegistration;
    CircleImageView circleImageView;
    Button btnSave;
    byte[] NewEntryImg;
    EditText firstName, lastName, email, phone;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //intent initial intent & get intent userid
        Intent intent = getIntent();
        USERID = intent.getIntExtra(MainActivity.EXTRA_USERID, MainActivity.USERID);

        //toolbar initial & call toolbar
        toolbarRegistration = (Toolbar) findViewById(R.id.toolbarRegistration);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)

        //initial validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //initialUI();
    }

    private void initialUI() {
        //initial UI
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        btnSave = (Button) findViewById(R.id.btnSave);

        //call validation
        awesomeValidation.addValidation(RegisterActivity.this, R.id.firstName, "[a-zA-Z\\s]+", R.string.firstNameErr);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.lastName, "[a-zA-Z\\s]+", R.string.lastNameErr);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.emailErr);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.phone, RegexTemplate.TELEPHONE, R.string.phoneErr);

        //call image path
        circleImageView.setOnClickListener(circleImageViewClick);
        //call execute save data
        btnSave.setOnClickListener(btnSaveClick);

    }


    private View.OnClickListener circleImageViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            // TODO Auto-generated method stub
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
        }
    };

    private  View.OnClickListener btnSaveClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            // TODO Auto-generated method stub
            if(awesomeValidation.validate()){
                NewEntryImg = profileImageToByte(circleImageView);

            }
        }

        private byte[] profileImageToByte(CircleImageView image) {
            Bitmap bitmapp = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        }
    };

    public void OnRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentImage = new Intent(Intent.ACTION_PICK);
                intentImage.setType("image/*");
                startActivityForResult(intentImage, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "You don't have the persmission to access file.", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri uri = data.getData();
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            circleImageView.setImageBitmap(bitmap);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
