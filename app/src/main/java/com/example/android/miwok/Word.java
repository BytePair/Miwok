package com.example.android.miwok;

public class Word {

    // Private Variables (State)
    private String mEnglishWord;
    private String mMiwokWord;
    private int mImageResourceId;
    private int mAudioResourceId;

    // Public Functions (Methods)
    public Word() {
        this.mEnglishWord = "English";
        this.mMiwokWord = "Miwok";
    }

    /**
     * Constructor for phrases with only audio file
     *
     * @param englishWord       - word in english
     * @param miwokWord         - miwok translation of the word
     * @param audioResourceId   - audio resource id of the word
     */
    public Word(String englishWord, String miwokWord, int audioResourceId) {
        this.mEnglishWord = englishWord;
        this.mMiwokWord = miwokWord;
        this.mAudioResourceId = audioResourceId;
        this.mImageResourceId = 0;
    }

    /**
     * Constructor for colors/family/numbers with image id and audio file
     *
     * @param englishWord       - word in english
     * @param miwokWord         - miwok translation of the word
     * @param imageId           - image resource id of the word
     * @param audioResourceId   - audio resource id of the word
     */
    public Word(String englishWord, String miwokWord, int imageId, int audioResourceId) {
        this.mEnglishWord = englishWord;
        this.mMiwokWord = miwokWord;
        this.mImageResourceId = imageId;
        this.mAudioResourceId = audioResourceId;
    }

    public String getEnglishWord() {
        return mEnglishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.mEnglishWord = englishWord;
    }

    public String getMiwokWord() {
        return mMiwokWord;
    }

    public void setMiwokWord(String miwokWord) {
        this.mMiwokWord = miwokWord;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        mImageResourceId = imageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public void setAudioResourceId(int audioResourceId) {
        mAudioResourceId = audioResourceId;
    }

}
