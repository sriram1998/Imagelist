package com.example.sriram.imagelist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 20;
    View v;
    EditText entercap;
    TextView textView;
    private ImageView imgView;
    ArrayList<Bitmap> image = new ArrayList<Bitmap>();

    //String[] caption = new String[500];
    List<String> caption = new ArrayList<String>();
    public int i=0;
    public int currimage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v=this.findViewById(android.R.id.content);
        imgView = (ImageView) findViewById(R.id.imageView);
        entercap   = (EditText)findViewById(R.id.editText2);
        textView = (TextView)findViewById(R.id.textView2);
    }

public void selectImgGal(View v)
{
Intent galleryIntent=new Intent(Intent.ACTION_PICK);
    File pictureDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    String pictureDirectoryPath=pictureDirectory.getPath();
    Uri data=Uri.parse(pictureDirectoryPath);
    galleryIntent.setDataAndType(data,"image/*");

    startActivityForResult(galleryIntent, REQUEST_CODE);
}
public void showImage(View v)
{   currimage=0;
    imgView.setImageBitmap(image.get(0));
    textView.setText ("Image 1 : "+caption.get(0));
}
public void next(View v)
{
    currimage++;
    if(currimage==i)
        currimage=0;
    imgView.setImageBitmap(image.get(currimage));
    textView.setText("Image "+(currimage+1)+" : "+ caption.get(currimage));


}
public void delete(View v)
{
i--;
    caption.remove(currimage);
    image.remove(currimage);
    showImage(v);

}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE){
                Uri data1 = data.getData();
                InputStream inputStream;
                try {
                    inputStream=getContentResolver().openInputStream(data1);
                    image.add(BitmapFactory.decodeStream(inputStream));
                    //caption.set(i, entercap.getText().toString());
                    caption.add(entercap.getText().toString());
                    i++;
                    showImage(v);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
