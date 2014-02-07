package com.bobotosoft.kvadrata;

public class Character {
	public String mName;
	public int mEyes;
	public int mHair;
	public int mSex;
	public int mJob;
	
	public int mStrength,mAgility,mIntelligence,mConstitution,mDexterity;
	
	
	public Character() {
		
	}

	public Character(String s){
		   		
		String[] arraychar = new String(s).split(";");
		this.mEyes = Integer.parseInt(arraychar[0]);
		this.mHair = Integer.parseInt(arraychar[1]);
		this.mSex = Integer.parseInt(arraychar[2]);
		this.mJob = Integer.parseInt(arraychar[3]);
		this.mStrength = Integer.parseInt(arraychar[4]);
		this.mAgility = Integer.parseInt(arraychar[5]);
		this.mIntelligence = Integer.parseInt(arraychar[6]);
		this.mConstitution = Integer.parseInt(arraychar[7]);
		this.mDexterity = Integer.parseInt(arraychar[8]);
		
	}

	public String getName() {
		return mName;
	}


	public void setName(String mName) {
		this.mName = mName;
	}


	public int getEyes() {
		return mEyes;
	}


	public void setEyes(int mEyes) {
		this.mEyes = mEyes;
	}


	public int getHair() {
		return mHair;
	}


	public void setHair(int mHair) {
		this.mHair = mHair;
	}


	public int getSex() {
		return mSex;
	}


	public void setSex(int mSex) {
		this.mSex = mSex;
	}


	public int getJob() {
		return mJob;
	}


	public void setJob(int mJob) {
		this.mJob = mJob;
	}


	public int getStrength() {
		return mStrength;
	}


	public void setStrength(int mStrength) {
		this.mStrength = mStrength;
	}


	public int getAgility() {
		return mAgility;
	}


	public void setAgility(int mAgility) {
		this.mAgility = mAgility;
	}


	public int getIntelligence() {
		return mIntelligence;
	}


	public void setIntelligence(int mIntelligence) {
		this.mIntelligence = mIntelligence;
	}


	public int getConstitution() {
		return mConstitution;
	}


	public void setConstitution(int mConstitution) {
		this.mConstitution = mConstitution;
	}


	public int getDexterity() {
		return mDexterity;
	}


	public void setDexterity(int mDexterity) {
		this.mDexterity = mDexterity;
	}


	@Override
	public String toString() {
		return mEyes + ";" + mHair + ";" + mSex + ";" + mJob + ";" +
				mStrength + ";" + mAgility + ";" + mIntelligence + ";" + mConstitution + ";" + mDexterity + ";" + mName;
			
	}
	
	
	
	
}
