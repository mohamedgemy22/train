package com.enggemy22.mytrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private  static final int PickINT=1;
   private EditText editTitle;
    private EditText editcontent;
    private StorageTask task;
    private Button button1;
    private Button button2;
    private  Button button3;
    ProgressBar mprogressBar;
    private ImageView imageView;
    private Uri uri;

    StorageReference mStorageReference;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle=findViewById(R.id.title);
        editcontent=findViewById(R.id.content);
        button1= findViewById(R.id.button);
        button2= findViewById(R.id.button2);
        button3 = findViewById(R.id.showUpload);
        imageView= findViewById(R.id.image);

     mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
     myRef= FirebaseDatabase.getInstance().getReference("uploads");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFile();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFile();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImage();

            }
        });
    }
    // choose image
    private void OpenFile() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,PickINT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
     //   super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode==PickINT&&requestCode==RESULT_OK&&data!=null && data.getData() !=null){
        if (requestCode==PickINT){

            uri=data.getData();
            Log.e("uri",uri+"");
            Picasso.with(MainActivity.this).load(uri).into(imageView);
        }
    }

    // upload;
    private String getfileExtention(Uri uri) {
    ContentResolver resolver= getContentResolver();
    MimeTypeMap map=MimeTypeMap.getSingleton();
    return map.getExtensionFromMimeType(resolver.getType(uri));
}
    private void uploadFile() {
        if (uri!=null)
        {
            StorageReference filereference= mStorageReference.child(System.currentTimeMillis()
            + "." + getfileExtention(uri));
           task = filereference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Handler handler =new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },5000);

                    Toast.makeText(MainActivity.this,"done",Toast.LENGTH_LONG).show();
                    upload up =new upload(editTitle.getText().toString().trim(),editcontent.getText().toString().trim(),
                            taskSnapshot.getUploadSessionUri().toString());
                    String uploaded= myRef.push().getKey();
                    myRef.child(uploaded).setValue(up);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
            ).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                }
            });
        }
        else
        {
            Toast.makeText(MainActivity.this,"no file", Toast.LENGTH_SHORT).show();
        }
    }



   //
    private void openImage() {
        Intent intent=new Intent(MainActivity.this,ImagesShos.class);
        startActivity(intent);
    }

}
