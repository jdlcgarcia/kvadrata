package com.bobotosoft.kvadrata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.encoder.QRCode;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QRActivity extends Activity {

	private static final String TAG = "QRActivity";
	String character;
	ImageView QRimage;
	Character c;
	Bitmap mBitmap; 
	SecretKeySpec sks;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    character = extras.getString("CHARACTER");
		}
		sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }
        
        c= new Character(character);
		// ImageView to display the QR code in.  This should be defined in 
		// your Activity's XML layout file
		QRimage = (ImageView) findViewById(R.id.ivQR);
		String[] arraychar = character.split(";");
		
		int qrCodeDimention = 500;
		String charcoded;
		try{
			charcoded = encrypt(character);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.d(TAG,e.toString());
			return;
		}
		
		QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(charcoded, null,
		        Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

		try {
		    mBitmap = qrCodeEncoder.encodeAsBitmap();
		    QRimage.setImageBitmap(mBitmap);
		} catch (WriterException e) {
		    e.printStackTrace();
		}
	}


	public void SaveQR(View v)
	{
		if (mBitmap == null) {
			return;
		}
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path.mkdirs();
		
		String filename = c.getName()+ ".png";
		File file = new File(path, filename);
		FileOutputStream stream;
		try {
			// This can fail if the external storage is mounted via USB
			stream = new FileOutputStream(file);
			mBitmap.compress(CompressFormat.PNG, 100, stream);
			stream.close();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return; // Do not continue
		}
		
		Uri uri = Uri.fromFile(file);
		
		// Tell Android that a new public picture exists
		
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(uri);
		sendBroadcast(intent);
	}
	
	public void ExportQR(View v)
	{
		if (mBitmap == null) {
			return;
		}
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path.mkdirs();
		
		String filename = c.getName()+ ".png";
		File file = new File(path, filename);
		FileOutputStream stream;
		try {
			// This can fail if the external storage is mounted via USB
			stream = new FileOutputStream(file);
			mBitmap.compress(CompressFormat.PNG, 100, stream);
			stream.close();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return; // Do not continue
		}
		
		Uri uri = Uri.fromFile(file);
		
		// Tell Android that a new public picture exists
		
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(uri);
		sendBroadcast(intent);
		
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/png");
		share.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(share, "Share using..."));
	}
	
	public  String encrypt(String message) throws Exception
	{
	    byte[] message1=Base64.encode(message.getBytes(),Base64.DEFAULT);
	    
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.ENCRYPT_MODE, sks);
	    byte[] encVal = c.doFinal(message1);
	    String encrypted=Base64.encodeToString(encVal, Base64.DEFAULT);
	    return encrypted;
	}

	//Decryption
	public  String decrypt(String message) throws Exception
	{
	    Cipher c = Cipher.getInstance("AES");
	    c.init(Cipher.DECRYPT_MODE, sks);
	    byte[] decordedValue = Base64.decode(message.getBytes(), Base64.DEFAULT);
	    byte[] decValue = c.doFinal(decordedValue);
	    String decryptedValue = new String(decValue);
	    String decoded=new String(Base64.decode(decryptedValue, Base64.DEFAULT));
	    return decoded;
	} 
}
