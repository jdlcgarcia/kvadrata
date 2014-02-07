package com.bobotosoft.kvadrata;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {
	private static final String TAG = "MainActivity";
	Spinner eyes, hair, sex, job;
	EditText name;
	TextView strength, constitution, dexterity, intelligence, agility;
	Character c;
	Random dice;
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		c = new Character();
		dice = new Random(System.currentTimeMillis());

		name = (EditText) findViewById(R.id.etName);
		eyes = (Spinner) findViewById(R.id.spEyes);
		hair = (Spinner) findViewById(R.id.spHair);
		sex = (Spinner) findViewById(R.id.spSex);
		job = (Spinner) findViewById(R.id.spJob);

		eyes.setOnItemSelectedListener(this);
		hair.setOnItemSelectedListener(this);
		sex.setOnItemSelectedListener(this);
		job.setOnItemSelectedListener(this);

		eyes.setSelection(dice.nextInt(eyes.getCount()));
		hair.setSelection(dice.nextInt(hair.getCount()));
		sex.setSelection(dice.nextInt(sex.getCount()));
		job.setSelection(dice.nextInt(job.getCount()));

		strength = (TextView) findViewById(R.id.tvStrength);
		constitution = (TextView) findViewById(R.id.tvConstitution);
		dexterity = (TextView) findViewById(R.id.tvDexterity);
		intelligence = (TextView) findViewById(R.id.tvInteligence);
		agility = (TextView) findViewById(R.id.tvAgility);

		c.setStrength(20 + dice.nextInt(10));
		c.setConstitution(20 + dice.nextInt(10));
		c.setDexterity(20 + dice.nextInt(10));
		c.setIntelligence(20 + dice.nextInt(10));
		c.setAgility(20 + dice.nextInt(10));
		
		
		
		strength.setText(getString(R.string.STRempty) + " = " + String.valueOf(c.getStrength()));
		constitution.setText(getString(R.string.CONempty)
				+ " = " + String.valueOf(c.getConstitution()));
		dexterity.setText(getString(R.string.DEXempty) + " = " + String.valueOf(c.getDexterity()));
		intelligence.setText(getString(R.string.INTempty)
				+ " = " + String.valueOf(c.getIntelligence()));
		agility.setText(getString(R.string.AGIempty) + " = " + String.valueOf(c.getAgility()));
		
		//image = (ImageView) findViewById(R.id.ivAvatar);
		//updateAvatar();

	}

	public void startQR(View v) {

		if (name.getText().length() > 0) {
			Intent i = new Intent(this.getApplicationContext(),
					QRActivity.class);
			i.putExtra("CHARACTER", c.toString());
			startActivity(i);
		} else {
			Toast.makeText(this.getApplicationContext(),
					R.string.tst_insertname, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.spEyes:
			c.setEyes(eyes.getSelectedItemPosition());
			break;
		case R.id.spHair:
			c.setHair(hair.getSelectedItemPosition());
			break;
		case R.id.spJob:
			c.setJob(job.getSelectedItemPosition());
			break;
		case R.id.spSex:
			c.setSex(sex.getSelectedItemPosition());
			break;
		}
		updateAvatar();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private void updateAvatar() {

		Bitmap body = BitmapFactory.decodeResource(getResources(),
				R.drawable.naked);
		Bitmap spriteeyes, spritehair, spritejob;

		String[] sourceEyes = { "redeyes", "blueyes", "greeneyes", "purpleyes",
				"blackeyes", "greyeyes", "browneyes" };
		String[] sourceHair = { "redhair", "bluehair", "greenhair",
				"purplehair", "blackhair", "whitehair", "blondehair",
				"browneyes" };
		String[] sourceJob = { "warrior", "whitewizard", "blackmage", "thief",
				"robot" };
		String[] sourceSex = { "male", "female" };

		spriteeyes = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(sourceEyes[c.getEyes()],
						"drawable", "com.bobotosoft.kvadrata"));
		spritehair = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						sourceHair[c.getHair()] + sourceSex[c.getSex()],
						"drawable", "com.bobotosoft.kvadrata"));
		spritejob = BitmapFactory.decodeResource(
				getResources(),
				getResources().getIdentifier(
						sourceJob[c.getJob()] + sourceSex[c.getSex()],
						"drawable", "com.bobotosoft.kvadrata"));

		try {
			Bitmap bmOverlay = Bitmap.createBitmap(body.getWidth(),
					body.getHeight(), body.getConfig());
			Canvas canvas = new Canvas(bmOverlay);
			canvas.drawBitmap(body, 0, 0, null);
			canvas.drawBitmap(spriteeyes, 22, 18, null);
			canvas.drawBitmap(spritehair, 22, 18, null);
			canvas.drawBitmap(spritejob, 22, 18, null);
			image.setImageBitmap(bmOverlay);

		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return;
		}
	}

}
